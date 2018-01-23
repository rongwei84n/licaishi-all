package com.auts.lcssv.model;

import com.auts.lcssv.constants.UrlConfig;
import com.auts.lcssv.net.callback.BaseCallback;
import com.auts.lcssv.net.engine.OkHttpUtil;

/**
 * Created by qisheng.lv on 2017/8/18.
 */

public class UpdateModel {

    public void appUpdate(int id, String platid, int vercode, String channel, BaseCallback callback) {
        OkHttpUtil.postJson(UrlConfig.SzUrl.UPDATE_APP_CHECK)
                .needCommonHeader(false)
                .addParams("id", id + "")
                .addParams("platid", platid)
                .addParams("vercode", vercode + "")
                .addParams("channel", channel)
                .run(null, callback);
    }


    public void roomUpdate(String device_id, BaseCallback callback) {
        OkHttpUtil.postJson(UrlConfig.SzUrl.UPDATE_ROOM_CHECK)
                .addParams("deviceId", device_id)
                .run(null, callback);
    }


}
