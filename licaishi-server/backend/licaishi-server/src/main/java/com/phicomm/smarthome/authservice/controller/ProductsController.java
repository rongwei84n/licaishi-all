package com.phicomm.smarthome.authservice.controller;

import com.phicomm.smarthome.authservice.consts.Const;
import com.phicomm.smarthome.authservice.model.common.PhiHomeBaseResponse;
import com.phicomm.smarthome.authservice.model.common.PhicommServerConfigModel;
import com.phicomm.smarthome.authservice.model.dao.HotProductModel;
import com.phicomm.smarthome.authservice.service.ProductsService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2017年12月04日, 斐讯云Token鉴权接口，我们做这一层接口可以在自己业务和斐讯之间加入熔断器.
 * @author rongwei.huang
 */
@RestController
public class ProductsController extends SBaseController {
    private static final Logger LOGGER = LogManager.getLogger(ProductsController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    PhicommServerConfigModel phicommServer;

    @Autowired
    ProductsService productsService;

    /**
     * 查询首页部分热门产品.
     */
    @RequestMapping(value = "/server/partial_hot_products", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse partialHotProducts(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        String token = request.getHeader(Const.AUTHORIZATION);
        // 通过token获取账户uid
        String uid = null;
        Object object = getUIDByToken(token);
        if (object instanceof String) {
            uid = (String) object;
        } else {
            return (PhiHomeBaseResponse) object;
        }

//        List<ProductModel> hotProducts = productsService.queryHotProducts();
        List<HotProductModel> hotProducts = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            HotProductModel model = new HotProductModel();
            model.setId((long) i);
            model.setName("赚钱" + i);
            model.setTimeLimit("12个月");
            model.setPic("www.baidu.com");
            model.setField("基础设施");
            model.setRebateP("0.05");
            model.setProgress("0.59");
            model.setIncomeP("0.09");

            hotProducts.add(model);
        }
        rspObj.setResult(hotProducts);
        return successResponse(rspObj);
    }

    /**
     * 查询首页所有热门产品.
     */
    @RequestMapping(value = "/server/all_hot_products", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse allHotProducts(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        String token = request.getHeader(Const.AUTHORIZATION);
        // 通过token获取账户uid
        String uid = null;
        Object object = getUIDByToken(token);
        if (object instanceof String) {
            uid = (String) object;
        } else {
            return (PhiHomeBaseResponse) object;
        }

//        List<ProductModel> hotProducts = productsService.queryHotProducts();
        List<HotProductModel> hotProducts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            HotProductModel model = new HotProductModel();
            model.setId((long) i);
            model.setName("赚钱" + i);
            model.setTimeLimit("12个月");
            model.setPic("www.baidu.com");
            model.setField("基础设施");
            model.setRebateP("0.05");
            model.setProgress("0.59");
            model.setIncomeP("0.09");

            hotProducts.add(model);
        }
        rspObj.setResult(hotProducts);
        return successResponse(rspObj);
    }

    /**
     * 查询首页部分热门产品.
     */
    @RequestMapping(value = "/server/partial_recommend_products", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse partialRecommendProducts(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        String token = request.getHeader(Const.AUTHORIZATION);
        // 通过token获取账户uid
        String uid = null;
        Object object = getUIDByToken(token);
        if (object instanceof String) {
            uid = (String) object;
        } else {
            return (PhiHomeBaseResponse) object;
        }

//        List<ProductModel> hotProducts = productsService.queryHotProducts();
        List<HotProductModel> hotProducts = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            HotProductModel model = new HotProductModel();
            model.setId((long) i);
            model.setName("赚钱" + i);
            model.setTimeLimit("12个月");
            model.setPic("www.baidu.com");
            model.setField("基础设施");
            model.setRebateP("0.05");
            model.setProgress("0.59");
            model.setIncomeP("0.09");

            hotProducts.add(model);
        }
        rspObj.setResult(hotProducts);
        return successResponse(rspObj);
    }

    /**
     * 查询首页部分热门产品.
     */
    @RequestMapping(value = "/server/all_recommend_products", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse allRecommendProducts(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        String token = request.getHeader(Const.AUTHORIZATION);
        // 通过token获取账户uid
        String uid = null;
        Object object = getUIDByToken(token);
        if (object instanceof String) {
            uid = (String) object;
        } else {
            return (PhiHomeBaseResponse) object;
        }

//        List<ProductModel> hotProducts = productsService.queryHotProducts();
        List<HotProductModel> hotProducts = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            HotProductModel model = new HotProductModel();
            model.setId((long) i);
            model.setName("赚钱" + i);
            model.setTimeLimit("12个月");
            model.setPic("www.baidu.com");
            model.setField("基础设施");
            model.setRebateP("0.05");
            model.setProgress("0.59");
            model.setIncomeP("0.09");

            hotProducts.add(model);
        }
        rspObj.setResult(hotProducts);
        return successResponse(rspObj);
    }
}
