package com.auts.lcs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auts.lcs.dao.ProductsMapper;
import com.auts.lcs.model.dao.ProductModel;
import com.auts.lcs.service.ProductsService;

@Service
public class ProductsImpl implements ProductsService {
    @Autowired
    ProductsMapper mapper;

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
}
