package com.phicomm.smarthome.authservice.service;

import java.util.List;

import com.phicomm.smarthome.authservice.model.dao.HotProductModel;

public interface ProductsService {

    List<HotProductModel> queryHotProducts();
}
