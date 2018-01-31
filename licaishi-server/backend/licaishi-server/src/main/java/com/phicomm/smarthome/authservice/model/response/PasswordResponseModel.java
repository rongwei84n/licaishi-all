package com.phicomm.smarthome.authservice.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordResponseModel {

    private String error;

    @JSONField(name = "token_status")
    @JsonProperty("token_status")
    private String tokenStatus;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }
}
