package com.auts.backstage.service;

import java.util.List;

import com.auts.backstage.model.common.PageInfo;
import com.auts.backstage.model.dao.product.ProductAttachmentModel;
import com.auts.backstage.model.dao.product.ProductModel;
import com.auts.backstage.model.dao.product.ProfitRebateModel;

public interface ProductsService {
	
	int addProduct(ProductModel productModel);
	
	int updateProducts(ProductModel productModel);

	PageInfo queryProductList(int pageNo, int pageSize, String pType);
    
    int queryProductCountByPType(String type);
    
    ProductModel queryProductDetail(String pCode);
    
    List<ProductModel> queryRecommendProducts(String recommendype);
    
    List<ProfitRebateModel> queryProfitRebateByPCode(String pCode);
    
    List<ProductAttachmentModel> queryProductAttachmentByPCode(String pCode);
}
