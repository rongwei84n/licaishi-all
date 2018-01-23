package com.auts.lcssv.net.request;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.constants.UrlConfig;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.net.callback.BaseCallback;
import com.auts.lcssv.net.engine.OkHttpUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * OkHttp Request基类
 * Created by qisheng.lv on 2017/4/19.
 */
public abstract class BaseRequest {
    String mUrl;
    Map<String, String> mParams;
    Request.Builder mBuilder;
    boolean mNeedCommonHeader = true;

    public BaseRequest addParams(String key, String value) {
        if (this.mParams == null) {
            mParams = new LinkedHashMap<>();
        }
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            mParams.put(key, value);
        }
        return this;
    }

    public BaseRequest addParams(String name, JSONObject value) {
        return this;
    }

    public BaseRequest addHeader(String name, String value) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(value) && mBuilder != null) {
            mBuilder.addHeader(name, value);
        }

        return this;
    }

    public BaseRequest needCommonHeader(boolean b) {
        mNeedCommonHeader = b;
        return this;
    }

    public abstract Request generateRequest();

    /**
     * 执行请求
     *
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Call run(String tag, BaseCallback<T> callback) {
        mBuilder.tag(TextUtils.isEmpty(tag) ? mUrl : tag);
        //如果需要添加token到heder
        if (mNeedCommonHeader) {
            addHeader("Authorization", mUrl.contains(UrlConfig.SzUrl.URL_HOST)
                    ? "Bearer " + AccountManager.getInstance().getValue(AppConstans.Sp.ACCESS_TOKEN) :
                    AccountManager.getInstance().getValue(AppConstans.Sp.ACCESS_TOKEN));
            addHeader("x-app-info", "9167601");
        }
        Request request = generateRequest();
        return OkHttpUtil.execute(request, callback);
    }

}
