package com.auts.lcss.service;

import java.util.List;

import com.auts.lcss.model.dao.HotProductModel;

public interface ProductsService {

    List<HotProductModel> queryHotProducts();
}
