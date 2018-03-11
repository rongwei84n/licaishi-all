package com.auts.lcs.controller.workshop;

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

import com.auts.lcs.consts.Const;
import com.auts.lcs.controller.SBaseController;
import com.auts.lcs.model.common.PhiHomeBaseResponse;
import com.auts.lcs.model.dao.CustomerModel;
import com.auts.lcs.model.dao.order.OrderModel;
import com.auts.lcs.model.enums.OrderStatus;
import com.auts.lcs.model.response.AllInfoCountResponse;
import com.auts.lcs.model.response.CustomerResponseDto;
import com.auts.lcs.model.response.Data;
import com.auts.lcs.model.response.OrderResponseDto;
import com.auts.lcs.model.response.Pager;
import com.auts.lcs.service.CustomerService;
import com.auts.lcs.service.OrderService;

/**
 * 工作室，我的客户相关功能
 * 
 * @author libing
 *
 */
@RestController
@CrossOrigin
public class CustomerController extends SBaseController {
    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;

    /**
     * 查询我的客户列表
     * @param request
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/v1/workshop/queryMyCustomers", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryMyCustomers(HttpServletRequest request,
    		@RequestParam(value = "pageNo", required = true) String pageNo,
            @RequestParam(value = "pageSize", required = true) String pageSize) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        Pager pager = null;
        LOGGER.info("queryMyCustomers type [{}]");
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("queryOrders toekn [{}]", token);
        String uid = getUidByToken(token);

        String financerId = "10";
        int totalCount = customerService.queryCustomerCountByFuid(financerId);
        List<CustomerModel> customers = customerService.queryCustomerByFUID(Integer.parseInt(pageNo), Integer.parseInt(pageSize),financerId);
        List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        if(customers!=null && !customers.isEmpty()) {
        	for(CustomerModel customerModel : customers) {
        		
        		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
            	BeanUtils.copyProperties(customerModel, customerResponseDto);
            	int orderCounts = 10;
            	customerResponseDto.setOrderCounts(orderCounts);
            	customerResponseDtoList.add(customerResponseDto);
            }
        	//分页
        	pager = genernatePager(Integer.parseInt(pageNo), Integer.parseInt(pageSize), totalCount, customers.size());
        }

        Data<CustomerResponseDto> data = new Data<CustomerResponseDto>();
        data.setList(customerResponseDtoList);
        data.setPager(pager);

        rspObj.setResult(data);
        return successResponse(rspObj);
        //计算佣金 todo ....
//        int totalCount = orderService.queryOrderCountByStatus(null, uid);
//        int wpTotalCount = orderService.queryOrderCountByStatus(OrderStatus.WP.getValue(), uid);
//        int wcTotalCount = orderService.queryOrderCountByStatus(OrderStatus.WC.getValue(), uid);
//        int ocTotalCount = orderService.queryOrderCountByStatus(OrderStatus.OC.getValue(), uid);
//        int ofTotalCount = orderService.queryOrderCountByStatus(OrderStatus.OF.getValue(), uid);
//        allInfoCount.getStatusMap().put(OrderStatus.WP.getValue(), wpTotalCount);
//        allInfoCount.getStatusMap().put(OrderStatus.WC.getValue(), wcTotalCount);
//        allInfoCount.getStatusMap().put(OrderStatus.OC.getValue(), ocTotalCount);
//        allInfoCount.getStatusMap().put(OrderStatus.OF.getValue(), ofTotalCount);
//        allInfoCount.getStatusMap().put("ALL", totalCount);
//        rspObj.setResult(allInfoCount);
//        return successResponse(rspObj);
    }
    
    /**
     * 根据客户ID查询客户所有订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/workshop/queryOrdersByCustomerId", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryOrdersByCustomerId(HttpServletRequest request,
    		@RequestParam(value = "pageNo", required = true) String pageNo,
            @RequestParam(value = "pageSize", required = true) String pageSize,
            @RequestParam(value = "customerId", required = true) String customerId) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        Pager pager = null;
                     
        LOGGER.info("queryOrdersByCustomerId customerId [{}]", customerId);
        String token = request.getHeader(Const.AUTHORIZATION);
        LOGGER.info("queryOrdersByCustomerId toekn [{}]", token);
        String uid = getUidByToken(token);
        int totalCount = orderService.queryOrderCountByCustomerId(customerId);
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        List<OrderModel> orders = orderService.queryOrdersByCustomerId(Integer.parseInt(pageNo), Integer.parseInt(pageSize), customerId);
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
    
//    /**
//     * 查询产品列表
//     * type 01：集合信托  02集合资管 03债权基金 04股权基金 05阳光私募
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/v1/user/allInfoCount", method = RequestMethod.GET, produces = { "application/json" })
//    public PhiHomeBaseResponse queryAllInfoCount(HttpServletRequest request) {
//        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
//        AllInfoCountResponse allInfoCount = new AllInfoCountResponse();
//        LOGGER.info("queryAllInfoCount type [{}]");
//        String token = request.getHeader(Const.AUTHORIZATION);
//        LOGGER.info("queryOrders toekn [{}]", token);
//        String uid = getUidByToken(token);
//
//        //计算佣金 todo ....
//        int totalCount = orderService.queryOrderCountByStatus(null, uid);
//        int wpTotalCount = orderService.queryOrderCountByStatus(OrderStatus.WP.getValue(), uid);
//        int wcTotalCount = orderService.queryOrderCountByStatus(OrderStatus.WC.getValue(), uid);
//        int ocTotalCount = orderService.queryOrderCountByStatus(OrderStatus.OC.getValue(), uid);
//        int ofTotalCount = orderService.queryOrderCountByStatus(OrderStatus.OF.getValue(), uid);
//        allInfoCount.getStatusMap().put(OrderStatus.WP.getValue(), wpTotalCount);
//        allInfoCount.getStatusMap().put(OrderStatus.WC.getValue(), wcTotalCount);
//        allInfoCount.getStatusMap().put(OrderStatus.OC.getValue(), ocTotalCount);
//        allInfoCount.getStatusMap().put(OrderStatus.OF.getValue(), ofTotalCount);
//        allInfoCount.getStatusMap().put("ALL", totalCount);
//        rspObj.setResult(allInfoCount);
//        return successResponse(rspObj);
//    }
}
