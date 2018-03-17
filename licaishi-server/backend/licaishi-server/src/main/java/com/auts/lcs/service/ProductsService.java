package com.auts.lcs.service;

import java.util.List;

import com.auts.lcs.model.dao.product.ProductAttachmentModel;
import com.auts.lcs.model.dao.product.ProductModel;
import com.auts.lcs.model.dao.product.ProfitRebateModel;

public interface ProductsService {
	
	int saveProducts(ProductModel productModel, List<ProfitRebateModel> profitRebates, List<ProductAttachmentModel> productAttachments);
	
	int updateProducts(ProductModel productModel);

    List<ProductModel> queryProducts(int pageNo, int pageSize, String type,
    		String pInvestType, String pPaymentInterestType, String pSizeRatioType, 
    		String minimumAmount, String dueTime, String annualRevenue, String saleStatus, String pRabateProfitParameter, 
    		String pAnnualRevenueExpectParameter, String pCommission);
    
    int queryProductCountByPType(String type,String pInvestType, String pPaymentInterestType, String pSizeRatioType, 
    		String minimumAmount, String dueTime, String annualRevenue, String saleStatus, String pRabateProfitParameter, 
    		String pAnnualRevenueExpectParameter, String pCommission);
        
    ProductModel queryProductDetail(String pCode);
    
    ProductModel queryProductByPid(String pid);
    
    List<ProductModel> queryRecommendProducts(String recommendype);
    
    List<ProfitRebateModel> queryProfitRebateByPCode(String pCode);
    
    List<ProductAttachmentModel> queryProductAttachmentByPCode(String pCode);
}
