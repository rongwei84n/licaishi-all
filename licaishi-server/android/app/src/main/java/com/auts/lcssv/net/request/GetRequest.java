package com.auts.lcssv.net.request;

import android.net.Uri;

import java.util.Iterator;
import java.util.Map;

import okhttp3.Request;

/**
 * Get请求
 * Created by qisheng.lv on 2017/4/12.
 */
public class GetRequest extends BaseRequest {

    public GetRequest(String url) {
        this.mUrl = url;
        mBuilder = new Request.Builder();
    }


    public String generateUrl(String url) {
        return getNormalUrl(url);
    }


    private String getNormalUrl(String url) {
        Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
        if (mParams != null && mParams.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = mParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                uriBuilder.appendQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        return uriBuilder.build().toString();
    }

    @Override
    public Request generateRequest() {
        String getUrl = generateUrl(mUrl);
        mBuilder = mBuilder.url(getUrl);
        return mBuilder.get().build();
    }

}
