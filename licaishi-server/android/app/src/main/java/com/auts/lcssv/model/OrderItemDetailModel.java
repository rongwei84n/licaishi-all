package com.auts.lcssv.model;

import com.auts.lcssv.constants.UrlConfig;
import com.auts.lcssv.net.callback.BaseCallback;
import com.auts.lcssv.net.engine.OkHttpUtil;

/**
 */

public class OrderItemDetailModel {

    public void cancelOrder(String orderNo, BaseCallback callback) {
        OkHttpUtil.post(UrlConfig.SzUrl.CANCEL_ORDER_ + orderNo)
                .run(null, callback);
    }

    //获取功能介绍详细信息
    public void getItemDetailByID(String orderID, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.GET_ORDER_ITEM_DETAIL + orderID)
                .run(null, callback);
    }


    //上传打款凭证
    public void uploadBase64(String imgBase64, String type, String orderNo, BaseCallback callback) {
        OkHttpUtil.post(UrlConfig.CloudAccountUrl.UPLOAD_VOUCHER)
                .addParams("imgBase64", String.valueOf(imgBase64))
                .addParams("orderNo", orderNo)
                .addParams("type", String.valueOf(type))
                .run(null, callback);
    }
}
