package com.auts.backstage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auts.backstage.dao.AccountMapper;
import com.auts.backstage.dao.FinancerMapper;
import com.auts.backstage.model.common.PageInfo;
import com.auts.backstage.model.dao.AccountModel;
import com.auts.backstage.model.dao.FinancerModel;
import com.auts.backstage.service.FinancerService;
import com.auts.backstage.util.EntryUtils;
import com.github.pagehelper.PageHelper;

@Service
public class FinancerServiceImpl implements FinancerService{
	
	@Autowired
	FinancerMapper financerMapper;
	@Autowired
	AccountMapper accountMapper;

	@Override
	public PageInfo queryFinancerList(String nameSearch, int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<FinancerModel> list = financerMapper.queryFinancerList(nameSearch);
		int total = financerMapper.queryFinancerCount(nameSearch);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNumber(pageNumber);
		pageInfo.setPageSize(pageSize);
		pageInfo.setDataList(list);
		pageInfo.setTotal(total);
		return pageInfo;
	}

	@Override
	public void addFinancer(FinancerModel financer) {
		Date nowDate = new Date();
		financer.setCreatetime(nowDate);
		financer.setUpdatetime(nowDate);
		financerMapper.addFinancer(financer);
		
		//新增账号
		AccountModel ac = new AccountModel();
		ac.setUser_name(financer.getPhone());
		ac.setReal_name(financer.getName());
		ac.setPasswd(EntryUtils.getMd5("123456"));
		ac.setRole(1);
		ac.setStatus(0);
		ac.setCreate_time(nowDate.getTime());
		ac.setUpdate_time(nowDate.getTime());
		accountMapper.addAccount(ac);
	}

	@Override
	public void delFinancer(String uid) {
		financerMapper.delFinancer(uid);
	}

	@Override
	public void btrvFinancer(String uids) {
		String[] uidArr = uids.split(",");
		List<Integer> uidList = new ArrayList<Integer>();
		for(int i = 0; i < uidArr.length; i ++){
			uidList.add(Integer.parseInt(uidArr[i]));
		}
		financerMapper.btrvFinancer(uidList);
	}

	@Override
	public void editFinancer(FinancerModel financer) {
		financerMapper.editFinancer(financer);
	}

}