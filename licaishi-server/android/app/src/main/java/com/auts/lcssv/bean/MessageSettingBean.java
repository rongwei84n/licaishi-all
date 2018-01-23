package com.auts.lcssv.bean;

/**
 * Created by weiming.zeng on 2017/8/22.
 */

public class MessageSettingBean {
    private String deviceName;
    private Boolean state;
    private String device_id;

    public MessageSettingBean(String deviceName, boolean state,String device_id) {
        this.deviceName = deviceName;
        this.state = state;
        this.device_id = device_id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
