package com.auts.lcscli.bean;

import java.io.Serializable;

/**
 * 设备绑定状态
 *
 * @author xiaolei.yang
 * @date 2017/8/8
 */

public class BindStatusBean implements Serializable {
    private static final long serialVersionUID = 2470875419111410854L;
    private int bind_status;//设备绑定状态 0-未绑定，1-绑定
    private long timestamp;//时间戳

    public int getBind_status() {
        return bind_status;
    }

    public void setBind_status(int bind_status) {
        this.bind_status = bind_status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "BindStatusBean{" +
                "bind_status=" + bind_status +
                ", timestamp=" + timestamp +
                '}';
    }
}
