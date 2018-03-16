package com.auts.lcs.model.dao.product;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.auts.lcs.model.common.BaseDaoModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductModel extends BaseDaoModel {

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
    private String pAllSubscriptionAmount;
    private String pRecruitmentSummary;
    private String pCpys;
    private String pMjzh;
    private String pFxkz;
    private String pHkly;
    private String pZjyt;
    private String pRzf;
    private String pDbf;
    private Date createTime;
    private Date updateTime;
    
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

    public String getpAllSubscriptionAmount() {
        return pAllSubscriptionAmount;
    }

    public void setpAllSubscriptionAmount(String pAllSubscriptionAmount) {
        this.pAllSubscriptionAmount = pAllSubscriptionAmount;
    }

    public String getpRecruitmentSummary() {
		return pRecruitmentSummary;
	}

	public void setpRecruitmentSummary(String pRecruitmentSummary) {
		this.pRecruitmentSummary = pRecruitmentSummary;
	}

	public String getpCpys() {
		return pCpys;
	}

	public void setpCpys(String pCpys) {
		this.pCpys = pCpys;
	}

	public String getpMjzh() {
		return pMjzh;
	}

	public void setpMjzh(String pMjzh) {
		this.pMjzh = pMjzh;
	}

	public String getpFxkz() {
		return pFxkz;
	}

	public void setpFxkz(String pFxkz) {
		this.pFxkz = pFxkz;
	}

	public String getpHkly() {
		return pHkly;
	}

	public void setpHkly(String pHkly) {
		this.pHkly = pHkly;
	}

	public String getpZjyt() {
		return pZjyt;
	}

	public void setpZjyt(String pZjyt) {
		this.pZjyt = pZjyt;
	}

	public String getpRzf() {
		return pRzf;
	}

	public void setpRzf(String pRzf) {
		this.pRzf = pRzf;
	}

	public String getpDbf() {
		return pDbf;
	}

	public void setpDbf(String pDbf) {
		this.pDbf = pDbf;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getpShortName() {
		return pShortName;
	}

	public void setpShortName(String pShortName) {
		this.pShortName = pShortName;
	}

	public String getpFullName() {
		return pFullName;
	}

	public void setpFullName(String pFullName) {
		this.pFullName = pFullName;
	}

	public String getpType() {
		return pType;
	}

	public void setpType(String pType) {
		this.pType = pType;
	}

	public String getpExpectAnnualRevenue() {
		return pExpectAnnualRevenue;
	}

	public void setpExpectAnnualRevenue(String pExpectAnnualRevenue) {
		this.pExpectAnnualRevenue = pExpectAnnualRevenue;
	}

	public String getpSaleStatus() {
		return pSaleStatus;
	}

	public void setpSaleStatus(String pSaleStatus) {
		this.pSaleStatus = pSaleStatus;
	}

	public String getpDulTime() {
		return pDulTime;
	}

	public void setpDulTime(String pDulTime) {
		this.pDulTime = pDulTime;
	}

	public Date getpSaleStartTime() {
		return pSaleStartTime;
	}

	public void setpSaleStartTime(Date pSaleStartTime) {
		this.pSaleStartTime = pSaleStartTime;
	}

	public String getpAllIssuingScale() {
        return pAllIssuingScale;
    }

    public void setpAllIssuingScale(String pAllIssuingScale) {
        this.pAllIssuingScale = pAllIssuingScale;
    }

	public String getpMinAmount() {
        return pMinAmount;
    }

    public void setpMinAmount(String pMinAmount) {
        this.pMinAmount = pMinAmount;
    }

    public String getpPaymentInterestType() {
		return pPaymentInterestType;
	}

	public void setpPaymentInterestType(String pPaymentInterestType) {
		this.pPaymentInterestType = pPaymentInterestType;
	}

	public String getpInvestType() {
		return pInvestType;
	}

	public void setpInvestType(String pInvestType) {
		this.pInvestType = pInvestType;
	}

	public String getpSizeRatioType() {
		return pSizeRatioType;
	}

	public void setpSizeRatioType(String pSizeRatioType) {
		this.pSizeRatioType = pSizeRatioType;
	}

	public String getpInvestOwnerId() {
		return pInvestOwnerId;
	}

	public void setpInvestOwnerId(String pInvestOwnerId) {
		this.pInvestOwnerId = pInvestOwnerId;
	}
}