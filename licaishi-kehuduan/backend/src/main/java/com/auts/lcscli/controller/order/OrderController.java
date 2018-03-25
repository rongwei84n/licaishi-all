package com.auts.lcscli.controller.order;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auts.lcscli.consts.Const;
import com.auts.lcscli.controller.SBaseController;
import com.auts.lcscli.model.common.PhiHomeBaseResponse;
import com.auts.lcscli.model.dao.CustomerModel;
import com.auts.lcscli.model.dao.order.OrderModel;
import com.auts.lcscli.model.enums.OrderStatus;
import com.auts.lcscli.model.request.CreateOrderRequestModel;
import com.auts.lcscli.model.response.CustomerListResponseDto;
import com.auts.lcscli.model.response.Data;
import com.auts.lcscli.model.response.OrderResponseDto;
import com.auts.lcscli.model.response.Pager;
import com.auts.lcscli.service.CustomerService;
import com.auts.lcscli.service.OrderService;
import com.auts.lcscli.service.ProductsService;
import com.auts.lcscli.util.Base64Utils;
import com.auts.lcscli.util.DateUtils;
import com.auts.lcscli.util.StringUtil;

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

    private static final String VOUCHER_SAVE_PATH = "/root/deploy/img/voucher/";
    public static final String VOUCHER_URL_PREFIX = "http://47.97.100.240/img/voucher/";

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ProductsService productsService;

    /**
     * 订单查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/order/list", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryOrders(HttpServletRequest request,
    		@RequestParam(value = "pageNo", required = true) Integer pageNo,
    		@RequestParam(value = "pageSize", required = true) Integer pageSize,
    		@RequestParam(value = "status", required = true) String status) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        Pager pager = null;

        LOGGER.info("queryOrders pageNo [{}] pageSize [{}] status [{}]", pageNo, pageSize, status);
        String token = request.getHeader(Const.AUTHORIZATION);
        String uid = getUidByToken(token);
        String financerUid = getFinancerUidByUid(uid);
        LOGGER.info("queryOrders toekn [{}] uid [{}] financerUid [{}]", token, uid, financerUid);

        if (StringUtil.isNullOrEmpty(uid)) {
            LOGGER.info("Parsed uid is null, return");
            rspObj.setCode(Const.ErrorCode.Account.TOKEN_INVILID);
            rspObj.setMessage(Const.ErrorCode.Account.STR_TOKEN_INVILID);
            return rspObj;
        }

        int totalCount = orderService.queryOrderCountByStatus(status, financerUid);
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        List<OrderModel> orders = orderService.queryOrders(pageNo, pageSize, status, financerUid);
        if(orders !=null && !orders.isEmpty()) {
        	for(OrderModel orderModel : orders) {
        		OrderResponseDto orderResponseDto = new OrderResponseDto();
        		BeanUtils.copyProperties(orderModel, orderResponseDto);
        		//查询产品简称
        		orderResponseDto.setProductShortName(productsService.queryProductByPid(orderModel.getProductId()).getpShortName());
        		orderResponseDto.setCustomerName(customerService.queryCustomerByUid(orderModel.getCustomerUid()).getName());
        		orderResponseDtoList.add(orderResponseDto);
        	}
        	//分页
        	pager = genernatePager(pageNo, pageSize, totalCount, orders.size());
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
    		orderResponseDto.setProductShortName(productsService.queryProductByPid(orderModel.getProductId()).getpShortName());
    		orderResponseDto.setCustomerName(customerService.queryCustomerByUid(orderModel.getCustomerUid()).getName());
        }
        if (StringUtil.isNotEmpty(orderResponseDto.getVoucherPath())) {
            orderResponseDto.setVoucherPath(VOUCHER_URL_PREFIX + orderResponseDto.getVoucherPath());
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
            @RequestBody CreateOrderRequestModel requestModel) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();

        LOGGER.info("预约订单接口 start pCode, userId [{}]", requestModel.getProductId(), requestModel.getCustomerId());
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("queryOrders toekn [{}]", token);
        String uid = getUidByToken(token);
        //通过理财师的uid找到理财师表里的UID
        String financerUid = getFinancerUidByUid(uid);
        OrderModel om = generateOrder(requestModel.getProductId(), requestModel.getCustomerId(),
                financerUid,
                requestModel.getCardId(),
                requestModel.getAmount(),
                requestModel.getLastPayDate(),
                requestModel.getComRatio(),
                requestModel.getProRatio(),
                requestModel.getIssuingBank(),
                requestModel.getBankCardNo());
        //todo 产品额度够不够
        int result = orderService.saveOrder(om);
        if (result < 1) {
        	return errorResponse(100);
        }

        return successResponse(rspObj);
    }

    /**
     * 查询我的客户列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/order/queryCustomersForOrder", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryMyCustomers(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        LOGGER.info("queryCustomersForOrder start [{}]");
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("queryCustomersForOrder toekn [{}]", token);
        String uid = getUidByToken(token);

        String financerId = getFinancerUidByUid(uid);
        List<CustomerModel> customers = customerService.queryCustomerForOrder(financerId);
        List<CustomerListResponseDto> customerResponseDtoList = new ArrayList<>();
        if(customers!=null && !customers.isEmpty()) {
        	for(CustomerModel customerModel : customers) {

        		CustomerListResponseDto customerResponseDto = new CustomerListResponseDto();
            	BeanUtils.copyProperties(customerModel, customerResponseDto);
            	customerResponseDtoList.add(customerResponseDto);
            }
        }

        Data<CustomerListResponseDto> data = new Data<CustomerListResponseDto>();
        data.setList(customerResponseDtoList);

        rspObj.setResult(data);
        return successResponse(rspObj);
    }

    private OrderModel generateOrder(String productId, String customerId, String financerUid, String cardId, String amount, String lastPayDate,
    		String comRatio, String proRatio, String issuingBank, String bankCardNo) {
    	OrderModel om = new OrderModel();
    	om.setOrderNo(generateOrderNo());
    	BigDecimal amountBD = new BigDecimal(amount).multiply(new BigDecimal("10000"));
    	om.setAmount(amountBD);
    	om.setOrderDate(new Date());
    	om.setLatestPayDate(DateUtils.parseStrToDate(lastPayDate, DateUtils.DATE_FORMAT_YYYY_MM_DD));
    	om.setFinancerUid(financerUid);
    	om.setCustomerUid(customerId);
    	om.setProductId(productId);
    	om.setComRatio(comRatio);
    	BigDecimal commission = amountBD.multiply(new BigDecimal(comRatio.replace("%", ""))).divide(new BigDecimal(100));
    	om.setCommission(commission);
    	om.setProRatio(proRatio);
    	if(proRatio == null || proRatio.contains("浮动")) {
    		om.setProfit(BigDecimal.ZERO);
    	} else {
    		BigDecimal profit = amountBD.multiply(new BigDecimal(proRatio.replace("%", ""))).divide(new BigDecimal(100));
        	om.setProfit(profit);
    	}
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
            @RequestParam(value = "imgBase64", required = false) String imgBase64,
    		@RequestParam(value = "orderNo", required = false) String orderNo) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("uploadPayPhote base64 token [{}] orderNo", token, orderNo);
        String uid = getUidByToken(token);
        LOGGER.info("parsed uid [{}]", uid);
        if (StringUtil.isNullOrEmpty(uid)) {
            LOGGER.info("Parsed uid is null, return");
            rspObj.setCode(Const.ErrorCode.Account.TOKEN_INVILID);
            return rspObj;
        }

        OrderModel om = orderService.queryOrderByOrderNo(orderNo);
        if (om == null) {
            rspObj.setCode(Const.ErrorCode.COMMON_ERROR);
            return rspObj;
        }
        om.setVoucherPath(savePic(imgBase64, uid, orderNo));
        om.setVoucherStatus("1");
        int result = orderService.updateVoucher(om);
        if (result <= 0) {//更新失败
            rspObj.setCode(Const.ErrorCode.COMMON_ERROR);
            return rspObj;
        }
        return successResponse(rspObj);
    }

    private String savePic(String imageString, String uid, String orderNo) {
        byte[] buf = Base64Utils.decode(imageString);
        InputStream inputStream = new ByteArrayInputStream(buf);

        String dir = VOUCHER_SAVE_PATH;
        File file = new File(dir, uid + "-" + orderNo + "-" + System.currentTimeMillis() + ".jpg");
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getName();
    }
}
