package com.auts.lcscli.bean;

import java.io.Serializable;

/**
 * 图片
 * Created by weiming.zeng on 2017/8/10.
 */

public class Picture implements Serializable {
    private String pic_url;



    private String pic_type;

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getPic_type() {
        return pic_type;
    }

    public void setPic_type(String pic_type) {
        this.pic_type = pic_type;
    }
}
