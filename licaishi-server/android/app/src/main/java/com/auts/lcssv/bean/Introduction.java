package com.auts.lcssv.bean;

import java.io.Serializable;

/**
 * Created by weiming.zeng on 2017/8/21.
 */

public class Introduction implements Serializable {
    private String introduction_title;
    private String introduction_content;
    private long introduction_publish_time;

    public String getIntroduction_title() {
        return introduction_title;
    }

    public void setIntroduction_title(String introduction_title) {
        this.introduction_title = introduction_title;
    }

    public String getIntroduction_content() {
        return introduction_content;
    }

    public void setIntroduction_content(String introduction_content) {
        this.introduction_content = introduction_content;
    }

    public long getIntroduction_publish_time() {
        return introduction_publish_time;
    }

    public void setIntroduction_publish_time(long introduction_publish_time) {
        this.introduction_publish_time = introduction_publish_time;
    }
}
