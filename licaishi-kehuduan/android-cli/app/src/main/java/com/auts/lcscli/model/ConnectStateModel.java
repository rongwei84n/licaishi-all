package com.auts.lcscli.model;

import com.auts.lcscli.constants.UrlConfig;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.engine.OkHttpUtil;

/**
 * 设备连接状态
 * Created by xiaolei.yang on 2017/8/8.
 */

public class ConnectStateModel {

    //检查设备绑定状态
    public void checkBindStatus(String deviceId, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.CHECK_BIND_STATUS + "/" + deviceId + "/check_bind_status")
                .run("check_bind_status", callback);
    }

    public void cancelCheckBindStatus(){
        OkHttpUtil.cancelRequest("check_bind_status");
    }
}
