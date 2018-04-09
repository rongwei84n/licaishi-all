package com.auts.lcscli.bean;

import java.io.Serializable;

/**
 * 场景
 * Created by weiming.zeng on 2017/9/6.
 */

public class SceneBean implements Serializable{
    private String scence_id;
    private String scence_nickname;
    private ScenePicBean scence_pic_group;
    private int position;

    public String getScence_id() {
        return scence_id;
    }

    public void setScence_id(String scence_id) {
        this.scence_id = scence_id;
    }

    public String getScence_nickname() {
        return scence_nickname;
    }

    public void setScence_nickname(String scence_nickname) {
        this.scence_nickname = scence_nickname;
    }

    public ScenePicBean getScence_pic_group() {
        return scence_pic_group;
    }

    public void setScence_pic_group(ScenePicBean scence_pic_group) {
        this.scence_pic_group = scence_pic_group;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
