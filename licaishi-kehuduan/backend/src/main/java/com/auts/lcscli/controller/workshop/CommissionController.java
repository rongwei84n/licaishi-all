package com.auts.lcscli.controller.workshop;

import java.util.ArrayList;
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

import com.auts.lcscli.consts.Const;
import com.auts.lcscli.controller.SBaseController;
import com.auts.lcscli.model.common.PhiHomeBaseResponse;
import com.auts.lcscli.model.dao.CustomerModel;
import com.auts.lcscli.model.dao.order.OrderModel;
import com.auts.lcscli.model.dao.product.ProductModel;
import com.auts.lcscli.model.enums.OrderStatus;
import com.auts.lcscli.model.response.CommissionOrderResponseDto;
import com.auts.lcscli.model.response.CommissionResponseDto;
import com.auts.lcscli.model.response.Data;
import com.auts.lcscli.model.response.Pager;
import com.auts.lcscli.service.CustomerService;
import com.auts.lcscli.service.OrderService;
import com.auts.lcscli.service.ProductsService;
import com.auts.lcscli.util.StringUtil;

/**
 * 工作室，我的佣金相关功能
 *
 * @author libing
 *
 */
@RestController
@CrossOrigin
public class CommissionController extends SBaseController {
    private static final Logger LOGGER = LogManager.getLogger(CommissionController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    OrderService orderService;
    @Autowired
    ProductsService productsService;
    @Autowired
    CustomerService customerService;

    /**
     * 根据理财师ID查询理财师的所有佣金
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/workshop/queryCommission", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryCommission(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        CommissionResponseDto commissionResponseDto = new CommissionResponseDto();

        LOGGER.info("queryCommission start");
        String token = request.getHeader(Const.AUTHORIZATION);
        String uid = getUidByToken(token);
        String financerId = getFinancerUidByUid(uid);
        LOGGER.info("queryCommission toekn [{}] uid [{}] financerId [{}]", token, uid, financerId);

        if (StringUtil.isNullOrEmpty(uid)) {
            LOGGER.info("Parsed uid is null, return");
            rspObj.setCode(Const.ErrorCode.Account.TOKEN_INVILID);
            rspObj.setMessage(Const.ErrorCode.Account.STR_TOKEN_INVILID);
            return rspObj;
        }
  
        //累计佣金
        List<String> allStatus = new ArrayList<>();
        allStatus.add(OrderStatus.WC.getValue());
        allStatus.add(OrderStatus.OC.getValue());
        String allComms = orderService.queryCommissinByFinancerId(financerId, allStatus);
        //待佣金
        List<String> wcStatus = new ArrayList<>();
        wcStatus.add(OrderStatus.WC.getValue());
        String wcCommission = orderService.queryCommissinByFinancerId(financerId, wcStatus);
        //已佣金
        List<String> ocStatus = new ArrayList<>();
        ocStatus.add(OrderStatus.OC.getValue());
        String ocCommission = orderService.queryCommissinByFinancerId(financerId, ocStatus);
        commissionResponseDto.setSumCommission(allComms);
        commissionResponseDto.setWcCommission(wcCommission);
        commissionResponseDto.setOcCommission(ocCommission);

        rspObj.setResult(commissionResponseDto);
        return successResponse(rspObj);
    }

    /**
     * 根据理财师ID查询客户所有订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/workshop/queryOrdersByfinancerId", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryOrdersByfinancerId(HttpServletRequest request,
    		@RequestParam(value = "pageNo", required = true) String pageNo,
            @RequestParam(value = "pageSize", required = true) String pageSize) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        Pager pager = null;

//        LOGGER.info("queryOrdersByfinancerId financerId [{}]", financerId);
        String token = request.getHeader(Const.AUTHORIZATION);
        String uid = getUidByToken(token);
        String financerId = getFinancerUidByUid(uid);
        LOGGER.info("queryOrdersByfinancerId toekn [{}] uid [{}] financerId [{}]", token, uid, financerId);
        
        int totalCount = orderService.queryOrderCountByFinancerId(financerId);
        List<CommissionOrderResponseDto> orderResponseDtoList = new ArrayList<>();
        List<OrderModel> orders = orderService.queryOrdersByFinancerId(Integer.parseInt(pageNo), Integer.parseInt(pageSize), financerId);
        LOGGER.info("queryOrdersByfinancerId result [{}]", orders.size());
        if(orders !=null && !orders.isEmpty()) {
        	for(OrderModel orderModel : orders) {
        		CommissionOrderResponseDto orderResponseDto = new CommissionOrderResponseDto();
        		BeanUtils.copyProperties(orderModel, orderResponseDto);
        		//查询产品简称
        		ProductModel productModel = productsService.queryProductByPid(orderModel.getProductId());
        		orderResponseDto.setProductShortName(productModel.getpShortName());
        		CustomerModel customerModel = customerService.queryCustomerByUid(orderModel.getCustomerUid());
        		orderResponseDto.setCustomerName(customerModel.getName());
        		orderResponseDtoList.add(orderResponseDto);
        	}
        	LOGGER.info("queryOrdersByfinancerId result [{}]", orderResponseDtoList.size());
        	//分页
        	pager = genernatePager(Integer.parseInt(pageNo), Integer.parseInt(pageSize), totalCount, orders.size());
        }

        Data<CommissionOrderResponseDto> data = new Data<CommissionOrderResponseDto>();
        data.setList(orderResponseDtoList);
        data.setPager(pager);
        rspObj.setResult(data);
        LOGGER.info("queryOrdersByfinancerId result [{}]", successResponse(rspObj));
        return successResponse(rspObj);
    }
}
