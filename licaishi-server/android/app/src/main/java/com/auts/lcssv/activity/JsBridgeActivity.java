package com.auts.lcssv.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.MqttCallback;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.jsbridge.JSData;
import com.auts.lcssv.jsbridge.JavaBridge;
import com.auts.lcssv.jsbridge.JavaCallback;
import com.auts.lcssv.jsbridge.NativeModel;
import com.auts.lcssv.jsbridge.PushData;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.NetworkUtils;
import com.auts.lcssv.util.SpfUtils;
import com.auts.lcssv.util.ToastUtil;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebBackForwardList;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;


public class JsBridgeActivity extends BaseActivity {
    @BindView(R.id.x5_webview)
    WebView mWebView;

    private String mUrl = "http://geek.csdn.net/";
    private JavaBridge mJavaBridge;
    private NativeModel mNativeModel;
    private JSData mInitJsData;
    private JavaBridge.JsCallback mOpenPageCallback;
    private HashMap<String, MqttCallback> mCbMap = new HashMap<>();
    private ArrayList<String> mPassTopicList = new ArrayList<>();
    private Handler mHandler = new Handler();
    private Runnable mOutTimeR = new Runnable() {
        @Override
        public void run() {
            ToastUtil.show("设备不在线");
        }
    };


    private int mPageIndex = 0;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_js_bridge);
    }

    @Override
    public void afterInitView() {
//        EventBus.getDefault().register(this);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mJavaBridge = new JavaBridge(this, mWebView);
        initWebView();
        getExtra();
        loadUrl(mUrl);
        mNativeModel = new NativeModel();
        initHandler();
    }

    public void getExtra() {
        String url = getIntent().getStringExtra(AppConstans.Common.INTENT_URL);
        if (!TextUtils.isEmpty(url)) {
            mUrl = url;
        }
    }

    private void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLightTouchEnabled(true);
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());

        // 设置 缓存模式
        webSettings.setCacheMode(NetworkUtils.isNetAvailable() ? android.webkit.WebSettings.LOAD_DEFAULT : android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK);

        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    LogUtils.show("initWebView exception: " + e);
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                LogUtils.debug("onReceivedError: " + s1);
//                hideLoading();
                ToastUtil.show("网络异常，请稍后再试");
//                finish();
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
//                showLoading();
//                mJavaBridge.loadJsBridge(mWebView);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                mJavaBridge.loadJsBridge(mWebView);
            }
        });
    }


    private void loadUrl(String url) {
        LogUtils.show("jsBridgeUrl: " + url);
        mWebView.loadUrl(url);
        SpfUtils.put("current_url", url);
    }

    @Override
    public void onBackPressed() {
        onGoback();
    }

    @Override
    public void onGoback() {
        if (mWebView != null && mWebView.canGoBack()) {
//            WebBackForwardList historys = mWebView.copyBackForwardList();
            if (mPageIndex > 0) {
                mPageIndex --;
                mWebView.goBack();
                mJavaBridge.callHandler("nativeBack");
            } else {
                super.onGoback();
            }
        } else {
            super.onGoback();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
            setPageTitle(s);
        }

        @Override
        public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
            return super.onJsConfirm(webView, s, s1, jsResult);
        }

        @Override
        public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
            return super.onJsAlert(webView, s, s1, jsResult);
        }

        @Override
        public boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult result) {
            result.cancel();
            return true;
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            LogUtils.debug("h5log", consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }
    }


    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        closeWebView();
