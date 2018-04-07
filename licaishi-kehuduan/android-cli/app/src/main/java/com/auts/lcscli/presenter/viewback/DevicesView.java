package com.auts.lcscli.presenter.viewback;

import com.auts.lcscli.bean.DevicePic;
import com.auts.lcscli.bean.MyDevice;
import com.auts.lcscli.bean.QrProduct;
import com.auts.lcscli.bean.UserConfig;

import java.util.List;

/**
 * 获取账户绑定的设备列表
 * Created by xiaolei.yang on 2017/7/12.
 */

public class DevicesView {

    public void onGetUserConfigError(String code, String msg) {

    }

    public void onGetUserConfigSuccess(UserConfig userConfig) {

    }

    public void onGetMyDevicesError(String code, String msg) {

    }


    public void onGetMyDevicesSuccess(MyDevice myDevice) {

    }

    public void onGetDevicePicSuccess(List<DevicePic> data){}

    public void onGetDevicePicError(String code, String msg){}

    public void onSaveDevicePicError(String code, String msg){}

    public void onSaveDevicePicSuccess(String msg){}

    public void onChangeTriggerError(String code,String msg){

    }

    public void onChangeTriggerSuccess(String result){

    }

    public void onCheckDeviceStateError(String code,String msg){

    }


    public void onCheckDeviceStateSuccess(boolean isOnline){

    }

    public void onGetQrProductsError(String code,String msg){

    }

    public void onGetQrProductsSuccess(List<QrProduct> list){

    }


}
