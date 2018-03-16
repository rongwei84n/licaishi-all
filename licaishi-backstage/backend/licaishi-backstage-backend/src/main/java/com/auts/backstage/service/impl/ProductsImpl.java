package com.auts.backstage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auts.backstage.dao.ProductAttachmentMapper;
import com.auts.backstage.dao.ProductsMapper;
import com.auts.backstage.dao.ProfitRebateMapper;
import com.auts.backstage.model.common.PageInfo;
import com.auts.backstage.model.dao.FinancerModel;
import com.auts.backstage.model.dao.product.ProductAttachmentModel;
import com.auts.backstage.model.dao.product.ProductModel;
import com.auts.backstage.model.dao.product.ProfitRebateModel;
import com.auts.backstage.model.response.ProductResponseModel;
import com.auts.backstage.service.ProductsService;
import com.github.pagehelper.PageHelper;

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
    public PageInfo queryProductList(int pageNumber, int pageSize, String pType) {
		PageHelper.startPage(pageNumber, pageSize);
		
		int totalCount = queryProductCountByPType(pType);
		List<ProductModel> products = productsMapper.queryProductList(pType);
        if(products!=null && !products.isEmpty()) {
        	for(ProductModel productModel : products) {
//            	List<ProfitRebateModel> profitRebates =  queryProfitRebateByPCode(productModel.getpCode());
//            	List<ProductAttachmentModel> productAttachments = queryProductAttachmentByPCode(productModel.getpCode());
//            	productModel.setProfitRebates(profitRebates);
//            	productModel.setProductAttachments(productAttachments);
            }
        
        }
        
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNumber(pageNumber);
		pageInfo.setPageSize(pageSize);
		pageInfo.setDataList(products);
		pageInfo.setTotal(totalCount);
		return pageInfo;
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

	@Override
	public int addProduct(ProductModel productModel) {
		Date nowDate = new Date();
		productModel.setCreateTime(nowDate);
		productModel.setUpdateTime(nowDate);
		int result = productsMapper.savaProduct(productModel);
		return result;
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
