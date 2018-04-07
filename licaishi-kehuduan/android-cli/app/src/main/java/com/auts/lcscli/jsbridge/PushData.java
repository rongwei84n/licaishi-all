package com.auts.lcscli.jsbridge;

import java.io.Serializable;

/**
 * Created by qisheng.lv on 2017/8/1.
 */

public class PushData implements Serializable {

    private static final long serialVersionUID = -3305449635542679841L;

    private String pushType;

    private String topic;

    private String pushData;

    public PushData(String pushType, String topic, String pushData) {
        this.pushType = pushType;
        this.topic = topic;
        this.pushData = pushData;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPushData() {
        return pushData;
    }

    public void setPushData(String pushData) {
        this.pushData = pushData;
    }
}
