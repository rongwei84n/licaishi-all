package com.auts.backstage.service;

import java.util.List;

import com.auts.backstage.model.dao.FinancerModel;

public interface FinancerService {
	List<FinancerModel> queryFinancerList();
}