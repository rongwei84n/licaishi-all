package com.auts.lcssv.base;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.auts.lcssv.presenter.viewback.ILoadingView;

/**
 * presenter基类。
 * 当前主要处理加载进度条。子类想要使用进度条方法时，必须为mLoadingView赋值
 *
 * @author xiaolei.yang
 * @date 2017/7/26
 */

public class BasePresenter {
    protected ILoadingView mILoadingView;
    protected Fragment mFragment;
    protected Activity mActivity;

    protected void showLoading(int resId) {
        if (mILoadingView != null) {
            mILoadingView.showLoadingDialog(resId);
        }
    }

    protected void hideLoading() {
        if (mILoadingView != null) {
            mILoadingView.hideLoadingDialog();
        }
    }

    protected void updateLoadingMessage(String message) {
        if (mILoadingView != null) {
            mILoadingView.updateLoadingMessage(message);
        }
    }

    protected boolean isAlive() {
        if (mFragment != null) {
            return !mFragment.isDetached();
        }
        if (mActivity != null) {
            return !mActivity.isFinishing() && !mActivity.isDestroyed();
        }
        return true;
    }

}
