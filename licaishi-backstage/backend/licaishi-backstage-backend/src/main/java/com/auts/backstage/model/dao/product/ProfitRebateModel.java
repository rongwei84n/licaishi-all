package com.auts.backstage.model.dao.product;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.auts.backstage.model.common.BaseDaoModel;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ProfitRebateModel extends BaseDaoModel {

    private String prProductCode; //产品的唯一标示
    private BigDecimal prStartAmount; //起购开始金额
    private BigDecimal prEndAmount; //起购最大金额
    private String prAmountDisplay; //描述
    private String prExpectAnnualRevenue; //预计年化收益
	private String prCommission; //佣金比例
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date prCreateTime;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date prUpdateTime;
    
	public Date getPrCreateTime() {
		return prCreateTime;
	}
	public void setPrCreateTime(Date prCreateTime) {
		this.prCreateTime = prCreateTime;
	}
	public Date getPrUpdateTime() {
		return prUpdateTime;
	}
	public void setPrUpdateTime(Date prUpdateTime) {
		this.prUpdateTime = prUpdateTime;
	}
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
    public String getPrExpectAnnualRevenue() {
		return prExpectAnnualRevenue;
	}
	public void setPrExpectAnnualRevenue(String prExpectAnnualRevenue) {
		this.prExpectAnnualRevenue = prExpectAnnualRevenue;
	}
}