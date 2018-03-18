package com.auts.lcssv.model;

import com.auts.lcssv.constants.UrlConfig;
import com.auts.lcssv.net.callback.BaseCallback;
import com.auts.lcssv.net.engine.OkHttpUtil;

/**
 */

public class OrderItemDetailModel {

    //获取功能介绍详细信息
    public void getItemDetailByID(String orderID, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.SzUrl.GET_ORDER_ITEM_DETAIL + orderID)
                .run(null, callback);
    }
}
