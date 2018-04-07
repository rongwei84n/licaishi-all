package com.auts.lcscli.presenter;

import com.auts.lcscli.R;
import com.auts.lcscli.base.BasePresenter;
import com.auts.lcscli.bean.Introduction;
import com.auts.lcscli.model.AppModel;
import com.auts.lcscli.net.callback.ListCallback;
import com.auts.lcscli.presenter.viewback.AppManageView;
import com.auts.lcscli.presenter.viewback.ILoadingView;

import java.util.List;

/**
 * APP数据相关presenter
 * Created by weiming.zeng on 2017/8/18.
 */

public class AppPresenter extends BasePresenter {

    private AppModel model;
    private AppManageView view;

    public AppPresenter(ILoadingView loadingView, AppManageView appManageView) {
        this.mILoadingView = loadingView;
        this.view = appManageView;
        this.model = new AppModel();
    }

    public void getInstroduction() {
        showLoading(R.string.empty);
        model.getInstroduction(new ListCallback<Introduction>() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                view.getIntroductionError(msg);
            }

            @Override
            public void onSuccess(List<Introduction> t) {
                hideLoading();
                view.getIntroductionSuccess(t);
            }
        });
    }
}
