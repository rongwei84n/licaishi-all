package com.auts.lcssv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 智能设备结果信息
 *
 * @author xiaolei.yang
 * @date 2017/7/13
 */

public class DeviceBeanResult implements Serializable {
    private static final long serialVersionUID = -4119449488547142469L;

    private int has_more_page;
    private List<DeviceBean> devices;


    public int getHas_more_page() {
        return has_more_page;
    }

    public void setHas_more_page(int has_more_page) {
        this.has_more_page = has_more_page;
    }

    public List<DeviceBean> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceBean> devices) {
        this.devices = devices;
    }

    @Override
    public String toString() {
        return "DeviceBeanResult{" +
                "has_more_page=" + has_more_page +
                ", devices=" + devices +
                '}';
    }
}
