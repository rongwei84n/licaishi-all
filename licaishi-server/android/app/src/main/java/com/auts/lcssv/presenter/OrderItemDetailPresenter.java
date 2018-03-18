package com.auts.lcssv.presenter;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BasePresenter;
import com.auts.lcssv.bean.Introduction;
import com.auts.lcssv.bean.litebean.OrderItemDetailBean;
import com.auts.lcssv.model.AppModel;
import com.auts.lcssv.model.OrderItemDetailModel;
import com.auts.lcssv.net.callback.BeanCallback;
import com.auts.lcssv.net.callback.ListCallback;
import com.auts.lcssv.presenter.viewback.AppManageView;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.presenter.viewback.OrderItemDetailView;
import com.auts.lcssv.util.LogUtils;

import java.util.List;

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
}
