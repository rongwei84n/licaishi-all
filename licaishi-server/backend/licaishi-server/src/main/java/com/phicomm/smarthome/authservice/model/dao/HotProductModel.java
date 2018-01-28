package com.phicomm.smarthome.authservice.model.dao;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.authservice.model.common.BaseDaoModel;

public class HotProductModel extends BaseDaoModel {

    private String name;

    private String pic;

    @JsonProperty("time_limit")
    @JSONField(name = "time_limit")
    private String timeLimit;

    private String field;

    @JsonProperty("rebate_p")
    @JSONField(name = "rebate_p")
    private String rebateP;

    private String progress;

    @JsonProperty("income_p")
    @JSONField(name = "income_p")
    private String incomeP;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRebateP() {
        return rebateP;
    }

    public void setRebateP(String rebateP) {
        this.rebateP = rebateP;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getIncomeP() {
        return incomeP;
    }

    public void setIncomeP(String incomeP) {
        this.incomeP = incomeP;
    }
}