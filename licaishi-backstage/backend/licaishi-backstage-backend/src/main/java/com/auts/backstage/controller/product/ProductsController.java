package com.auts.backstage.controller.product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.auts.backstage.consts.Const;
import com.auts.backstage.controller.SBaseController;
import com.auts.backstage.model.common.PageInfo;
import com.auts.backstage.model.common.PhiHomeBaseResponse;
import com.auts.backstage.model.dao.product.ProductAttachmentModel;
import com.auts.backstage.model.dao.product.ProductModel;
import com.auts.backstage.model.dao.product.ProfitRebateModel;
import com.auts.backstage.model.response.Data;
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
     * 新增产品
     * @param proModalValidate
     * @return
     */
    @RequestMapping(value = "/v1/product/addProduct", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse addProduct(@RequestBody ProductModel proModalValidate) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        LOGGER.info("addProduct ProductModel [{}]", JSON.toJSONString(proModalValidate));
        if(proModalValidate.getProfitRebates() == null || proModalValidate.getProfitRebates().size() < 1) {
        	LOGGER.info("addProduct failure, profitRebates is null , [{}]", JSON.toJSONString(proModalValidate));
        	return errorResponse(10001);
        }
        try{
        	int result = productsService.addProduct(proModalValidate);
        	if(result < 1) {
        		return errorResponse(10008);
        	}
        }catch(Exception e){
        	LOGGER.error("新增产品异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
    
    /**
     * 修改产品
     * @param proModalValidate
     * @return
     */
    @RequestMapping(value = "/v1/product/editProduct", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse editProduct(@RequestBody ProductModel proModalValidate) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        LOGGER.info("editProduct ProductModel [{}]", JSON.toJSONString(proModalValidate));
        if(proModalValidate.getProfitRebates() == null || proModalValidate.getProfitRebates().size() < 1) {
        	LOGGER.info("editProduct failure, profitRebates is null , [{}]", JSON.toJSONString(proModalValidate));
        	return errorResponse(10001);
        }
        try{
        	int result = productsService.updateProducts(proModalValidate);
        	if(result < 1) {
        		return errorResponse(10008);
        	}
        }catch(Exception e){
        	LOGGER.error("修改产品异常", e);
        	return errorResponse(10001);
        }
        return successResponse(rspObj);
    }
    
    /**
     * 查询产品列表
     * type 01：集合信托  02集合资管 03债权基金 04私募
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/product/list", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryProducts(HttpServletRequest request,
    		@RequestParam(value="pType", required=true) String pType,
    		@RequestParam(value="pageNumber", required=true) int pageNumber,
    		@RequestParam(value="pageSize", required=true) int pageSize) {
    	 
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        LOGGER.info("queryProducts pType [{}]", pType);
        PageInfo pageInfo = productsService.queryProductList(pageNumber, pageSize, pType);
        rspObj.setResult(pageInfo);
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
