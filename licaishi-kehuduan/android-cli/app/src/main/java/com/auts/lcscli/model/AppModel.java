package com.auts.lcscli.model;

import com.auts.lcscli.constants.UrlConfig;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.engine.OkHttpUtil;

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
