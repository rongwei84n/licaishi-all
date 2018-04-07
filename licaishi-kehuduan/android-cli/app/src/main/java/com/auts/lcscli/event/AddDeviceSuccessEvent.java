package com.auts.lcscli.event;

import com.auts.lcscli.bean.RoomBean;

/**
 * 选中的当前房间
 * Created by xiaolei.yang on 2017/8/5.
 */

public class AddDeviceSuccessEvent {
    private RoomBean roomBean;

    public AddDeviceSuccessEvent(RoomBean roomBean) {
        this.roomBean = roomBean;
    }

    public RoomBean getRoomBean() {
        return roomBean;
    }
}
