package com.auts.lcs.service;

import java.util.List;

import com.auts.lcs.model.dao.CustomerModel;

public interface CustomerService {

	int queryCustomerCountByFuid(String financerId);
	 
	List<CustomerModel> queryCustomerByFUID(int pageNo, int pageSize, String financerId);
	
	void addCustomer(CustomerModel customer);

	void delCustomer(String uid);

	void editCustomer(CustomerModel customer);
}