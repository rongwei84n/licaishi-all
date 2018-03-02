package com.auts.lcs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.auts.lcs.dao.OrderMapper;
import com.auts.lcs.model.dao.order.OrderModel;
import com.auts.lcs.service.OrderService;

@Service
public class OrderSerivceImpl implements OrderService {
	@Autowired
	OrderMapper orderMapper;

	@Override
	public List<OrderModel> queryOrders(int pageNo, int pageSize, String type, String uid) {
		int startIndex = (pageNo - 1) * pageSize;
		return orderMapper.queryOrders(startIndex, pageSize, type, uid);
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

	@Override
	public int queryOrderCountByStatus(String status, String uid) {
		if(StringUtils.isEmpty(status)) {
			status = null;
		}
		return orderMapper.queryOrderCountByStatus(status, uid);
	}


}
