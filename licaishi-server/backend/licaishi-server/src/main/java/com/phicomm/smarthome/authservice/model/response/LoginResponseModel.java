package com.phicomm.smarthome.authservice.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseModel {

    @JSONField(name = "access_token")
    @JsonProperty("access_token")
    private String accessToken;

    @JSONField(name = "access_token_expire")
    @JsonProperty("access_token_expire")
    private String accessTokenExpire;

    private String error;

    @JSONField(name = "refresh_token")
    @JsonProperty("refresh_token")
    private String refreshToken;

    @JSONField(name = "refresh_token_expire")
    @JsonProperty("refresh_token_expire")
    private String refreshTokenExpire;

    private String uid;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(String accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setRefreshTokenExpire(String refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
