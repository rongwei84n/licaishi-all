package com.auts.lcs.controller.account;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.auts.lcs.api.YPSmsApi;
import com.auts.lcs.consts.Const;
import com.auts.lcs.controller.SBaseController;
import com.auts.lcs.model.common.PhiHomeBaseResponse;
import com.auts.lcs.model.dao.AccountModel;
import com.auts.lcs.model.dao.CaptchaModel;
import com.auts.lcs.model.dao.TokenModel;
import com.auts.lcs.model.request.PropertyChangeRequestModel;
import com.auts.lcs.model.response.AccountBaseResponseModel;
import com.auts.lcs.model.response.AccountDetailResponseModel;
import com.auts.lcs.model.response.AuthorizationCodeResponseCode;
import com.auts.lcs.model.response.LoginResponseModel;
import com.auts.lcs.model.response.PasswordResponseModel;
import com.auts.lcs.model.response.PropertyChangeResponseModel;
import com.auts.lcs.model.response.RegistResponseModel;
import com.auts.lcs.service.AccountService;
import com.auts.lcs.service.CaptchaService;
import com.auts.lcs.util.Base64Utils;
import com.auts.lcs.util.MyResponseutils;
import com.auts.lcs.util.RegexUtils;
import com.auts.lcs.util.StringUtil;

/**
 * 账户相关的Controller.
 */
@RestController
@CrossOrigin
public class AccountController extends SBaseController {
    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    AccountService accountService;
    @Autowired
    YPSmsApi ypSmsApi;
    @Autowired
    CaptchaService captchaService;

    private static final String AVATAR_SAVE_PATH = "/root/deploy/img/avatar/lcss";

    public static final String AVATAR_URL_PREFIX = "http://47.97.100.240/img/avatar/lcss/";

    /**
     * 获取授权码.
     */
    @RequestMapping(value = "/v1/authorization", method = RequestMethod.GET, produces = { "application/json" })
    public AuthorizationCodeResponseCode getPushMessages(HttpServletRequest request,
            @RequestParam(value = "client_id", required = false) String client_id,
            @RequestParam(value = "client_secret", required = false) String client_secret,
            @RequestParam(value = "redirect_uri", required = false) String redirect_uri,
            @RequestParam(value = "response_type", required = false) String response_type,
            @RequestParam(value = "scope", required = false) String scope)
    {
        AuthorizationCodeResponseCode rsp = new AuthorizationCodeResponseCode();
        rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
        rsp.setAuthorizationcode("lcs-gogo");
        return rsp;
    }

    /**
     * 校验短信验证码.
     */
    @RequestMapping(value = "/v1/verifyVerificationCode", method = RequestMethod.GET, produces = { "application/json" })
    public AccountBaseResponseModel verifyVerificationCode(HttpServletRequest request,
            @RequestParam(value = "authorizationcode", required = false) String authorizationcode,
            @RequestParam(value = "phonenumber", required = true) String phonenumber,
            @RequestParam(value = "verificationcode", required = true) String verificationcode) {
        LOGGER.info("verifyVerificationCode authorizationcode [{}] phonenumber [{}] verificationcode [{}]",
                authorizationcode,
                phonenumber,
                verificationcode);

        AccountBaseResponseModel rsp = new AccountBaseResponseModel();
        CaptchaModel captchaModel = captchaService.queryCaptchaByPhoneNo(phonenumber);
        //暂定3分钟过期
        long totalSeconds = System.currentTimeMillis() / 1000;
        if(captchaModel == null || (totalSeconds - 3 * 60) > captchaModel.getSendTime() ) {
            LOGGER.info("if");
        	rsp.setError(String.valueOf(Const.ErrorCode.Account.REGIST_VERCODE_OVERDUE));
        	captchaService.delCaptcha(phonenumber);
        } else if(!verificationcode.equals(captchaModel.getCaptchaCode())) {
            LOGGER.info("else if");
        	rsp.setError(String.valueOf(Const.ErrorCode.Account.REGIST_VERCODE_ERROR));
        } else {
            LOGGER.info("else");
            rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
        }

        LOGGER.info("totalSeconds [{}] captchaModel.getSendTime() [{}]", totalSeconds, captchaModel.getSendTime());
        return rsp;
    }

