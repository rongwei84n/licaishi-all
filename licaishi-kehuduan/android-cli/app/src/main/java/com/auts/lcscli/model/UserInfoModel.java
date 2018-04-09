package com.auts.lcscli.model;

import com.auts.lcscli.constants.AppConstans;
import com.auts.lcscli.constants.UrlConfig;
import com.auts.lcscli.manager.AccountManager;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.engine.OkHttpUtil;

/**
 * 上传Base64字符串
 * Created by xiaolei.yang on 2017/7/24.
 */

public class UserInfoModel {

    /**
     * 上传Base64字符串
     * 该接口Header里要传入token
     *
     * @param imgBase64 图片的Base64编码
     * @param type      1-头像
     * @param callback  请求回调。
     */
    public void uploadBase64(String imgBase64, String type, BaseCallback callback) {
        OkHttpUtil.post(UrlConfig.CloudPortrait.UPLOAD_BASE64)
                .addParams("imgBase64", String.valueOf(imgBase64))
                .addParams("type", String.valueOf(type))
                .run(null, callback);
    }

    /**
     * 获取用户头像
     *
     * @param callback 请求回调。
     */
    public void avatarUrl(BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.CloudPortrait.AVATAR_URL)
                .addParams("access_token", AccountManager.getInstance().getValue(AppConstans.Sp.ACCESS_TOKEN))//可选，如果header中没有Authorization，则使用参数传入token
                .run(null, callback);
    }

    /**
     * 获取账户信息
     *
     * @param callback 请求回调。
     */
    public void accountDetail(BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.CloudAccountUrl.ACCOUNT_DETAIL)
                .run(null, callback);
    }

    /**
     * 修改账户信息
     *
     * @param callback 请求回调。
     */
    public void property(String accountDetailsString, BaseCallback callback) {
        OkHttpUtil.post(UrlConfig.CloudAccountUrl.PROPERTY)
                .addParams("data", accountDetailsString)
                .run(null, callback);
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码（用户明文密码的MD5值）
     * @param newPassword 新密码（用户明文密码的MD5值）
     * @param callback
     */
    public void password(String oldPassword, String newPassword, BaseCallback callback) {
        OkHttpUtil.post(UrlConfig.CloudAccountUrl.PASSWORD)
                .addParams("newpassword", newPassword)
                .addParams("oldpassword", oldPassword)
                .run(null, callback);
    }

    public void oldAccountVerification(String mailAddress, String phoneNumber, String verificationCode, BaseCallback callback) {
        OkHttpUtil.post(UrlConfig.CloudAccountUrl.OLD_ACCOUNT_VERIFICATION)
                .addParams("mailaddress", mailAddress)
                .addParams("phonenumber", phoneNumber)
                .addParams("verificationcode", verificationCode)
                .run(null, callback);
    }


    public void rebind(String mailAddress, String phoneNumber, String verificationCode, BaseCallback callback) {
        OkHttpUtil.post(UrlConfig.CloudAccountUrl.REBIND)
                .addParams("mailaddress", mailAddress)
                .addParams("phonenumber", phoneNumber)
                .addParams("verificationcode", verificationCode)
                .run(null, callback);
    }


}
