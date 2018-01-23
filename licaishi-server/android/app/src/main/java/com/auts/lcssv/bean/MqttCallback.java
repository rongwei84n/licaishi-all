package com.auts.lcssv.bean;

import com.auts.lcssv.jsbridge.JavaBridge;

import java.io.Serializable;

/**
 * Created by qisheng.lv on 2017/9/4.
 */

public class MqttCallback implements Serializable{
    private static final long serialVersionUID = 4128523498178680517L;

    public int dataType;

    public  boolean needUn;

    public JavaBridge.JsCallback jsCallback;

}
