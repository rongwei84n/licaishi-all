package com.auts.lcscli.model;

import com.auts.lcscli.bean.MissionBean;
import com.auts.lcscli.constants.UrlConfig;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.engine.OkHttpUtil;

import java.util.List;

/**
 * 场景model
 * Created by weiming.zeng on 2017/9/6.
 */

public class SceneModel {

    public void getScene(BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.GET_SCENE)
                .run(null, callback);
    }

    //修改场景
    public void modifySceneInfo(String scenceId, String scenceNickname, String groupPicId, List<MissionBean> tasks, BaseCallback callback) {
        handleSocketName(tasks);
        OkHttpUtil.postJson(UrlConfig.SzUrl.MODIFY_SCENE + scenceId)
                .addParams("scence_nickname",scenceNickname)
                .addParams("pic_group_id", groupPicId)
                .addParams("task_list", tasks)
                .run(null, callback);
    }

    public void addSceneInfo(String scenceNickname, String groupPicId, List tasks, BaseCallback callback) {
        handleSocketName(tasks);
        OkHttpUtil.postJson(UrlConfig.SzUrl.ADD_SCENE )
                .addParams("scence_nickname",scenceNickname)
                .addParams("pic_group_id", groupPicId)
                .addParams("task_list", tasks)
                .run(null, callback);
    }

    private void handleSocketName(List<MissionBean> tasks){
        for (MissionBean bean : tasks) {
            String socketName = null;
            if (bean.getSocket_name().startsWith("插口")) {
                socketName = bean.getSocket_name().substring(2);
            }
            bean.setSocket_name(socketName.toLowerCase());
        }
    }
    //删除场景
    public void deleteSceneInfo(String scenceId, BaseCallback callback) {
        OkHttpUtil.delete(UrlConfig.SzUrl.MODIFY_SCENE + scenceId )
                .run(null, callback);
    }

    public void getScenePicData(BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.GET_SCENEPIC)
                .run(null, callback);
    }

    public void getMissionData(String scenceId, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.GET_MISSION + scenceId + "/tasks")
                .run(null, callback);
    }
}
