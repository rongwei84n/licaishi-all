package com.auts.backstage.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author rongwei.huang
 *
 */
public class PhiHomeBaseResponse {

    @JsonProperty("status")
    private int code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("result")
    private Object result;

    public PhiHomeBaseResponse() {
        code = 200;
        message = "";
    }

    public PhiHomeBaseResponse(long timeStamp, int code, String error, String exception, String message, String path) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the result
     */
    public Object getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(Object result) {
        this.result = result;
    }

}
