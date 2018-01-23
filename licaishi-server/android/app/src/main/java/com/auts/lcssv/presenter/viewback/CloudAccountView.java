package com.auts.lcssv.presenter.viewback;


import com.auts.lcssv.bean.Captcha;
import com.auts.lcssv.bean.CloudAccount;

/**
 * 云账号相关View回调
 * Created by qisheng.lv on 2017/4/12.
 */
public class CloudAccountView {
    public void onAuthorizationError(String code, String msg) {

    }

    public void onAuthorizationSuccess(String authorizationcode) {

    }

    public void onLoginError(String code, String msg) {

    }

    public void onLoginSuccess(CloudAccount cloudAccount) {

    }

    public void onLogoutError(String code, String msg) {

    }

    public void onLogoutSuccess() {

    }


    public void onCheckPhoneError(String code, String msg) {

    }

    public void onCheckPhoneSuccess(boolean isExist) {

    }

    public void onGetCaptchaError(String code, String msg) {

    }

    public void onGetCaptchaSuccess(Captcha captcha) {

    }


    public void onGetVerCodeError(String code, String msg) {

    }

    public void onGetVerCodeSuccess() {

    }

    public void onCheckVerCodeError(String code, String msg) {

    }

    public void onCheckVerCodeSuccess() {

    }

    public void onRegisterError(String code, String msg) {

    }

    public void onRegisterSuccess() {

    }

    public void onResetPwdError(String code,String msg){

    }

    public void onResetPwdSuccess(){

    }

    public void onRefreshTokenError(String code,String msg){

    }

    public void onRefreshTokenSuccess(CloudAccount cloudAccount){

    }

}
