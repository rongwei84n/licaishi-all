package com.auts.lcs.model.response;

import java.math.BigDecimal;
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
	@JSONField(name = "pCode")
    @JsonProperty("pCode")
	private String pCode;
	@JSONField(name = "pShortName")
    @JsonProperty("pShortName")
    private String pShortName;
	@JSONField(name = "pFullName")
    @JsonProperty("pFullName")
    private String pFullName;
	@JSONField(name = "pType")
    @JsonProperty("pType")
    private String pType;
	@JSONField(name = "pExpectAnnualRevenue")
    @JsonProperty("pExpectAnnualRevenue")
    private String pExpectAnnualRevenue;
	@JSONField(name = "pSaleStatus")
    @JsonProperty("pSaleStatus")
    private String pSaleStatus;
	@JSONField(name = "pDulTime")
    @JsonProperty("pDulTime")
    private String pDulTime;
	@JSONField(name = "pSaleStartTime")
    @JsonProperty("pSaleStartTime")
    private Date pSaleStartTime;
	@JSONField(name = "pAllIssuingScale")
    @JsonProperty("pAllIssuingScale")
    private BigDecimal pAllIssuingScale;
	@JSONField(name = "pMinAmount")
    @JsonProperty("pMinAmount")
    private BigDecimal pMinAmount;
	@JSONField(name = "pPaymentInterestType")
    @JsonProperty("pPaymentInterestType")
    private String pPaymentInterestType;
	@JSONField(name = "pInvestType")
    @JsonProperty("pInvestType")
    private String pInvestType;
	@JSONField(name = "pSizeRatioType")
    @JsonProperty("pSizeRatioType")
    private String pSizeRatioType;
	@JSONField(name = "pInvestOwnerId")
    @JsonProperty("pInvestOwnerId")
    private String pInvestOwnerId;
	@JSONField(name = "profitRebates")
    @JsonProperty("profitRebates")
    private List<ProfitRebateModel> profitRebates;
    private BigDecimal pIssuingScale;
    private BigDecimal pExpectSaleAmount;
    private BigDecimal pAllSubscriptionAmount;
	
	public BigDecimal getpIssuingScale() {
		return pIssuingScale;
	}
	public void setpIssuingScale(BigDecimal pIssuingScale) {
		this.pIssuingScale = pIssuingScale;
	}
	public BigDecimal getpExpectSaleAmount() {
		return pExpectSaleAmount;
	}
	public void setpExpectSaleAmount(BigDecimal pExpectSaleAmount) {
		this.pExpectSaleAmount = pExpectSaleAmount;
	}
	public BigDecimal getpAllSubscriptionAmount() {
		return pAllSubscriptionAmount;
	}
	public void setpAllSubscriptionAmount(BigDecimal pAllSubscriptionAmount) {
		this.pAllSubscriptionAmount = pAllSubscriptionAmount;
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
	public BigDecimal getpAllIssuingScale() {
		return pAllIssuingScale;
	}
	public void setpAllIssuingScale(BigDecimal pAllIssuingScale) {
		this.pAllIssuingScale = pAllIssuingScale;
	}
	public BigDecimal getpMinAmount() {
		return pMinAmount;
	}
	public void setpMinAmount(BigDecimal pMinAmount) {
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
	public List<ProfitRebateModel> getProfitRebates() {
		return profitRebates;
	}
	public void setProfitRebates(List<ProfitRebateModel> profitRebates) {
		this.profitRebates = profitRebates;
	}
}
