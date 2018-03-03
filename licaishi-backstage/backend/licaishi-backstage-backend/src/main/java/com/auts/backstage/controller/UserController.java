package com.auts.backstage.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auts.backstage.model.common.PhiHomeBaseResponse;
import com.auts.backstage.model.dao.AccountModel;
import com.auts.backstage.service.UserService;

/**
 * 用户相关API
 * @author ranshao2017
 */
@RestController
@CrossOrigin
public class UserController extends SBaseController {
	
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	/**
     * 登录请求
     */
    @RequestMapping(value = "/v1/user/login", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse login(HttpServletRequest request, 
    		@RequestParam(value="username", required = true) String username, 
    		@RequestParam(value="password", required = true) String password) {
        LOGGER.info("请求参数：" + username + "-" + password);
        
        AccountModel accountMode = userService.queryLoginUser(username, password);
        if(null == accountMode){
        	LOGGER.info("Login error username or password");
            return errorResponse(90001);
        }
        return successResponse(new PhiHomeBaseResponse());
    }
    
}