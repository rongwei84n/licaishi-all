package com.auts.lcssv.jsbridge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.auts.lcssv.R;
import com.auts.lcssv.activity.LoginCloudActivity;
import com.auts.lcssv.activity.OrderItemDetailActivity;
import com.auts.lcssv.activity.PersonalInformationActivity;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.net.callback.SynCallback;
import com.auts.lcssv.net.engine.OkHttpUtil;
import com.auts.lcssv.net.request.BaseRequest;
import com.auts.lcssv.util.IntentUtils;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.NetworkUtils;
import com.auts.lcssv.util.ToastUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import cn.jiguang.share.wechat.*;
import cn.jiguang.share.android.api.*;

/**
 * JSBridge-原生端需要实现的方法
 * Created by qisheng.lv on 2017/6/30.
 */

public class NativeModel {

    private static final String BEARAR = "Bearer ";

    public interface NetCallback {
        void onResponse(int errorCode, String errorMesssage, String netResponse);
    }

    public void toast(String message) {
        ToastUtil.show(message);
    }

    public void toastLong(String message) {
        ToastUtil.showLong(message);
    }

    public void showLoading(String message, int showTime) {

    }

    public void hideLoading() {

    }

    public void closePage() {

    }

    public void openPage(Activity context, JSData jsData, JavaBridge javaBridge, JavaBridge.JsCallback jsCallbacke, String deviceId) {
        try {
            LogUtils.debug("openage: " + jsData.getPageName());
            Intent intent = null;
            switch (jsData.getPageName()) {
                case "lcs.account.login":
                    intent = new Intent(context, LoginCloudActivity.class);
                    break;
                case "lcs.account.personinfo":
                    intent = new Intent(context, PersonalInformationActivity.class);
                    break;
                case "lcs.order.orderdetail":
                    String orderid = jsData.getPageExtra();
                    intent = new Intent(context, OrderItemDetailActivity.class);
                    intent.putExtra("orderid", orderid);
                    break;
                case "lcs.account.share.wechat": //微信分享
                    String para = jsData.getPageExtra(); //分享标题，分享链接
                    String[] paras = para.split(";");
                    for (int i = 0; i < paras.length; i++) {
                        LogUtils.debug("i: " + i + " value: " + paras[i]);
                    }
                    if (paras == null || paras.length < 2) {
                        LogUtils.debug("paras size wrong.. can't share");
                        return;
                    }
                    shareToWechat(paras[0], paras[1]);
                    return;
                case "lcs.account.share.wechatmoments": //微信朋友圈分享
                    para = jsData.getPageExtra(); //分享标题，分享链接
                    paras = para.split(";");
                    for (int i = 0; i < paras.length; i++) {
                        LogUtils.debug("i: " + i + " value: " + paras[i]);
                    }
                    if (paras == null || paras.length < 2) {
                        LogUtils.debug("paras size wrong.. can't share");
                        return;
                    }
                    shareToWechatMoments(paras[0], paras[1]);
                    return;
                default:
                    break;
            }

            if (intent != null) {
                context.startActivityForResult(intent, AppConstans.RequestCode.JS_OPEN_PAGE);
//                javaBridge.callbackSuccess(jsCallbacke, null);
            } else {
                javaBridge.callback2Js(jsCallbacke, 121, "pager找不到");
            }
        } catch (Exception e) {
            LogUtils.debug("openPager: " + e);
            javaBridge.callback2Js(jsCallbacke, 121, e.getMessage());
        }

    }

    private boolean shareToWechat(String title, String url){
        return shareInner(Wechat.Name, title, url);
    }

    /**
     * 分享到微信朋友圈
     */
    private boolean shareToWechatMoments(String title, String url) {
        return shareInner(WechatMoments.Name, title, url);
    }

    private boolean shareInner(String platform, String title, String url) {
        ////创建分享参数
        ShareParams shareParams = new ShareParams();

        //设置分享的数据类型
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setText(title);
        shareParams.setTitle(title);
        shareParams.setUrl(url);

        //调用分享接口share，分享到微信平台。
        JShareInterface.share(platform, shareParams, new PlatActionListener(){
            @Override
            public void onComplete(Platform platform, int i, HashMap<String,Object> hashMap){
                LogUtils.debug("share wechat onComplete");
            }

            @Override
            public void onError(Platform platform, int i,int i1, Throwable throwable){
                LogUtils.debug("share wechat onError");
            }

            @Override
            public void onCancel(Platform platform, int i){
                LogUtils.debug("share wechat onCancel");
            }
        });

        return true;
    }


    public String getNetType() {
        return NetworkUtils.getNetType();
    }


    public void netRequest(JSData jsData, JavaBridge javaBridge, JavaBridge.JsCallback jsCallback, NetCallback netCallback) {
        if (TextUtils.isEmpty(jsData.getNetAction()) || TextUtils.isEmpty(jsData.getUrl())) {
            javaBridge.callback2Js(jsCallback, 301, "参数错误");
            return;
        }

        String netAction = jsData.getNetAction();
        String url = jsData.getUrl();
        BaseRequest baseRequest = null;
        if (netAction.equals("get")) {
            baseRequest = OkHttpUtil.get(url);
        } else if (netAction.equals("post")) {
            baseRequest = OkHttpUtil.postJson(url);
        } else if (netAction.equals("delete")) {
            baseRequest = OkHttpUtil.delete(url);
        }

        if (baseRequest != null) {
            doNetRequest(baseRequest, jsData, netCallback);
        }
    }

