package com.phicomm.smarthome.authservice.controller.account;


import com.phicomm.smarthome.authservice.controller.SBaseController;
import com.phicomm.smarthome.authservice.model.common.PhicommServerConfigModel;
import com.phicomm.smarthome.authservice.model.dao.AccountModel;
import com.phicomm.smarthome.authservice.model.request.LoginRequestModel;
import com.phicomm.smarthome.authservice.model.request.PasswordRequestModel;
import com.phicomm.smarthome.authservice.model.request.RegistRequestModel;
import com.phicomm.smarthome.authservice.model.response.LoginResponseModel;
import com.phicomm.smarthome.authservice.model.response.PasswordResponseModel;
import com.phicomm.smarthome.authservice.model.response.RegistResponseModel;
import com.phicomm.smarthome.authservice.service.AccountService;
import com.phicomm.smarthome.authservice.util.StringUtil;
import com.phicomm.smarthome.authservice.util.UidGenerater;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * 登录.
     */
    @RequestMapping(value = "/srv/v1/login", method = RequestMethod.POST, produces = { "application/json" })
    public LoginResponseModel login(HttpServletRequest request, @RequestBody LoginRequestModel requestModel) {
        LOGGER.info("login request model [{}]", requestModel);
        if (requestModel == null) {
            LOGGER.info("Login with no request params");
            return errorLogin("12");
        }

        String userName = requestModel.getUsername();
        String phone = requestModel.getPhonenumber();
        String passwd = requestModel.getPassword();

        if (StringUtil.isNullOrEmpty(phone) || StringUtil.isNullOrEmpty(passwd)) {
            LOGGER.info("Phone/passwd is empty phone [{}], passwd [{}]", phone, passwd);
            return errorLogin("12");
        }

        AccountModel accountMode = accountService.loginPhone(phone, passwd);
        if (accountMode == null) {
            return errorLogin("8");
        }

        LOGGER.debug("accountMode [{}]", accountMode);

        LoginResponseModel rsp = new LoginResponseModel();
        rsp.setAccessToken("acs-" + System.currentTimeMillis());
        rsp.setAccessTokenExpire("100");
        rsp.setError("0");
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
     * 注册.
     */
    @RequestMapping(value = "/srv/v1/account", method = RequestMethod.POST, produces = { "application/json" })
    public RegistResponseModel account(HttpServletRequest request, @RequestBody RegistRequestModel requestModel) {
        LOGGER.info("regist request [{}]", requestModel);

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
        rsp.setError("1");
        rsp.setUid("234342");
        return rsp;
    }

    /**
     * 修改密码.
     */
    @RequestMapping(value = "/srv/v1/password", method = RequestMethod.POST, produces = { "application/json" })
    public PasswordResponseModel password(HttpServletRequest request, @RequestBody PasswordRequestModel requestModel) {
        LOGGER.info("regist request [{}]", requestModel);

        PasswordResponseModel rsp = new PasswordResponseModel();
        return rsp;
    }
}
