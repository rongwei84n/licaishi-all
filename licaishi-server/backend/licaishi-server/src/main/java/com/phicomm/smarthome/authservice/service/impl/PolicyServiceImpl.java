package com.phicomm.smarthome.authservice.service.impl;

import com.phicomm.smarthome.authservice.dao.DevicesMapper;
import com.phicomm.smarthome.authservice.model.dao.DeviceDaoModel;
import com.phicomm.smarthome.authservice.model.policy.ClientPolicyModel;
import com.phicomm.smarthome.authservice.model.policy.PolicyModel;
import com.phicomm.smarthome.authservice.service.PolicyService;
import com.phicomm.smarthome.authservice.util.StringUtil;
import com.phicomm.smarthome.cache.Cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class PolicyServiceImpl implements PolicyService {
    private static Logger LOGGER = LogManager.getLogger(PolicyServiceImpl.class);
    private static String CLIENT_POLICY_KEY = "%s_policy";
    private static int TIMEOUT = 3600;
    private static String MQTT_POLICY_COLLECTION = "mqtt_policy";
    private static String DEFAULT_POLICY_COLLECTION = "default_policy_detail";
    private static String DEFAULT_DEVICE_POLICY_NAME = "device_default_policy";
    private static String DEFAULT_APP_POLICY_NAME = "app_default_policy";

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private Cache cache;
    @Autowired
    private DevicesMapper devicesMapper;

    @Override
    public List<PolicyModel> getDefaultPolicies() {
        try {
            List<PolicyModel> policyModels = mongoTemplate.findAll(PolicyModel.class, DEFAULT_POLICY_COLLECTION);
            return policyModels;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<ClientPolicyModel> getPolicyByClientId(String clientId) {
        try {
            // 先从缓存获取
            String key = String.format(CLIENT_POLICY_KEY, clientId);
            List<ClientPolicyModel> clientPolicyModels = (List<ClientPolicyModel>) cache.get(key);
            if (clientPolicyModels == null) {
                // 缓存没有则从mongodb中获取
                Query query = new Query(Criteria.where("client_id").is(clientId));
                clientPolicyModels = mongoTemplate.find(query, ClientPolicyModel.class, MQTT_POLICY_COLLECTION);
                cache.put(key, clientPolicyModels, TIMEOUT);
            }
            return clientPolicyModels;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException();
        }
    }

    @Override
    public void initClientPolicy(String clientId) {
        List<ClientPolicyModel> clientPolicyModels = getPolicyByClientId(clientId);
        if (clientPolicyModels == null || clientPolicyModels.isEmpty()) {
            ClientPolicyModel model = null;
            clientPolicyModels = new ArrayList<ClientPolicyModel>();
            // 初始化策略
            // app 客户端
            List<DeviceDaoModel> devices = devicesMapper.queryDevicesByUid(clientId);
            if (devices != null) {
                for (DeviceDaoModel deviceDaoModel : devices) {
                    model = new ClientPolicyModel();
                    model.setClientId(clientId);
                    model.setPolicyName(DEFAULT_APP_POLICY_NAME);
                    model.setDeviceId(deviceDaoModel.getDeviceId());
                    mongoTemplate.insert(model, MQTT_POLICY_COLLECTION);
                    clientPolicyModels.add(model);
                }
            }
            // 设备客户端暂时不设策略，指定默认策略
        }
        try {
            // 保存到缓存中
            cache.put(String.format(CLIENT_POLICY_KEY, clientId), clientPolicyModels, TIMEOUT);
        } catch (Exception e) {
            LOGGER.error("save policy to cache failed.", e);
            throw new RuntimeException();
        }
    }

    @Override
    public void addPolicy(ClientPolicyModel clientPolicyModel) {
        if (StringUtil.isNullOrEmpty(clientPolicyModel.getPolicyName())) {
            clientPolicyModel.setPolicyName(DEFAULT_APP_POLICY_NAME);
        }
        String key = String.format(CLIENT_POLICY_KEY, clientPolicyModel.getClientId());
        try {
            // 插入到mongodb中
            mongoTemplate.insert(clientPolicyModel, MQTT_POLICY_COLLECTION);
            // 更新缓存数据
            List<ClientPolicyModel> cacheModels = (List<ClientPolicyModel>) cache.get(key);
            if (cacheModels != null) {
                cacheModels.add(clientPolicyModel);
                cache.put(key, cacheModels, TIMEOUT);
            }
        } catch (Exception e) {
            LOGGER.error("insert policy failed.", e);
            throw new RuntimeException();
        }
    }

    @Override
    public void removePolicy(ClientPolicyModel clientPolicyModel) {
        String key = String.format(CLIENT_POLICY_KEY, clientPolicyModel.getClientId());
        try {
            // 先获取一下数据，保存到缓存中，用于解绑时短时间保存发送topic权限
            List<ClientPolicyModel> cacheModels = getPolicyByClientId(clientPolicyModel.getClientId());
            cache.put(key, cacheModels);

            // 插入到mongodb中
            Criteria criatira = new Criteria();
            criatira.andOperator(Criteria.where("client_id").is(clientPolicyModel.getClientId()),
                    Criteria.where("device_id")
                    .is(clientPolicyModel.getDeviceId()));
            Query query = new Query(criatira);
            mongoTemplate.remove(query, MQTT_POLICY_COLLECTION);
        } catch (Exception e) {
            LOGGER.error("remove policy failed.", e);
            throw new RuntimeException();
        }
    }

}


