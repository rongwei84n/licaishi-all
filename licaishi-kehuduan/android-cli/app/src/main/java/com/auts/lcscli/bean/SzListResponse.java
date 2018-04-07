package com.auts.lcscli.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 网络响应基类-深圳后台
 * Created by qisheng.lv on 2017/7/13.
 */

public class SzListResponse<T> extends BaseSzResponse implements Serializable{
    private static final long serialVersionUID = -2686590170564090399L;

    private List<T> result;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

}
