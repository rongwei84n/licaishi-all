package com.auts.lcss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auts.lcss.dao.ProductsMapper;
import com.auts.lcss.model.dao.HotProductModel;
import com.auts.lcss.model.dao.ProductModel;
import com.auts.lcss.service.ProductsService;

@Service
public class ProductsImpl implements ProductsService {
    @Autowired
    ProductsMapper mapper;

    @Override
    public List<ProductModel> queryHotProducts() {
        try {
            return mapper.queryHotProducts();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