    /**
     * 账户登录.
     */
    @RequestMapping(value = "/v1/login", method = RequestMethod.POST, produces = { "application/json" })
    public LoginResponseModel login(HttpServletRequest request,
            @RequestParam(value = "authorizationcode", required = false) String authorizationcode,
            @RequestParam(value = "mailaddress", required = false) String mailaddress,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "phonenumber", required = true) String phonenumber,
            @RequestParam(value = "username", required = false) String username) {
        LOGGER.info("login request phonenumber [{}] password [{}]", phonenumber, password);
        if (StringUtil.isNullOrEmpty(phonenumber) || StringUtil.isNullOrEmpty(password)) {
            LOGGER.info("Phone/passwd is empty phone [{}], passwd [{}]", phonenumber, password);
            return errorLogin(String.valueOf(Const.ErrorCode.Account.LOGIN_PARA_ERROR));
        }

        AccountModel accountMode = accountService.loginPhone(phonenumber, password);
        if (accountMode == null) {
            LOGGER.info("Login error username or password");
            return errorLogin(String.valueOf(Const.ErrorCode.Account.LOGIN_PASSWORD_ERROR));
        }

        LOGGER.debug("accountMode [{}]", accountMode);

        String acsToken = "acs-" + System.currentTimeMillis();
        String rfsToken = "rfs token " + System.currentTimeMillis();
        long acsTokenExpire = 23328000;
        long rfsTokenExpire = 23328000;
        //登录的时候生成access token,存入tbl_tokens数据库，以便后续使用
        TokenModel tokenModel = tokenService.getByUid(accountMode.getUid());
        if (tokenModel == null) {
            insertToken(accountMode.getUid(), acsToken,acsTokenExpire, rfsToken, rfsTokenExpire);
        } else {
            tokenModel.setUpdate_time(System.currentTimeMillis() / 1000);
            updateToken(tokenModel);
            acsToken = tokenModel.getAccess_token();
            acsTokenExpire = tokenModel.getAck_timeout();
            rfsToken = tokenModel.getRefresh_token();
            rfsTokenExpire = tokenModel.getRfs_timeout();
        }

        LoginResponseModel rsp = new LoginResponseModel();
        rsp.setAccessToken(acsToken);
        rsp.setAccessTokenExpire(String.valueOf(acsTokenExpire));
        rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
        rsp.setRefreshTokenExpire(String.valueOf(rfsTokenExpire));
        rsp.setRefreshToken(rfsToken);
        rsp.setUid(accountMode.getUid());

        return rsp;
    }

    private void insertToken(String uid, String acsToken, long acsTokenExpire, String rfsToken, long rfsTokenExpire) {
        //insert
        TokenModel tokenM = new TokenModel();
        tokenM.setAccess_token(acsToken);
        tokenM.setAck_timeout(acsTokenExpire);
        tokenM.setRefresh_token(rfsToken);
        tokenM.setRfs_timeout(rfsTokenExpire);
        tokenM.setStatus(0);
        tokenM.setUid(uid);
        long currentTime = System.currentTimeMillis() / 1000;
        tokenM.setCreate_time(currentTime);
        tokenM.setUpdate_time(currentTime);

        tokenService.insertToken(tokenM);
    }

    private void updateToken(TokenModel tokenModel) {
        //update
        tokenModel.setUpdate_time(System.currentTimeMillis()/1000);
        tokenService.updateToken(tokenModel);
    }

    private LoginResponseModel errorLogin(String errorCode) {
        LoginResponseModel error = new LoginResponseModel();
        error.setError(errorCode);
        return error;
    }

    /**
     * 注册账号.
     */
    @RequestMapping(value = "/v1/account", method = RequestMethod.POST, produces = { "application/json" })
    public RegistResponseModel account(HttpServletRequest request,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "phonenumber", required = true) String phonenumber,
            @RequestParam(value = "registersource", required = false) String registersource,
            @RequestParam(value = "verificationcode", required = true) String verificationcode) {
        LOGGER.info("regist request password [{}] phonenumber[{}] registersource [{}] verificationcode [{}]",
                password,
                phonenumber,
                registersource,
                verificationcode);

        if (StringUtil.isNullOrEmpty(password) || StringUtil.isNullOrEmpty(phonenumber)
                || StringUtil.isNullOrEmpty(verificationcode)) {
            LOGGER.info("Register with no params");
            return errorRegister(String.valueOf(Const.ErrorCode.REQUEST_NO_PARAS));
        }

        //校验验证码
        if(!checkCaptchaCode(phonenumber, verificationcode)) {
        	LOGGER.info("Register with verificationcode erro [{}]", verificationcode);
        	return errorRegister(String.valueOf(Const.ErrorCode.Account.REGIST_VERCODE_ERROR));
        }

        AccountModel acModel = accountService.queryByUserPhone(phonenumber);
        if (acModel != null) {
            LOGGER.info("Already registed phone [{}]", phonenumber);
            return errorRegister(String.valueOf(Const.ErrorCode.Account.REGIST_ACCOUNT_EXISTS));
        }

        AccountModel model = new AccountModel();
        String userName = "";
        model.setUser_name(userName);
        //注册的时候，保存md5密码，任何人都无法知道密码，防止泄漏!md5无法解密
        model.setPasswd(password);
        model.setReal_name("");
        model.setPhone(phonenumber);
        model.setEmail("");
        model.setWorkstudio("");
        model.setAvtr("");
        model.setSex(1);
        model.setRemark("测试注册接口");
        model.setRole(1);
        model.setStatus(0);
        long curTime = System.currentTimeMillis() / 1000;
        model.setCreate_time(curTime);
        model.setUpdate_time(curTime);

        int result = accountService.register(model);
        RegistResponseModel rsp = new RegistResponseModel();
        if (result > 0) {
            rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
            LOGGER.info("Regist ok uid [{}]", model.getUid());
            rsp.setUid(model.getUid());
        } else {
            return errorRegister(String.valueOf(Const.ErrorCode.Account.REGIST_ERROR));
        }
        return rsp;
    }

    /**
     * 获取验证码信息.
     */
    @RequestMapping(value = "/v1/verificationMsg", method = RequestMethod.GET, produces = { "application/json" })
    public AccountBaseResponseModel verificationMsg(HttpServletRequest request,
            @RequestParam(value = "authorizationcode", required = false) String authorizationcode,
            @RequestParam(value = "phonenumber", required = true) String phonenumber,
            @RequestParam(value = "verificationtype", required = false) String verificationtype) {
        LOGGER.info("verificationMsg authorizationcode [{}] phone [{}]", authorizationcode, phonenumber);
        AccountBaseResponseModel rsp = new AccountBaseResponseModel();
        if (StringUtil.isNullOrEmpty(phonenumber)) {
            LOGGER.info("verificationMsg with no phonenumber");
            rsp.setError(String.valueOf(Const.ErrorCode.Account.REGIST_PHONE_ERROR));
            return rsp;
        }

        //发送验证码短信
        String captchaCode = YPSmsApi.getRandCaptchaCode();
        try {
			String result = YPSmsApi.sendSms(YPSmsApi.API_KEY, String.format(YPSmsApi.CAPTCHA_TEXT, captchaCode), phonenumber);
			if(result.contains("发送成功")) {
				CaptchaModel cm = new CaptchaModel();
				cm.setPhoneNo(phonenumber);
				cm.setCaptchaCode(captchaCode);
				cm.setSendTime(System.currentTimeMillis() / 1000);
				if(captchaService.queryCaptchaByPhoneNo(phonenumber) == null) {
					captchaService.addCaptcha(cm);
				} else {
					captchaService.updateCaptcha(cm);
				}
				rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
			} else {
				rsp.setError(String.valueOf(Const.ErrorCode.Account.REGIST_ERROR));
			}
		} catch (IOException e) {
			e.printStackTrace();
			rsp.setError(String.valueOf(Const.ErrorCode.Account.REGIST_ERROR));
		}

        return rsp;
    }

    /**
     * 检查手机号码，是否是合法的号码，是否已经注册.
     */
    @RequestMapping(value = "/v1/checkPhonenumber", method = RequestMethod.GET, produces = { "application/json" })
    public AccountBaseResponseModel checkPhoneNumber(HttpServletRequest request,
            @RequestParam(value = "authorizationcode", required = false) String authorizationcode,
            @RequestParam(value = "phonenumber", required = true) String phonenumber) {
        LOGGER.info("check phone request authorizationcode [{}] phone [{}]", authorizationcode, phonenumber);

        if (StringUtil.isNullOrEmpty(phonenumber)) {
            LOGGER.info("Check phone with no phonenumber");
            AccountBaseResponseModel rsp = new AccountBaseResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.REQUEST_NO_PARAS));
        }

        AccountModel acModel = accountService.queryByUserPhone(phonenumber);
        if (acModel != null) {
            LOGGER.info("Already registed phone [{}]", phonenumber);
            AccountBaseResponseModel rsp = new AccountBaseResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.REGIST_ACCOUNT_EXISTS));
            return rsp;
        }

        boolean result = RegexUtils.checkPhone(phonenumber);

        AccountBaseResponseModel rsp = new AccountBaseResponseModel();
        if (result) {
            rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
        } else {
            rsp.setError(String.valueOf(Const.ErrorCode.Account.REGIST_PHONE_ERROR));
            return rsp;
        }
        return rsp;
    }

    private RegistResponseModel errorRegister(String errorCode) {
        RegistResponseModel rsp = new RegistResponseModel();
        rsp.setError(errorCode);
        rsp.setUid("");
        return rsp;
    }

    /**
     * 修改密码.
     */
    @RequestMapping(value = "/v1/password", method = RequestMethod.POST, produces = { "application/json" })
    public PasswordResponseModel password(HttpServletRequest request,
            @RequestParam(value="oldpassword") String oldpassword,
            @RequestParam(value="newpassword") String newpassword) {
        LOGGER.info("Modify password request oldpassword [{}] newpassword [{}]", oldpassword, newpassword);
        if (StringUtil.isNullOrEmpty(oldpassword)
                || StringUtil.isNullOrEmpty(newpassword)) {
            LOGGER.info("Modify password paras is null");
            PasswordResponseModel rsp = new PasswordResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.REQUEST_NO_PARAS));
            return rsp;
        }

        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("Modify password toekn [{}]", token);

        String uid = getUidByToken(token);
        LOGGER.info("parsed uid [{}]", uid);
        if (StringUtil.isNullOrEmpty(uid)) {
            LOGGER.info("Parsed uid is null, return");
        }

        //check old password right or not.
