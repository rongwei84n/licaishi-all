package com.auts.lcs.model.dao.product;

import java.math.BigDecimal;

import com.auts.lcs.model.common.BaseDaoModel;

public class ProfitRebateModel extends BaseDaoModel {

    private String prProductCode; //产品的唯一标示
    private BigDecimal prStartAmount; //起购开始金额
    private BigDecimal prEndAmount; //起购最大金额
    private String prAmountDisplay; //描述
    private String prExpectAnnualRevenue; //预计年化收益
    
    public String getPrExpectAnnualRevenue() {
		return prExpectAnnualRevenue;
	}
	public void setPrExpectAnnualRevenue(String prExpectAnnualRevenue) {
		this.prExpectAnnualRevenue = prExpectAnnualRevenue;
	}
	private String prCommission; //佣金比例
    
	public String getPrProductCode() {
		return prProductCode;
	}
	public void setPrProductCode(String prProductCode) {
		this.prProductCode = prProductCode;
	}
	public BigDecimal getPrStartAmount() {
		return prStartAmount;
	}
	public void setPrStartAmount(BigDecimal prStartAmount) {
		this.prStartAmount = prStartAmount;
	}
	public BigDecimal getPrEndAmount() {
		return prEndAmount;
	}
	public void setPrEndAmount(BigDecimal prEndAmount) {
		this.prEndAmount = prEndAmount;
	}
	public String getPrAmountDisplay() {
		return prAmountDisplay;
	}
	public void setPrAmountDisplay(String prAmountDisplay) {
		this.prAmountDisplay = prAmountDisplay;
	}
	public String getPrCommission() {
		return prCommission;
	}
	public void setPrCommission(String prCommission) {
		this.prCommission = prCommission;
	}
    
}