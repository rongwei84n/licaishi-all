package com.auts.lcssv.bean;

import java.io.Serializable;

/**
 *
 * @author qisheng.lv
 * @date 2017/8/18
 */

public class AppUpdate implements Serializable{

    private static final long serialVersionUID = 57543753413742519L;

    private int ret;

    private String id;

    private String ver_type;

    private String ver_name;

    private String ver_time;

    private String ver_infos;

    private String ver_down_url;

    private String ver_code;


    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVer_type() {
        return ver_type;
    }

    public void setVer_type(String ver_type) {
        this.ver_type = ver_type;
    }

    public String getVer_name() {
        return ver_name;
    }

    public void setVer_name(String ver_name) {
        this.ver_name = ver_name;
    }

    public String getVer_time() {
        return ver_time;
    }

    public void setVer_time(String ver_time) {
        this.ver_time = ver_time;
    }

    public String getVer_infos() {
        return ver_infos;
    }

    public void setVer_infos(String ver_infos) {
        this.ver_infos = ver_infos;
    }

    public String getVer_down_url() {
        return ver_down_url;
    }

    public void setVer_down_url(String ver_down_url) {
        this.ver_down_url = ver_down_url;
    }

    public String getVer_code() {
        return ver_code;
    }

    public void setVer_code(String ver_code) {
        this.ver_code = ver_code;
    }
}