//        String md5OldPassword = EntryUtils.getMd5(requestModel.getOldpassword());
//        String md5NewPassword = EntryUtils.getMd5(requestModel.getNewpassword());
        AccountModel accountModel = accountService.queryByUid(uid);
        if (accountModel == null) {
            LOGGER.info("Can't find uid [{}] for token [{}]", uid, token);
            PasswordResponseModel rsp = new PasswordResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.REQUEST_NO_PARAS));
            return rsp;
        }
        if (!StringUtil.equals(oldpassword, accountModel.getPasswd())) {
            LOGGER.info("Password not equal for oldpassword [{}] uid [{}] for token [{}]", oldpassword, uid, token);
        }

        //modify password in database.
        accountModel.setUpdate_time(System.currentTimeMillis() / 1000);
        accountModel.setPasswd(newpassword);

        accountService.updateAccount(accountModel);

        PasswordResponseModel rsp = new PasswordResponseModel();
        rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
        rsp.setTokenStatus(String.valueOf(Const.ErrorCode.Account.TOKEN_OK));
        return rsp;
    }

    /**
     * 找回密码-重新设置新密码.
     * 0.表示修改成功；1.验证码错误 2.验证码过期； 4.旧密码错误；7.用户名不存在; 11. 授权码错误；23.验证码已使用；50.服务器异常
     */
    @RequestMapping(value = "/v1/forgetpassword", method = RequestMethod.POST, produces = { "application/json" })
    public AccountBaseResponseModel resetPassword(HttpServletRequest request,
            @RequestParam(value = "authorizationcode", required = false) String authorizationcode,
            @RequestParam(value = "newpassword", required = false) String newpassword,
            @RequestParam(value = "phonenumber", required = false) String phonenumber,
            @RequestParam(value = "verificationcode", required = false) String verificationcode) {
        LOGGER.info("Forget pd request authorizationcode [{}] newpassword [{}] phonenumber [{}] verificationcode [{}]",
                authorizationcode,
                newpassword,
                phonenumber,
                verificationcode);
        if (StringUtil.isNullOrEmpty(newpassword)
                || StringUtil.isNullOrEmpty(phonenumber)
                || StringUtil.isNullOrEmpty(verificationcode)) {
            LOGGER.info("Modify password params is null");
            AccountBaseResponseModel rsp = new AccountBaseResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.REQUEST_NO_PARAS));
            return rsp;
        }
        //校验验证码
        if(!checkCaptchaCode(phonenumber, verificationcode)) {
        	LOGGER.info("Modify password with verificationcode erro [{}]", verificationcode);
        	AccountBaseResponseModel rsp = new AccountBaseResponseModel();
        	rsp.setError(String.valueOf(Const.ErrorCode.Account.REGIST_VERCODE_ERROR));
        	return rsp;
        }

        AccountModel model = null;
        try {
            model = accountService.queryByUserPhone(phonenumber);
        } catch (Exception e) {
            LOGGER.info("Query databasse error", e);
            AccountBaseResponseModel rsp = new AccountBaseResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.LOGIN_ERROR));
            return rsp;
        }
        if (model == null) {
            LOGGER.info("Account is not exists [{}]", phonenumber);
            AccountBaseResponseModel rsp = new AccountBaseResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.LOGIN_USER_NOT_EXIST));
            return rsp;
        }
        model.setPasswd(newpassword);
        model.setUpdate_time(System.currentTimeMillis() / 1000);
        try {
            accountService.updateAccount(model);
        } catch (Exception e) {
            LOGGER.info("Update database error", e);
            AccountBaseResponseModel rsp = new AccountBaseResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.LOGIN_ERROR));
            return rsp;
        }

        AccountBaseResponseModel rsp = new AccountBaseResponseModel();
        rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
        return rsp;
    }

    /**
     * 获取账户详情.
     *
     */
    @RequestMapping(value = "/v1/accountDetail", method = RequestMethod.GET, produces = { "application/json" })
    public AccountDetailResponseModel accountDetail(HttpServletRequest request) {
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("Get account detail token [{}]", token);

//        token = "acs-1519122162777";
        String uid = getUidByToken(token);
        LOGGER.info("parsed uid [{}]", uid);
        if (StringUtil.isNullOrEmpty(uid)) {
            LOGGER.info("Parsed uid is null, return");
            AccountDetailResponseModel rsp = new AccountDetailResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.LOGIN_ERROR));
            return rsp;
        }

        AccountModel model = null;
        try {
            model = accountService.queryByUid(uid);
        } catch (Exception e) {
            LOGGER.info("Query databasse error", e);
            AccountDetailResponseModel rsp = new AccountDetailResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.LOGIN_ERROR));
            return rsp;
        }
        if (model == null) {
            LOGGER.info("Account is not exists [{}]", uid);
            AccountDetailResponseModel rsp = new AccountDetailResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.LOGIN_ERROR));
            return rsp;
        }

        AccountDetailResponseModel rsp = new AccountDetailResponseModel(model);
        rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
        return rsp;
    }

    /**
     * 修改账户公共属性.
     */
    @RequestMapping(value = "/v1/property", method = RequestMethod.POST, produces = { "application/json" })
    public PropertyChangeResponseModel property(HttpServletRequest request,
            @RequestParam(value = "data", required = true) String data) {
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("Property change request [{}] token [{}]", data, token);

        String uid = getUidByToken(token);
        LOGGER.info("parsed uid [{}]", uid);
        if (StringUtil.isNullOrEmpty(uid)) {
            LOGGER.info("Parsed uid is null, return");
            PropertyChangeResponseModel rsp = new PropertyChangeResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.LOGIN_ERROR));
            return rsp;
        }

        AccountModel model = null;
        try {
            model = accountService.queryByUid(uid);
        } catch (Exception e) {
            LOGGER.info("Query databasse error", e);
            PropertyChangeResponseModel rsp = new PropertyChangeResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.LOGIN_ERROR));
            return rsp;
        }
        if (model == null) {
            LOGGER.info("Account is not exists [{}]", uid);
            PropertyChangeResponseModel rsp = new PropertyChangeResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.LOGIN_ERROR));
            return rsp;
        }

        PropertyChangeRequestModel requestModel = JSON.parseObject(data, PropertyChangeRequestModel.class);

        if (StringUtil.isNotEmpty(requestModel.getNickname())) {
            model.setReal_name(requestModel.getNickname());
        }
        if (StringUtil.isNotEmpty(requestModel.getWorkstudio())) {
            model.setWorkstudio(requestModel.getWorkstudio());
        }
        accountService.updateAccount(model);
        PropertyChangeResponseModel rsp  = new PropertyChangeResponseModel();
        rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
        rsp.setToken_status(String.valueOf(Const.ErrorCode.Account.TOKEN_OK));
        return rsp;
    }

    /**
     * 通过上传头像Base64值上传头像.
     */
    @RequestMapping(value = "/v1/pic/uploadBase64", method = RequestMethod.POST, produces = { "application/json" })
    public PropertyChangeResponseModel uploadAvar(HttpServletRequest request,
            @RequestParam(value = "imgBase64", required = false) String imgBase64,
            @RequestParam(value = "type", required = false) String type) {
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("update avar base64 token [{}] type [{}]", token, type);

        String uid = getUidByToken(token);
        LOGGER.info("parsed uid [{}]", uid);
        if (StringUtil.isNullOrEmpty(uid)) {
            LOGGER.info("Parsed uid is null, return");
            PropertyChangeResponseModel rsp = new PropertyChangeResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.LOGIN_ERROR));
            return rsp;
        }

        AccountModel model = null;
        try {
            model = accountService.queryByUid(uid);
        } catch (Exception e) {
            LOGGER.info("Query databasse error", e);
            PropertyChangeResponseModel rsp = new PropertyChangeResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.LOGIN_ERROR));
            return rsp;
        }
        if (model == null) {
            LOGGER.info("Account is not exists [{}]", uid);
            PropertyChangeResponseModel rsp = new PropertyChangeResponseModel();
            rsp.setError(String.valueOf(Const.ErrorCode.Account.LOGIN_ERROR));
            return rsp;
        }
        String oldFileStr = AVATAR_SAVE_PATH + "/" + model.getAvtr();
        File oldFile = new File(oldFileStr);
        if (oldFile.exists()) {
            oldFile.delete();
        }

        model.setAvtr(savePic(imgBase64, type, uid));
        accountService.updateAccount(model);
        PropertyChangeResponseModel rsp  = new PropertyChangeResponseModel();
        rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
        rsp.setToken_status(String.valueOf(Const.ErrorCode.Account.TOKEN_OK));
        return rsp;
    }

    private String savePic(String imageString, String type, String uid) {
        byte[] buf = Base64Utils.decode(imageString);
        InputStream inputStream = new ByteArrayInputStream(buf);

        String dir = AVATAR_SAVE_PATH;
        File file = new File(dir, uid + "-" + System.currentTimeMillis() + ".jpg");
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getName();
    }

    @RequestMapping(value = "/v1/login_status", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse loginStatus(HttpServletRequest request) {
        PhiHomeBaseResponse rsp = new PhiHomeBaseResponse();
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("Property change request [{}] token [{}]", token);

        String uid = getUidByToken(token);
        LOGGER.info("parsed uid [{}]", uid);
        if (StringUtil.isNullOrEmpty(uid)) {
            LOGGER.info("Parsed uid is null, return");
            rsp.setCode(Const.ErrorCode.Account.TOKEN_INVILID);
            rsp.setMessage(MyResponseutils.parseMsg(Const.ErrorCode.Account.TOKEN_INVILID));
            return rsp;
        }
        rsp.setCode(Const.STATUS_OK);
        rsp.setMessage(MyResponseutils.parseMsg(Const.STATUS_OK));
        return rsp;
    }

    /**
     * 校验验证码 三分钟之内都有效
     * @param phonenumber
     * @param verificationcode
     * @return
     */
    private boolean checkCaptchaCode(String phonenumber, String verificationcode) {
    	boolean result = true;
    	CaptchaModel captchaModel = captchaService.queryCaptchaByPhoneNo(phonenumber);
        //暂定3分钟过期
        long totalSeconds = System.currentTimeMillis() / 1000;
        if(captchaModel == null || (totalSeconds - 3 * 60) > captchaModel.getSendTime() ) {
        	captchaService.delCaptcha(phonenumber);
        	result = false;
        } else if(!verificationcode.equals(captchaModel.getCaptchaCode())) {
        	result = false;
        }
        return result;
    }
}
