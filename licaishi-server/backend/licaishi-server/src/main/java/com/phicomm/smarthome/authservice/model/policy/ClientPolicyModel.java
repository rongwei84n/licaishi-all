package com.phicomm.smarthome.authservice.model.policy;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author qiangbin.wei
 *
 *         2018年1月3日
 */
public class ClientPolicyModel {

    @Field("client_id")
    private String clientId;
    @Field("policy_name")
    private String policyName;
    @Field("device_id")
    private String deviceId;
    
    
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}

