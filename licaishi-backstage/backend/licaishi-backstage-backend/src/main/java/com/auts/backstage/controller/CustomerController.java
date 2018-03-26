package com.auts.backstage.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auts.backstage.model.common.PageInfo;
import com.auts.backstage.model.common.PhiHomeBaseResponse;
import com.auts.backstage.model.dao.CustomerModel;
import com.auts.backstage.service.CustomerService;

/**
 * 客户API
 * @author ranshao2017
 */
@RestController
@CrossOrigin
public class CustomerController extends SBaseController{
	
	private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

	public static final String DEFAULT_CHARSET = "utf-8";
	public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
	public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";
	
	@Autowired
	private CustomerService customerService;
	
	/**
     * 新增客户
     */
    @RequestMapping(value = "/v1/customer/addcustomer", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse addCustomer(HttpServletRequest request, @Validated CustomerModel customer) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        try{
        	customerService.addCustomer(customer);
        }catch(Exception e){
        	LOGGER.error("新增理客户异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
    
    /**
     * 客户管理列表数据
     */
    @RequestMapping(value = "/v1/customer/customerlist", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse customerList(HttpServletRequest request,
    		@RequestParam(value="nameSearch", required=false) String nameSearch,
    		@RequestParam(value="pageNumber", required=true) int pageNumber,
    		@RequestParam(value="pageSize", required=true) int pageSize) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        PageInfo pageInfo = customerService.queryCustomerList(nameSearch, pageNumber, pageSize);
        rspObj.setResult(pageInfo);
        return successResponse(rspObj);
    }
    
    /**
     * 修改客户
     */
    @RequestMapping(value = "/v1/customer/editcustomer", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse editCustomer(HttpServletRequest request, @Validated CustomerModel customer) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        try{
        	customerService.editCustomer(customer);
        }catch(Exception e){
        	LOGGER.error("修改客户异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
    
    /**
     * 删除客户
     */
    @RequestMapping(value = "/v1/customer/delcustomer", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse delCustomer(HttpServletRequest request, 
    		@RequestParam(value="uid", required=true) String uid) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        try{
        	customerService.delCustomer(uid);
        }catch(Exception e){
        	LOGGER.error("删除客户异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
    
    /**
     * 批量删除客户
     */
    @RequestMapping(value = "/v1/customer/btrvcustomer", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse btrvCustomer(HttpServletRequest request, 
    		@RequestParam(value="uids", required=true) String uids) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        try{
        	customerService.btrvCustomer(uids);
        }catch(Exception e){
        	LOGGER.error("批量删除客户异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
    
    /**
     * 注销客户
     */
    @RequestMapping(value = "/v1/customer/handlecancel", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse handleCancel(HttpServletRequest request, 
    		@RequestParam(value="uid", required=true) String uid) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        try{
        	customerService.handleCancel(uid);
        }catch(Exception e){
        	LOGGER.error("注销理财师异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
    
    /**
     * 启用客户
     */
    @RequestMapping(value = "/v1/customer/handlenormal", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse handleNormal(HttpServletRequest request, 
    		@RequestParam(value="uid", required=true) String uid) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        try{
        	customerService.handleNormal(uid);
        }catch(Exception e){
        	LOGGER.error("启用理财师异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }

    /**
     * 切换理财师状态
     */
    @RequestMapping(value = "/v1/customer/handleswitch", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse handleSwitch(HttpServletRequest request, 
    		@RequestParam(value="uid", required=true) String uid) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        try{
        	customerService.handleSwitch(uid);
        }catch(Exception e){
        	LOGGER.error("启用客户异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
}