package com.phicomm.smarthome.authservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.phicomm.smarthome.consts.PhihomeConst;
import com.phicomm.smarthome.phihome.model.BaseController;
import com.phicomm.smarthome.phihome.model.PhiHomeBaseResponse;
import com.phicomm.smarthome.util.StringUtil;

/**
 * @author qiangbin.wei
 *
 *         2017年11月3日
 */
@RestController
public class DeviceAuthController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(DeviceAuthController.class);

    @Autowired
    private EurekaClient discoveryClient;

    @RequestMapping(value = "/auth/check/device_id", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse<Object> authPublishMessage(@RequestBody String requestBody) {
        LOGGER.info("auth deviceId {}", requestBody);

        if (StringUtil.isNullOrEmpty(requestBody)) {
            LOGGER.error("no deviceId.");
            return errorResponse(PhihomeConst.ResponseStatus.STATUS_NO_PARA_IN_REQUEST);
        }
        String deviceId = null;
        try {
            deviceId = (String) JSON.parseObject(requestBody).get("device_id");
        } catch (Exception e) {
            LOGGER.error("no deviceId.");
            return errorResponse(PhihomeConst.ResponseStatus.STATUS_NO_PARA_IN_REQUEST);
        }

        // 调用deviceId系统进行鉴权
        String body = String.format("{\"device_id\":\"%s\"}", deviceId);
        DeviceAuthModel rspModel = new DeviceAuthModel();
        int status = authDevice(body, rspModel);
        if (status != 200) {
            LOGGER.error("auth device failed. errorCode:" + status);
            return errorResponse(status);
        }
        PhiHomeBaseResponse<Object> rspObj = new PhiHomeBaseResponse<Object>();
        rspObj.setResult(rspModel.getResult());
        return successResponse(rspObj);
    }

    /**
     * 设备id鉴权
     *
     * @param body
     * @return
     */
    private int authDevice(Object body, DeviceAuthModel rspModel) {
        return sendPost(getServiceUrl("smarthome-phihome-deviceid", "deviceid/device_id_check"), body, rspModel);
    }

    /**
     * 获取服务的调用地址
     *
     * @param end
     *            url后缀
     * @return url
     */
    private String getServiceUrl(String serviceName, String end) {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka(serviceName, false);
        return instance.getHomePageUrl() + end;
    }

    private int sendPost(String url, Object body, DeviceAuthModel model) {
        try {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setReadTimeout(PhihomeConst.INNER_SERVICE_REQUEST_READ_TIMEOUT);
            requestFactory.setConnectTimeout(PhihomeConst.INNER_SERVICE_REQUEST_CONNECT_TIMEOUT);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(requestFactory);
            restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);

            HttpEntity<Object> formEntity = new HttpEntity<Object>(body, headers);
            String rspPhihome = restTemplate.postForObject(url, formEntity, String.class);
            if (StringUtil.isNotEmpty(rspPhihome)) {
                DeviceAuthModel rspModel = JSON.parseObject(rspPhihome, DeviceAuthModel.class);
                LOGGER.info("post url [{}] rsp status [{}]", url, rspModel.getStatus());
                if (model != null) {
                    model.setResult(rspModel.getResult());
                }
                return rspModel.getStatus();
            }
            LOGGER.debug("post url [{}] result [{}]", url, rspPhihome);
            return PhihomeConst.ResponseStatus.STATUS_OK;
        } catch (Exception e) {
            LOGGER.error("post url [{}] failed. error [{}]", url, e);
            return PhihomeConst.ResponseStatus.STATUS_COMMON_FAILED;
        }
    }

    public static class DeviceAuthModel {
        private int status;
        private String message;
        private Object result;

        /**
         * @return the status
         */
        public int getStatus() {
            return status;
        }

        /**
         * @param status
         *            the status to set
         */
        public void setStatus(int status) {
            this.status = status;
        }

        /**
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * @param message
         *            the message to set
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * @return the result
         */
        public Object getResult() {
            return result;
        }

        /**
         * @param result
         *            the result to set
         */
        public void setResult(Object result) {
            this.result = result;
        }

    }

}
