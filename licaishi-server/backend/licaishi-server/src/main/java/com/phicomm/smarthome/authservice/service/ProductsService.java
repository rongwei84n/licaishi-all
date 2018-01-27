package com.phicomm.smarthome.authservice.service;

import java.util.List;

import com.phicomm.smarthome.authservice.model.dao.ProductModel;

public interface ProductsService {

    List<ProductModel> queryProducts();
}
