package com.auts.lcs.model.response;

import org.hibernate.validator.constraints.NotBlank;

public class CustomerListResponseDto {

	private int uid;
	private int userId;
	private int financerId; //所属于理财师的UID
	@NotBlank(message = "用户名不能为空")
	private String name;
	@NotBlank(message = "手机号不能为空")
	private String phone;

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