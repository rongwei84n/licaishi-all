package com.auts.backstage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auts.backstage.consts.Const;
import com.auts.backstage.model.common.PhiHomeBaseResponse;
import com.auts.backstage.model.dao.FinancerModel;
import com.auts.backstage.service.FinancerService;

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
     * 查询首页部分热门产品.
     */
    @RequestMapping(value = "/v1/financerlist", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse financerList(HttpServletRequest request) {
    	System.out.println("请求进入");
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        String token = request.getHeader(Const.AUTHORIZATION);
        // 通过token获取账户uid
        String uid = null;
        Object object = getUIDByToken(token);
        if (object instanceof String) {
            uid = (String) object;
        } else {
            return (PhiHomeBaseResponse) object;
        }
        LOGGER.info("financerList uid [{}]", uid);

        List<FinancerModel> financerList = financerService.queryFinancerList();
        rspObj.setResult(financerList);
        return successResponse(rspObj);
    }
}