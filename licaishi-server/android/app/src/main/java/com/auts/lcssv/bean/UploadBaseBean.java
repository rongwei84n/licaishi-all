package com.auts.lcssv.bean;

import java.io.Serializable;

/**
 * 上传Base64字符串
 * Created by xiaolei.yang on 2017/7/24.
 */

public class UploadBaseBean implements Serializable {
    private static final long serialVersionUID = -1688407490893365889L;

    private String error; //错误码 0：上传成功 18：图片格式错误 19:图片为空 50：服务器异常
    private String url; //上传图片接口返回,头像url
    private String avatarUrl;//获取头像接口返回,头像url

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "UploadBaseBean{" +
                "error='" + error + '\'' +
                ", url='" + url + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
