package com.auts.lcscli.model;

import android.text.TextUtils;

import com.auts.lcscli.constants.UrlConfig;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.engine.OkHttpUtil;

/**
 * 添加设备过程中，家庭相关的
 * Created by xiaolei.yang on 2017/8/9.
 */

public class FamilyModel {

    public void rooms(String familyId, BaseCallback callback) {
        String url = UrlConfig.SzUrl.HOUSE_MODIFY_URL + familyId + "/rooms";
        OkHttpUtil.get(url)
                .run(null, callback);
    }

    public void modifyDevice(String deviceId, String deviceUrl, String deviceName, String room_id, BaseCallback callback) {
        String url = UrlConfig.SzUrl.MODIFTY_DEVICE_INFO + deviceId;
        OkHttpUtil.postJson(url)
                .addParams("device_pic_url", deviceUrl)
                .addParams("device_nickname", deviceName)
                .addParams("room_id", TextUtils.isEmpty(room_id) ? "0" : room_id)
                .run(null, callback);
    }

    public void addDeviceToRoom(String familyId, String roomId, String deviceId, BaseCallback callback) {
        String url = UrlConfig.SzUrl.HOUSE_MODIFY_URL + familyId + "/room/" + roomId + "/device/" + deviceId;
        OkHttpUtil.postJson(url)
                .run(null, callback);
    }

    public void addRoom(String familyId, String roomPicUrl, String roomNickname, BaseCallback callback) {
        String url = UrlConfig.SzUrl.HOUSE_MODIFY_URL + familyId + "/room";
        OkHttpUtil.postJson(url)
                .addParams("room_pic_url", roomPicUrl)
                .addParams("room_nickname", roomNickname)
                .run(null, callback);

    }
}
