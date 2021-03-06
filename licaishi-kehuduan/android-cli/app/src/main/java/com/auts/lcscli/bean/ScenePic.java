package com.auts.lcscli.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by weiming.zeng on 2017/9/7.
 */

public class ScenePic implements Serializable {
    private String pic_group_id;
    private List<Picture> group_pics;

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
