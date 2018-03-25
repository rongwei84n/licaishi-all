package com.auts.lcscli.controller.product;

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

import com.auts.lcscli.consts.Const;
import com.auts.lcscli.controller.SBaseController;
import com.auts.lcscli.model.common.PhiHomeBaseResponse;
import com.auts.lcscli.model.dao.product.ProductAttachmentModel;
import com.auts.lcscli.model.dao.product.ProductModel;
import com.auts.lcscli.model.dao.product.ProfitRebateModel;
import com.auts.lcscli.model.response.Data;
import com.auts.lcscli.model.response.Pager;
import com.auts.lcscli.model.response.ProductDetailResponseModel;
import com.auts.lcscli.model.response.ProductResponseModel;
import com.auts.lcscli.service.ProductsService;

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
     * type 01：集合信托  02集合资管 03债权基金 04私募股权
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/product/list", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse queryProducts(HttpServletRequest request,
            @RequestParam(value = "pageNo", required = true) int pageNo,
            @RequestParam(value = "pageSize", required = true) int pageSize,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "pInvestType", required = false) String pInvestType, //01：房地产类 02：金融市场 03：基础设施 04：资金池 05：工商企业 99：其他
            @RequestParam(value = "pPaymentInterestType", required = false) String pPaymentInterestType,//01：按月付息 02：按季付息 03：按半年付息 04：按年付息 05 到期付本息
            @RequestParam(value = "pSizeRatioType", required = false) String pSizeRatioType,//01：小额畅打 02：已配出小额 03：严格配比 04：全大额
            @RequestParam(value = "minimumAmount", required = false) String minimumAmount,// 500000；1000000；2000000；3000000
            @RequestParam(value = "dueTime", required = false) String dueTime,//产品期限      01；02；03；04；05
            @RequestParam(value = "annualRevenue", required = false) String annualRevenue,//预期收益     01；02；03；04；05；06
            @RequestParam(value = "saleStatus", required = false) String saleStatus,//募集状态    01：预热中 02：募集中 03：募集结束 
            @RequestParam(value = "pRabateProfitParameter", required = false) String pRabateProfitParameter,
            @RequestParam(value = "pAnnualRevenueExpectParameter", required = false) String pAnnualRevenueExpectParameter,
            @RequestParam(value = "pCommission", required = false) String pCommission) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        Pager pager = null;

        LOGGER.info("queryProducts pageNo [{}] pageSize [{}] type [{}]", pageNo, pageSize, type);

        int totalCount = productsService.queryProductCountByPType(type, pInvestType, pPaymentInterestType, 
        		pSizeRatioType, minimumAmount, dueTime, annualRevenue, saleStatus, pRabateProfitParameter,
        		pAnnualRevenueExpectParameter, pCommission);
        List<ProductResponseModel> productResponseList = new ArrayList<>();
        List<ProductModel> products = productsService.queryProducts(pageNo, pageSize, type, pInvestType, pPaymentInterestType, 
        		pSizeRatioType, minimumAmount, dueTime, annualRevenue, saleStatus, pRabateProfitParameter, 
        		pAnnualRevenueExpectParameter, pCommission);
        if(products!=null && !products.isEmpty()) {
        	for(ProductModel productModel : products) {
            	List<ProfitRebateModel> profitRebates =  productsService.queryProfitRebateByPCode(productModel.getpCode());
//            	List<ProductAttachmentModel> productAttachments = productsService.queryProductAttachmentByPCode(productModel.getpCode());

            	ProductResponseModel productResponseModel = new ProductResponseModel();
            	BeanUtils.copyProperties(productModel, productResponseModel);
            	productResponseModel.setProfitRebates(profitRebates);
            	convertProductResponse(productResponseModel, productModel);
            	productResponseList.add(productResponseModel);
            }
        	//分页
        	pager = genernatePager(pageNo, pageSize, totalCount, products.size());
        }

        Data<ProductResponseModel> data = new Data<ProductResponseModel>();
        data.setList(productResponseList);
        data.setPager(pager);

        rspObj.setResult(data);
        return successResponse(rspObj);
    }

    /**
     * 封装接口转换数据 收益率，佣金比例等等
     * @param productResponseModel
     * @param productModel
     */
    private void convertProductResponse(ProductResponseModel productResponseModel, ProductModel productModel) {
    	//设置最高预计收益率
//    	List<ProfitRebateModel> profitRebates = productResponseModel.getProfitRebates();
//    	if(profitRebates != null && profitRebates.size() > 0) {
//    		double maxAnnualRevenue = 0;
//    		double maxCommission = 0;
//    		for(ProfitRebateModel prm : profitRebates) {
//    			String annualRevenue = prm.getPrExpectAnnualRevenue();
//    			double annualRevenueTmp =  Double.parseDouble(annualRevenue.replaceAll("%", ""));
//    			if(annualRevenueTmp > maxAnnualRevenue) {
//    				maxAnnualRevenue = annualRevenueTmp;
//    			}
//    			
//    			String prCommission = prm.getPrCommission();
//    			double prCommissionTmp =  Double.parseDouble(prCommission.replaceAll("%", ""));
//    			if(prCommissionTmp > maxCommission) {
//    				maxCommission = prCommissionTmp;
//    			}
//    		}
    	if(productModel.getpExpectAnnualRevenue() != null && !productModel.getpExpectAnnualRevenue().contains("浮动")) {
    		productResponseModel.setpExpectAnnualRevenue(productModel.getpExpectAnnualRevenue() + "%");
    	}
    	productResponseModel.setpCommission(productModel.getpCommission() + "%");
    		
//    	}
    	
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
        	convertProductResponse(productResponseModel, productModel);
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
    	convertProductDetailResponse(productResponseModel, productModel);

        rspObj.setResult(productResponseModel);
        return successResponse(rspObj);
    }
    
    private void convertProductDetailResponse(ProductDetailResponseModel productResponseModel, ProductModel productModel) {
    	productResponseModel.setpId(productModel.getId()+"");
    	//TODO查询机构简称
    	productResponseModel.setpInvestName("上海信托");
    	if(productModel.getpExpectAnnualRevenue() != null && !productModel.getpExpectAnnualRevenue().contains("浮动")) {
    		productResponseModel.setpExpectAnnualRevenue(productModel.getpExpectAnnualRevenue() + "%");
    	}
    	productResponseModel.setpCommission(productModel.getpCommission() + "%");
    	
    }
}
