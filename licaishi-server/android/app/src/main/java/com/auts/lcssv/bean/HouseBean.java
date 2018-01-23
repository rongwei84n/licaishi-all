package com.auts.lcssv.bean;

import java.io.Serializable;

/**
 * Created by weiming.zeng on 2017/8/8.
 */

public class HouseBean implements Serializable {
    private static final long serialVersionUID = 1502366242432716164L;
    private String family_id;
    private String room_id;
    private String family_nickname;
    private String room_nickname;
    private String family_pic_url;
    private String room_pic_url;
    public int position;

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public void setFamily_nickname(String family_nickname) {
        this.family_nickname = family_nickname;
    }

    public void setRoom_nickname(String room_nickname) {
        this.room_nickname = room_nickname;
    }

    public void setFamily_pic_url(String family_pic_url) {
        this.family_pic_url = family_pic_url;
    }

    public void setRoom_pic_url(String room_pic_url) {
        this.room_pic_url = room_pic_url;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getRoom_id() {
        return room_id;
    }

    public String getFamily_nickname() {
        return family_nickname;
    }

    public String getRoom_nickname() {
        return room_nickname;
    }

    public String getFamily_pic_url() {
        return family_pic_url;
    }

    public String getRoom_pic_url() {
        return room_pic_url;
    }

    public int getPosition() {
        return position;
    }
}
