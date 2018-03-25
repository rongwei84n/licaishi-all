package com.auts.lcscli.model.dao;
/**
 * 短信验证码Model
 * @author libing
 *
 */
public class CaptchaModel {

    private String phoneNo;

    private String captchaCode;

    private long sendTime;


    public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getCaptchaCode() {
		return captchaCode;
	}


	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}


	public long getSendTime() {
		return sendTime;
	}


	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}


	@Override
    public String toString() {
        return "CaptchaModel [phoneNo=" + phoneNo + ", captchaCode=" + captchaCode 
        		+ ", sendTime=" + sendTime;
    }
}
