package com.auts.backstage.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auts.backstage.controller.SBaseController;
import com.auts.backstage.model.common.PageInfo;
import com.auts.backstage.model.common.PhiHomeBaseResponse;
import com.auts.backstage.model.dao.order.OrderModel;
import com.auts.backstage.service.OrderService;

/**
 * 订单管理API入口
 * @author libing ranshao2017
 */
@RestController
@CrossOrigin
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
    
    /**
     * 完成订单
     */
    @RequestMapping(value = "/v1/order/orderdone", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse orderDone(HttpServletRequest request,
    		@RequestParam(value = "uid", required = true) String uid) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        OrderModel order = orderService.queryOrder(uid);
        if("0".equals(order.getPayStatus())){
        	return errorResponse(100, "该订单还未打款");
        }
        if("0".equals(order.getContractStatus())){
        	return errorResponse(100, "该订单还未签订合同");
        }
        orderService.orderSettle(uid);
        return successResponse(rspObj);
    }
    
    /**
     * 订单失败
     */
    @RequestMapping(value = "/v1/order/orderfailure", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse orderFailure(HttpServletRequest request,
    		@RequestParam(value = "uid", required = true) String uid) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        orderService.orderFailure(uid);
        return successResponse(rspObj);
    }
    
    /**
     * 完成合同
     */
    @RequestMapping(value = "/v1/order/ordercontract", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse orderContract(HttpServletRequest request,
    		@RequestParam(value = "uid", required = true) String uid) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        orderService.orderContract(uid);
        return successResponse(rspObj);
    }
    
    /**
     * 完成打款
     */
    @RequestMapping(value = "/v1/order/orderpay", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse orderPay(HttpServletRequest request,
    		@RequestParam(value = "uid", required = true) String uid) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        orderService.orderPay(uid);
        return successResponse(rspObj);
    }
    
    /**
     * 完成结佣
     */
    @RequestMapping(value = "/v1/order/ordersettled", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse orderSettled(HttpServletRequest request,
    		@RequestParam(value = "uid", required = true) String uid) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        orderService.orderSettled(uid);
        return successResponse(rspObj);
    }
    
}