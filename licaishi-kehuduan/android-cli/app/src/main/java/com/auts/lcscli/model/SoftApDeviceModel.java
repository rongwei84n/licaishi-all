package com.auts.lcscli.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auts.lcscli.constants.AppConstans;
import com.auts.lcscli.constants.UrlConfig;
import com.auts.lcscli.manager.AccountManager;
import com.auts.lcscli.net.callback.BaseCallback;
import com.auts.lcscli.net.engine.OkHttpUtil;


/**
 * 获取智能设备信息
 *
 * @author xiaolei.yang
 * @date 2017/7/6
 */

public class SoftApDeviceModel {

    /**
     * 获取设备周围的SSID列表
     *
     * @param callback
     */
    public void readDeviceInfo(BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SoftApInfoUrl.READ_DEVICE_INFO_URL)
                .needCommonHeader(false)
                .run(null, callback);
    }

    /**
     * 向设备写入SSID信息
     *
     * @param callback
     */
    public void writeSsidInfo(String ssid, String password, BaseCallback callback) {
        JSONObject extraObj = new JSONObject();
        try {
            extraObj.put("u",AccountManager.getInstance().getValue(AppConstans.Sp.CLOUD_ACCOUNT_UID));
            extraObj.put("f", AccountManager.getInstance().getValue(AccountManager.getInstance().getValue(AppConstans.Sp.CLOUD_ACCOUNT_UID) + AppConstans.Sp.FAMILY_ID));
            //roomId
            extraObj.put("r", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpUtil.postJson(UrlConfig.SoftApInfoUrl.WRITE_SSID_INFO_URL)
                .needCommonHeader(false)
                .addParams("GATEWAY", "10.10.10.1")
                .addParams("DNS1", "10.10.10.1")
                .addParams("IDENTIFIER", String.valueOf(168430083))
                .addParams("SSID", ssid)
                .addParams("NETMASK", "255.255.255.0")
                .addParams("IP", "10.10.10.4")
                .addParams("PASSWORD", password)
                .addParams("DHCP", String.valueOf(true))
                .addParams("EXTRA_DATA", JSON.toJSONString(extraObj))
                .run(null, callback);
    }

    /**
     * 获取智能设备和路由器之间的连接状态
     */
    public void getConnState(BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SoftApInfoUrl.GET_CONN_STATE_URL)
                .run(null, callback);
    }

    /**
     * 关闭智能设备的SoftAp
     */
    public void closeSoftAp(BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SoftApInfoUrl.CLOSE_DEVICE_AP_URL)
                .run(null, callback);
    }

    /**
     * 手机通过可联网的网络绑定设备
     */
    public void bindDevice(String macAddress, BaseCallback callback) {
        String url = "/phihome/v1/devices/" + macAddress + "/bind";
        OkHttpUtil.post(UrlConfig.SzUrl.URL_HOST + url)
                .addHeader("Authorization", AccountManager.getInstance().getValue(AppConstans.Sp.ACCESS_TOKEN))
                .run(null, callback);
    }


}
