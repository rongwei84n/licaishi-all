package com.auts.lcssv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by weiming.zeng on 2017/8/15.
 */

public class ScenePicBean implements Serializable {
    private String pic_group_id;
    private List<Picture> group_pics;

    public ScenePicBean(String pic_group_id) {
        this.pic_group_id = pic_group_id;
    }

    public ScenePicBean() {
    }

    public String getPic_group_id() {
        return pic_group_id;
    }

    public void setPic_group_id(String pic_group_id) {
        this.pic_group_id = pic_group_id;
    }

    public List<Picture> getGroup_pics() {
        return group_pics;
    }

    public void setGroup_pics(List<Picture> group_pics) {
        this.group_pics = group_pics;
    }
}
