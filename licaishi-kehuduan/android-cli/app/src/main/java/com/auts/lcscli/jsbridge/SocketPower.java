package com.auts.lcscli.jsbridge;

import java.io.Serializable;

/**
 * Created by qisheng.lv on 2017/8/1.
 */

public class SocketPower implements Serializable{
    private static final long serialVersionUID = 7703720467283304580L;

    private int power;

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
