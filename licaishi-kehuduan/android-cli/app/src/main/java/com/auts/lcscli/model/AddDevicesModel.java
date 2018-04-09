package com.auts.lcscli.model;

import com.auts.lcscli.constants.UrlConfig;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.engine.OkHttpUtil;

/**
 * 获取设备类型列表
 *
 * @author xiaolei.yang
 * @date 2017/8/9
 */

public class AddDevicesModel {
    public void deviceTypes(BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.DEVICE_TYPES)
                .run(null, callback);
    }

    public void timestamp(BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.TIMESTAMP)
                .run(null, callback);
    }
}
