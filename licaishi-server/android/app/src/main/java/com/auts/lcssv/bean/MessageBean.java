package com.auts.lcssv.bean;

import java.io.Serializable;

/**
 * Created by weiming.zeng on 2017/8/25.
 */

public class MessageBean implements Serializable {
    private String msg_title;
    private String msg_content;
    private String msg_type;
    private String device_name;
    private long create_time;
    private int read_status;
    private String device_pic_url;

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_pic_url() {
        return device_pic_url;
    }

    public void setDevice_pic_url(String device_pic_url) {
        this.device_pic_url = device_pic_url;
    }

    public String getMsg_title() {
        return msg_title;
    }

    public void setMsg_title(String msg_title) {
        this.msg_title = msg_title;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getRead_status() {
        return read_status;
    }

    public void setRead_status(int read_status) {
        this.read_status = read_status;
    }
}
