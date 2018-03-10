package com.auts.backstage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auts.backstage.dao.OrderMapper;
import com.auts.backstage.model.common.PageInfo;
import com.auts.backstage.model.dao.order.OrderModel;
import com.auts.backstage.service.OrderService;
import com.github.pagehelper.PageHelper;

@Service
public class OrderSerivceImpl implements OrderService {
	@Autowired
	OrderMapper orderMapper;

	@Override
	public PageInfo queryOrders(String status, String startDate, String endDate, int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<OrderModel> list = orderMapper.queryOrders(status, startDate, endDate);
		int total = orderMapper.queryOrdersCnt(status, startDate, endDate);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNumber(pageNumber);
		pageInfo.setPageSize(pageSize);
		pageInfo.setDataList(list);
		pageInfo.setTotal(total);
		return pageInfo;
	}

	@Override
	public void orderSettle(String uid) {
		orderMapper.orderSettle(uid);
	}

	@Override
	public void orderFailure(String uid) {
		orderMapper.orderFailure(uid);
	}

	@Override
	public void orderContract(String uid) {
		orderMapper.orderContract(uid);
	}

	@Override
	public void orderSettled(String uid) {
		orderMapper.orderSettled(uid);
	}

}