package com.auts.lcscli.model;

import com.auts.lcscli.constants.UrlConfig;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.engine.OkHttpUtil;

/**
 * Created by weiming.zeng on 2017/8/8.
 */

public class HouseManageModel {


    /**
     * 获取用户家庭
     */
    public void getHouseData(BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.HOUSE_URL)
                .run(null, callback);
    }

    /**
     * 修改家庭
     */
    public void modifyHouseInfo(String familyId, String family_pic_url, String family_nickname, int position, BaseCallback callback) {
        OkHttpUtil.postJson(UrlConfig.SzUrl.HOUSE_MODIFY_URL + familyId)
                .addParams("family_pic_url", family_pic_url)
                .addParams("family_nickname", family_nickname)
                .addParams("position", String.valueOf(position))
                .run(null, callback);
    }

    /**
     * 获取家庭房间
     */
    public void getRoomData(String familyId, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.HOUSE_MODIFY_URL + familyId + "/rooms")
                .run(null, callback);
    }

    /**
     * 添加家庭房间
     */
    public void addRoomInfo(String familyId, String room_pic_url, String room_nickname, BaseCallback callback) {
        OkHttpUtil.postJson(UrlConfig.SzUrl.HOUSE_MODIFY_URL + familyId + "/room")
                .addParams("room_pic_url", room_pic_url)
                .addParams("room_nickname", room_nickname)
                .run(null, callback);
    }


    /**
     * 修改家庭房间信息
     */
    public void modifyRoomInfo(String familyId, String roomId, String room_pic_url, String room_nickname, BaseCallback callback, int position) {
        OkHttpUtil.postJson(UrlConfig.SzUrl.HOUSE_MODIFY_URL + familyId + "/room/" + roomId)
                .addParams("room_pic_url", room_pic_url)
                .addParams("room_nickname", room_nickname)
                .addParams("position", String.valueOf(position))
                .run(null, callback);
    }

    /**
     * 删除房间
     */
    public void deleteRoomInfo(String familyId, String roomId, String room_pic_url, String room_nickname, int position, BaseCallback callback) {
        OkHttpUtil.delete(UrlConfig.SzUrl.HOUSE_MODIFY_URL + familyId + "/room/" + roomId)
                .run(null, callback);
    }

    /**
     * 拉去图片列表
     */
    public void getPicData(String pic_type, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.URL_HOST + "/v1/" + pic_type + "/pictures")
                .run(null, callback);
    }
}
