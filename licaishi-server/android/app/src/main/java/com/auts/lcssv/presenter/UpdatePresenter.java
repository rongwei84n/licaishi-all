package com.auts.lcssv.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auts.lcssv.base.BasePresenter;
import com.auts.lcssv.bean.AppUpdate;
import com.auts.lcssv.bean.RoomUpdate;
import com.auts.lcssv.model.UpdateModel;
import com.auts.lcssv.net.callback.BeanCallback;
import com.auts.lcssv.presenter.viewback.UpdateView;
import com.auts.lcssv.util.AppInfoUtils;
import com.auts.lcssv.util.LogUtils;

/**
 * Created by qisheng.lv on 2017/8/18.
 */

public class UpdatePresenter extends BasePresenter {

    private static final String PLAT_ID = "2015100011";
    private static final int APP_UPDATE_ID = 3;

    private UpdateModel mModel;
    private UpdateView mView;

    public UpdatePresenter(UpdateView updateView) {
        this.mView = updateView;
        mModel = new UpdateModel();
    }

    public void checkAppUpdate() {
        int vercode = AppInfoUtils.getAppVersionCode();
        String channel = AppInfoUtils.getChannelName();

        mModel.appUpdate(APP_UPDATE_ID, PLAT_ID, vercode, channel, new BeanCallback<AppUpdate>() {

            @Override
            public void onError(String code, String msg) {
                if (mView != null) {
                    mView.onAppCheckUpdateError(code, msg);
                }
            }

            @Override
            public void onSuccess(AppUpdate appUpdate) {
                if (mView != null) {
                    mView.onAppCheckUpdateSuccess(appUpdate);
                }
            }
        });

    }

    public void checkRoomUpdate(final String deviceId, final Context context) {
        mModel.roomUpdate(deviceId, new BeanCallback<RoomUpdate>() {

            @Override
            public void onError(String code, String msg) {
                LogUtils.show("checkRoomUpdate: " + msg);
            }

            @Override
            public void onSuccess(RoomUpdate roomUpdate) {
                LogUtils.show("checkRoomUpdate: " + roomUpdate);
                if (!TextUtils.isEmpty(roomUpdate.file_url)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("action", "check_update");
                    jsonObject.put("device_id", deviceId);
                    jsonObject.put("fw_bin_url", roomUpdate.file_url);
                    jsonObject.put("fw_ver", roomUpdate.fw_ver);
                }

            }
        });
    }

}
