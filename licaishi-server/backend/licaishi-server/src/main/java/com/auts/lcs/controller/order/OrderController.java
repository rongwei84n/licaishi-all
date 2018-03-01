package com.auts.lcs.controller.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auts.lcs.consts.Const;
import com.auts.lcs.controller.SBaseController;
import com.auts.lcs.model.common.PhiHomeBaseResponse;
import com.auts.lcs.model.dao.order.OrderModel;
import com.auts.lcs.model.response.RegistResponseModel;
import com.auts.lcs.service.OrderService;

/**
 * 订单管理API入口
 * 
 * @author libing
 *
 */
@RestController
public class OrderController extends SBaseController {
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/v1/order/list", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryOrders(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        String pageNo = request.getParameter(Const.PAGE_NO);
        String pageSize = request.getParameter(Const.PAGE_SIZE);
        String type = request.getParameter(Const.TYPE);
        
        LOGGER.info("queryOrders type [{}]", type);
        
        List<OrderModel> orders = orderService.queryOrders(1, 1, type);
        
        rspObj.setResult(orders);
        return successResponse(rspObj);
    }
    
    @RequestMapping(value = "/v1/order/createOrder", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse createOrder(HttpServletRequest request,
    		@RequestParam(value = "pCode", required = false) String pCode,
            @RequestParam(value = "pName", required = true) String pName,
            @RequestParam(value = "amount", required = false) String amount,
            @RequestParam(value = "orderDate", required = true) String orderDate) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        
//        LOGGER.info("createOrder type [{}]", type);
        
        OrderModel om = new OrderModel();
        int result = orderService.saveOrder(om);
        if (result > 0) {
        	
        } else {
//            return errorRegister(String.valueOf(Const.ErrorCode.Account.REGIST_ERROR));
        }
        return successResponse(rspObj);
    }
    
    @RequestMapping(value = "/v1/order/cancelOrder", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse cancelOrder(HttpServletRequest request,
    		@RequestParam(value = "orderNo", required = false) String orderNo) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        
        LOGGER.info("cancelOrder type [{}]", orderNo);
        
        OrderModel om = new OrderModel();
        int result = orderService.cancelOrder(orderNo);
        if (result > 0) {
        	
        } else {
//            return errorRegister(String.valueOf(Const.ErrorCode.Account.REGIST_ERROR));
        }
        return successResponse(rspObj);
    }
    
    @RequestMapping(value = "/v1/order/uploadPayPhote", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse uploadPayPhote(HttpServletRequest request,
    		@RequestParam(value = "orderNo", required = false) String orderNo) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        
        LOGGER.info("cancelOrder type [{}]", orderNo);
        
        OrderModel om = new OrderModel();
        int result = orderService.cancelOrder(orderNo);
        if (result > 0) {
        	
        } else {
//            return errorRegister(String.valueOf(Const.ErrorCode.Account.REGIST_ERROR));
        }
        return successResponse(rspObj);
    }
}
