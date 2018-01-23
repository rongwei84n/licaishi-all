package com.auts.lcssv.jsbridge;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.auts.lcssv.R;
import com.auts.lcssv.util.LogUtils;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * implements Serializable in case of javascript interface will be removed in obfuscated code.
 * <p>
 * User: jack_fang
 * Date: 13-8-15
 * Time: 下午6:08
 */
public class JavaBridge implements Serializable {

    private WebView mWebView;
    private Activity mContext;
    private Map<String, JavaHandler> _messageHandlers;
    private Map<String, JsCallback> _responseCallbacks;
    private long _uniqueId;
    private boolean mIsTest = false;

    public JavaBridge(Activity context, com.tencent.smtt.sdk.WebView webview) {
        this.mContext = context;
        this.mWebView = webview;
        _messageHandlers = new HashMap<>();
        _responseCallbacks = new HashMap<>();
        _uniqueId = 0;
        //注册接口给H5调用
        mWebView.addJavascriptInterface(this, "JavaBridge");
    }


    public void loadJsBridge(WebView webView) {
        InputStream is = mContext.getResources().openRawResource(R.raw.jsbridge);
        String script = convertStreamToString(is);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(script, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    LogUtils.debug("onReceiveValue " + s);
                }
            });
        } else {
            webView.loadUrl("javascript:" + script);
        }
//        onPageFinish();
    }

    public static String convertStreamToString(InputStream is) {
        String s = "";
        try {
            Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
            if (scanner.hasNext()) s = scanner.next();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }


    public interface JavaHandler {
        void handle(JSData jsData, JsCallback jsCallback);
    }

    public interface JsCallback {
        void callback(String data);
    }

    public void registerHandler(String handlerName, JavaHandler handler) {
        _messageHandlers.put(handlerName, handler);
    }

    private class CallbackJs implements JsCallback {
        private final String callbackIdJs;

        public CallbackJs(String callbackIdJs) {
            this.callbackIdJs = callbackIdJs;
        }

        @Override
        public void callback(String data) {
            callbackJs(callbackIdJs, data);
        }
    }

    private void callbackJs(String callbackIdJs, String data) {
        Map<String, String> message = new HashMap<>();
        message.put("responseId", callbackIdJs);
        message.put("responseData", data);
        _dispatchMessage(message);
    }


    public void send(String data) {
        send(data, null);
    }

    public void send(String data, JsCallback responseCallback) {
        _sendData(null, data, responseCallback);
    }

    private void _sendData(String handlerName, String data, JsCallback responseCallback) {
        Map<String, String> message = new HashMap<>();
        message.put("data", data);
        if (null != responseCallback) {
            String callbackId = "id_" + (++_uniqueId);
            _responseCallbacks.put(callbackId, responseCallback);
            message.put("callbackId", callbackId);
        }
        if (null != handlerName) {
            message.put("handlerName", handlerName);
        }
        _dispatchMessage(message);
    }

    private void _dispatchMessage(Map<String, String> message) {
        String messageJSON = new JSONObject(message).toString();
        LogUtils.jsBridge("java call js: " + messageJSON);
        final String javascriptCommand = String.format("javascript:WebViewJavascriptBridge._handleMessageFromJava('%s');", doubleEscapeString(messageJSON));
        LogUtils.debug("_dispatchMessage: " + javascriptCommand);
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(javascriptCommand);
            }
        });
    }


    public void callHandler(String handlerName) {
        callHandler(handlerName, null, null);
    }

    public void callHandler(String handlerName, String data) {
        callHandler(handlerName, data, null);
    }

    /**
     * 调用前端方法
     *
     * @param handlerName
     * @param data
     * @param responseCallback
     */
    public void callHandler(String handlerName, String data, JsCallback responseCallback) {
        try {
            _sendData(handlerName, data, responseCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String doubleEscapeString(String javascript) {
        String result;
        result = javascript.replace("\\", "\\\\");
        result = result.replace("\"", "\\\"");
        result = result.replace("\'", "\\\'");
        result = result.replace("\n", "\\n");
        result = result.replace("\r", "\\r");
        result = result.replace("\f", "\\f");
        return result;
    }

    @JavascriptInterface
    public void _handleMessageFromJs(final String data, String responseId, String responseData, String callbackId, String handlerName) {
//        LogUtils.debug("_handleMessageFromJs: " + data + " * " + responseId + " * " + responseData + " * " + callbackId + " * " + handlerName);
        LogUtils.jsBridge("_handleMessageFromJs: " + data + " * " + responseId + " * " + responseData + " * " + callbackId + " * " + handlerName);
        if (mIsTest) {
            return;
        }
        if (null != responseId) {
            JsCallback responseCallback = _responseCallbacks.get(responseId);
            responseCallback.callback(responseData);
            _responseCallbacks.remove(responseId);
        } else {
            JsCallback responseCallback = null;
            if (null != callbackId) {
                responseCallback = new CallbackJs(callbackId);
            }
            JavaHandler handler = null;
            if (null != handlerName) {
                handler = _messageHandlers.get(handlerName);
                if (null == handler) {
                    LogUtils.jsBridge("_handleMessageFromJs error: " + handlerName);
                    callback2Js(responseCallback, 101, "方法找不到");
                    return;
                }
            }

            final JavaHandler javaHandler = handler;
            try {
                final JsCallback finalResponseCallback = responseCallback;
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(data)) {
                            JSData jsData = JSON.parseObject(data, JSData.class);
                            if (jsData == null) {
                                callback2Js(finalResponseCallback, 102, "参数错误");
                                return;
                            }
                            javaHandler.handle(jsData, finalResponseCallback);
                        } else {
                            javaHandler.handle(null, finalResponseCallback);
                        }
                    }
                });
            } catch (Exception exception) {
                if (exception instanceof JSONException) {
                    callback2Js(responseCallback, 102, "参数错误");
                } else {
                    callback2Js(responseCallback, 103, "方法调用失败");
                }
                LogUtils.jsBridge("_handleMessageFromJs error: " + exception.getMessage());
            }
        }
    }

    public void callbackSuccess(JsCallback jsCallback, JavaCallback javaCallback) {
        if (javaCallback == null) {
            javaCallback = new JavaCallback();
        }
        try {
            jsCallback.callback(JSON.toJSONString(javaCallback));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void callback2Js(JsCallback jsCallback, int errorCode, String message) {
        try {
            JavaCallback javaCallback = new JavaCallback(errorCode, message);
            jsCallback.callback(JSON.toJSONString(javaCallback));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onPageFinish() {
        callHandler("onPageFinish", "", new JsCallback() {
            @Override
            public void callback(String data) {
            }
        });
    }

}
