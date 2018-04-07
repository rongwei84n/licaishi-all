package com.auts.lcscli.bean;

/**
 * 网络响应基类-深圳后台
 * Created by qisheng.lv on 2017/7/13.
 */

public class SzResponse extends BaseSzResponse{
    private static final long serialVersionUID = -2686590170564090399L;

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
