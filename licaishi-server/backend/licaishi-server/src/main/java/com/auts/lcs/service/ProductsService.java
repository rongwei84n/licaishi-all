package com.auts.lcs.service;

import java.util.List;

import com.auts.lcs.model.dao.product.ProductAttachmentModel;
import com.auts.lcs.model.dao.product.ProductModel;
import com.auts.lcs.model.dao.product.ProfitRebateModel;

public interface ProductsService {

    List<ProductModel> queryProducts(int pageNo, int pageSize, String type);
    
    int queryProductCountByPType(String type);
    
    ProductModel queryProductDetail(String pCode);
    
    List<ProductModel> queryRecommendProducts(String recommendype);
    
    List<ProfitRebateModel> queryProfitRebateByPCode(String pCode);
    
    List<ProductAttachmentModel> queryProductAttachmentByPCode(String pCode);
}
