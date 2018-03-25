package com.auts.lcscli.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auts.lcscli.dao.FinancerMapper;
import com.auts.lcscli.model.dao.FinancerModel;
import com.auts.lcscli.service.FinancerService;


@Service
public class FinancerServiceImpl implements FinancerService{
	
	@Autowired
	FinancerMapper financerMapper;


	@Override
	public void addFinancer(FinancerModel financer) {
		Date nowDate = new Date();
		financer.setCreatetime(nowDate);
		financer.setUpdatetime(nowDate);
		financerMapper.addFinancer(financer);
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
	public int editFinancer(FinancerModel financer) {
		return financerMapper.editFinancer(financer);
	}

	@Override
	public FinancerModel queryFinancerByUID(String uid) {
		return financerMapper.queryFinancerByUID(uid);
	}

	@Override
	public String queryFinancerIDByUID(String userID) {
		return financerMapper.queryFinancerIDByUserID(userID);
	}

}