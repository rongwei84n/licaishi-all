package com.auts.backstage.model.dao.product;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.auts.backstage.model.common.BaseDaoModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductModel extends BaseDaoModel {

	private String pCode; //产品code 后台API自己生成
    private String pShortName; //产品简称
    private String pFullName;//产品全称
    private String pType; //产品类型
    private String pExpectAnnualRevenue;//预期年化收益
    private String pSaleStatus; //产品销售状态 热销中等
    private String pDulTime; //发行期限
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pSaleStartTime; //发行开始日期
    private String pAllIssuingScale;//募集规模
    private String pMinAmount; //最少起投金额 100W
    private String pPaymentInterestType; //付息方式
    private String pInvestType; //投资领域
    private String pSizeRatioType;//大小配比
    private String pInvestOwnerId;//发行机构
    private String pAllSubscriptionAmount;// 已销售金额
    private String pRecruitmentSummary;//产品summary
    private int latestPayNum; //最迟打款天数
    private String pTgjg; //托管机构
	private String pCpys;//产品优势
    private String pMjzh;//募集账号
    private String pFxkz;//风险控制
    private String pHkly;//还款来源
    private String pZjyt;//资金用途
    private String pRzf;//融资方
    private String pDbf;//担保方
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
	private List<ProfitRebateModel> profitRebates;//不同起投金额，不同收益率和佣金
	private List<ProductAttachmentModel> productAttachments;//预览资料
    private String pLatestPayNum;//最迟打款天数
	private String pRexiao;//是否热销
	private String pTuijian;//是否推荐
	private String pRgxz; //认购须知
	private String pCommission;//返点佣金

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

	public List<ProfitRebateModel> getProfitRebates() {
		return profitRebates;
	}

	public void setProfitRebates(List<ProfitRebateModel> profitRebates) {
		this.profitRebates = profitRebates;
	}

	public List<ProductAttachmentModel> getProductAttachments() {
		return productAttachments;
	}

	public void setProductAttachments(List<ProductAttachmentModel> productAttachments) {
		this.productAttachments = productAttachments;
	}

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
    public int getLatestPayNum() {
		return latestPayNum;
	}

	public void setLatestPayNum(int latestPayNum) {
		this.latestPayNum = latestPayNum;
	}
	
	public String getpTgjg() {
		return pTgjg;
	}

	public void setpTgjg(String pTgjg) {
		this.pTgjg = pTgjg;
	}
}