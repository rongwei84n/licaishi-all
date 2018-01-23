package com.auts.lcssv.net.callback;

import com.alibaba.fastjson.JSON;
import com.auts.lcssv.net.engine.Err2MsgUtils;
import com.auts.lcssv.util.GenericUtils;
import com.auts.lcssv.util.LogUtils;

import okhttp3.Request;

/**
 * 网络请求回调Bean解析类
 * Created by qisheng.lv on 2017/4/12.
 */
public abstract class BeanCallback<T> extends BaseCallback<T> {

    @Override
    public void onError(int code, String msg) {

    }

    public abstract void onSuccess(T t);

    @Override
    public void onSuccess(String result, Request request) {
        Class clasz = GenericUtils.getGenericClass(getClass());
        T obj = null;
        try {
            obj = (T) JSON.parseObject(result, clasz);
        } catch (Exception e) {
            LogUtils.debug(e);
        }

        if (obj == null) {
            toUiError(Err2MsgUtils.CODE_PARSE_ERROR, null, request);
        } else {
            onSuccess(obj);
        }
    }

}
