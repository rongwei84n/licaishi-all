package com.auts.backstage.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auts.backstage.controller.SBaseController;
import com.auts.backstage.model.common.PageInfo;
import com.auts.backstage.model.common.PhiHomeBaseResponse;
import com.auts.backstage.service.OrderService;

/**
 * 订单管理API入口
 * @author libing ranshao2017
 */
@RestController
public class OrderController extends SBaseController {
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    OrderService orderService;

    /**
     * 订单查询
     */
    @RequestMapping(value = "/v1/order/orderlist", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryOrders(HttpServletRequest request,
    		@RequestParam(value="status", required=false) String status,
    		@RequestParam(value="startDate", required=false) String startDate,
    		@RequestParam(value="endDate", required=false) String endDate,
    		@RequestParam(value="pageNumber", required=true) int pageNumber,
    		@RequestParam(value="pageSize", required=true) int pageSize) {
    	PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
    	PageInfo pageInfo = orderService.queryOrders(status, startDate, endDate, pageNumber, pageSize);
    	rspObj.setResult(pageInfo);
        return successResponse(rspObj);
    }
    
    @RequestMapping(value = "/v1/order/cancelOrder", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse cancelOrder(HttpServletRequest request,
    		@RequestParam(value = "orderNo", required = false) String orderNo) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        
        LOGGER.info("cancelOrder type [{}]", orderNo);
        
        int result = orderService.cancelOrder(orderNo);
        if (result > 0) {
        	
        } else {
//            return errorRegister(String.valueOf(Const.ErrorCode.Account.REGIST_ERROR));
        }
        return successResponse(rspObj);
    }
    
}