package com.auts.lcs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auts.lcs.dao.ProductsMapper;
import com.auts.lcs.dao.ProfitRebateMapper;
import com.auts.lcs.model.dao.product.ProductModel;
import com.auts.lcs.model.dao.product.ProfitRebateModel;
import com.auts.lcs.service.ProductsService;

@Service
public class ProductsImpl implements ProductsService {
    @Autowired
    ProductsMapper mapper;
    @Autowired
    ProfitRebateMapper profitRebateMapper;

    @Override
    public List<ProductModel> queryProducts(int pageNo, int pageSize, String type) {
        try {
            return mapper.queryProducts(pageNo, pageSize, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	@Override
	public List<ProductModel> queryRecommendProducts(String recommendype) {
		return mapper.queryRecommendProducts(recommendype);
	}

	@Override
	public List<ProfitRebateModel> queryProfitRebateByPCode(String pCode) {
		return profitRebateMapper.queryProfitRebateByPCode(pCode);
	}
}
