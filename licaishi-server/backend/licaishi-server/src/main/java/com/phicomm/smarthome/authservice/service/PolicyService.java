package com.phicomm.smarthome.authservice.service;

import com.phicomm.smarthome.authservice.model.policy.ClientPolicyModel;
import com.phicomm.smarthome.authservice.model.policy.PolicyModel;

import java.util.List;

/**
 * @author qiangbin.wei
 *
 *         2018年1月3日
 */
public interface PolicyService {

    /**
     * 获取默认策略.
     */
    List<PolicyModel> getDefaultPolicies();

    /**
     * 获取客户端的策略引用.
     * 
     * @param clientId
     *            客户端id
     * @return 策略引用列表
     */
    List<ClientPolicyModel> getPolicyByClientId(String clientId);

    /**
     * 初始化客户端策略.
     * 
     * @param clientId
     *            客户端id
     */
    void initClientPolicy(String clientId);

    /**
     * 添加策略引用， 绑定设备时添加.
     * 
     * @param clientPolicyModel
     *            客户端绑定数据
     */
    void addPolicy(ClientPolicyModel clientPolicyModel);

    /**
     * 删除策略引用.
     * 
     * @param clientPolicyModel
     *            需要删除的策略数据
     */
    void removePolicy(ClientPolicyModel clientPolicyModel);
}

