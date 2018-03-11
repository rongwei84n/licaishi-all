package com.auts.lcs.controller.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
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
import com.auts.lcs.model.enums.OrderStatus;
import com.auts.lcs.model.response.Data;
import com.auts.lcs.model.response.OrderResponseDto;
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
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        List<OrderModel> orders = orderService.queryOrders(Integer.parseInt(pageNo), Integer.parseInt(pageSize), type, uid);
        if(orders !=null && !orders.isEmpty()) {
        	for(OrderModel orderModel : orders) {
        		OrderResponseDto orderResponseDto = new OrderResponseDto();
        		BeanUtils.copyProperties(orderModel, orderResponseDto);
        		//查询产品简称
        		orderResponseDto.setProductShortName("大通阳明 1222 号");
        		orderResponseDto.setCustomerName("李冰帅哥");
        		orderResponseDtoList.add(orderResponseDto);
        	}
        	//分页
        	pager = genernatePager(Integer.parseInt(pageNo), Integer.parseInt(pageSize), totalCount, orders.size());
        }

        Data<OrderResponseDto> data = new Data<OrderResponseDto>();
        data.setList(orderResponseDtoList);
        data.setPager(pager);
        rspObj.setResult(data);
        return successResponse(rspObj);
    }

    @RequestMapping(value = "/v1/order/orderDetail", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryOrderDetail(HttpServletRequest request,
    		@RequestParam(value = "orderNo", required = true) String orderNo) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        OrderResponseDto orderResponseDto = new OrderResponseDto();
                   
        LOGGER.info("queryOrderDetail orderNo [{}]", orderNo);
        
        OrderModel orderModel = orderService.queryOrderByOrderNo(orderNo);
        if(orderModel !=null) {
    		BeanUtils.copyProperties(orderModel, orderResponseDto);
    		//查询产品简称
    		orderResponseDto.setProductShortName("大通阳明 1222 号");
    		orderResponseDto.setCustomerName("李冰帅哥");
        }
        rspObj.setResult(orderResponseDto);
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
    		@RequestParam(value = "productId", required = true) String productId,
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "cardId", required = true) String cardId,
            @RequestParam(value = "amount", required = true) String amount,
            @RequestParam(value = "lastPayDate", required = true) String lastPayDate,
            @RequestParam(value = "comRatio", required = true) String comRatio,
            @RequestParam(value = "proRatio", required = true) String proRatio,
            @RequestParam(value = "issuingBank", required = true) String issuingBank,
            @RequestParam(value = "bankCardNo", required = true) String bankCardNo,
            @RequestParam(value = "note", required = false) String note) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();

        LOGGER.info("预约订单接口 start pCode, userId [{}]", productId, userId);
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("queryOrders toekn [{}]", token);
        String uid = getUidByToken(token);
        //通过理财师的uid找到理财师表里的UID
        String financerUid = "10";
        OrderModel om = generateOrder(productId, userId, financerUid, cardId,amount,lastPayDate, comRatio, proRatio,issuingBank, bankCardNo);
        //todo 产品额度够不够
        int result = orderService.saveOrder(om);
        if (result < 1) {
        	return errorResponse(100);
        }

        return successResponse(rspObj);
    }
    
    private OrderModel generateOrder(String productId, String userId, String financerUid, String cardId, String amount, String lastPayDate,
    		String comRatio, String proRatio, String issuingBank, String bankCardNo) {
    	OrderModel om = new OrderModel();
    	om.setOrderNo(generateOrderNo());
    	om.setAmount(new BigDecimal(amount));
    	om.setOrderDate(new Date());
    	om.setLatestPayDate(new Date());
    	om.setFinancerUid(financerUid);
    	om.setCustomerUid("2");
    	om.setProductId(productId);
//    	om.setCommission(commission);
    	om.setComRatio(comRatio);
    	om.setProRatio(proRatio);
//    	om.setProfit(profit);
    	om.setStatus(OrderStatus.WP.getValue());
    	om.setVoucherStatus("0");
    	om.setContractStatus("0");
    	om.setIssueBank(issuingBank);
    	om.setCardNo(bankCardNo);  	
    	return om;
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

        LOGGER.info("cancelOrder orderNo [{}]", orderNo);

        int result = orderService.cancelOrder(orderNo);
        if (result < 1) {
        	return errorResponse(100);
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
