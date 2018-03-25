package com.auts.lcscli.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auts.lcscli.dao.CaptchaMapper;
import com.auts.lcscli.model.dao.CaptchaModel;
import com.auts.lcscli.service.CaptchaService;


@Service
public class CaptchaServiceImpl implements CaptchaService{
	
	@Autowired
	CaptchaMapper captchaMapper;

	@Override
	public int addCaptcha(CaptchaModel captcha) {
		if(captchaMapper.queryByPhoneNo(captcha.getPhoneNo()) != null) {
			return captchaMapper.updateCaptcha(captcha);
		} else {
			return captchaMapper.insert(captcha);
		}
	}

	@Override
	public int delCaptcha(String phoneNo) {
		return captchaMapper.delCaptcha(phoneNo);
	}

	@Override
	public int updateCaptcha(CaptchaModel captcha) {
		return captchaMapper.updateCaptcha(captcha);
	}

	@Override
	public CaptchaModel queryCaptchaByPhoneNo(String phoneNo) {
		return captchaMapper.queryByPhoneNo(phoneNo);
	}

}