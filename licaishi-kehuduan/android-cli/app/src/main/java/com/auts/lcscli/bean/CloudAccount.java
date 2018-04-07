package com.auts.lcscli.bean;

import java.io.Serializable;

/**
 * 云账号登陆结果
 *
 * @author qisheng.lv
 * @date 2017/4/12
 */
public class CloudAccount extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -5744549194590221767L;

    private String access_token;

    private String access_token_expire;

    private String refresh_token;

    private String refresh_token_expire;

    private String uid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token_expire() {
        return access_token_expire;
    }

    public void setAccess_token_expire(String access_token_expire) {
        this.access_token_expire = access_token_expire;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token_expire() {
        return refresh_token_expire;
    }

    public void setRefresh_token_expire(String refresh_token_expire) {
        this.refresh_token_expire = refresh_token_expire;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
