package com.auts.lcss.model.request;

public class BaseRequestModel {

    private String platid;

    private String vercode;

    private String channel;

    public String getPlatid() {
        return platid;
    }

    public void setPlatid(String platid) {
        this.platid = platid;
    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
