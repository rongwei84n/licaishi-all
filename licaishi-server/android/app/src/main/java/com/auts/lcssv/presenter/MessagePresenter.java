package com.auts.lcssv.presenter;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BasePresenter;
import com.auts.lcssv.bean.MessagesBean;
import com.auts.lcssv.model.MessageModel;
import com.auts.lcssv.net.callback.BaseCallback;
import com.auts.lcssv.net.callback.BeanCallback;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.presenter.viewback.MessageView;

import okhttp3.Request;

/**
 * 消息管理presenter
 * Created by weiming.zeng on 2017/8/25.
 */

public class MessagePresenter extends BasePresenter {

    private MessageModel model;
    private MessageView view;

    public MessagePresenter(ILoadingView loadingView, MessageView view) {
        this.model = new MessageModel();
        this.mILoadingView = loadingView;
        this.view = view;
    }

    public void getMessages(int page, int pageSize, long latestTimestampm) {
        showLoading(R.string.empty);
        model.getMessages(page, pageSize, latestTimestampm, new BeanCallback<MessagesBean>() {
            @Override
            public void onSuccess(MessagesBean messagesBean) {
                hideLoading();
                view.getMessageSuccess(messagesBean);
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                view.getMessageError(msg);
            }
        });
    }

    public void checkMsgReadStatus() {
        model.checkMsgReadStatus(new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                view.checkMessageError(msg);
            }

            @Override
            public void onSuccess(String result, Request request) {
                view.checkMessageSuccess(result);
            }
        });
    }

}
