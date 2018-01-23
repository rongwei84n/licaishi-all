package com.auts.lcssv.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.NetworkUtils;
import com.auts.lcssv.zxing.CaptureActivity;
import com.auts.lcssv.zxing.Intents;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebBackForwardList;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;


public class X5WebActivity extends BaseActivity {
    @BindView(R.id.x5_webview)
    WebView mWebView;

    //    private String mUrl = "https://www.baidu.com/";
    private String mUrl = "http://geek.csdn.net/";
    //    private String mUrl = "http://info.3g.qq.com/";
//    private String mUrl = "file:///android_asset/webpage/fileChooser.html";
//    private String mUrl = "file:///android_asset/test.html";

//    private ValueCallback<Uri> uploadFile;
//    private ValueCallback<Uri[]> uploadFiles;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_x5_web);
    }

    @Override
    public void afterInitView() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        initWebView();
        getIntentUrl();
        loadUrl(mUrl);
        gotoCapture();
    }


    public void getIntentUrl() {
        String url = getIntent().getStringExtra(AppConstans.Common.INTENT_URL);
        if (!TextUtils.isEmpty(url)) {
            mUrl = url;
        }
    }

    private void initWebView() {
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
                    LogUtils.debug("initWebView exception: " + e);
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
            }

        });
    }

    private void gotoCapture() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 3001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3001 && resultCode == RESULT_OK && data != null) {
            String url = data.getStringExtra(Intents.Scan.RESULT);
            if (!TextUtils.isEmpty(url)) {
                mUrl = url;
                loadUrl(url);
            }
        }
    }

    private void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        onGoback();
    }

    @Override
    public void onGoback() {
        if (mWebView != null && mWebView.canGoBack()) {
            WebBackForwardList historys = mWebView.copyBackForwardList();
            if (historys != null && historys.getSize() > 1) {
                mWebView.goBack();
            } else {
                super.onGoback();
            }
        } else {
            super.onGoback();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//            uploadFile = uploadMsg;
            openFileChooseProcess();
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
//            uploadFile = uploadMsgs;
            openFileChooseProcess();
        }

        // For Android  > 4.1.1
        @Override
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//            uploadFile = uploadMsg;
            openFileChooseProcess();
        }

        // For Android  >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         WebChromeClient.FileChooserParams fileChooserParams) {
//            uploadFiles = filePathCallback;
            openFileChooseProcess();
            return true;
        }

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
//            result.confirm(JsBridgeUtils.callJava(webView, message, X5WebActivity.this));
            return true;
        }

    }

    private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i, "test"), 0);
    }

    @Override
    protected void onDestroy() {
        closeWebView();
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


}