    private void doNetRequest(BaseRequest baseRequest, JSData jsData, final NetCallback netCallback) {
        if (!TextUtils.isEmpty(jsData.getRequestHeader())) {
            Map<String, String> headerMap = JSON.parseObject(jsData.getRequestHeader(), new TypeReference<Map<String, String>>() {
            });
            if (headerMap != null && headerMap.size() > 0) {
                Iterator<Map.Entry<String, String>> iterator1 = headerMap.entrySet().iterator();
                while (iterator1.hasNext()) {
                    Map.Entry<String, String> entry = iterator1.next();
                    if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                        baseRequest.addHeader(entry.getKey(), entry.getValue());
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(jsData.getRequestBody())) {
            LogUtils.debug("js doNetRequest: " + jsData.getRequestBody());
            Map<String, String> bodyMap = JSON.parseObject(jsData.getRequestBody(), new TypeReference<Map<String, String>>() {
            });
            if (bodyMap != null && bodyMap.size() > 0) {
                Iterator<Map.Entry<String, String>> iterator1 = bodyMap.entrySet().iterator();
                while (iterator1.hasNext()) {
                    Map.Entry<String, String> entry = iterator1.next();
                    if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                        baseRequest.addParams(entry.getKey(), entry.getValue());
                    }
                }
            }
        }

        baseRequest.run(jsData.getUrl(), new SynCallback() {

            @Override
            public void onError(int code, String msg) {
                netCallback.onResponse(code, msg, null);
            }

            @Override
            public void onSuccess(int code, String netResponse) {
                netCallback.onResponse(0, "success", netResponse);
            }
        });
    }

    public void initConfig(Context context, JSData jsData, String deviceId) {

    }


    public void unSucribe(Context context, JSData jsData, String deviceId, ArrayList<String> passList) {

    }

    public void passthrough(Context context, JSData jsData) {
        String requestTopic = jsData.getRequestTopic();
        String requestData = jsData.getRequestData();
        String responseTopic = jsData.getResponseTopic();

//        MqttUtils.subscribeTopic(context, responseTopic);

        String publishData = TextUtils.isEmpty(requestData) ? "" : requestData;
//        MqttUtils.publishTopic(context, requestTopic, publishData);
    }

//    public void pushMqttData(Context context, ReceivedMessage receiveMsg, MqttCallback mcb) {
//        if (mcb == null || mcb.jsCallback == null) {
//            return;
//        }
//        try {
//            String bodyMsg = receiveMsg.getMessage().toString();
//            if (mcb.dataType > 0) {
//                bodyMsg = Base64Utils.encode(receiveMsg.getMessage().getPayload());
//            }
//            LogUtils.show("bodyMsg: " + bodyMsg);
//            if (!TextUtils.isEmpty(bodyMsg)) {
//                JavaCallback javaCallback = new JavaCallback(0, "success");
//                javaCallback.setMqttData(bodyMsg);
//                mcb.jsCallback.callback(JSON.toJSONString(javaCallback));
//            }
//            //对于透传类topic，收到一次消息之后就取消订阅
//            if (mcb.needUn) {
//                Intent reIntent = MqttPhihomeClientService.getUnSuscribeIntent(context, receiveMsg.getTopic());
//                context.startService(reIntent);
//            }
//        } catch (Exception e) {
//            LogUtils.debug("pushMqttData excetption: " + e.toString());
//        }
//    }

//    public void pushPublishData(ReceivedMessage receiveMsg, JavaBridge.JsCallback jsCallback) {
//        try {
//            if (jsCallback == null) {
//                return;
//            }
//            String bodyMsg = receiveMsg.getMessage().toString();
//            if (!TextUtils.isEmpty(bodyMsg)) {
//                JavaCallback javaCallback = new JavaCallback(0, "success");
//                javaCallback.setMqttData(bodyMsg);
//                jsCallback.callback(JSON.toJSONString(javaCallback));
//            }
//        } catch (Exception e) {
//            LogUtils.debug("pushPublishData excetption: " + e.toString());
//        }
//    }


    /**
     * 发布Topic
     *
     * @param context
     * @param jsData
     */
//    public void publish(Context context, JSData jsData) {
//        MqttUtils.publishTopic(context, jsData.getPublishTopic(), jsData.getPublishData());
//    }

    /**
     * 订阅topic
     *
     * @param context
     * @param jsData
     */
    public void subscribe(Context context, JSData jsData) {
//        MqttUtils.subscribeTopic(context, jsData.getSubscribeTopic());
    }

    public void openDownload(Context context, JSData jsData) {
        IntentUtils.openDownload(context, jsData.getUrl());
    }


    /**
     * 数据统计API
     *
     * @param jsData
     * @param jsCallback
     */
    public void onEvent(JSData jsData, JavaBridge.JsCallback jsCallback) {
        try {
            if (jsCallback == null) {
                return;
            }
            String eventType = jsData.getEventType();
            String eventId = jsData.getEventId();
            String pageTitle = jsData.getPageTitle();
            //类型为空
            if (TextUtils.isEmpty(eventType)) {
                callBackParamError(jsCallback);
                return;
            }

            if (eventType.equals("1")) {
                //eventId为空
                if (TextUtils.isEmpty(eventId)) {
                    callBackParamError(jsCallback);
                    return;
                }
            }

            //pageTitle为空
            if (TextUtils.isEmpty(pageTitle)) {
                callBackParamError(jsCallback);
                return;
            }

            JavaCallback javaCallback = new JavaCallback(0, "success");
            jsCallback.callback(JSON.toJSONString(javaCallback));

        } catch (Exception e) {
            LogUtils.debug("onEvent excetption: " + e.toString());
        }
    }

    private void callBackParamError(JavaBridge.JsCallback jsCallback) {
        JavaCallback javaCallback = new JavaCallback(102, "参数错误");
        jsCallback.callback(JSON.toJSONString(javaCallback));
    }

}
