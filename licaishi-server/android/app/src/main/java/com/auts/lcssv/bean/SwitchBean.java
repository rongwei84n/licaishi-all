package com.auts.lcssv.bean;

import java.io.Serializable;

/**
 * Created by weiming.zeng on 2017/9/14.
 */

public class SwitchBean implements Serializable {
    private String switchName;
    private int state;
    private boolean isSelect = true;

    public SwitchBean(String switchName, int state, boolean isSelect) {
        this.switchName = switchName;
        this.state = state;
        this.isSelect = isSelect;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public SwitchBean() {
    }

    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
