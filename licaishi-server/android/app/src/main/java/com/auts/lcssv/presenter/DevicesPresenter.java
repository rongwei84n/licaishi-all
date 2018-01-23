package com.auts.lcssv.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.auts.lcssv.base.BasePresenter;
import com.auts.lcssv.bean.DevicePic;
import com.auts.lcssv.bean.MyDevice;
import com.auts.lcssv.bean.QrProduct;
import com.auts.lcssv.bean.UserConfig;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.model.DevicesModel;
import com.auts.lcssv.net.callback.BaseCallback;
import com.auts.lcssv.net.callback.BeanCallback;
import com.auts.lcssv.net.callback.ListCallback;
import com.auts.lcssv.presenter.viewback.DevicesView;
import com.auts.lcssv.presenter.viewback.ILoadingView;

import java.util.List;

import okhttp3.Request;

/**
 * 设备相关Presneter
 * Created by xiaolei.yang on 2017/7/12.
 */

public class DevicesPresenter extends BasePresenter {
    private DevicesView mDevicesView;
    private DevicesModel mDevicesModel;


    public DevicesPresenter(Fragment fragment, ILoadingView loadingView, DevicesView mDevicesView) {
        this.mFragment = fragment;
        mILoadingView = loadingView;
        this.mDevicesView = mDevicesView;
        mDevicesModel = new DevicesModel();
    }

    public DevicesPresenter(Activity activity, ILoadingView loadingView, DevicesView mDevicesView) {
        this.mActivity = activity;
        mILoadingView = loadingView;
        this.mDevicesView = mDevicesView;
        mDevicesModel = new DevicesModel();
    }

    public void getUserConfig() {
        mDevicesModel.getUserConfig(new BeanCallback<UserConfig>() {

            @Override
            public void onError(String code, String msg) {
                if (mDevicesView != null) {
                    mDevicesView.onGetUserConfigError(code, msg);
                }
            }

            @Override
            public void onSuccess(UserConfig userConfig) {
                if (mDevicesView != null) {
                    if (userConfig != null && userConfig.selected_family != null && !TextUtils.isEmpty(userConfig.selected_family.family_id)) {
                        AccountManager.getInstance().saveFamilyId(userConfig.selected_family.family_id);
                        mDevicesView.onGetUserConfigSuccess(userConfig);
                    } else {
                        mDevicesView.onGetUserConfigError("0", "未获取到FamilyId");
                    }
                }
            }
        });
    }


    public void getMyDevices() {
        String familyId = AccountManager.getInstance().getFamilyId();
        mDevicesModel.getMyDevice(1, 100, familyId, new BeanCallback<MyDevice>() {

            @Override
            public void onError(String code, String msg) {
                if (mDevicesView != null) {
                    mDevicesView.onGetMyDevicesError(code, msg);
                }
            }

            @Override
            public void onSuccess(MyDevice myDevice) {
                if (mDevicesView != null) {
                    mDevicesView.onGetMyDevicesSuccess(myDevice);
                }
            }
        });
    }

    /**
     * 获取设备图片
     */
    public void getDevicePic() {
        showLoading(0);
        mDevicesModel.getDevicePic(new ListCallback<DevicePic>() {
            @Override
            public void onSuccess(List<DevicePic> t) {
                hideLoading();
                mDevicesView.onGetDevicePicSuccess(t);
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                mDevicesView.onGetDevicePicError(code, msg);
            }
        });
    }

    public void saveDeviceInfo(String deviceId, String deviceNickname, int taskRemind, String roomId, int position, String picGroupId) {
        showLoading(0);
        mDevicesModel.saveDeviceInfo(deviceId, deviceNickname, taskRemind, roomId, position, picGroupId, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                mDevicesView.onSaveDevicePicError(code, msg);
            }

            @Override
            public void onSuccess(String result, Request request) {
                hideLoading();
                mDevicesView.onSaveDevicePicSuccess(result);
            }
        });
    }

    public void changeAllTaskRemind(boolean isOpen) {
        mDevicesModel.changeAllTaskRemind(isOpen, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                mDevicesView.onChangeTriggerError(code, msg);
            }

            @Override
            public void onSuccess(String result, Request request) {
                mDevicesView.onChangeTriggerSuccess(result);
            }
        });
    }

    public void getQrProducts() {
        showLoading(0);
        mDevicesModel.getQrProducts(new ListCallback<QrProduct>() {

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mDevicesView != null) {
                    mDevicesView.onGetQrProductsError(code, msg);
                }
            }

            @Override
            public void onSuccess(List<QrProduct> list) {
                hideLoading();
                if (mDevicesView != null) {
                    mDevicesView.onGetQrProductsSuccess(list);
                }
            }
        });
    }


}
