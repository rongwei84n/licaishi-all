package com.auts.lcscli.bean;

import java.io.Serializable;

/**
 * Created by qisheng.lv on 2017/10/20.
 */

public class LogoutReason implements Serializable {
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
