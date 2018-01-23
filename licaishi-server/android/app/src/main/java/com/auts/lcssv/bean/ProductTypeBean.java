package com.auts.lcssv.bean;

import java.io.Serializable;

/**
 * 添加设备时选择设备类型的列表的列表项bean
 * Created by xiaolei.yang on 2017/7/18.
 */

public class ProductTypeBean implements Serializable {
    private static final long serialVersionUID = -924448836993601028L;

    /**
     * 1-智能排插
     */
    private int device_type;
    /**
     * 智能设备的名称
     */
    private String name;
    /**
     * 智能设备头像的url
     */
    private String default_device_icon;


    public int getDevice_type() {
        return device_type;
    }

    public void setDevice_type(int device_type) {
        this.device_type = device_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefault_device_icon() {
        return default_device_icon;
    }

    public void setDefault_device_icon(String default_device_icon) {
        this.default_device_icon = default_device_icon;
    }

    @Override
    public String toString() {
        return "ProductTypeBean{" +
                "device_type=" + device_type +
                ", name='" + name + '\'' +
                ", default_device_icon='" + default_device_icon + '\'' +
                '}';
    }
}
