package com.auts.lcssv.net.request;

import android.text.TextUtils;

import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * post请求：请求体为键值对
 * Created by qisheng.lv on 2017/4/12.
 */
public class PostRequest extends BaseRequest {

    public PostRequest(String url) {
        this.mUrl = url;
        mBuilder = new Request.Builder().url(url);
    }

    @Override
    public Request generateRequest() {
        return getNormalRequest();
    }

    public Request getNormalRequest() {
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (mParams != null && mParams.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = mParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                    formBuilder.add(entry.getKey(), entry.getValue());
                }
            }
        }
        return mBuilder.post(formBuilder.build()).build();
    }



}
