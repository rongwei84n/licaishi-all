package com.auts.lcscli.bean;

import java.io.Serializable;

/**
 * 设备信息
 *
 * @author xiaolei.yang
 * @date 2017/7/13
 */

public class DeviceBean implements Serializable{
    private static final long serialVersionUID = -6101443532807751724L;
    private String device_id;
    private String sw0;
    private String sw1;
    private String sw2;
    private String sw3;
    private String sw4;
    private String sw5;
    private String sw6;
    private String sw7;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getSw0() {
        return sw0;
    }

    public void setSw0(String sw0) {
        this.sw0 = sw0;
    }

    public String getSw1() {
        return sw1;
    }

    public void setSw1(String sw1) {
        this.sw1 = sw1;
    }

    public String getSw2() {
        return sw2;
    }

    public void setSw2(String sw2) {
        this.sw2 = sw2;
    }

    public String getSw3() {
        return sw3;
    }

    public void setSw3(String sw3) {
        this.sw3 = sw3;
    }

    public String getSw4() {
        return sw4;
    }

    public void setSw4(String sw4) {
        this.sw4 = sw4;
    }

    public String getSw5() {
        return sw5;
    }

    public void setSw5(String sw5) {
        this.sw5 = sw5;
    }

    public String getSw6() {
        return sw6;
    }

    public void setSw6(String sw6) {
        this.sw6 = sw6;
    }

    public String getSw7() {
        return sw7;
    }

    public void setSw7(String sw7) {
        this.sw7 = sw7;
    }

    @Override
    public String toString() {
        return "DeviceBean{" +
                "device_id='" + device_id + '\'' +
                ", sw0='" + sw0 + '\'' +
                ", sw1='" + sw1 + '\'' +
                ", sw2='" + sw2 + '\'' +
                ", sw3='" + sw3 + '\'' +
                ", sw4='" + sw4 + '\'' +
                ", sw5='" + sw5 + '\'' +
                ", sw6='" + sw6 + '\'' +
                ", sw7='" + sw7 + '\'' +
                '}';
    }
}
