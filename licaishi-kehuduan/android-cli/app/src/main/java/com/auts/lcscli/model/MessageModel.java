package com.auts.lcscli.model;

import com.auts.lcscli.constants.UrlConfig;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.engine.OkHttpUtil;

/**
 * 消息提醒model
 * Created by weiming.zeng on 2017/8/25.
 */

public class MessageModel {

    public void getMessages(int page, int page_size, long latestTimestampm, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.GET_MESSAGES)
                .addParams("page", String.valueOf(page))
                .addParams("page_size", String.valueOf(page_size))
                .addParams("latest_timestamp", String.valueOf(latestTimestampm))
                .run(null, callback);
    }

    public void checkMsgReadStatus(BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.CHECK_UNREADMESSAGES)
                .run(null, callback);
    }

}
