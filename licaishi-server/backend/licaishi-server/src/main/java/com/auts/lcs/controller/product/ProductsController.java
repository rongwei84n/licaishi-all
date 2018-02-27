package com.auts.lcs.controller.product;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auts.lcs.consts.Const;
import com.auts.lcs.controller.SBaseController;
import com.auts.lcs.model.common.PhiHomeBaseResponse;
import com.auts.lcs.model.dao.HotProductModel;
import com.auts.lcs.model.dao.product.ProductModel;
import com.auts.lcs.model.dao.product.ProfitRebateModel;
import com.auts.lcs.service.ProductsService;

/**
 * 产品模块
 * 
 * @author rongwei.huang
 */
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
     * type 02：集合信托  03集合资管 04债权基金 05股权基金 06阳光私募
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/product/list", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryProducts(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        String pageNo = request.getParameter(Const.PAGE_NO);
        String pageSize = request.getParameter(Const.PAGE_SIZE);
        String type = request.getParameter(Const.TYPE);
        
        LOGGER.info("queryProducts type [{}]", type);
        
        List<ProductModel> hotProducts = productsService.queryProducts(Integer.parseInt(pageNo), Integer.parseInt(pageSize), type);
        rspObj.setResult(hotProducts);
        return successResponse(rspObj);
    }

    /**
     * 首页查询推荐产品和热门产品
     * recommendype： 1 推荐产品 2热门产品
     */
    @RequestMapping(value = "/v1/product/recommendProducts", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse allRecommendProducts(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        
        String recommendType = request.getParameter(Const.RECOMMEND_TYPE);
        List<ProductModel> recommendProducts = productsService.queryRecommendProducts(recommendType);
        for(ProductModel productModel : recommendProducts) {
        	List<ProfitRebateModel> profitRebateList =  productsService.queryProfitRebateByPCode(productModel.getpCode());
        }
        
        rspObj.setResult(null);
        return successResponse(rspObj);
    }
}
