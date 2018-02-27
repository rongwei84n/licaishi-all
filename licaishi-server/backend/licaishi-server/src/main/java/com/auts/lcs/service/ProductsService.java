package com.auts.lcs.service;

import java.util.List;

import com.auts.lcs.model.dao.HotProductModel;
import com.auts.lcs.model.dao.ProductModel;

public interface ProductsService {

    List<ProductModel> queryProducts(int pageNo, int pageSize, String type);
    
    List<ProductModel> queryRecommendProducts(String recommendype);
}
