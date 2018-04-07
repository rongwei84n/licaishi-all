package com.auts.lcscli.net.request;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Post请求：请求体为json
 * Created by qisheng.lv on 2017/4/12.
 */
public class PostJsonRequest extends BaseRequest {
    private JSONObject jsonObj;

    public PostJsonRequest(String url) {
        this.mUrl = url;
        jsonObj = new JSONObject();
        mBuilder = new Request.Builder().url(url);
    }

    @Override
    public PostJsonRequest addParams(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            jsonObj.put(key, value);
        }
        return this;
    }

    public PostJsonRequest addParams(String key, JSONObject value) {
        if (!TextUtils.isEmpty(key) && value != null) {
            jsonObj.put(key, value);
        }
        return this;
    }

    public PostJsonRequest addParams(String key, List value) {
        if (value != null) {
            JSONArray jsonArray = new JSONArray(value);
            jsonObj.put(key, jsonArray);
        }
        return this;
    }

    @Override
    public Request generateRequest() {
        final MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(jsonType, jsonObj.toString());
        return mBuilder.post(requestBody).build();
    }

}
