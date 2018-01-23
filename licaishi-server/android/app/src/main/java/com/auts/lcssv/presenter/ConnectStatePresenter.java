package com.auts.lcssv.presenter;

import com.auts.lcssv.base.BasePresenter;
import com.auts.lcssv.bean.BindStatusBean;
import com.auts.lcssv.model.ConnectStateModel;
import com.auts.lcssv.net.callback.BeanCallback;
import com.auts.lcssv.presenter.viewback.ConnectStateView;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.util.LogUtils;

/**
 * 设备连接状态
 * Created by xiaolei.yang on 2017/8/8.
 */

public class ConnectStatePresenter extends BasePresenter {
    private ConnectStateView mConnectStateView;
    private ConnectStateModel mConnectStateModel;

    public ConnectStatePresenter(ILoadingView loadingView, ConnectStateView connectStateView) {
        mILoadingView = loadingView;
        mConnectStateView = connectStateView;
        mConnectStateModel = new ConnectStateModel();
    }

    public void checkBindStatus(String deviceId) {
        mConnectStateModel.checkBindStatus(deviceId, new BeanCallback<BindStatusBean>() {
            @Override
            public void onSuccess(BindStatusBean bindStatusBean) {
                LogUtils.error("====checkBindStatus", "onSuccess: " + (bindStatusBean == null ? "++" : bindStatusBean.toString()));
                if (mConnectStateView != null) {
                    mConnectStateView.bindDeviceSuccess(bindStatusBean);
                }
            }

            @Override
            public void onError(String code, String msg) {
                LogUtils.error("====checkBindStatus", "onError: " + code + "====" + msg);
                if (mConnectStateView != null) {
                    mConnectStateView.bindDeviceFail(code, msg);
                }
            }
        });
    }

    public void cancelCheckBindStatus() {
        mConnectStateModel.cancelCheckBindStatus();
    }


}
