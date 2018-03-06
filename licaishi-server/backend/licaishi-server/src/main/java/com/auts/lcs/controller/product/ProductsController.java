package com.auts.lcs.controller.product;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auts.lcs.consts.Const;
import com.auts.lcs.controller.SBaseController;
import com.auts.lcs.model.common.PhiHomeBaseResponse;
import com.auts.lcs.model.dao.product.ProductAttachmentModel;
import com.auts.lcs.model.dao.product.ProductModel;
import com.auts.lcs.model.dao.product.ProfitRebateModel;
import com.auts.lcs.model.response.Data;
import com.auts.lcs.model.response.Pager;
import com.auts.lcs.model.response.ProductDetailResponseModel;
import com.auts.lcs.model.response.ProductResponseModel;
import com.auts.lcs.service.ProductsService;

/**
 * 产品模块
 *
 * @author rongwei.huang
 */
@CrossOrigin
@RestController
public class ProductsController extends SBaseController {
    private static final Logger LOGGER = LogManager.getLogger(ProductsController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    ProductsService productsService;

    /**
     * 查询产品列表
     * type 01：集合信托  02集合资管 03债权基金 04股权基金 05阳光私募
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/product/list", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryProducts(HttpServletRequest request,
            @RequestParam(value = "pageNo", required = false) String pageNo,
            @RequestParam(value = "pageSize", required = true) String pageSize,
            @RequestParam(value = "type", required = false) String type) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        Pager pager = null;

        LOGGER.info("queryProducts pageNo [{}] pageSize [{}] type [{}]", pageNo, pageSize, type);

        int totalCount = productsService.queryProductCountByPType(type);
        List<ProductResponseModel> productResponseList = new ArrayList<>();
        List<ProductModel> products = productsService.queryProducts(Integer.parseInt(pageNo), Integer.parseInt(pageSize), type);
        if(products!=null && !products.isEmpty()) {
        	for(ProductModel productModel : products) {
            	List<ProfitRebateModel> profitRebates =  productsService.queryProfitRebateByPCode(productModel.getpCode());
//            	List<ProductAttachmentModel> productAttachments = productsService.queryProductAttachmentByPCode(productModel.getpCode());

            	ProductResponseModel productResponseModel = new ProductResponseModel();
            	BeanUtils.copyProperties(productModel, productResponseModel);
            	productResponseModel.setProfitRebates(profitRebates);
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
    @CrossOrigin
    @RequestMapping(value = "/v1/product/recommendProducts", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryRecommendProducts(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        List<ProductResponseModel> productResponseList = new ArrayList<>();

        String recommendType = request.getParameter(Const.RECOMMEND_TYPE);
        List<ProductModel> recommendProducts = productsService.queryRecommendProducts(recommendType);
        for(ProductModel productModel : recommendProducts) {
        	List<ProfitRebateModel> profitRebates =  productsService.queryProfitRebateByPCode(productModel.getpCode());
        	ProductResponseModel productResponseModel = new ProductResponseModel();
        	BeanUtils.copyProperties(productModel, productResponseModel);
        	productResponseModel.setProfitRebates(profitRebates);
        	productResponseList.add(productResponseModel);
        }

        rspObj.setResult(productResponseList);
        return successResponse(rspObj);
    }

    @RequestMapping(value = "/v1/product/productDetail", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryProductDetail(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();

        String pCode = request.getParameter(Const.P_CODE);
        ProductModel productModel= productsService.queryProductDetail(pCode);
    	List<ProfitRebateModel> profitRebates =  productsService.queryProfitRebateByPCode(pCode);
    	List<ProductAttachmentModel> productAttachments = productsService.queryProductAttachmentByPCode(pCode);
    	ProductDetailResponseModel productResponseModel = new ProductDetailResponseModel();
    	BeanUtils.copyProperties(productModel, productResponseModel);
    	productResponseModel.setProfitRebates(profitRebates);
    	productResponseModel.setProductAttachments(productAttachments);

        rspObj.setResult(productResponseModel);
        return successResponse(rspObj);
    }
}
