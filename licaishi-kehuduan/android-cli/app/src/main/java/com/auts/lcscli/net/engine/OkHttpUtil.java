package com.auts.lcscli.net.engine;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.auts.lcscli.PhApplication;
import com.auts.lcscli.constants.AppConstans;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.interceptor.MyInterceptor;
import com.auts.lcscli.net.request.DeleteRequest;
import com.auts.lcscli.net.request.GetRequest;
import com.auts.lcscli.net.request.PostJsonRequest;
import com.auts.lcscli.net.request.PostRequest;
import com.auts.lcscli.net.ssl.MySslFatory;
import com.auts.lcscli.util.NetworkUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;


/**
 * 以OkHttp为内核的网络请求工具类
 *
 * @author qisheng.lv
 * @date 2017/4/12
 */
public class OkHttpUtil {
    private static volatile Handler mHandler;
    private static volatile OkHttpClient mHttpClient;

    static {
        mHandler = new Handler(Looper.getMainLooper());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        initBuilder(builder);
        mHttpClient = builder.build();
    }

    private static void initBuilder(OkHttpClient.Builder builder) {
        builder.connectTimeout(AppConstans.NetConfig.HTTP_CONNECT_TIME_OUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(AppConstans.NetConfig.HTTP_READ_TIME_OUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(AppConstans.NetConfig.HTTP_WRITE_TIME_OUT, TimeUnit.MILLISECONDS);
        builder.addInterceptor(new MyInterceptor().setLevel(MyInterceptor.Level.BODY));
        builder.hostnameVerifier(MySslFatory.getHostnameVerifier());
        MySslFatory.setCertificates(builder, new Buffer().writeUtf8(AppConstans.Ca.CA_RELEASE).inputStream());
    }

    private OkHttpUtil() {

    }

    public static void postRunable(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static GetRequest get(String url) {
        return new GetRequest(url);
    }

    public static PostRequest post(String url) {
        return new PostRequest(url);
    }

    public static PostJsonRequest postJson(String url) {
        return new PostJsonRequest(url);
    }

    public static DeleteRequest delete(String url) {
        return new DeleteRequest(url);
    }

    public static <T> Call execute(Request request, BaseCallback<T> callback) {
        if (!NetworkUtils.isNetAvailable()) {
            callback.onError(Err2MsgUtils.CODE_NET_DISABLE, Err2MsgUtils.getErrMsg(Err2MsgUtils.CODE_NET_DISABLE));
            callback.onError(302, "当前网络不可用，请检查网络设置");
            return null;
        }
        Call call = mHttpClient.newCall(request);

        if (!PhApplication.isJunitTest) {
            call.enqueue(callback);
            return call;
        }

        //以下是把异步网络请求改成同步
        Response response;
        try {
            response = call.execute();
            if (response == null) {
                callback.onFailure(call, new IOException("no response"));
            } else {
                callback.onResponse(call, response);
            }
        } catch (IOException e) {
            callback.onFailure(call, e);
        }

        return call;
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public static void cancelRequest(String tag) {
        try {
            List<Call> queuedCalls = mHttpClient.dispatcher().queuedCalls();
            List<Call> runningCalls = mHttpClient.dispatcher().runningCalls();

            for (Call call : queuedCalls) {
                if (call.request().tag().equals(tag)) {
                    call.cancel();
                }
            }

            for (Call call : runningCalls) {
                if (call.request().tag().equals(tag)) {
                    call.cancel();
                }
            }

        } catch (Exception e) {
            Log.d("OkHttp", "cancelRequest Exception: " + e.toString());
        }
    }


}
