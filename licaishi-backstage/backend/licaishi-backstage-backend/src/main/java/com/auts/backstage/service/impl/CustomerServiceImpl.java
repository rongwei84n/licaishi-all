package com.auts.backstage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auts.backstage.dao.AccountMapper;
import com.auts.backstage.dao.CustomerMapper;
import com.auts.backstage.model.common.PageInfo;
import com.auts.backstage.model.dao.AccountModel;
import com.auts.backstage.model.dao.CustomerModel;
import com.auts.backstage.service.CustomerService;
import com.auts.backstage.util.EntryUtils;
import com.github.pagehelper.PageHelper;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private AccountMapper accountMapper;

	@Override
	@Transactional
	public void addCustomer(CustomerModel customer) {
		Date nowDate = new Date();
		
		//新增账号
		AccountModel ac = new AccountModel();
		ac.setUser_name(customer.getPhone());
		ac.setReal_name(customer.getName());
		ac.setPasswd(EntryUtils.getMd5("123456"));
		ac.setPhone(customer.getPhone());
		ac.setRole(0);
		ac.setStatus(0);
		ac.setCreate_time(nowDate.getTime());
		ac.setUpdate_time(nowDate.getTime());
		accountMapper.addAccount(ac);
		
		customer.setCreatetime(nowDate);
		customer.setUpdatetime(nowDate);
		customer.setUserId(Integer.parseInt(ac.getUid()));
		customerMapper.addCustomer(customer);
	}

	@Override
	public PageInfo queryCustomerList(String nameSearch, int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<CustomerModel> list = customerMapper.queryFinancerList(nameSearch);
		int total = customerMapper.queryFinancerCount(nameSearch);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNumber(pageNumber);
		pageInfo.setPageSize(pageSize);
		pageInfo.setDataList(list);
		pageInfo.setTotal(total);
		return pageInfo;
	}

	@Override
	@Transactional
	public void editCustomer(CustomerModel customer) {
		customerMapper.editFinancer(customer);
		accountMapper.updateAccountByCustomer(customer.getPhone(), customer.getName(), customer.getUserId());
	}

	@Override
	@Transactional
	public void delCustomer(String uid) {
		CustomerModel model = customerMapper.queryCustomer(uid);
		customerMapper.delCustomer(uid);
		accountMapper.deleteAccount(model.getUserId());
	}

	@Override
	@Transactional
	public void btrvCustomer(String uids) {
		String[] uidArr = uids.split(",");
		List<Integer> uidList = new ArrayList<Integer>();
		for(int i = 0; i < uidArr.length; i ++){
			uidList.add(Integer.parseInt(uidArr[i]));
		}
		customerMapper.btrvCustomer(uidList);
	}

	@Override
	@Transactional
	public void handleCancel(String uid) {
		CustomerModel model = customerMapper.queryCustomer(uid);
		accountMapper.handelCancel(model.getUserId());
	}

	@Override
	@Transactional
	public void handleNormal(String uid) {
		CustomerModel model = customerMapper.queryCustomer(uid);
		accountMapper.handleNormal(model.getUserId());
	}

	@Override
	@Transactional
	public void handleSwitch(String uid) {
		CustomerModel model = customerMapper.queryCustomer(uid);
		AccountModel am = accountMapper.queryModelByUid(model.getUserId());
		if(0 == am.getStatus()){
			accountMapper.handelCancel(Integer.parseInt(am.getUid()));
		}else{
			accountMapper.handleNormal(Integer.parseInt(am.getUid()));
		}
	}

}