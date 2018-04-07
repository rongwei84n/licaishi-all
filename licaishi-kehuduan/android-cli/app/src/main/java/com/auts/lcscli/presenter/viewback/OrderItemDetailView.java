package com.auts.lcscli.presenter.viewback;

import com.auts.lcscli.bean.UploadBaseBean;
import com.auts.lcscli.bean.litebean.OrderItemDetailBean;


/**
 * 处理订单详情相关回调.
 */

public class OrderItemDetailView {

    public void onCancelOrderSuccess() {
    }

    public void onCancelOrderFailed() {
    }

    public void onGetOrderSuccess(OrderItemDetailBean orderBean) {}

    public void onGetOrderError() {}

    //上传打款凭证
    public void uploadBaseSuccess(UploadBaseBean uploadBaseBean) {
    }

    public void uploadBaseError(String code, String msg) {
    }
}
