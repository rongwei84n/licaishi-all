package com.auts.lcssv.presenter.viewback;

import com.auts.lcssv.bean.UploadBaseBean;
import com.auts.lcssv.bean.litebean.OrderItemDetailBean;


/**
 * 处理订单详情相关回调.
 */

public class OrderItemDetailView {

    public void onGetOrderSuccess(OrderItemDetailBean orderBean) {}

    public void onGetOrderError() {}

    //上传打款凭证
    public void uploadBaseSuccess(UploadBaseBean uploadBaseBean) {
    }

    public void uploadBaseError(String code, String msg) {
    }
}
