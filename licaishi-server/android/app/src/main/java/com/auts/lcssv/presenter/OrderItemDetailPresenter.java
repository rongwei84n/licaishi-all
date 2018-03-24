package com.auts.lcssv.presenter;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BasePresenter;
import com.auts.lcssv.bean.UploadBaseBean;
import com.auts.lcssv.bean.litebean.OrderItemDetailBean;
import com.auts.lcssv.model.OrderItemDetailModel;
import com.auts.lcssv.net.callback.BeanCallback;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.presenter.viewback.OrderItemDetailView;

/**
 * 订单详情的presenter
 */

public class OrderItemDetailPresenter extends BasePresenter {

    private OrderItemDetailModel model;
    private OrderItemDetailView view;

    public OrderItemDetailPresenter(ILoadingView loadingView, OrderItemDetailView appManageView) {
        this.mILoadingView = loadingView;
        this.view = appManageView;
        this.model = new OrderItemDetailModel();
    }

    public void cancelOrder(String orderNo) {
        model.cancelOrder(orderNo, new BeanCallback<OrderItemDetailBean> () {
            @Override
            public void onError(String code, String msg) {
                view.onCancelOrderFailed();
            }

            @Override
            public void onSuccess(OrderItemDetailBean orderItemDetailBean) {
                view.onCancelOrderSuccess();
            }
        });
    }

    public void getOrderDetailByID(String orderID) {
        showLoading(R.string.empty);
        model.getItemDetailByID(orderID, new BeanCallback<OrderItemDetailBean> () {

            @Override
            public void onError(String code, String msg) {
                view.onGetOrderError();
            }

            @Override
            public void onSuccess(OrderItemDetailBean orderItemDetailBean) {
                view.onGetOrderSuccess(orderItemDetailBean);
            }
        });
    }

    public void uploadBase64(String imgBase64, String type, String orderNo) {
        showLoading(R.string.loading_text);
        model.uploadBase64(imgBase64, type, orderNo, new BeanCallback<UploadBaseBean>() {
            @Override
            public void onSuccess(UploadBaseBean uploadBaseBean) {
                hideLoading();
                if (view != null) {
                    view.uploadBaseSuccess(uploadBaseBean);
                }
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (view != null) {
                    view.uploadBaseError(code, msg);
                }
            }
        });
    }
}
