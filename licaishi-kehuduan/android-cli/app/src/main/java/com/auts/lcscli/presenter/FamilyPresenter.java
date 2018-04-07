package com.auts.lcscli.presenter;

import com.auts.lcscli.base.BasePresenter;
import com.auts.lcscli.bean.RoomBean;
import com.auts.lcscli.model.FamilyModel;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.callback.BeanCallback;
import com.auts.lcscli.net.callback.ListCallback;
import com.auts.lcscli.presenter.viewback.FamilyView;
import com.auts.lcscli.presenter.viewback.ILoadingView;

import java.util.List;

import okhttp3.Request;

/**
 * 添加设备过程中，家庭相关的
 * Created by xiaolei.yang on 2017/8/9.
 */

public class FamilyPresenter extends BasePresenter {
    private FamilyView mFamilyView;
    private FamilyModel mFamilyModel;

    public FamilyPresenter(ILoadingView loadingView, FamilyView mFamilyView) {
        this.mILoadingView = loadingView;
        this.mFamilyView = mFamilyView;
        mFamilyModel = new FamilyModel();
    }

    public void rooms(String familyId) {
        showLoading(0);
        mFamilyModel.rooms(familyId, new ListCallback<RoomBean>() {
            @Override
            public void onSuccess(List<RoomBean> roomsBeans) {
                hideLoading();
                if (mFamilyView != null) {
                    mFamilyView.roomsSuccess(roomsBeans);
                }
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mFamilyView != null) {
                    mFamilyView.roomsError(code, msg);
                }
            }
        });
    }

    public void addDeviceToRoom(String familyId, String roomId, String deviceId) {
        mFamilyModel.addDeviceToRoom(familyId, roomId, deviceId, new BaseCallback() {
            @Override
            public void onSuccess(String result, Request request) {
                if (mFamilyView != null) {
                    mFamilyView.addDeviceToRoomSuccess();
                }
            }

            @Override
            public void onError(String code, String msg) {
                if (mFamilyView != null) {
                    mFamilyView.addDeviceToRoomError(code, msg);
                }
            }


        });
    }

    public void modifyDevice(String deviceId, String deviceUrl, String deviceName, String roomId) {
        mFamilyModel.modifyDevice(deviceId, deviceUrl, deviceName, roomId, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                if (mFamilyView != null) {
                    mFamilyView.modifyDeviceError(code, msg);
                }
            }

            @Override
            public void onSuccess(String result, Request request) {
                if (mFamilyView != null) {
                    mFamilyView.modifyDeviceSuccess();
                }
            }
        });
    }

    public void addRoom(String familyId, String roomPicUrl, String roomNickname) {
        mFamilyModel.addRoom(familyId, roomPicUrl, roomNickname, new BeanCallback<RoomBean>() {
            @Override
            public void onSuccess(RoomBean roomBean) {
                if (mFamilyView != null) {
                    mFamilyView.addRoomSuccess(roomBean);
                }
            }

            @Override
            public void onError(String code, String msg) {
                if (mFamilyView != null) {
                    mFamilyView.addRoomError(code, msg);
                }
            }
        });
    }
}
