package com.phicomm.smarthome.authservice.model.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "phicomm")
public class PhicommServerConfigModel {
    private String tokenserver;

    public String getTokenserver() {
        return tokenserver;
    }

    public void setTokenserver(String tokenserver) {
        this.tokenserver = tokenserver;
    }
}
