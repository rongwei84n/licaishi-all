package com.auts.lcs.service;

import java.util.List;

import com.auts.lcs.model.dao.order.OrderModel;

public interface OrderService {

    List<OrderModel> queryOrders(int pageNo, int pageSize, String type);
    
    OrderModel queryOrderByOrderNo(String orderNo);
    
    int saveOrder(OrderModel om);
    
    int cancelOrder(String orderNo);
}
