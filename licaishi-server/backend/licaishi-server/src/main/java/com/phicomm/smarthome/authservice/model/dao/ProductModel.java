package com.phicomm.smarthome.authservice.model.dao;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.authservice.model.common.BaseDaoModel;

public class ProductModel extends BaseDaoModel {

    private String name;

    private String url;

    @JsonProperty("time_limit")
    @JSONField(name = "time_limit")
    private String timeLimit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }
}