package com.auts.lcs.service;

import java.util.List;

import com.auts.lcs.model.dao.order.OrderModel;

public interface OrderService {

    List<OrderModel> queryOrders(int pageNo, int pageSize, String type, String uid);

    List<OrderModel> queryOrdersByCustomerId(int pageNo, int pageSize, String customerId, String status);

    List<OrderModel> queryOrdersByFinancerId(int pageNo, int pageSize, String financerId);

    public String queryCommissinByFinancerId(String financerId, List<String> statusList);

    OrderModel queryOrderByOrderNo(String orderNo);

    int saveOrder(OrderModel om);

    int updateVoucher(OrderModel om);

    int cancelOrder(String orderNo);

    int queryOrderCountByStatus(String status, String uid);

    int queryOrderCountByCustomerId(String customerId);

    int queryOrderCountByFinancerId(String financerId);
}
