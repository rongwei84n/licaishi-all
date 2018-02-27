package com.auts.lcs.model.dao.product;

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

	@JsonProperty("time_limit")
    @JSONField(name = "time_limit")
    private String timeLimit;

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }
}