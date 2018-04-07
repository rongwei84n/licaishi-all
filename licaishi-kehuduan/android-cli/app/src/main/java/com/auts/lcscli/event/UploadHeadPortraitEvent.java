package com.auts.lcscli.event;

/**
 * 上传头像成功
 * Created by xiaolei.yang on 2017/7/24.
 */

public class UploadHeadPortraitEvent {
    private String url;

    public UploadHeadPortraitEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
