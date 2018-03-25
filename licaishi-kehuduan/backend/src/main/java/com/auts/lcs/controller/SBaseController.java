package com.auts.lcs.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.auts.lcs.consts.Const;
import com.auts.lcs.model.common.PhiHomeBaseResponse;
import com.auts.lcs.model.common.PhicommAccountDetailModel;
import com.auts.lcs.model.dao.TokenModel;
import com.auts.lcs.model.response.Pager;
import com.auts.lcs.page.Page;
import com.auts.lcs.service.FinancerService;
import com.auts.lcs.service.TokenService;
import com.auts.lcs.util.MyResponseutils;
import com.auts.lcs.util.PhicommHttpsClient;
import com.auts.lcs.util.StringUtil;

/**
 * Phihome Controller基类, 提供了一些返回的基本功能.
 *
 * @author huangrongwei
 *
 */
public abstract class SBaseController {

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    private static Logger logger = LogManager.getLogger(SBaseController.class);

//    private RestTemplate restTemplate = new RestTemplate();

//    @Autowired
//    private EurekaClient discoveryClient;

    @Autowired
    protected TokenService tokenService;
    @Autowired
    protected FinancerService financerService;

    public static PhiHomeBaseResponse geResponse(Object result) {
        PhiHomeBaseResponse smartHomeResponseT = new PhiHomeBaseResponse();
        smartHomeResponseT.setResult(result);
        return smartHomeResponseT;
    }

    protected PhiHomeBaseResponse errorResponse(int errCode) {
        String errMsg = MyResponseutils.parseMsg(errCode);
        return errorResponse(errCode, errMsg);
    }

    protected PhiHomeBaseResponse errorResponse(int errCode, String errMsg) {
        PhiHomeBaseResponse response = geResponse(null);
        response.setCode(errCode);
        if (StringUtil.isNullOrEmpty(errMsg)) {
            response.setMessage(MyResponseutils.parseMsg(errCode));
        } else {
            response.setMessage(errMsg);
        }
        return response;
    }

    protected PhiHomeBaseResponse successResponse(Object obj) {
        PhiHomeBaseResponse response = (PhiHomeBaseResponse) obj;
        response.setCode(Const.STATUS_OK);
        response.setMessage(MyResponseutils.parseMsg(Const.STATUS_OK));
        return response;
    }

    protected String getUidByToken(String token) {
        if (StringUtil.isNullOrEmpty(token)) {
            return "";
        }
        token = token.replace(Const.TOKEN_PREFIX, "").trim();

        TokenModel model = tokenService.getByToken(token);
        if (model == null) {
            return "";
        } else {
            return model.getUid();
        }
    }

    protected Object getUIDByToken(String token) {
        logger.info("getUIDByToken token: " + token);
        return "";
//        if (StringUtil.isNullOrEmpty(token)) {
//            logger.error("No token in request");
//            return errorResponse(Const.STATUS_NO_TOKEN_IN_REQUEST);
//        }
//        token = token.replace("Bearer ", "");
//        // 通过token获取斐讯云uid
//        PhicommAccountDetailModel parsedObj = getPhicommAccountByToken(token);
//        if (parsedObj == null) {
//            logger.error("Failed to get phicomm account parsedObj null");
//            return errorResponse(Const.STATUS_FAILED_GET_PHICOMM_ACCOUNT);
//        }
//        if (StringUtil.equals(parsedObj.getError(), "5")) {
//            logger.error("token is invalid.");
//            return errorResponse(Const.STATUS_TOKEN_INVALID);
//        } else if (StringUtil.equals(parsedObj.getError(), "30")) {
//            logger.error("account logined by other phone.");
//            String reason = String.format("{\"reason\":\"%s\"}", parsedObj.getReason());
//            Object obj = JSON.parse(reason);
//            PhiHomeBaseResponse response = errorResponse(Const.STATUS_LOGIN_BY_OTHER_PHIONE);
//            response.setResult(obj);
//            return response;
//        } else if (StringUtil.equals(parsedObj.getError(), "16")) {
//            logger.error("No account detail.");
//            return errorResponse(Const.STATUS_NO_ACCOUNT_DETAIL);
//        }
//
//        if (!StringUtil.equals(parsedObj.getError(), "0")) { // 根据token获取账户信息失败
//            logger.error("Failed to get phicomm account uid null");
//            return errorResponse(Const.STATUS_FAILED_GET_PHICOMM_ACCOUNT);
//        }
//
//        String uid = parsedObj.getData().getUid();
//        if (StringUtil.isNullOrEmpty(uid)) {
//            logger.error("Failed to get phicomm account uid null");
//            return errorResponse(Const.STATUS_FAILED_GET_PHICOMM_ACCOUNT);
//        }
//        logger.info("uid: {}", uid);
//        return uid;
    }

    protected PhicommAccountDetailModel getPhicommAccountByToken(String token) {
        PhicommAccountDetailModel parsedObj = null;
        try {
            String accountJson = getPhicommAccountByToken(Const.ThirdPart.PHICOMM_ACCOUNT_URL, token);
            logger.debug("accountJson: " + accountJson);
            if (StringUtil.isNotEmpty(accountJson)) {
                parsedObj = JSON.parseObject(accountJson, PhicommAccountDetailModel.class);
            }
        } catch (Exception e) {
            logger.error("failed to get phicomm account ", e);
        }

        return parsedObj;
    }

    private String getPhicommAccountByToken(String phicommCloudServer, String token) {
        Map<String, String> headerParas = new HashMap<String, String>(1);
        headerParas.put(HTTP_HEAD_AUTHORIZATION, token);
        try {
            String accountJson = PhicommHttpsClient.httpsGet(phicommCloudServer,
                    DEFAULT_CHARSET,
                    headerParas);
            logger.info("accountJson [{}]", accountJson);
            return accountJson;
        } catch (Exception e) {
            logger.error("get phicomm account error: ", e);
        }
        return null;
    }
    
    protected Pager genernatePager(int pageNo, int pageSize, int totalCount, int pageCount) {
    	Pager pager = new Pager(pageNo, pageSize, pageCount, totalCount);
    	Page page = getPage(pageNo, pageSize, totalCount);
    	pager.setHasNaxtPage(page.isHasNextPage());
    	pager.setHasPrePage(page.isHasPrePage());
    	pager.setPrePage(page.getPrePage());
    	if(page.isHasNextPage()) {
    		pager.setNextPage(page.getNextPage());
    	}
    	pager.setStartIndex((pageNo - 1) * pageSize);
    	
    	return pager;
    }
    
    private Page getPage(int pageNo, int pageSize, int totalCount) {
    	Page page = new Page(pageNo, pageSize, totalCount);
    	return page;
    }

    //orderCode=20180228151534729942
    protected String generateOrderNo() {
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");//设置日期格式
    	String orderNo = String.format("%s%s", df.format(new Date()), new Random().nextInt(100));//订单编号
    	
    	return orderNo; 	
    }
    
    protected String getFinancerUidByUid(String userID) {
        if (StringUtil.isNullOrEmpty(userID)) {
            return "";
        }
        
        return financerService.queryFinancerIDByUID(userID);
    }
}
