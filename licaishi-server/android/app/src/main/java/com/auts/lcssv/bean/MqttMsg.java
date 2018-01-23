package com.auts.lcssv.bean;


import java.io.Serializable;

/**
 * Created by qisheng.lv on 2017/8/2.
 */

public class MqttMsg implements Serializable{

    private static final long serialVersionUID = 2657568508660023640L;

    public MqttMsg(String topic, String mqttBody){
        this.topic = topic;
        this.mqttBody = mqttBody;
    }

    private String topic;

    private String mqttBody;

    private int qos;



}
