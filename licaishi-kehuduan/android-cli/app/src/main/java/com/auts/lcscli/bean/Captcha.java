package com.auts.lcscli.bean;

/**
 * 图形验证码
 * Created by qisheng.lv on 2017/7/11.
 */
public class Captcha extends BaseResponse {
    private static final long serialVersionUID = 1445111617397166837L;
    private String captcha;

    private String captchaid;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getCaptchaid() {
        return captchaid;
    }

    public void setCaptchaid(String captchaid) {
        this.captchaid = captchaid;
    }

}
