package com.phicomm.smarthome.authservice.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.consts.PhihomeConst.ResponseStatus;
import com.phicomm.smarthome.util.MyResponseutils;

/**
 * 返回给客户端Json数据基类，返回的数据必须包含ret_code和ret_msg数据
 * @author rongwei.huang
 *
 */
public class BusinessBaseResponseModel {
    @JsonProperty("ret_status")
    private int retCode;
    
    @JsonProperty("ret_message")
    private String retMsg;
    
    public BusinessBaseResponseModel() {
        retCode = ResponseStatus.STATUS_OK;
        retMsg = MyResponseutils.parseMsg(ResponseStatus.STATUS_OK);
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
        this.retMsg = MyResponseutils.parseMsg(retCode);
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
