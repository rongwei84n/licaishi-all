package com.auts.lcs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auts.lcs.dao.OrderMapper;
import com.auts.lcs.model.dao.order.OrderModel;
import com.auts.lcs.service.OrderService;

@Service
public class OrderSerivceImpl implements OrderService {
	@Autowired
	OrderMapper orderMapper;

	@Override
	public List<OrderModel> queryOrders(int pageNo, int pageSize, String type) {
		return orderMapper.queryOrders(pageNo, pageSize, type);
	}

	@Override
	public OrderModel queryOrderByOrderNo(String orderNo) {
		return orderMapper.queryOrderByOrderNo(orderNo);
	}

	@Override
	public int saveOrder(OrderModel om) {
		return orderMapper.saveOrder(om);
	}

	@Override
	public int cancelOrder(String orderNo) {
		return orderMapper.cancelOrder(orderNo);
	}


}
