package com.auts.lcs.model.response;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.auts.lcs.model.dao.product.ProfitRebateModel;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 产品接口返回数据JSON
 * 
 * @author li.bing
 * @date 2018年2月27日
 */
public class ProductResponseModel {
	
	private String pCode;
    private String pShortName;
    private String pFullName;
    private String pType;
    private String pExpectAnnualRevenue;
    private String pSaleStatus;
    private String pDulTime;
    private Date pSaleStartTime;
    private String pAllIssuingScale;
    private String pMinAmount;
    private String pPaymentInterestType;
    private String pInvestType;
    private String pSizeRatioType;
    private String pInvestOwnerId;
    private List<ProfitRebateModel> profitRebateList;

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
