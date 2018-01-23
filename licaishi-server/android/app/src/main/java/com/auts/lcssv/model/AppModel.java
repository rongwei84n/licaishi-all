package com.auts.lcssv.model;

import com.auts.lcssv.constants.UrlConfig;
import com.auts.lcssv.net.callback.BaseCallback;
import com.auts.lcssv.net.engine.OkHttpUtil;

/**
 * App数据相关model
 * Created by weiming.zeng on 2017/8/24.
 */

public class AppModel {

    //获取功能介绍详细信息
    public void getInstroduction(BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.GET_INSTRODUCTION)
                .run(null, callback);
    }
}
