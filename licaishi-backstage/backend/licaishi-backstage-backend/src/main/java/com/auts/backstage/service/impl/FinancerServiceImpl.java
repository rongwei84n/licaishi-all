package com.auts.backstage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auts.backstage.dao.FinancerMapper;
import com.auts.backstage.model.dao.FinancerModel;
import com.auts.backstage.service.FinancerService;

@Service
public class FinancerServiceImpl implements FinancerService{
	
	@Autowired
	FinancerMapper financerMapper;

	@Override
	public List<FinancerModel> queryFinancerList() {
		return financerMapper.queryFinancerList();
	}

}
