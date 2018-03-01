package com.auts.lcs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auts.lcs.dao.ProductAttachmentMapper;
import com.auts.lcs.dao.ProductsMapper;
import com.auts.lcs.dao.ProfitRebateMapper;
import com.auts.lcs.model.dao.product.ProductAttachmentModel;
import com.auts.lcs.model.dao.product.ProductModel;
import com.auts.lcs.model.dao.product.ProfitRebateModel;
import com.auts.lcs.service.ProductsService;

@Service
public class ProductsImpl implements ProductsService {
    @Autowired
    ProductsMapper productsMapper;
    @Autowired
    ProfitRebateMapper profitRebateMapper;
    @Autowired
    ProductAttachmentMapper productAttachmentMapper;

    @Override
    public List<ProductModel> queryProducts(int pageNo, int pageSize, String type) {
        try {
        	int startIndex = (pageNo - 1) * pageSize;
            return productsMapper.queryProducts(startIndex, pageSize, type);
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
		return productsMapper.queryCountByPType(type);
	}
}
