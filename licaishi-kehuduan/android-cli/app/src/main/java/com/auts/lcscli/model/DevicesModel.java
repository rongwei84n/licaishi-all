package com.auts.lcscli.model;

import com.auts.lcscli.constants.UrlConfig;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.engine.OkHttpUtil;
import com.auts.lcscli.net.request.PostJsonRequest;

/**
 * 获取设备已绑定的设备列表
 * Created by xiaolei.yang on 2017/7/12.
 */

public class DevicesModel {

    public void getUserConfig(BaseCallback baseCallback) {
        OkHttpUtil.get(UrlConfig.SzUrl.USER_CONFIG)
                .run(null, baseCallback);
    }


    /**
     * 获取全部设备
     *
     * @param baseCallback
     * @param page
     * @param page_size
     * @param fid
     */
    public void getMyDevice(int page, int page_size, String fid, BaseCallback baseCallback) {
        OkHttpUtil.get(UrlConfig.SzUrl.MY_DEVICE)
                .addParams("page", page + "")
                .addParams("page_size", page_size + "")
                .addParams("fid", fid)
                .run(null, baseCallback);
    }

    /**
     * 获取设备图标图片
     */
    public void getDevicePic(BaseCallback baseCallback) {
        OkHttpUtil.get(UrlConfig.SzUrl.URL_HOST + "/v1/outlet_tc1/pictures").run(null, baseCallback);
    }

    /**
     * 修改设备信息
     */
    public void saveDeviceInfo(String deviceId, String device_nickname, int task_remind, String room_id, int position, String pic_group_id, BaseCallback baseCallback) {
        PostJsonRequest request = OkHttpUtil.postJson(UrlConfig.SzUrl.MODIFTY_DEVICE_INFO + deviceId)
                .addParams("room_id", room_id)
                .addParams("pic_group_id", pic_group_id)
                .addParams("device_nickname", device_nickname);
        if (position >= 0) {
            request.addParams("position", String.valueOf(position));
        }
        if (task_remind >= 0) {
            request.addParams("task_remind", String.valueOf(task_remind));
        }
        request.run(null, baseCallback);
    }

    public void changeAllTaskRemind(boolean isOpen, BaseCallback callback) {
        OkHttpUtil.postJson(UrlConfig.SzUrl.MY_DEVICE_REMIND)
                .addParams("task_remind", String.valueOf(isOpen ? 1 : 0))
                .run(null, callback);
    }

    public void checkDeviceState(String deviceid, BaseCallback callback) {
        OkHttpUtil.postJson(UrlConfig.SzUrl.CHECK_DEVICE_STATE + deviceid + "/passthrough")
                .addParams("action", "dector")
                .run(null, callback);
    }

    public void getQrProducts(BaseCallback callback){
        OkHttpUtil.get(UrlConfig.SzUrl.GET_QR_RPODUCTS)
                .run(null, callback);
    }


}
