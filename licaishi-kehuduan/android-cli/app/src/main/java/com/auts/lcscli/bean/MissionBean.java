package com.auts.lcscli.bean;

import java.io.Serializable;

/**
 * Created by weiming.zeng on 2017/9/7.
 */

public class MissionBean implements Serializable {

    private String room_name;
    private String device_name;
    private String socket_name;
    private String device_id;
    private int task_act;

    public MissionBean() {

    }

    public MissionBean(String room_name, String device_name, String socket_name, String device_id, int task_act) {
        this.room_name = room_name;
        this.device_name = device_name;
        this.socket_name = socket_name;
        this.device_id = device_id;
        this.task_act = task_act;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getSocket_name() {
        return socket_name;
    }

    public void setSocket_name(String socket_name) {
        this.socket_name = socket_name;
    }

    public int getTask_act() {
        return task_act;
    }

    public void setTask_act(int task_act) {
        this.task_act = task_act;
    }
}
