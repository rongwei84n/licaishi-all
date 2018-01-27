package com.phicomm.smarthome.authservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.authservice.dao.ProductsMapper;
import com.phicomm.smarthome.authservice.model.dao.ProductModel;
import com.phicomm.smarthome.authservice.service.ProductsService;

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
