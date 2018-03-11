package com.auts.lcs.model.response;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CustomerResponseDto {

	private int uid;
	private int userId;
	private int financerId; //所属于理财师的UID
	@NotBlank(message = "用户名不能为空")
	private String name;
	@NotBlank(message = "手机号不能为空")
	private String phone;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;
	private int orderCounts;

	public int getOrderCounts() {
		return orderCounts;
	}
	public void setOrderCounts(int orderCounts) {
		this.orderCounts = orderCounts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getFinancerId() {
		return financerId;
	}
	public void setFinancerId(int financerId) {
		this.financerId = financerId;
	}
}