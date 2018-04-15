package com.auts.lcscli.model.dao;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CustomerModel {

	private int uid;
	private int userId;
	private int financerId; //所属于理财师的UID
	@NotBlank(message = "用户名不能为空")
	private String name;
	private int sex;
	private String birthday;
	@NotBlank(message = "手机号不能为空")
	private String phone;
	private String email;
	private String address;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatetime;

	public CustomerModel() {}

	public CustomerModel(AccountModel accountModel) {
	    this.userId = Integer.parseInt(accountModel.getUid());
	    this.name = accountModel.getUser_name();
	    this.phone = accountModel.getPhone();
	    this.email = accountModel.getEmail();
	    this.address = "";
	    this.sex = accountModel.getSex();
	    this.birthday = "";

	    Date nowDate = new Date();
	    this.createtime = nowDate;
	    this.updatetime = nowDate;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
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