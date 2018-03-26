package com.auts.backstage.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
            	List<ProfitRebateModel> profitRebates =  queryProfitRebateByPCode(productModel.getpCode());
            	List<ProductAttachmentModel> productAttachments = queryProductAttachmentByPCode(productModel.getpCode());
            	productModel.setProfitRebates(profitRebates);
            	productModel.setProductAttachments(productAttachments);
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
		productModel.setpCode(UUID.randomUUID().toString().replaceAll("-", ""));
		productModel.setpAllSubscriptionAmount("0");
    	//设置最高预计收益率
    	List<ProfitRebateModel> profitRebates = productModel.getProfitRebates();
    	if(profitRebates != null && profitRebates.size() > 0) {
    		double maxAnnualRevenue = 0;
    		double maxCommission = 0;
    		for(ProfitRebateModel prm : profitRebates) {
    			String annualRevenue = prm.getPrExpectAnnualRevenue();
    			double annualRevenueTmp =  Double.parseDouble(annualRevenue.replaceAll("%", ""));
    			if(annualRevenueTmp > maxAnnualRevenue) {
    				maxAnnualRevenue = annualRevenueTmp;
    			}
    			
    			String prCommission = prm.getPrCommission();
    			double prCommissionTmp =  Double.parseDouble(prCommission.replaceAll("%", ""));
    			if(prCommissionTmp > maxCommission) {
    				maxCommission = prCommissionTmp;
    			}
    		}
    		productModel.setpExpectAnnualRevenue(maxAnnualRevenue+"");
    		productModel.setpCommission(maxCommission+"");
    	}
		int result = productsMapper.savaProduct(productModel);
		if(result > 0) {
			if(productModel.getProfitRebates() != null && productModel.getProfitRebates().size() > 0) {
				saveProfitRebate(productModel);
			}
			
			if(productModel.getProductAttachments() != null && productModel.getProductAttachments().size() > 0) {
				saveProductAttachment(productModel);
			}
		}
		return result;
	}
	
	private void saveProfitRebate(ProductModel productModel) {
		for(ProfitRebateModel pr : productModel.getProfitRebates()) {
			Date nowDate = new Date();
			pr.setPrCreateTime(nowDate);
			pr.setPrUpdateTime(nowDate);
			pr.setPrProductCode(productModel.getpCode());
			if(!pr.getPrExpectAnnualRevenue().contains("浮动")) {
				pr.setPrExpectAnnualRevenue(pr.getPrExpectAnnualRevenue() +"%");
			}
			pr.setPrCommission(pr.getPrCommission()+"%");
			BigDecimal rate = new BigDecimal(10000);
			if(pr.getPrEndAmount() == null) {
				String prAmountDisplay = pr.getPrStartAmount() + "万以上";
				pr.setPrAmountDisplay(prAmountDisplay);
				pr.setPrStartAmount(pr.getPrStartAmount().multiply(rate));
				pr.setPrEndAmount(BigDecimal.ZERO);
			} else {
				String prAmountDisplay = pr.getPrStartAmount() + "-" + pr.getPrEndAmount() + "万";
				pr.setPrAmountDisplay(prAmountDisplay);
				pr.setPrStartAmount(pr.getPrStartAmount().multiply(rate));
				pr.setPrEndAmount(pr.getPrEndAmount().multiply(rate));
			}
			profitRebateMapper.savaProfitRebate(pr);
		}
	}

	private void saveProductAttachment(ProductModel productModel){
		for(ProductAttachmentModel pa : productModel.getProductAttachments()) {
			pa.setPaProductCode(productModel.getpCode());
			productAttachmentMapper.savaProductAttachment(pa);
		}	
	}
	
	@Override
	public int updateProducts(ProductModel productModel) {
		Date nowDate = new Date();
		productModel.setUpdateTime(nowDate);
    	//设置最高预计收益率
    	List<ProfitRebateModel> profitRebates = productModel.getProfitRebates();
    	if(profitRebates != null && profitRebates.size() > 0) {
    		double maxAnnualRevenue = 0;
    		double maxCommission = 0;
    		for(ProfitRebateModel prm : profitRebates) {
    			String annualRevenue = prm.getPrExpectAnnualRevenue();
    			double annualRevenueTmp =  Double.parseDouble(annualRevenue.replaceAll("%", ""));
    			if(annualRevenueTmp > maxAnnualRevenue) {
    				maxAnnualRevenue = annualRevenueTmp;
    			}
    			
    			String prCommission = prm.getPrCommission();
    			double prCommissionTmp =  Double.parseDouble(prCommission.replaceAll("%", ""));
    			if(prCommissionTmp > maxCommission) {
    				maxCommission = prCommissionTmp;
    			}
    		}
    		productModel.setpExpectAnnualRevenue(maxAnnualRevenue+"");
    		productModel.setpCommission(maxCommission+"");
    	}
		int result = productsMapper.updateProduct(productModel);
		if(result > 0) {
			if(productModel.getProfitRebates() != null && productModel.getProfitRebates().size() > 0) {
				//先删除，后增加
				profitRebateMapper.delProfitRebateByPCode(productModel.getpCode());
				saveProfitRebate(productModel);
			}
			
			if(productModel.getProductAttachments() != null && productModel.getProductAttachments().size() > 0) {
				saveProductAttachment(productModel);
			}
		}
		return 0;
	}
}
