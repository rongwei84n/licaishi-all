package com.auts.lcs.model.response;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.auts.lcs.model.common.BaseDaoModel;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CommissionOrderResponseDto extends BaseDaoModel {

    private String orderNo;
    private BigDecimal amount;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    private String customerUid;
    private String customerName;//客户姓名
    private String productId;
    private String productShortName; //产品简称
	private String comRatio;//佣金比率
    private BigDecimal commission;//佣金
    private String status;//状态
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
    public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductShortName() {
		return productShortName;
	}
	public void setProductShortName(String productShortName) {
		this.productShortName = productShortName;
	}
}