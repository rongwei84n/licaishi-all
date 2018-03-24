package com.auts.lcs.service;

import com.auts.lcs.model.dao.FinancerModel;

public interface FinancerService {

	FinancerModel queryFinancerByUID(String uid);
	
	void addFinancer(FinancerModel financer);

	void delFinancer(String uid);

	void btrvFinancer(String uids);

	int editFinancer(FinancerModel financer);
	
	String queryFinancerIDByUID(String userID);
}