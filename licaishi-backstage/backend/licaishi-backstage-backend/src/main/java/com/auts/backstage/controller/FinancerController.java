package com.auts.backstage.controller;

import java.util.List;
import java.util.Map;

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
import com.auts.backstage.model.dao.FinancerModel;
import com.auts.backstage.service.FinancerService;

/**
 * 理财师相关API
 * @author ranshao2017
 */
@RestController
@CrossOrigin
public class FinancerController extends SBaseController {

	private static final Logger LOGGER = LogManager.getLogger(FinancerController.class);

	public static final String DEFAULT_CHARSET = "utf-8";
	public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
	public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";
	
	@Autowired
	private FinancerService financerService;
	
	/**
     * 理财师管理列表数据
     */
    @RequestMapping(value = "/v1/financer/financerlist", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse financerList(HttpServletRequest request,
    		@RequestParam(value="nameSearch", required=false) String nameSearch,
    		@RequestParam(value="pageNumber", required=true) int pageNumber,
    		@RequestParam(value="pageSize", required=true) int pageSize) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        PageInfo pageInfo = financerService.queryFinancerList(nameSearch, pageNumber, pageSize);
        rspObj.setResult(pageInfo);
        return successResponse(rspObj);
    }
    
	/**
     * 新增理财师
     */
    @RequestMapping(value = "/v1/financer/addfinancer", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse addFinancer(HttpServletRequest request, @Validated FinancerModel financer) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        try{
        	financerService.addFinancer(financer);
        }catch(Exception e){
        	LOGGER.error("新增理财师异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
    
	/**
     * 修改理财师
     */
    @RequestMapping(value = "/v1/financer/editfinancer", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse editFinancer(HttpServletRequest request, @Validated FinancerModel financer) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        try{
        	financerService.editFinancer(financer);
        }catch(Exception e){
        	LOGGER.error("修改理财师异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
    
    /**
     * 删除理财师
     */
    @RequestMapping(value = "/v1/financer/delfinancer", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse delFinancer(HttpServletRequest request, 
    		@RequestParam(value="uid", required=true) String uid) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        try{
        	financerService.delFinancer(uid);
        }catch(Exception e){
        	LOGGER.error("删除理财师异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
    
    /**
     * 批量删除理财师
     */
    @RequestMapping(value = "/v1/financer/btrvfinancer", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse btrvFinancer(HttpServletRequest request, 
    		@RequestParam(value="uids", required=true) String uids) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        try{
        	financerService.btrvFinancer(uids);
        }catch(Exception e){
        	LOGGER.error("批量删除理财师异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
    
    /**
     * 查询理财师uid和name
     */
    @RequestMapping(value = "/v1/financer/queryfinaasync", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryFinaAsync(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        List<Map<String, Object>> financerList = financerService.queryFinaAsync();
        rspObj.setResult(financerList);
        return successResponse(rspObj);
    }
    
    /**
     * 切换理财师状态
     */
    @RequestMapping(value = "/v1/financer/handleswitch", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse handleSwitch(HttpServletRequest request, 
    		@RequestParam(value="uid", required=true) String uid) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        try{
        	financerService.handleSwitch(uid);
        }catch(Exception e){
        	LOGGER.error("启用理财师异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
}