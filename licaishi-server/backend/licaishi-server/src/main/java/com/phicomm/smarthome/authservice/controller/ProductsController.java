package com.phicomm.smarthome.authservice.controller;

import com.phicomm.smarthome.authservice.consts.Const;
import com.phicomm.smarthome.authservice.model.common.PhiHomeBaseResponse;
import com.phicomm.smarthome.authservice.model.common.PhicommServerConfigModel;
import com.phicomm.smarthome.authservice.model.dao.ProductModel;
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
     * 查询热门产品.
     */
    @RequestMapping(value = "/server/hot_products", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse hotProducts(HttpServletRequest request) {
        PhiHomeBaseResponse rspObj = new PhiHomeBaseResponse();
        String token = request.getHeader(Const.AUTHORIZATION);
        // 通过token获取账户uid
        String uid = null;
        Object object = getUIDByToken(token, rspObj);
        if (object instanceof String) {
            uid = (String) object;
        } else {
            return (PhiHomeBaseResponse) object;
        }

//        List<ProductModel> hotProducts = productsService.queryHotProducts();
        List<ProductModel> hotProducts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductModel model = new ProductModel();
            model.setId((long) i);
            model.setName("赚钱" + i);
            model.setTimeLimit("12个月");
            model.setUrl("www.baidu.com");

            hotProducts.add(model);
        }
        return successResponse(hotProducts);
    }
}
