package com.phicomm.smarthome.authservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.phicomm.smarthome.authservice.handler.BindedAuthHandler;
import com.phicomm.smarthome.authservice.model.request.InnerAuthPublishMessageModel;
import com.phicomm.smarthome.authservice.util.StringUtil;
import com.phicomm.smarthome.consts.PhihomeConst.ResponseStatus;
import com.phicomm.smarthome.phihome.model.BaseController;
import com.phicomm.smarthome.phihome.model.PhiHomeBaseResponse;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2017年10月25日.
 * @author rongwei.huang
 */
@RestController
public class BindedAuthController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(BindedAuthController.class);

    /**
     * 根据deviceid访问相对应的uid.
     * @param requestParas 请求的deviceid
     * @return 返回对应的uid
     */
    @RequestMapping(value = "/inner/auth/binded", method = RequestMethod.POST, produces = { "application/json" })
    @HystrixCommand(fallbackMethod = "findOrderFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public PhiHomeBaseResponse<Object> getUidByDeviceId(@RequestBody InnerAuthPublishMessageModel requestParas) {
        PhiHomeBaseResponse<Object> rspObj = new PhiHomeBaseResponse<Object>();
        if (requestParas == null) {
            LOGGER.info("no request params");
            return errorResponse(ResponseStatus.STATUS_NO_PARA_IN_REQUEST);
        }

        if (StringUtil.isNullOrEmpty(requestParas.getDeviceid())) {
            LOGGER.info("cannot get uid for device id[{}]", requestParas.getDeviceid());
            return errorResponse(ResponseStatus.STATUS_NO_PARA_IN_REQUEST);
        }

        String savedUid = BindedAuthHandler.instance.authDeviceIdAndUid(requestParas.getDeviceid());
        if (savedUid == null) {
            return errorResponse(ResponseStatus.STATUS_DATABASE_OPERATE_ERROR);
        }
        if (StringUtil.isNotEmpty(savedUid)) {
            LOGGER.info("getUid successfully. savedUid [{}]", savedUid);
            Map<String, String> resultMap = new HashMap<String, String>(1);
            resultMap.put("uid", savedUid);
            rspObj.setResult(resultMap);

            return successResponse(rspObj);
        } else {
            LOGGER.info("publishMqttMsg failed.");
            return errorResponse(ResponseStatus.STATUS_INNER_AUTH_FAILED);
        }
    }

    public PhiHomeBaseResponse<Object> findOrderFallback(InnerAuthPublishMessageModel requestParas) {
        LOGGER.debug("get uid by deviceId fallback");
        return errorResponse(ResponseStatus.STATUS_HYSTRIX_ERROR);
    }
}
