package com.phicomm.smarthome.authservice.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.phicomm.smarthome.authservice.model.common.PhicommAccountDetailModel;
import com.phicomm.smarthome.authservice.model.common.PhicommServerConfigModel;
import com.phicomm.smarthome.authservice.model.request.InnerAuthPhicommTokenModel;
import com.phicomm.smarthome.authservice.util.PhicommHttpsClient;
import com.phicomm.smarthome.authservice.util.StringUtil;
import com.phicomm.smarthome.consts.PhihomeConst.ResponseStatus;

import com.phicomm.smarthome.util.MyResponseutils;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2017年12月04日, 斐讯云Token鉴权接口，我们做这一层接口可以在自己业务和斐讯之间加入熔断器.
 * @author rongwei.huang
 */
@RestController
public class ProductsController {
    private static final Logger LOGGER = LogManager.getLogger(ProductsController.class);

    public static final String DEFAULT_CHARSET = "utf-8";
    public static String HTTP_HEAD_AUTHORIZATION = "Authorization";
    public static String HTTP_HEAD_CONTENT_TYPE = "Content-Type";

    @Autowired
    PhicommServerConfigModel phicommServer;

    /**
     * 查询产品.
     */
    @RequestMapping(value = "/app/products", method = RequestMethod.POST, produces = { "application/json" })
    public PhicommAccountDetailModel authPhicommToken(@RequestBody InnerAuthPhicommTokenModel requestParas) {
        if (requestParas == null) {
            LOGGER.info("no request params");
            return errorResponse(ResponseStatus.STATUS_NO_PARA_IN_REQUEST);
        }

        if (StringUtil.isNullOrEmpty(requestParas.getToken())) {
            LOGGER.info("cannot find request token for this reqeust");
            return errorResponse(ResponseStatus.STATUS_NO_PARA_IN_REQUEST);
        }

        return errorResponse(ResponseStatus.STATUS_NO_PARA_IN_REQUEST);
    }

    private PhicommAccountDetailModel errorResponse(int code) {
        PhicommAccountDetailModel model = new PhicommAccountDetailModel();
        model.setTokenStatus(String.valueOf(code));
        model.setMessage(MyResponseutils.parseMsg(code));
        return model;
    }

    private PhicommAccountDetailModel getPhicommAccountByToken(String phicommCloudServer, String token) {
        Map<String, String> headerParas = new HashMap<String, String>(1);
        headerParas.put(HTTP_HEAD_AUTHORIZATION, token);
        try {
            String accountJson = PhicommHttpsClient.httpsGet(phicommCloudServer,
                    DEFAULT_CHARSET,
                    headerParas);
            LOGGER.info("accountJson [{}]", accountJson);
            if (StringUtil.isNotEmpty(accountJson)) {
                return JSON.parseObject(accountJson, PhicommAccountDetailModel.class);
            }
        } catch (Exception e) {
            LOGGER.error("get phicomm account error: ", e);
        }
        return null;
    }
}
