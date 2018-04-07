package com.auts.lcscli.presenter.viewback;

import com.auts.lcscli.bean.MissionBean;
import com.auts.lcscli.bean.SceneBean;
import com.auts.lcscli.bean.ScenePic;

import java.util.List;

/**
 * 场景回调
 * Created by weiming.zeng on 2017/9/6.
 */

public class SceneView {

    public void getSceneSuccess(List<SceneBean> data) {}

    public void getSceneError(String msg) {}

    public void getPicSuccess(List<ScenePic> data) {}

    public void getPicError(String msg) {}

    public void modifySceneSuccess(String msg) {}

    public void modifySceneError(String msg) {}

    public void removeSceneSuccess(String msg) {}

    public void removeSceneError(String msg) {}

    public void addSceneSuccess(String msg) {}

    public void addSceneError(String msg) {}

    public void getMissionSuccess(List<MissionBean> data) {}

    public void getMissionError(String msg) {}

    public void setViewClock() {}

    public void removeClock() {}

}
