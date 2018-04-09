package com.auts.lcscli.presenter;

import com.auts.lcscli.base.BasePresenter;
import com.auts.lcscli.bean.ProductTypeBean;
import com.auts.lcscli.bean.TimestampBean;
import com.auts.lcscli.constants.AppConstans;
import com.auts.lcscli.manager.AccountManager;
import com.auts.lcscli.model.AddDevicesModel;
import com.auts.lcscli.net.callback.BeanCallback;
import com.auts.lcscli.net.callback.ListCallback;
import com.auts.lcscli.presenter.viewback.AddDevicesView;
import com.auts.lcscli.presenter.viewback.ILoadingView;

import java.util.List;

/**
 * 获取设备类型列表
 *
 * @author xiaolei.yang
 * @date 2017/8/9
 */

public class AddDevicesPresenter extends BasePresenter {
    private AddDevicesModel mAddDevicesModel;
    private AddDevicesView mAddDevicesView;

    public AddDevicesPresenter(ILoadingView loadingView, AddDevicesView addDevicesView) {
        this.mILoadingView = loadingView;
        this.mAddDevicesView = addDevicesView;
        mAddDevicesModel = new AddDevicesModel();
    }

    public void deviceTypes() {
        showLoading(0);
        mAddDevicesModel.deviceTypes(new ListCallback<ProductTypeBean>() {

            @Override
            public void onSuccess(List<ProductTypeBean> productTypeBeans) {
                hideLoading();
                if (mAddDevicesView != null) {
                    mAddDevicesView.deviceTypesSuccess(productTypeBeans);
                }
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mAddDevicesView != null) {
                    mAddDevicesView.deviceTypeError(code, msg);
                }
            }
        });
    }

    public void timestamp() {
        showLoading(0);
        mAddDevicesModel.timestamp(new BeanCallback<TimestampBean>() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mAddDevicesView != null) {
                    mAddDevicesView.onTimestampError(code,msg);
                }
            }

            @Override
            public void onSuccess(TimestampBean timestampBean) {
                if (timestampBean != null) {
                    AccountManager.getInstance().saveValue(AppConstans.Sp.TIMESTAMP, String.valueOf(timestampBean.timestamp));
                    if (mAddDevicesView != null) {
                        mAddDevicesView.onTimestampSuccess();
                    }else{
                        hideLoading();
                    }
                }else{
                    hideLoading();
                    if (mAddDevicesView != null) {
                        mAddDevicesView.onTimestampError("1","获取时间信息失败");
                    }
                }
            }
        });
    }
}
