package com.auts.lcs.service;

import java.util.List;

import com.auts.lcs.model.dao.product.ProductModel;
import com.auts.lcs.model.dao.product.ProfitRebateModel;

public interface ProductsService {

    List<ProductModel> queryProducts(int pageNo, int pageSize, String type);
    
    List<ProductModel> queryRecommendProducts(String recommendype);
    
    List<ProfitRebateModel> queryProfitRebateByPCode(String pCode);
}
