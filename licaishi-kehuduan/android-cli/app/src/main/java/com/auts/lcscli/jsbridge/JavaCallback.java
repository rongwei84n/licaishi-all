package com.auts.lcscli.jsbridge;

import java.io.Serializable;

/**
 * Created by qisheng.lv on 2017/7/31.
 */

public class JavaCallback implements Serializable {
    private static final long serialVersionUID = -2693996902854783749L;

    public JavaCallback(){

    }

    public JavaCallback(int code,String msg){
        this.errorCode = code;
        this.errorMsg = msg;
    }

    private int errorCode;

    private String errorMsg = "success";

    private String netType;

    private String netResponse;

    private String mqttData;

    private int hasChange;

    private String dataType;

    private String passResponse;


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getNetResponse() {
        return netResponse;
    }

    public void setNetResponse(String netResponse) {
        this.netResponse = netResponse;
    }

    public String getMqttData() {
        return mqttData;
    }

    public void setMqttData(String mqttData) {
        this.mqttData = mqttData;
    }

    public int getHasChange() {
        return hasChange;
    }

    public void setHasChange(int hasChange) {
        this.hasChange = hasChange;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getPassResponse() {
        return passResponse;
    }

    public void setPassResponse(String passResponse) {
        this.passResponse = passResponse;
    }
}
