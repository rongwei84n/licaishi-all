package com.auts.lcs.controller.order;

import java.util.List;

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
import com.auts.lcs.model.dao.order.OrderModel;
import com.auts.lcs.model.response.Data;
import com.auts.lcs.model.response.Pager;
import com.auts.lcs.service.OrderService;

/**
 * 订单管理API入口
 *
 * @author libing
 *
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
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/order/list", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryOrders(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        Pager pager = null;
        String pageNo = request.getParameter(Const.PAGE_NO);
        String pageSize = request.getParameter(Const.PAGE_SIZE);
        String type = request.getParameter(Const.TYPE);
                     
        LOGGER.info("queryOrders type [{}]", type);
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("queryOrders toekn [{}]", token);
        String uid = getUidByToken(token);
        int totalCount = orderService.queryOrderCountByStatus(type, uid);
        List<OrderModel> orders = orderService.queryOrders(Integer.parseInt(pageNo), Integer.parseInt(pageSize), type, uid);
        if(orders !=null && !orders.isEmpty()) {
        	//分页
        	pager = genernatePager(Integer.parseInt(pageNo), Integer.parseInt(pageSize), totalCount, orders.size());
        }

        Data<OrderModel> data = new Data<OrderModel>();
        data.setList(orders);
        data.setPager(pager);
        rspObj.setResult(data);
        return successResponse(rspObj);
    }

    /**
     * 预约产品
     * @param request
     * @param pCode
     * @param pName
     * @param amount
     * @param orderDate
     * @return
     */
    @RequestMapping(value = "/v1/order/createOrder", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse createOrder(HttpServletRequest request,
    		@RequestParam(value = "pCode", required = false) String pCode,
            @RequestParam(value = "pName", required = true) String pName,
            @RequestParam(value = "amount", required = false) String amount,
            @RequestParam(value = "orderDate", required = true) String orderDate) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();

        LOGGER.info("createOrder pCode [{}]", pCode);

        OrderModel om = new OrderModel();
        //todo 产品额度够不够
        int result = orderService.saveOrder(om);
        if (result < 1) {
        	return errorResponse(100);
        }

        return successResponse(rspObj);
    }

    /**
     * 取消订单
     *
     * @param request
     * @param orderNo
     * @return
     */
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

    /**
     * 上传支付凭证
     * @param request
     * @param orderNo
     * @return
     */
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
