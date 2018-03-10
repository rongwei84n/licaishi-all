package com.auts.backstage.model.dao.order;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.auts.backstage.model.common.BaseDaoModel;
import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderModel extends BaseDaoModel {

	private int uid;
    private String orderNo;
    private BigDecimal amount;
    private String orderDate;
    private String latestPayDate;
    private String financerUid;
    private String financer;
    private String financerTel;
    private String customerUid;
    private String customer;
    private String customerTel;
    private String productId;
    private String product;//产品全称
    private String proShortNam;//产品简称
    private String inst;//发行机构
    private String comRatio;//佣金比率
    private BigDecimal commission;//佣金
    private String proRatio;//收益比率
    private BigDecimal profit;//收益
    private String status;//状态
    private String contractStatus;//合同状态
    private String voucherStatus;//凭证状态
    private String voucherPath;//凭证路径
    private String issuingBank;//发卡银行
    private String cardNo;//银行卡号
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatetime;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
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
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getFinancer() {
		return financer;
	}
	public void setFinancer(String financer) {
		this.financer = financer;
	}
	public String getFinancerTel() {
		return financerTel;
	}
	public void setFinancerTel(String financerTel) {
		this.financerTel = financerTel;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCustomerTel() {
		return customerTel;
	}
	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getProShortNam() {
		return proShortNam;
	}
	public void setProShortNam(String proShortNam) {
		this.proShortNam = proShortNam;
	}
	public String getInst() {
		return inst;
	}
	public void setInst(String inst) {
		this.inst = inst;
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
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
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
	public String getIssuingBank() {
		return issuingBank;
	}
	public void setIssuingBank(String issuingBank) {
		this.issuingBank = issuingBank;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getLatestPayDate() {
		return latestPayDate;
	}
	public void setLatestPayDate(String latestPayDate) {
		this.latestPayDate = latestPayDate;
	}
    
}