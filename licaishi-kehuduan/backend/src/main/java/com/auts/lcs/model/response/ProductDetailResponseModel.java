package com.auts.lcs.model.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.auts.lcs.model.dao.product.ProductAttachmentModel;
import com.auts.lcs.model.dao.product.ProfitRebateModel;

/**
 * 产品接口返回数据JSON
 * 
 * @author li.bing
 * @date 2018年2月27日
 */
public class ProductDetailResponseModel {
	private String pId;
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
    private String pInvestName;
    private BigDecimal pAllSubscriptionAmount;
    private String pRecruitmentSummary;
    private String pCpys;
    private String pMjzh;
    private String pFxkz;
    private String pHkly;
    private String pZjyt;
    private String pRrzf;
    private String pDbf;
    private List<ProfitRebateModel> profitRebates;
    private List<ProductAttachmentModel> productAttachments;
    private String pCommission; //佣金比例
    private String pLatestPayNum;//最迟打款天数
	private String pRexiao;//是否热销
	private String pTuijian;//是否推荐
	private String pRgxz; //认购须知
    
	public String getpLatestPayNum() {
		return pLatestPayNum;
	}
	public void setpLatestPayNum(String pLatestPayNum) {
		this.pLatestPayNum = pLatestPayNum;
	}
	public String getpRexiao() {
		return pRexiao;
	}
	public void setpRexiao(String pRexiao) {
		this.pRexiao = pRexiao;
	}
	public String getpTuijian() {
		return pTuijian;
	}
	public void setpTuijian(String pTuijian) {
		this.pTuijian = pTuijian;
	}
	public String getpRgxz() {
		return pRgxz;
	}
	public void setpRgxz(String pRgxz) {
		this.pRgxz = pRgxz;
	}
	public String getpCommission() {
		return pCommission;
	}
	public void setpCommission(String pCommission) {
		this.pCommission = pCommission;
	}
	public List<ProductAttachmentModel> getProductAttachments() {
		return productAttachments;
	}
	public void setProductAttachments(List<ProductAttachmentModel> productAttachments) {
		this.productAttachments = productAttachments;
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
	public BigDecimal getpAllSubscriptionAmount() {
		return pAllSubscriptionAmount;
	}
	public void setpAllSubscriptionAmount(BigDecimal pAllSubscriptionAmount) {
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
	public String getpRrzf() {
		return pRrzf;
	}
	public void setpRrzf(String pRrzf) {
		this.pRrzf = pRrzf;
	}
	public String getpDbf() {
		return pDbf;
	}
	public void setpDbf(String pDbf) {
		this.pDbf = pDbf;
	}
	public List<ProfitRebateModel> getProfitRebates() {
		return profitRebates;
	}
	public void setProfitRebates(List<ProfitRebateModel> profitRebates) {
		this.profitRebates = profitRebates;
	}
    public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getpInvestName() {
		return pInvestName;
	}
	public void setpInvestName(String pInvestName) {
		this.pInvestName = pInvestName;
	}
}
