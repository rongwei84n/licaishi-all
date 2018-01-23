package com.auts.lcssv.presenter.viewback;

/**
 * LoadingDialog 加载中弹窗
 *
 * @author xiaolei.yang
 * @date 2017/7/26
 */

public interface ILoadingView {
    void showLoadingDialog(int resId);

    void hideLoadingDialog();

    void updateLoadingMessage(String message);

}
