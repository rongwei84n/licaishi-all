package com.auts.lcss.service;

import java.util.List;

import com.auts.lcss.model.dao.HotProductModel;
import com.auts.lcss.model.dao.ProductModel;

public interface ProductsService {

    List<ProductModel> queryHotProducts();
}
