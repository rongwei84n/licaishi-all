package com.auts.lcssv.presenter;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BasePresenter;
import com.auts.lcssv.bean.ReadDeviceInfoBean;
import com.auts.lcssv.bean.WriteSsidInfoBean;
import com.auts.lcssv.model.SoftApDeviceModel;
import com.auts.lcssv.net.callback.BeanCallback;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.presenter.viewback.SoftApDeviceView;
import com.auts.lcssv.util.CommonUtils;
import com.auts.lcssv.util.LogUtils;

/**
 * 与智能设备交互
 *
 * @author xiaolei.yang
 * @date 2017/7/6
 */

public class SoftApDevicePresenter extends BasePresenter {
    private SoftApDeviceView mSoftApDeviceView;
    private SoftApDeviceModel mSoftApDeviceModel;

    public SoftApDevicePresenter(ILoadingView loadingView, SoftApDeviceView mSoftApDeviceView) {
        this.mILoadingView = loadingView;
        this.mSoftApDeviceView = mSoftApDeviceView;
        mSoftApDeviceModel = new SoftApDeviceModel();
    }

    /**
     * 读取设备周围的SSID列表
     */
    public void readDeviceInfo() {
        showLoading(R.string.loading_text);
        mSoftApDeviceModel.readDeviceInfo(new BeanCallback<ReadDeviceInfoBean>() {
            @Override
            public void onSuccess(ReadDeviceInfoBean readDeviceInfoBean) {
                hideLoading();
                LogUtils.error("======", "readDeviceInfoonSuccess: " + (readDeviceInfoBean == null ? "" : readDeviceInfoBean.toString()));
                if (mSoftApDeviceView != null) {
                    if (readDeviceInfoBean != null) {
                        mSoftApDeviceView.readDeviceSsidSSuccess(readDeviceInfoBean.getWifi_scan());
                        mSoftApDeviceView.readDeviceIdSuccess(readDeviceInfoBean.getDEVICEID());
                    } else {
                        mSoftApDeviceView.readDeviceSsidError("0", CommonUtils.getString(R.string.get_device_wifi_fail));
                    }
                }
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                LogUtils.error("======", "readDeviceInfoonError: " + code + "===" + msg);
                if (mSoftApDeviceView != null) {
                    mSoftApDeviceView.readDeviceSsidError(code, msg);
                }

            }


        });
    }

    /**
     * 向设备写入要连接的ssid信息
     *
     * @param ssid     要连接的ssid
     * @param password 要连接的ssid的密码
     */
    public void writeSsidInfo(String ssid, String password) {
        mSoftApDeviceModel.writeSsidInfo(ssid, password, new BeanCallback<WriteSsidInfoBean>() {
            @Override
            public void onSuccess(WriteSsidInfoBean writeSsidInfoBean) {
                if (mSoftApDeviceView != null) {
                    mSoftApDeviceView.writeSsidSSuccess(writeSsidInfoBean);
                }
            }

            @Override
            public void onError(String code, String msg) {
                if (mSoftApDeviceView != null) {
                    mSoftApDeviceView.writeSsidError(code, msg);
                }
            }
        });
    }


//    /**
//     * 获取设备与ssid之间的连接状态
//     */
//    public void getConnState() {
//        mSoftApDeviceModel.getConnState(hasnew BeanCallback<GetConnStateBean>() {
//            @Override
//            public void onSuccess(GetConnStateBean getConnStateBean) {
//                if (mSoftApDeviceView != null) {
//                    mSoftApDeviceView.getConnStateSuccess(getConnStateBean);
//                }
//            }
//
//            @Override
//            public void onError(String code, String msg) {
//                if (mSoftApDeviceView != null) {
//                    mSoftApDeviceView.getConnStateError(code, msg);
//                }
//            }
//        });
//    }

//    /**
//     * 关闭设备的SoftAp
//     */
//    public void closeSoftAp() {
//        mSoftApDeviceModel.closeSoftAp(hasnew BeanCallback<WriteSsidInfoBean>() {
//            @Override
//            public void onSuccess(WriteSsidInfoBean writeSsidInfoBean) {
//                if (mSoftApDeviceView != null) {
//                    mSoftApDeviceView.closeSoftApSuccess(writeSsidInfoBean);
//                }
//
//            }
//
//            @Override
//            public void onError(String code, String msg) {
//                if (mSoftApDeviceView != null) {
//                    mSoftApDeviceView.closeSoftApError(code, msg);
//                }
//
//            }
//        });
//    }


//    /**
//     * 获取设备与ssid之间的连接状态
//     */
//    public void bindDevice(String macAddress) {
//        mSoftApDeviceModel.bindDevice(macAddress, hasnew BeanCallback() {
//            @Override
//            public void onSuccess(Object o) {
//                if (mSoftApDeviceView != null) {
//                    mSoftApDeviceView.bindDeviceSuccess();
//                }
//            }
//
//            @Override
//            public void onError(String code, String msg) {
//                if (mSoftApDeviceView != null) {
//                    mSoftApDeviceView.bindDeviceError(code, msg);
//                }
//            }
//        });
//    }

}
