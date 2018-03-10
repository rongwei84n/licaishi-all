package com.auts.backstage.service;

import com.auts.backstage.model.common.PageInfo;

public interface OrderService {

	PageInfo queryOrders(String status, String startDate, String endDate, int pageNumber, int pageSize);

	/**
     * 完成打款
     */
	void orderSettle(String uid);

	void orderFailure(String uid);

	/**
	 * 完成合同
	 */
	void orderContract(String uid);

	/**
	 * 完成结佣
	 */
	void orderSettled(String uid);
}
