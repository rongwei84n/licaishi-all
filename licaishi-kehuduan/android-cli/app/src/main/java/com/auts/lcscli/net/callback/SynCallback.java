package com.auts.lcscli.net.callback;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auts.lcscli.activity.LoginCloudActivity;
import com.auts.lcscli.bean.LogoutReason;
import com.auts.lcscli.bean.SzResponse;
import com.auts.lcscli.listener.DialogOnClickListener;
import com.auts.lcscli.manager.AccountManager;
import com.auts.lcscli.net.engine.OkHttpUtil;
import com.auts.lcscli.util.AppManager;
import com.auts.lcscli.util.LogUtils;
import com.auts.lcscli.util.ToastUtil;
import com.auts.lcscli.views.LogoutDialog;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * JSBridge-网络请求callback基类-同步
 * Created by qisheng.lv on 2017/4/12.
 */
public abstract class SynCallback extends BaseCallback {
    //已默认继承onErrror(int code,String msg)

    private LogoutDialog mLogoutDialg;

    /**
     * 最终UI线程回调：请求成功
     *
     * @param code
     */
    public abstract void onSuccess(int code, String netResponse);

    public void onSuccess(String result, Request request) {

    }

    @Override
    public void onError(String code, String msg) {

    }

    /**
     * OkHttp失败回调
     *
     * @param call
     * @param e
     */
    @Override
    public void onFailure(Call call, IOException e) {
        if (e instanceof SocketTimeoutException) {
            onError(113, "网络异常，请稍候再试");
        } else {
            onError(111, "网络异常，请稍候再试");
        }
    }


    /**
     * OkHttp响应回调
     *
     * @param call
     * @param response
     * @throws IOException
     */
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response == null) {
            onError(114, "服务异常，请稍候再试");
            return;
        }
//        if (response.isSuccessful()) {
//            onSucessResponse(response);
//        } else {
//            onError(111, "未知错误");
//        }
        onSucessResponse(response);
    }

    /**
     * 成功收到响应，在此对数据进行预处理，此处仍然是子线程
     *
     * @param response
     */
    private void onSucessResponse(Response response) {
        ResponseBody body = response.body();
        if (body == null) {
            onError(114, "服务异常，请稍候再试");
        } else {
            String bodyStr = null;
            try {
                bodyStr = body.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(bodyStr)) {
                onError(114, "服务异常，请稍候再试");
                return;
            }
//            onSuccess(0, bodyStr);
            parseSzResponse(bodyStr, response);
        }
    }

    private void parseSzResponse(String bodyStr, Response response) {
        SzResponse szObj;
        try {
            szObj = JSON.parseObject(bodyStr, SzResponse.class);
        } catch (Exception e) {
            onError(114, "服务异常，请稍候再试");
            return;
        }

        int status = szObj.getStatus();
        String message = szObj.getMessage();
        String resultStr = szObj.getResult();

        if (status >= 10003 && status <= 10005) {    //token过期
            handleTokenExpire(status + "", message, response);
        } else if (status == 10006) {  //被踢
            LogoutReason reason = JSONObject.parseObject(resultStr, LogoutReason.class);
            handleTokenExpire(status + "", reason == null ? "" : reason.getReason(), response);
        } else {
            onSuccess(0, bodyStr);
        }
    }

    /**
     * 处理登录被踢的情况
     *
     * @param error
     * @param reason
     */
    private void handleTokenExpire(final String error, final String reason, final Response response) {
        OkHttpUtil.postRunable(new Runnable() {
            @Override
            public void run() {
                try {
                    AccountManager.getInstance().clearRefreshToken();
                    gotoLogin(error, reason);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.show("请重新登录");
                }
            }
        });
    }

    // {"reason":"last_login_time: 2017-09-29 11:33:31","error":"30","message":"this account has logged in another device"}
    private void gotoLogin(String error, String reason) {
        LogUtils.show("handleTokenExpire: " + error + " * " + LogoutDialog.checkShow());
        final Activity activity = AppManager.getAppManager().currentActivity();
        if (error.equals("30") || error.equals("10006")) {  // 被踢
            if (LogoutDialog.checkShow()) {
                return;
            }
            String alertText = "你的帐户于" + getLogTime(reason) + "在其它设备上登录。如非本人操作，登录密码可能已泄露，请修改密码。";
            mLogoutDialg = new LogoutDialog(activity, alertText, "确定", Gravity.CENTER, new DialogOnClickListener() {
                @Override
                public void onClickListener(Dialog dialog, View v) {
                    AccountManager.getInstance().clearRefreshToken();
                    AppManager.getAppManager().finishAllActivity();
                    mLogoutDialg.dismiss();
                    activity.startActivity(new Intent(activity, LoginCloudActivity.class));
                }
            });
            LogUtils.error("handleTokenExpire", "show");
            mLogoutDialg.show();
        } else { // 登录过期
            toUiError(error, "登录过期，请重新登录", null);
            ToastUtil.show("登录过期，请重新登录");
            AppManager.getAppManager().finishAllActivity();
            activity.startActivity(new Intent(activity, LoginCloudActivity.class));
        }

    }

    public String getLogTime(String reason) {
        if (TextUtils.isEmpty(reason) || !reason.startsWith("last_login_time")) {
            return "";
        }
        try {
            String time = reason.substring(reason.indexOf(":") + 2);
            LogUtils.debug(time);
//        //2017-09-29 11:33:31
            String[] split = time.split(" ");
            String[] before = split[0].split("-");
            String[] after = split[1].split(":");
            return before[0] + "年" + before[1] + "月" + before[2] + "日" + after[0] + "时" + after[1] + "分" + after[2] + "秒";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
