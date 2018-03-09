package com.auts.backstage.service;

import com.auts.backstage.model.common.PageInfo;

public interface OrderService {

	int cancelOrder(String orderNo);

	PageInfo queryOrders(String status, String startDate, String endDate, int pageNumber, int pageSize);
}
