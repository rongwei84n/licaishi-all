package com.auts.lcssv.net.callback;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auts.lcssv.activity.LoginCloudActivity;
import com.auts.lcssv.bean.CommonResponse;
import com.auts.lcssv.bean.FxResponse;
import com.auts.lcssv.bean.LogoutReason;
import com.auts.lcssv.bean.SzResponse;
import com.auts.lcssv.constants.UrlConfig;
import com.auts.lcssv.listener.DialogOnClickListener;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.net.engine.Err2MsgUtils;
import com.auts.lcssv.net.engine.OkHttpUtil;
import com.auts.lcssv.util.AppManager;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.views.LogoutDialog;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 网络请求callback基类
 * Created by qisheng.lv on 2017/4/12.
 */
public abstract class BaseCallback<T> implements okhttp3.Callback {
    private static final int SZ_SUCCESS_CODE = 200;
    private LogoutDialog mLogoutDialg;

    /**
     * 最终UI线程回调：请求失败
     *
     * @param code
     * @param msg
     */
    public void onError(int code, String msg) {

    }

    /**
     * 最终UI线程回调：请求失败
     *
     * @param code
     * @param msg
     */
    public abstract void onError(String code, String msg);


    /**
     * 最终UI线程回调：请求成功
     *
     * @param result
     */
    public abstract void onSuccess(String result, Request request);

    /**
     * OkHttp失败回调
     *
     * @param call
     * @param e
     */
    @Override
    public void onFailure(Call call, IOException e) {
        LogUtils.debug("okhttp onFailure: " + e.toString());
        if (e instanceof SocketTimeoutException) {
            toUiError(Err2MsgUtils.CODE_NET_TIMEOUT, null, call.request());
        } else {
            toUiError(Err2MsgUtils.CODE_UNKNOW_ERROR, null, call.request());
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
            toUiError(Err2MsgUtils.CODE_NO_RESPONSE, null, call.request());
            return;
        }

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
            toUiError(Err2MsgUtils.CODE_NO_RESPONSE, null, response.request());
        } else {
            String bodyStr = null;
            try {
                bodyStr = body.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(bodyStr)) {
                toUiError(Err2MsgUtils.CODE_NO_RESPONSE, null, response.request());
                return;
            }

            //连接的路由器不能上外网，却把一个html响应回来的情况
            if ((bodyStr.contains("<HTML>") && bodyStr.contains("<HEAD>") && bodyStr.contains("<BODY>"))
                    || (bodyStr.contains("<html>") && bodyStr.contains("<head>") && bodyStr.contains("<body>"))) {
                toUiError(Err2MsgUtils.CODE_UNKNOW_ERROR, null, response.request());
                return;
            }

            String url = response.request().url().toString();

            //处理斐讯云响应
            if (url.startsWith(UrlConfig.CloudAccountUrl.URL_HOST)) {
                parseFxResponse(bodyStr, response);
                return;
            }

            //处理深圳后台响应
            if (url.startsWith(UrlConfig.SzUrl.URL_HOST)) {
                parseSzResponse(bodyStr, response);
                return;
            }

            //处理其它响应
            parseCommonResponse(bodyStr, response);
        }
    }

    private void parseFxResponse(String bodyStr, Response response) {
        if (!response.isSuccessful()) {
            toUiError(Err2MsgUtils.CODE_UNKNOW_ERROR, null, response.request());
            return;
        }

        FxResponse fxObj = null;
        try {
            fxObj = JSON.parseObject(bodyStr, FxResponse.class);
        } catch (Exception e) {
            LogUtils.debug(e);
        }
        if (fxObj == null) {
            toUiError(Err2MsgUtils.CODE_PARSE_ERROR, null, response.request());
            return;
        }

        String error = fxObj.getError();
        int tokenStatus = fxObj.getToken_status();
//        String message = fxObj.getMessage();
        String httpCode = fxObj.getHttpCode();
        if (error.equals("0") && tokenStatus == 0 && httpCode.equals("200")) {
            toUiSuccess(bodyStr, response.request());
        } else if (error.equals("26") || error.equals("30") || tokenStatus > 0) {
            handleTokenExpire(error, fxObj.getReason(), response);
//            toUiError(Err2MsgUtils.CODE_TOKEN_TIMEOUT, null, response.request());
        } else {
            toUiError(error, null, response.request());
        }
    }

    private void parseSzResponse(String bodyStr, Response response) {
        SzResponse szObj;
        try {
            szObj = JSON.parseObject(bodyStr, SzResponse.class);
        } catch (Exception e) {
            toUiError(Err2MsgUtils.CODE_PARSE_ERROR, null, response.request());
            return;
        }

        int status = szObj.getStatus();
        String message = szObj.getMessage();
        String resultStr = szObj.getResult();

        if (status == SZ_SUCCESS_CODE) {
            toUiSuccess(resultStr, response.request());
        } else if (status >= 10003 && status <= 10005) {    //token过期
            handleTokenExpire(status + "", message, response);
        } else if (status == 10006) {  //被踢
            Log.d("phresult", "parsesz result: " + resultStr);
            LogoutReason reason = JSONObject.parseObject(resultStr, LogoutReason.class);
            handleTokenExpire(status + "", reason == null ? "" : reason.getReason(), response);
        } else {
            toUiError(String.valueOf(status), "服务异常，请稍候再试(" + status + ")", response.request());
        }
    }

    private void parseCommonResponse(String bodyStr, final Response response) {
        CommonResponse commonObj = null;
        try {
            commonObj = JSON.parseObject(bodyStr, CommonResponse.class);
        } catch (Exception e) {
            LogUtils.debug(e);
        }
        if (commonObj == null) {
            toUiError(Err2MsgUtils.CODE_PARSE_ERROR, null, response.request());
            return;
        }

        String error = commonObj.getError();
        int tokenStatus = commonObj.getToken_status();
        String message = commonObj.getMessage();
        String httpCode = commonObj.getHttpCode();
        if (error.equals("0") && tokenStatus == 0 && httpCode.equals("200")) {
            toUiSuccess(bodyStr, response.request());
        } else if (error.equals("30") || tokenStatus > 0) {
            handleTokenExpire(tokenStatus + "", message, response);
//            toUiError(Err2MsgUtils.CODE_TOKEN_TIMEOUT, message, response.request());
        } else {
            toUiError(error, message, response.request());
        }
    }


    /**
     * 从子线程转到UI线程
     *
     * @param code    错误码
     * @param message 错误信息，如果需要Err2MsgUtils根据code来转化message，直接传null
     */
    public void toUiError(final String code, final String message, Request request) {
        OkHttpUtil.postRunable(new Runnable() {
            @Override
            public void run() {
                try {
                    String errorMsg = TextUtils.isEmpty(message) ? Err2MsgUtils.getErrMsg(code) : message;
                    onError(code, errorMsg);
                } catch (Exception e) {
                    LogUtils.debug(e);
                }
            }
        });
    }


    /**
     * 从子线程转到UI线程
     *
     * @param result
     */
    public void toUiSuccess(final String result, final Request request) {
        OkHttpUtil.postRunable(new Runnable() {
            @Override
            public void run() {
                onSuccess(result, request);
            }
        });
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
                    //如果是欢迎页刷新token失败，把逻辑交回给欢迎页处理
                    AccountManager.getInstance().clearRefreshToken();
                    if (response.request().url().toString().contains("/v1/token")) {
                        toUiError(error, reason, response.request());
                    } else {
                        gotoLogin(error, reason);
                    }
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
