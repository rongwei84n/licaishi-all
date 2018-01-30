package com.phicomm.smarthome.authservice.controller.account;


import com.phicomm.smarthome.authservice.controller.SBaseController;
import com.phicomm.smarthome.authservice.model.common.PhicommServerConfigModel;
import com.phicomm.smarthome.authservice.model.request.LoginRequestModel;
import com.phicomm.smarthome.authservice.model.request.RegistRequestModel;
import com.phicomm.smarthome.authservice.model.response.LoginResponseModel;
import com.phicomm.smarthome.authservice.model.response.RegistResponseModel;

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
 * @author huangrongwei
 *
 */
@RestController
public class AccountController extends SBaseController {
    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    PhicommServerConfigModel phicommServer;


    /**
     * 登录.
     */
    @RequestMapping(value = "/srv/v1/login", method = RequestMethod.POST, produces = { "application/json" })
    public LoginResponseModel login(HttpServletRequest request, @RequestBody LoginRequestModel requestModel) {
        LOGGER.info("login request");

        LoginResponseModel rsp = new LoginResponseModel();
        rsp.setAccessToken("acs");
        rsp.setAccessTokenExpire("xxx");
        rsp.setError("1");
        rsp.setRefreshTokenExpire("xx");
        rsp.setRefreshToken("xx");
        rsp.setUid("213");
        return rsp;
    }

    /**
     * 注册.
     */
    @RequestMapping(value = "/srv/v1/account", method = RequestMethod.POST, produces = { "application/json" })
    public RegistResponseModel account(HttpServletRequest request, @RequestBody RegistRequestModel requestModel) {
        LOGGER.info("regist request [{}]", requestModel);

        RegistResponseModel rsp = new RegistResponseModel();
        rsp.setError("1");
        rsp.setUid("234342");
        return rsp;
    }
}
