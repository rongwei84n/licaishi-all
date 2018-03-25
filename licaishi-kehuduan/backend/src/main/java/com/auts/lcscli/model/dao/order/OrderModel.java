package com.auts.lcscli.model.dao.order;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.auts.lcscli.model.common.BaseDaoModel;
import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderModel extends BaseDaoModel {

    private String orderNo;
    private BigDecimal amount;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date latestPayDate;
    private String financerUid;
    private String customerUid;
    private String productId;
    private String comRatio;//佣金比率
    private BigDecimal commission;//佣金
    private String proRatio;//收益比率
    private BigDecimal profit;//收益
    private String status;//状态
    private String contractStatus;//合同状态
    private String voucherStatus;//凭证状态
    private String voucherPath;//凭证路径
    private String issueBank;//发卡行
    private String cardNo;//银行卡号
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getLatestPayDate() {
		return latestPayDate;
	}
	public void setLatestPayDate(Date latestPayDate) {
		this.latestPayDate = latestPayDate;
	}
	public String getComRatio() {
		return comRatio;
	}
	public void setComRatio(String comRatio) {
		this.comRatio = comRatio;
	}
	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	public String getProRatio() {
		return proRatio;
	}
	public void setProRatio(String proRatio) {
		this.proRatio = proRatio;
	}
	public BigDecimal getProfit() {
		return profit;
	}
	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getVoucherStatus() {
		return voucherStatus;
	}
	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
	}
	public String getVoucherPath() {
		return voucherPath;
	}
	public void setVoucherPath(String voucherPath) {
		this.voucherPath = voucherPath;
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
	public String getFinancerUid() {
		return financerUid;
	}
	public void setFinancerUid(String financerUid) {
		this.financerUid = financerUid;
	}
	public String getCustomerUid() {
		return customerUid;
	}
	public void setCustomerUid(String customerUid) {
		this.customerUid = customerUid;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
    public String getIssueBank() {
		return issueBank;
	}
	public void setIssueBank(String issueBank) {
		this.issueBank = issueBank;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
}