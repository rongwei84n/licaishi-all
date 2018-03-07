package com.auts.backstage.controller.product;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auts.backstage.consts.Const;
import com.auts.backstage.controller.SBaseController;
import com.auts.backstage.model.common.PhiHomeBaseResponse;
import com.auts.backstage.model.dao.product.ProductAttachmentModel;
import com.auts.backstage.model.dao.product.ProductModel;
import com.auts.backstage.model.dao.product.ProfitRebateModel;
import com.auts.backstage.model.response.Data;
import com.auts.backstage.model.response.Pager;
import com.auts.backstage.model.response.ProductResponseModel;
import com.auts.backstage.service.ProductsService;

/**
 * 产品模块
 * 
 * @author rongwei.huang
 */
@RestController
@CrossOrigin
public class ProductsController extends SBaseController {
    private static final Logger LOGGER = LogManager.getLogger(ProductsController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    ProductsService productsService;

    /**
     * 查询产品列表
     * type 02：集合信托  03集合资管 04债权基金 05股权基金 06阳光私募
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/product/list", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryProducts(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        Pager pager = null;
        String pageNo = request.getParameter(Const.PAGE_NO);
        String pageSize = request.getParameter(Const.PAGE_SIZE);
        String type = request.getParameter(Const.TYPE);
        
        LOGGER.info("queryProducts type [{}]", type);
        
        int totalCount = productsService.queryProductCountByPType(type);
        List<ProductResponseModel> productResponseList = new ArrayList<>();
        List<ProductModel> products = productsService.queryProducts(Integer.parseInt(pageNo), Integer.parseInt(pageSize), type);
        if(products!=null && !products.isEmpty()) {
        	for(ProductModel productModel : products) {
            	List<ProfitRebateModel> profitRebates =  productsService.queryProfitRebateByPCode(productModel.getpCode());
            	List<ProductAttachmentModel> productAttachments = productsService.queryProductAttachmentByPCode(productModel.getpCode());
            	
            	ProductResponseModel productResponseModel = new ProductResponseModel();
            	BeanUtils.copyProperties(productModel, productResponseModel);
            	productResponseModel.setProfitRebates(profitRebates);
            	productResponseModel.setProductAttachments(productAttachments);
            	productResponseList.add(productResponseModel);
            }
        	//分页
        	pager = genernatePager(Integer.parseInt(pageNo), Integer.parseInt(pageSize), totalCount, products.size());
        }
        
        
        Data<ProductResponseModel> data = new Data<ProductResponseModel>();
        data.setList(productResponseList);
        data.setPager(pager);
        
        rspObj.setResult(data);
        return successResponse(rspObj);
    }

    
    /**
     * 首页查询推荐产品和热门产品
     * recommendype： 1 推荐产品 2热门产品
     */
    @RequestMapping(value = "/v1/product/recommendProducts", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryRecommendProducts(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        List<ProductResponseModel> productResponseList = new ArrayList<>();
        
        String recommendType = request.getParameter(Const.RECOMMEND_TYPE);
        List<ProductModel> recommendProducts = productsService.queryRecommendProducts(recommendType);
        for(ProductModel productModel : recommendProducts) {
        	List<ProfitRebateModel> profitRebates =  productsService.queryProfitRebateByPCode(productModel.getpCode());
        	List<ProductAttachmentModel> productAttachments = productsService.queryProductAttachmentByPCode(productModel.getpCode());
        	
        	ProductResponseModel productResponseModel = new ProductResponseModel();
        	BeanUtils.copyProperties(productModel, productResponseModel);
        	productResponseModel.setProfitRebates(profitRebates);
        	productResponseModel.setProductAttachments(productAttachments);
        	productResponseList.add(productResponseModel);
        }
        
        Data<ProductResponseModel> data = new Data<ProductResponseModel>();
        data.setList(productResponseList);
        rspObj.setResult(data);
        return successResponse(rspObj);
    }
    
    @RequestMapping(value = "/v1/product/productDetail", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryProductDetail(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        List<ProductResponseModel> productResponseList = new ArrayList<>();
        
        String pCode = request.getParameter(Const.P_CODE);
        ProductModel productModel= productsService.queryProductDetail(pCode);
    	List<ProfitRebateModel> profitRebates =  productsService.queryProfitRebateByPCode(pCode);
    	List<ProductAttachmentModel> productAttachments = productsService.queryProductAttachmentByPCode(pCode);
    	ProductResponseModel productResponseModel = new ProductResponseModel();
    	BeanUtils.copyProperties(productModel, productResponseModel);
    	productResponseModel.setProfitRebates(profitRebates);
    	productResponseModel.setProductAttachments(productAttachments);
    	productResponseList.add(productResponseModel);
    	
    	Data<ProductResponseModel> data = new Data<ProductResponseModel>();
        data.setList(productResponseList);
        rspObj.setResult(data);
        return successResponse(rspObj);
    }
}
