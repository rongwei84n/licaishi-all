package com.phicomm.smarthome.authservice.controller.policy;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.phicomm.smarthome.authservice.consts.Const;
import com.phicomm.smarthome.authservice.model.policy.ClientPolicyModel;
import com.phicomm.smarthome.authservice.model.policy.PolicyModel;
import com.phicomm.smarthome.authservice.model.policy.StatementModel;
import com.phicomm.smarthome.authservice.service.PolicyService;
import com.phicomm.smarthome.authservice.util.StringUtil;
import com.phicomm.smarthome.consts.PhihomeConst.ResponseStatus;
import com.phicomm.smarthome.phihome.model.BaseController;
import com.phicomm.smarthome.phihome.model.PhiHomeBaseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * mqtt 鉴权策略.
 * 
 * @author qiangbin.wei
 *
 *         2018年1月3日
 */
@RestController
public class MqttPolicyController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(MqttPolicyController.class);
    private final Map<String, Map> defaultPolicies = new HashMap<>();
    @Autowired
    private PolicyService policyService;

    /**
     * 获取客户端策略.
     * 
     * @param clientId
     *            客户端id
     * @return 客户端策略
     */
    @RequestMapping(value = "/mqtt/client/{client_id}/policies", method = RequestMethod.GET, produces = {
            "application/json" })
    public PhiHomeBaseResponse<Object> getAppClientPolicies(@PathVariable("client_id") String clientId) {
        LOGGER.info("get policy for client [{}]", clientId);
        List<ClientPolicyModel> list = null;
        try {
            // 获取策略引用
            list = policyService.getPolicyByClientId(clientId);
        } catch (Exception e) {
            LOGGER.error("get policy for client [{}] failed", clientId);
            return errorResponse(ResponseStatus.STATUS_OPERATE_MONGODB_FAILED);
        }

        PhiHomeBaseResponse<Object> rspObj = new PhiHomeBaseResponse<Object>();
        rspObj.setResult(list);
        return successResponse(rspObj);
    }

    @RequestMapping(value = "/mqtt/{action}/auth/topics", method = RequestMethod.GET, produces = {
            "application/json" })
    @HystrixCommand(fallbackMethod = "findOrderFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000") })
    public PhiHomeBaseResponse<Object> getAuthTopics(@RequestParam("client_id") String clientId,
            @PathVariable("action") String action) {
        LOGGER.info("get {} authtopics for client [{}]", action, clientId);
        List<ClientPolicyModel> list = null;
        try {
            // 获取策略引用
            list = policyService.getPolicyByClientId(clientId);
        } catch (Exception e) {
            LOGGER.error("get {} auth topics for client [{}] failed", action, clientId);
            return errorResponse(ResponseStatus.STATUS_OPERATE_MONGODB_FAILED);
        }
        if (list == null || list.isEmpty()) {
            LOGGER.error("get {} auth topics for client [{}] failed", action, clientId);
            return errorResponse(ResponseStatus.STATUS_INVAID_PARA);
        }

        Map<String, Map<String, List<String>>> actionResourceMap = new HashMap<>();
        // 获取所有的key，并初始化列表
        for (ClientPolicyModel reference : list) {
            Map<String, List<String>> effectMap = new HashMap<>();
            effectMap.put(Const.Mqtt.EFFECT_ALLOW, new ArrayList<String>());
            effectMap.put(Const.Mqtt.EFFECT_DENY, new ArrayList<String>());
            actionResourceMap.put(reference.getDeviceId(), effectMap);
        }
        // 将数据保存到列表
        try {
            String iotAction = "iot:" + action;
            for (ClientPolicyModel reference : list) {
                Map<String, List<String>> resources = (Map<String, List<String>>) defaultPolicies
                        .get(reference.getPolicyName()).get(iotAction);
                actionResourceMap.get(reference.getDeviceId()).get(Const.Mqtt.EFFECT_ALLOW)
                        .addAll(resources.get(Const.Mqtt.EFFECT_ALLOW));
                actionResourceMap.get(reference.getDeviceId()).get(Const.Mqtt.EFFECT_DENY)
                        .addAll(resources.get(Const.Mqtt.EFFECT_DENY));
            }
        } catch (Exception e) {
            LOGGER.error(e);
            return errorResponse(ResponseStatus.STATUS_COMMON_FAILED);
        }


        PhiHomeBaseResponse<Object> rspObj = new PhiHomeBaseResponse<Object>();
        rspObj.setResult(actionResourceMap);
        LOGGER.info("get {} auth topics for client [{}] successfully.", action, clientId);
        return successResponse(rspObj);
    }

    public PhiHomeBaseResponse<Object> findOrderFallback(String clientId, String action) {
        LOGGER.debug("authPublishMessage fallback, clientId: [{}]", clientId);
        return errorResponse(ResponseStatus.STATUS_HYSTRIX_ERROR);
    }

    /**
     * 加载默认策略.
     * 
     * @return 默认策略内容.
     */
    @RequestMapping(value = "/mqtt/policy/load", method = RequestMethod.GET, produces = { "application/json" })
    public PhiHomeBaseResponse<Object> loadDefaultPolicies() {
        try {
            // 先清除，后添加
            defaultPolicies.clear();
            List<PolicyModel> policyModels = policyService.getDefaultPolicies();
            for (PolicyModel model : policyModels) {
                Map<String, Map<String, List<String>>> actionMap = new HashMap<>();
                initMap(actionMap, Const.Mqtt.IOT_RECEIVE_ACTION);
                initMap(actionMap, Const.Mqtt.IOT_SUBSCRIBE_ACTION);
                initMap(actionMap, Const.Mqtt.IOT_PUBLISH_ACTION);
                initMap(actionMap, Const.Mqtt.IOT_CONNETCT_ACTION);
                defaultPolicies.put(model.getPolicyName(), actionMap);
                for (StatementModel tempModel : model.getStatement()) {
                    Map<String, List<String>> effectMap = new HashMap<>();
                    for (String action : tempModel.getAction()) {
                        ((ArrayList<String>) actionMap.get(action).get(tempModel.getEffect()))
                                .addAll(tempModel.getResource());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("load default policies failed.");
            return errorResponse(ResponseStatus.STATUS_OPERATE_MONGODB_FAILED);
        }

        PhiHomeBaseResponse<Object> rspObj = new PhiHomeBaseResponse<Object>();
        rspObj.setResult(defaultPolicies);
        LOGGER.info("load default policies successfully.");
        return successResponse(rspObj);
    }

    private void initMap(Map<String, Map<String, List<String>>> actionMap, String action) {
        Map<String, List<String>> map = new HashMap<>();
        actionMap.put(action, map);
        map.put(Const.Mqtt.EFFECT_ALLOW, new ArrayList<String>());
        map.put(Const.Mqtt.EFFECT_DENY, new ArrayList<String>());
    }

    /**
     * 客户端策略初始化.
     * 
     * @param requestBody
     *            客户端信息
     * @return 初始化结果
     */
    @RequestMapping(value = "/mqtt/client/policy/init", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse<Object> initClientPolicy(@RequestBody String requestBody) {
        String clientId = JSON.parseObject(requestBody).getString("client_id");
        if (StringUtil.isNullOrEmpty(clientId)) {
            LOGGER.error("no clientId");
            return errorResponse(ResponseStatus.STATUS_INVAID_PARA);
        }
        LOGGER.info("mqtt client [{}] policy init", clientId);

        try {
            policyService.initClientPolicy(clientId);
        } catch (Exception e) {
            LOGGER.error("init client policy failed.");
            return errorResponse(ResponseStatus.STATUS_OPERATE_MONGODB_FAILED);
        }

        PhiHomeBaseResponse<Object> rspObj = new PhiHomeBaseResponse<Object>();
        return successResponse(rspObj);
    }

    @RequestMapping(value = "/mqtt/client/policy", method = RequestMethod.POST, produces = { "application/json" })
    public PhiHomeBaseResponse<Object> addPolicy(@RequestBody String requestBody) {
        LOGGER.info("add policy  [{}]", requestBody);
        ClientPolicyModel model = JSON.parseObject(requestBody, ClientPolicyModel.class);
        if (StringUtil.isNullOrEmpty(model.getClientId())) {
            LOGGER.error("no para clientId");
            return errorResponse(ResponseStatus.STATUS_INVAID_PARA);
        }
        if (StringUtil.isNullOrEmpty(model.getDeviceId())) {
            LOGGER.error("no para deviceId");
            return errorResponse(ResponseStatus.STATUS_INVAID_PARA);
        }
        try {
            policyService.addPolicy(model);
        } catch (Exception e) {
            LOGGER.error("add policy fail. client: [{}]  deviceId: [{}]", model.getClientId(), model.getDeviceId());
            return errorResponse(ResponseStatus.STATUS_OPERATE_MONGODB_FAILED);
        }
        PhiHomeBaseResponse<Object> rspObj = new PhiHomeBaseResponse<Object>();
        return successResponse(rspObj);
    }

    @RequestMapping(value = "/mqtt/client/policy/remove", method = RequestMethod.POST, produces = {
            "application/json" })
    public PhiHomeBaseResponse<Object> removePolicy(@RequestBody String requestBody) {
        LOGGER.info("remove policy  [{}]", requestBody);
        ClientPolicyModel model = JSON.parseObject(requestBody, ClientPolicyModel.class);
        if (StringUtil.isNullOrEmpty(model.getClientId())) {
            LOGGER.error("no para clientId");
            return errorResponse(ResponseStatus.STATUS_INVAID_PARA);
        }
        if (StringUtil.isNullOrEmpty(model.getDeviceId())) {
            LOGGER.error("no para deviceId");
            return errorResponse(ResponseStatus.STATUS_INVAID_PARA);
        }
        try {
            policyService.removePolicy(model);
        } catch (Exception e) {
            LOGGER.error("remove policy fail. client: [{}]  deviceId: [{}]", model.getClientId(), model.getDeviceId());
            return errorResponse(ResponseStatus.STATUS_OPERATE_MONGODB_FAILED);
        }
        PhiHomeBaseResponse<Object> rspObj = new PhiHomeBaseResponse<Object>();
        return successResponse(rspObj);
    }

}

