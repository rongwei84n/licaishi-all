package com.phicomm.smarthome.authservice.controller.account;

import com.phicomm.smarthome.authservice.consts.Const;
import com.phicomm.smarthome.authservice.controller.SBaseController;
import com.phicomm.smarthome.authservice.model.common.PhicommServerConfigModel;
import com.phicomm.smarthome.authservice.model.dao.AccountModel;
import com.phicomm.smarthome.authservice.model.request.LoginRequestModel;
import com.phicomm.smarthome.authservice.model.request.PasswordRequestModel;
import com.phicomm.smarthome.authservice.model.request.RegistRequestModel;
import com.phicomm.smarthome.authservice.model.response.AccountBaseResponseModel;
import com.phicomm.smarthome.authservice.model.response.AuthorizationCodeResponseCode;
import com.phicomm.smarthome.authservice.model.response.LoginResponseModel;
import com.phicomm.smarthome.authservice.model.response.PasswordResponseModel;
import com.phicomm.smarthome.authservice.model.response.RegistResponseModel;
import com.phicomm.smarthome.authservice.service.AccountService;
import com.phicomm.smarthome.authservice.util.RegexUtils;
import com.phicomm.smarthome.authservice.util.StringUtil;
import com.phicomm.smarthome.authservice.util.UidGenerater;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户相关的Controller.
 */
@RestController
public class AccountController extends SBaseController {
    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    PhicommServerConfigModel phicommServer;

    @Autowired
    AccountService accountService;

    /**
     * 获取授权码.
     */
    @RequestMapping(value = "/v1/authorization", method = RequestMethod.GET, produces = { "application/json" })
    public AuthorizationCodeResponseCode getPushMessages(HttpServletRequest request,
            @RequestParam(value="client_id", required = false) String client_id,
            @RequestParam(value="client_secret", required = false) String client_secret,
            @RequestParam(value="redirect_uri", required = false) String redirect_uri,
            @RequestParam(value="response_type", required = false) String response_type,
            @RequestParam(value="scope", required = false) String scope)
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
            @RequestParam(value="authorizationcode", required = false) String authorizationcode,
            @RequestParam(value="phonenumber", required = true) String phonenumber,
            @RequestParam(value="verificationcode", required = true) String verificationcode) {
        LOGGER.info("verifyVerificationCode authorizationcode [{}] phonenumber [{}] verificationcode [{}]",
                authorizationcode,
                phonenumber,
                verificationcode);

        AccountBaseResponseModel rsp = new AccountBaseResponseModel();
        rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
        return rsp;
    }


    /**
     * 账户登录.
     */
    @RequestMapping(value = "/v1/login", method = RequestMethod.POST, produces = { "application/json" })
    public LoginResponseModel login(HttpServletRequest request, @RequestBody LoginRequestModel requestModel) {
        LOGGER.info("login request model [{}]", requestModel);
        if (requestModel == null) {
            LOGGER.info("Login with no request params");
            return errorLogin(String.valueOf(Const.ErrorCode.Account.LOGIN_PARA_ERROR));
        }

        String userName = requestModel.getUsername();
        String phone = requestModel.getPhonenumber();
        String passwd = requestModel.getPassword();

        if (StringUtil.isNullOrEmpty(phone) || StringUtil.isNullOrEmpty(passwd)) {
            LOGGER.info("Phone/passwd is empty phone [{}], passwd [{}]", phone, passwd);
            return errorLogin(String.valueOf(Const.ErrorCode.Account.LOGIN_PARA_ERROR));
        }

        AccountModel accountMode = accountService.loginPhone(phone, passwd);
        if (accountMode == null) {
            return errorLogin(String.valueOf(Const.ErrorCode.Account.LOGIN_PASSWORD_ERROR));
        }

        LOGGER.debug("accountMode [{}]", accountMode);

        LoginResponseModel rsp = new LoginResponseModel();
        rsp.setAccessToken("acs-" + System.currentTimeMillis());
        rsp.setAccessTokenExpire("100000");
        rsp.setError(String.valueOf(Const.ErrorCode.Account.OK));
        rsp.setRefreshTokenExpire("xx");
        rsp.setRefreshToken("xx");
        rsp.setUid(accountMode.getUid());
        return rsp;
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
    public RegistResponseModel account(HttpServletRequest request, @RequestBody RegistRequestModel requestModel) {
        LOGGER.info("regist request [{}]", requestModel);

        if (requestModel == null) {
            LOGGER.info("Register with no params");
            return errorRegister(String.valueOf(Const.ErrorCode.REQUEST_NO_PARAS));
        }

        if (StringUtil.isNullOrEmpty(requestModel.getPassword())
                || StringUtil.isNullOrEmpty(requestModel.getPhonenumber())) {
            LOGGER.info("No usename [{}] or password [{}]", requestModel.getUsername(), requestModel.getPassword());
            return errorRegister(String.valueOf(Const.ErrorCode.REQUEST_NO_PARAS));
        }

        AccountModel acModel = accountService.queryByUserPhone(requestModel.getPhonenumber());
        if (acModel != null) {
            LOGGER.info("Already registed phone [{}]", requestModel.getPhonenumber());
            return errorRegister(String.valueOf(Const.ErrorCode.Account.REGIST_ACCOUNT_EXISTS));
        }

        AccountModel model = new AccountModel();
        model.setUid(String.valueOf(UidGenerater.gen()));
        model.setUser_name(requestModel.getUsername());
        model.setPasswd(requestModel.getPassword());
        model.setReal_name("");
        model.setPhone(requestModel.getPhonenumber());
        model.setPasswd(requestModel.getPassword());
        model.setEmail(requestModel.getMailaddress());
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
            rsp.setUid(model.getUid());
        } else {
            return errorRegister(String.valueOf(Const.ErrorCode.Account.REGIST_ERROR));
        }
        return rsp;
    }

    /**
     * 检查手机号码，是否是合法的号码，是否已经注册.
     */
    @RequestMapping(value = "/v1/checkPhonenumber", method = RequestMethod.GET, produces = { "application/json" })
    public AccountBaseResponseModel checkPhoneNumber(HttpServletRequest request,
            @RequestParam(value="authorizationcode", required=false) String authorizationcode,
            @RequestParam(value="phonenumber", required = true) String phonenumber) {
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
    public PasswordResponseModel password(HttpServletRequest request, @RequestBody PasswordRequestModel requestModel) {
        LOGGER.info("Modify password request [{}]", requestModel);

        PasswordResponseModel rsp = new PasswordResponseModel();
        return rsp;
    }
}
