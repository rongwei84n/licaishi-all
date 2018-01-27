package com.phicomm.smarthome.authservice.model.policy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiangbin.wei
 *
 *         2018年1月3日
 */
public class StatementModel {

    private String effect;
    private List<String> action = new ArrayList<>();
    private List<String> resource = new ArrayList<>();

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public List<String> getAction() {
        return action;
    }

    public void setAction(List<String> action) {
        this.action = action;
    }

    public List<String> getResource() {
        return resource;
    }

    public void setResource(List<String> resource) {
        this.resource = resource;
    }

}

