package com.auts.lcscli.service;


import com.auts.lcscli.model.dao.CaptchaModel;

public interface CaptchaService {

	
	int addCaptcha(CaptchaModel captcha);

	int delCaptcha(String phoneNo);

	int updateCaptcha(CaptchaModel captcha);
	
	CaptchaModel queryCaptchaByPhoneNo(String phoneNo);
}