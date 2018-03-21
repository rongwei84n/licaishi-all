package com.auts.lcs.controller.workshop;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auts.lcs.consts.Const;
import com.auts.lcs.controller.SBaseController;
import com.auts.lcs.model.common.PhiHomeBaseResponse;
import com.auts.lcs.model.dao.FinancerModel;
import com.auts.lcs.service.FinancerService;

/**
 *我的工作室
 * 
 * @author libing
 */
@RestController
@CrossOrigin
public class WorkshopController extends SBaseController {
    private static final Logger LOGGER = LogManager.getLogger(WorkshopController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    FinancerService financerService;

    /**
     * 查询我的工作室相关信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/Workshop/queryWorkshop", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryWorkshop(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("queryWorkshop toekn [{}]", token);
        String uid = getUidByToken(token);
        String financerUid = getFinancerUidByUid(uid);
        FinancerModel financerModel = financerService.queryFinancerByUID(financerUid);
        
        rspObj.setResult(financerModel);
        return successResponse(rspObj);
    }
    
    /**
     * 修改工作室信息
     * @param request
     * @param workshopName
     * @param workshopUrl
     * @param workshopIntro
     * @return
     */
    @RequestMapping(value = "/v1/Workshop/udpateWorkshop", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse updateWorkshop(HttpServletRequest request,
    		@RequestParam(value = "workshopName", required = false) String workshopName,
    		@RequestParam(value = "workshopUrl", required = false) String workshopUrl,
    		@RequestParam(value = "workshopIntro", required = false) String workshopIntro) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("updateWorkshop token [{}] workshopName [{}] workshopUrl [{}] workshopIntro [{}]", token, workshopName, workshopUrl, workshopIntro);
        String uid = getUidByToken(token);
        String financerUid = getFinancerUidByUid(uid);
        FinancerModel financerModel = financerService.queryFinancerByUID(financerUid);
        financerModel.setWorkshopName(workshopName);
        financerModel.setWorkshopUrl(workshopUrl);
        financerModel.setWorkshopIntro(workshopIntro);
        int result = financerService.editFinancer(financerModel);
        if (result > 0) {
        	return errorResponse(10008);
        } 
        return successResponse(rspObj);
    }
}
