package com.auts.lcscli.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.auts.lcscli.R;
import com.auts.lcscli.base.BasePresenter;
import com.auts.lcscli.bean.MissionBean;
import com.auts.lcscli.bean.SceneBean;
import com.auts.lcscli.bean.ScenePic;
import com.auts.lcscli.model.SceneModel;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.callback.ListCallback;
import com.auts.lcscli.presenter.viewback.ILoadingView;
import com.auts.lcscli.presenter.viewback.SceneView;
import com.auts.lcscli.util.LogUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Request;

/**
 * 场景presenter
 * Created by weiming.zeng on 2017/9/6.
 */

public class ScenePresenter extends BasePresenter {

    private SceneModel model;
    private SceneView view;

    public ScenePresenter(ILoadingView loadingView, SceneView view) {
        this.mILoadingView = loadingView;
        this.model = new SceneModel();
        this.view = view;
    }

    public void getScene() {
        showLoading(R.string.empty);
        model.getScene(new ListCallback<SceneBean>() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                view.getSceneError(msg);
            }

            @Override
            public void onSuccess(List<SceneBean> t) {
                hideLoading();
                view.getSceneSuccess(t);
            }
        });
    }

    public void modifySceneInfo(String scenceId, String scenceNickname, String scenePicId, List tasks) {
        model.modifySceneInfo(scenceId, scenceNickname, scenePicId, tasks, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                view.modifySceneError(msg);
            }

            @Override
            public void onSuccess(String result, Request request) {
                view.modifySceneSuccess(result);
            }
        });
    }

    public void addSceneInfo(String scenceNickname, String scencePicUrl, List tasks) {
        model.addSceneInfo(scenceNickname, scencePicUrl, tasks, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                view.addSceneError(msg);
            }

            @Override
            public void onSuccess(String result, Request request) {
                view.addSceneSuccess(result);
            }
        });
    }

    public void deleteSceneInfo(String scenceId) {
        model.deleteSceneInfo(scenceId, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                view.removeSceneError(msg);
            }

            @Override
            public void onSuccess(String result, Request request) {
                view.removeSceneSuccess(result);
            }
        });
    }


    public void getScenePicData() {
        showLoading(R.string.empty);
        model.getScenePicData(new ListCallback<ScenePic>() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                view.getPicError(msg);
            }

            @Override
            public void onSuccess(List<ScenePic> t) {
                hideLoading();
                view.getPicSuccess(t);
            }
        });
    }

    public void getMissionData(String scenceId) {
        showLoading(R.string.empty);
        model.getMissionData(scenceId, new ListCallback<MissionBean>() {
            @Override
            public void onSuccess(List<MissionBean> t) {
                hideLoading();
                view.getMissionSuccess(t);
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                view.getMissionError(msg);
            }
        });
    }

    /**
     * 执行场景任务
     */
    public void executeMission(Context context, List<MissionBean> missions) {
        
    }

    public Set<String> getDevice(List<MissionBean> missions) {
        Set<String> device = new HashSet<>();
        for (MissionBean mission : missions) {
            device.add(mission.getDevice_id());
        }
        return device;
    }

    private String getMissionBody(List<MissionBean> missions) {
        JSONObject switchState = new JSONObject();
        for (MissionBean bean : missions) {
            String socketName = bean.getSocket_name();
            if (socketName.startsWith("插口")) {
                socketName = socketName.substring(2).toLowerCase();
            }
            switchState.put(socketName, bean.getTask_act());
        }
        JSONObject switchJson = new JSONObject();
        switchJson.put("switch", switchState);
        JSONObject desired = new JSONObject();
        desired.put("desired", switchJson);
        JSONObject json = new JSONObject();
        json.put("state", desired);
        return json.toString();
    }

}
