package com.auts.lcscli.net.callback;

import com.alibaba.fastjson.JSON;
import com.auts.lcscli.net.engine.Err2MsgUtils;
import com.auts.lcscli.util.GenericUtils;
import com.auts.lcscli.util.LogUtils;

import java.util.List;

import okhttp3.Request;

/**
 * 网络请求回调Bean解析类
 * Created by qisheng.lv on 2017/4/12.
 */
public abstract class ListCallback<T> extends BaseCallback<T> {

    @Override
    public void onError(int code, String msg) {

    }

    public abstract void onSuccess(List<T> t);

    @Override
    public void onSuccess(String result, Request request) {
        Class clasz = GenericUtils.getGenericClass(getClass());
        List<T> list = null;
        try {
            list = JSON.parseArray(result, clasz);
        } catch (Exception e) {
            LogUtils.show(e);
        }

        if (list == null) {
            toUiError(Err2MsgUtils.CODE_PARSE_ERROR, null, request);
        } else {
            onSuccess(list);
        }
    }

}
