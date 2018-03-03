package com.auts.backstage.service;

import com.auts.backstage.model.common.PageInfo;
import com.auts.backstage.model.dao.FinancerModel;

public interface FinancerService {
	PageInfo queryFinancerList(String nameSearch, int pageNumber, int pageSize);

	void addFinancer(FinancerModel financer);

	void delFinancer(String uid);

	void btrvFinancer(String uids);

	void editFinancer(FinancerModel financer);
}