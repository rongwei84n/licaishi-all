package com.auts.lcs.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auts.lcs.dao.CustomerMapper;
import com.auts.lcs.model.dao.CustomerModel;
import com.auts.lcs.service.CustomerService;


@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerMapper customerMapper;

	@Override
	public List<CustomerModel> queryCustomerByFUID(int pageNo, int pageSize, String financerId) {
		int startIndex = (pageNo - 1) * pageSize;
		return customerMapper.queryCustomerByFUID(startIndex, pageSize, financerId);
	}

	@Override
	public void addCustomer(CustomerModel customer) {
		Date nowDate = new Date();
		customer.setCreatetime(nowDate);
		customer.setUpdatetime(nowDate);
		customerMapper.addCustomer(customer);
	}

	@Override
	public void delCustomer(String uid) {
		customerMapper.delCustomer(uid);
	}

	@Override
	public void editCustomer(CustomerModel customer) {
		
	}

	@Override
	public int queryCustomerCountByFuid(String financerId) {
		return customerMapper.queryCustomerCountByFuid(financerId);
	}

	@Override
	public List<CustomerModel> queryCustomerForOrder(String financerId) {
		return customerMapper.queryCustomerForOrder(financerId);
	}

}