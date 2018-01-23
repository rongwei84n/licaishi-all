package com.auts.lcssv.event;

/**
 * 更换手机号成功
 * Created by xiaolei.yang on 2017/7/25.
 */

public class ChangePhoneNumberSuccessEvent {
    private String phoneNumber;
    private int type;  //0-只处理号码变更，1-处理界面关闭

    public ChangePhoneNumberSuccessEvent(int type, String phoneNumber) {
        this.type = type;
        this.phoneNumber = phoneNumber;
    }

    public int getType() {
        return type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
