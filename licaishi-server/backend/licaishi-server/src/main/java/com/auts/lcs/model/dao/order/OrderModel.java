package com.auts.lcs.model.dao.order;

import java.math.BigDecimal;
import java.util.Date;

import com.auts.lcs.model.common.BaseDaoModel;

public class OrderModel extends BaseDaoModel {

    private String orderNo;
    private BigDecimal amount;
    private Date orderDate;
    private Date latestPayDate;
    private String customerName;
    private String customerCardId;
    private String customerPhoneNum;
    private String productId;
    private String productCode;
    private String productType;
    private String productName;
    private String commissionRatio;
    private BigDecimal commission;
    private String payStatus;
    private String photoStatus;
    private String contractStatus;
    private String uid;//
    
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCardId() {
		return customerCardId;
	}
	public void setCustomerCardId(String customerCardId) {
		this.customerCardId = customerCardId;
	}
	public String getCustomerPhoneNum() {
		return customerPhoneNum;
	}
	public void setCustomerPhoneNum(String customerPhoneNum) {
		this.customerPhoneNum = customerPhoneNum;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCommissionRatio() {
		return commissionRatio;
	}
	public void setCommissionRatio(String commissionRatio) {
		this.commissionRatio = commissionRatio;
	}
	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPhotoStatus() {
		return photoStatus;
	}
	public void setPhotoStatus(String photoStatus) {
		this.photoStatus = photoStatus;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
}