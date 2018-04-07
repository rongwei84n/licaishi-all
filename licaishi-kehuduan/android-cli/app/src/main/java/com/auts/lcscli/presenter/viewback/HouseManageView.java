package com.auts.lcscli.presenter.viewback;

import com.auts.lcscli.bean.HouseBean;
import com.auts.lcscli.bean.Picture;
import com.auts.lcscli.bean.RoomBean;

import java.util.List;

/**
 * Created by weiming.zeng on 2017/8/9.
 */

public class HouseManageView {
    public void getHouseDataSuccess(List<HouseBean> data) {}

    public void modifyHouseInfoSuccess(String msg) {}

    public void getRoomDataSuccess(List<RoomBean> data) {}

    public void getPicSuccess(List<Picture> data) {}

    public void saveRoomInfoSuccess(String result) {}

    public void deleteRoomInfoSuccess() {}

    public void getHouseDataError(String result, String msg) {}

    public void modifyHouseInfoError(String code, String msg) {}

    public void getRoomDataError(String result, String msg) {}

    public void getPicError(String result, String msg) {}

    public void saveRoomInfoError(String result, String msg) {}

    public void deleteRoomInfoError(String result, String msg) {}
}
