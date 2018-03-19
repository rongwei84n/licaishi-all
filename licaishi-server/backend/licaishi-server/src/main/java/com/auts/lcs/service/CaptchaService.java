package com.auts.lcs.service;


import com.auts.lcs.model.dao.CaptchaModel;

public interface CaptchaService {

	
	int addCaptcha(CaptchaModel captcha);

	int delCaptcha(String phoneNo);

	int updateCaptcha(CaptchaModel captcha);
	
	CaptchaModel queryCaptchaByPhoneNo(String phoneNo);
}