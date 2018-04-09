package com.auts.lcscli.bean;

import java.io.Serializable;

/**
 * 智能设备所属房间
 *
 * @author xiaolei.yang
 * @date 2017/8/5
 */

public class RoomBean implements Serializable {
    private static final long serialVersionUID = -7903036971237920855L;

    private String family_id;
    private String room_id;
    private String room_nickname;
    private String room_pic_url;
    private int position;

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_nickname() {
        return room_nickname;
    }

    public void setRoom_nickname(String room_nickname) {
        this.room_nickname = room_nickname;
    }

    public String getRoom_pic_url() {
        return room_pic_url;
    }

    public void setRoom_pic_url(String room_pic_url) {
        this.room_pic_url = room_pic_url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    @Override
    public String toString() {
        return "RoomBean{" +
                "family_id='" + family_id + '\'' +
                ", room_id='" + room_id + '\'' +
                ", room_nickname='" + room_nickname + '\'' +
                ", room_pic_url='" + room_pic_url + '\'' +
                ", position=" + position +
                '}';
    }
}
