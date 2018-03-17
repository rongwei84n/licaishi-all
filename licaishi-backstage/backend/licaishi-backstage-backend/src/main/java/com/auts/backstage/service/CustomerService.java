package com.auts.backstage.service;

import com.auts.backstage.model.common.PageInfo;
import com.auts.backstage.model.dao.CustomerModel;

public interface CustomerService {

	void addCustomer(CustomerModel customer);

	PageInfo queryCustomerList(String nameSearch, int pageNumber, int pageSize);

	void editCustomer(CustomerModel financer);

	void delCustomer(String uid);

	void btrvCustomer(String uids);

	void handleCancel(String uid);

	void handleNormal(String uid);

}
