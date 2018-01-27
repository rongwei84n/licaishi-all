package com.phicomm.smarthome.authservice.model.policy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author qiangbin.wei
 *
 *         2018年1月3日
 */
public class PolicyModel {

    @Field("policy_name")
    private String policyName;
    private List<StatementModel> statement = new ArrayList<>();

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public List<StatementModel> getStatement() {
        return statement;
    }

    public void setStatement(List<StatementModel> statement) {
        this.statement = statement;
    }

}