//        mNativeModel.unSucribe(JsBridgeActivity.this, mInitJsData, mDeviceId, mPassTopicList);
        if (mHandler != null && mOutTimeR != null) {
            mHandler.removeCallbacks(mOutTimeR);
        }
        super.onDestroy();
    }

    private void closeWebView() {
        try {
            if (mWebView != null) {
                mWebView.removeAllViews();
                //在5.1上如果不加上这句话就会出现内存泄露。这是5.1的bug
                // mComponentCallbacks导致的内存泄漏
                ((ViewGroup) mWebView.getParent()).removeView(mWebView);
                mWebView.setTag(null);
//                mWebView.clearHistory();
                mWebView.stopLoading();
                mWebView.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //******************* JSBridge相关  *******************//
    private void jsBridge_nativeBack() {
        mJavaBridge.callHandler("nativeBack");
    }

    private void initHandler() {
        mJavaBridge.registerHandler("changePageIndex", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, JavaBridge.JsCallback jsCallback) {
                int pageIndexModifier = jsData.getPageIndexModifier();
                mPageIndex += pageIndexModifier;
                mNativeModel.toast(jsData.getMessage());
                mJavaBridge.callbackSuccess(jsCallback, null);
            }
        });

        mJavaBridge.registerHandler("toast", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, JavaBridge.JsCallback jsCallback) {
                mNativeModel.toast(jsData.getMessage());
                mJavaBridge.callbackSuccess(jsCallback, null);
            }
        });


        mJavaBridge.registerHandler("toastLong", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, JavaBridge.JsCallback jsCallback) {
                mNativeModel.toastLong(jsData.getMessage());
                mJavaBridge.callbackSuccess(jsCallback, null);
            }
        });

        mJavaBridge.registerHandler("showLoading", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, JavaBridge.JsCallback jsCallback) {
                showLoading(jsData.getMessage(), jsData.getShowTime());
                mJavaBridge.callbackSuccess(jsCallback, null);
            }
        });

        mJavaBridge.registerHandler("hideLoading", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, JavaBridge.JsCallback jsCallback) {
                hideLoading();
                mJavaBridge.callbackSuccess(jsCallback, null);
            }
        });

        mJavaBridge.registerHandler("closePage", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, JavaBridge.JsCallback jsCallback) {
                mJavaBridge.callbackSuccess(jsCallback, null);
                finish();
            }
        });

        mJavaBridge.registerHandler("openPage", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, JavaBridge.JsCallback jsCallback) {
                mNativeModel.openPage(JsBridgeActivity.this, jsData, mJavaBridge, jsCallback, "");
                mOpenPageCallback = jsCallback;
            }
        });


        mJavaBridge.registerHandler("getNetType", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, JavaBridge.JsCallback jsCallback) {
                String netType = mNativeModel.getNetType();
                JavaCallback javaCallback = new JavaCallback(0, "success");
                javaCallback.setNetType(netType);
                mJavaBridge.callbackSuccess(jsCallback, javaCallback);
            }
        });


        //工具类API
        mJavaBridge.registerHandler("netRequest", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, final JavaBridge.JsCallback jsCallback) {
                Log.d("sandy", "netRequest");
                mNativeModel.netRequest(jsData, mJavaBridge, jsCallback, new NativeModel.NetCallback() {
                    @Override
                    public void onResponse(int errorCode, String errorMesssage, String netResponse) {
                        JavaCallback javaCallback = new JavaCallback();
                        javaCallback.setErrorCode(errorCode);
                        javaCallback.setErrorMsg(errorMesssage);
                        if (!TextUtils.isEmpty(netResponse)) {
                            javaCallback.setNetResponse(netResponse);
                        }
                        mJavaBridge.callbackSuccess(jsCallback, javaCallback);
                    }
                });
            }
        });

        mJavaBridge.registerHandler("openDownload", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, final JavaBridge.JsCallback jsCallback) {
                mNativeModel.openDownload(JsBridgeActivity.this, jsData);
                mJavaBridge.callbackSuccess(jsCallback, new JavaCallback(0, "success"));
            }
        });

        mJavaBridge.registerHandler("onStatisEvent", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, final JavaBridge.JsCallback jsCallback) {
                mNativeModel.onEvent(jsData, jsCallback);
            }
        });


        //设备相关API
        mJavaBridge.registerHandler("initConfig", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, JavaBridge.JsCallback jsCallback) {
                mInitJsData = jsData;
                mNativeModel.initConfig(JsBridgeActivity.this, jsData, "");
                mJavaBridge.callbackSuccess(jsCallback, null);
            }
        });

        mJavaBridge.registerHandler("passthrough", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, final JavaBridge.JsCallback jsCallback) {
                if (TextUtils.isEmpty(jsData.getRequestTopic()) || TextUtils.isEmpty(jsData.getResponseTopic())
                        || TextUtils.isEmpty(jsData.getRequestData())) {
                    mJavaBridge.callback2Js(jsCallback, 102, "参数错误");
                    return;
                }
                MqttCallback mcb = new MqttCallback();
                mcb.jsCallback = jsCallback;
                mcb.dataType = jsData.getDataType();
                mcb.needUn = true;
                mCbMap.put(jsData.getResponseTopic(), mcb);
                mPassTopicList.add(jsData.getResponseTopic());
                mNativeModel.passthrough(JsBridgeActivity.this, jsData);
//                mJavaBridge.callbackSuccess(jsCallback, null);
            }
        });

        mJavaBridge.registerHandler("publish", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, final JavaBridge.JsCallback jsCallback) {
                if (TextUtils.isEmpty(jsData.getPublishTopic())) {
                    mJavaBridge.callback2Js(jsCallback, 102, "参数错误");
                    return;
                }
                if (!NetworkUtils.isNetAvailable()) {
                    ToastUtil.show("当前网络不可用，请检查网络设置");
                    return;
                }
                showLoading(null, 10 * 1000);
                mHandler.postDelayed(mOutTimeR, 10 * 1000);
                mJavaBridge.callbackSuccess(jsCallback, null);
            }
        });

        mJavaBridge.registerHandler("subscribe", new JavaBridge.JavaHandler() {
            @Override
            public void handle(JSData jsData, final JavaBridge.JsCallback jsCallback) {
                if (TextUtils.isEmpty(jsData.getSubscribeTopic())) {
                    mJavaBridge.callback2Js(jsCallback, 102, "参数错误");
                    return;
                }
                mNativeModel.subscribe(JsBridgeActivity.this, jsData);
                mJavaBridge.callbackSuccess(jsCallback, null);
            }
        });
    }


//    @Subscribe
//    public void onEventMainThread(MqttOnPublishEvent event) {
//        LogUtils.mqtt("mqttReceive: " + event.getMsg().getMessage());
//        event.getMsg().getTopic();
//        hideLoading();
//        if (mHandler != null && mOutTimeR != null) {
//            mHandler.removeCallbacks(mOutTimeR);
//        }
//        ReceivedMessage msgObj = event.getMsg();
//        mNativeModel.pushMqttData(this, msgObj, mCbMap.get(msgObj.getTopic()));
//        pushData(msgObj);
//    }

//    private void pushData(ReceivedMessage msgObj) {
//        PushData pushData = new PushData("203", msgObj.getTopic(), msgObj.getMessage().toString());
//        mJavaBridge.callHandler("pushData", JSON.toJSONString(pushData));
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.show("jsbridge onActivityResult: " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstans.RequestCode.JS_OPEN_PAGE) {
            int result = resultCode == RESULT_OK ? 1 : 0;
            JavaCallback openPageJcb = new JavaCallback(0, "success");
            openPageJcb.setHasChange(result);
            mJavaBridge.callbackSuccess(mOpenPageCallback, openPageJcb);
        }
    }


    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

}
