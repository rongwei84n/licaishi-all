package com.auts.lcs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.auts.lcs.dao.ProductAttachmentMapper;
import com.auts.lcs.dao.ProductsMapper;
import com.auts.lcs.dao.ProfitRebateMapper;
import com.auts.lcs.model.dao.product.ProductAttachmentModel;
import com.auts.lcs.model.dao.product.ProductModel;
import com.auts.lcs.model.dao.product.ProfitRebateModel;
import com.auts.lcs.service.ProductsService;

@Service
@Transactional
public class ProductsImpl implements ProductsService {
    @Autowired
    ProductsMapper productsMapper;
    @Autowired
    ProfitRebateMapper profitRebateMapper;
    @Autowired
    ProductAttachmentMapper productAttachmentMapper;

    @Override
    public List<ProductModel> queryProducts(int pageNo, int pageSize, String type,
    		String pInvestType, String pPaymentInterestType, String pSizeRatioType, 
    		String minimumAmount, String dueTime, String annualRevenue, String saleStatus) {
        try {
        	int startIndex = (pageNo - 1) * pageSize;
        	if(StringUtils.isEmpty(type)) {
        		return productsMapper.queryAllProducts(startIndex, pageSize);
        	}
            return productsMapper.queryProductsByType(startIndex, pageSize, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	@Override
	public List<ProductModel> queryRecommendProducts(String recommendype) {
		return productsMapper.queryRecommendProducts(recommendype);
	}

	@Override
	public List<ProfitRebateModel> queryProfitRebateByPCode(String pCode) {
		return profitRebateMapper.queryProfitRebateByPCode(pCode);
	}

	@Override
	public List<ProductAttachmentModel> queryProductAttachmentByPCode(String pCode) {
		return productAttachmentMapper.queryProductAttachmentByPCode(pCode);
	}

	@Override
	public ProductModel queryProductDetail(String pCode) {
		return productsMapper.queryProductByPCode(pCode);
	}

	@Override
	public int queryProductCountByPType(String type) {
		if(StringUtils.isEmpty(type)) {
			return productsMapper.queryAllCount();
		}
		return productsMapper.queryCountByPType(type);
	}

	@Override
	public int saveProducts(ProductModel productModel, List<ProfitRebateModel> profitRebates, List<ProductAttachmentModel> productAttachments) {
		int result = productsMapper.savaProduct(productModel);
		if(result > 0) {
			if(profitRebates!= null && !profitRebates.isEmpty()) {
				saveProfitRebate(profitRebates);
			}
			if(productAttachments!= null && !productAttachments.isEmpty()) {
				saveProductAttachment(productAttachments);
			}
			
			return result;
		}
		return 0;
	}
	
	private void saveProfitRebate(List<ProfitRebateModel> profitRebates) {
		for(ProfitRebateModel pr : profitRebates) {
			profitRebateMapper.savaProfitRebate(pr);
		}
	}

	private void saveProductAttachment(List<ProductAttachmentModel> productAttachments){
		for(ProductAttachmentModel pa : productAttachments) {
			productAttachmentMapper.savaProductAttachment(pa);
		}	
	}
	
	@Override
	public int updateProducts(ProductModel productModel) {
		// TODO Auto-generated method stub
		return 0;
	}
}
