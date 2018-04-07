package com.auts.lcscli.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auts.lcscli.util.LogUtils;
import com.auts.lcscli.views.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基类
 *
 * @author qisheng.lv
 * @date 2017/4/12
 */
public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;

    protected View view;

    protected LoadingDialog mLoadingDialog;

    /**
     * 在实例化布局之前处理的逻辑
     */
    public void beforeInitView() {

    }

    /**
     * 实例化布局文件/组件
     *
     * @param inflater
     * @return
     */
    public abstract View initView(LayoutInflater inflater);

    /**
     * 在实例化布局之后处理的逻辑
     */
    public void afterInitView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        beforeInitView();
        view = initView(inflater);
        //初始化ButterKnife
        unbinder = ButterKnife.bind(this, view);
        initPresenter();
        afterInitView();
        return view;
    }

    protected void initPresenter() {
    }

    public void showLoading() {
        showLoading(null);
    }

    public void showLoading(String message) {
        if (null == this.mLoadingDialog) {
            this.mLoadingDialog = new LoadingDialog(getActivity(), message);
        }
        mLoadingDialog.show(message, LoadingDialog.DURATION);
    }

    public void showLoading(String message, long duration) {
        if (null == this.mLoadingDialog) {
            this.mLoadingDialog = new LoadingDialog(getActivity(), message);
        }
        mLoadingDialog.show(message, duration);
    }

    public void showLoading(int resId) {
        if (null == this.mLoadingDialog) {
            this.mLoadingDialog = new LoadingDialog(getActivity(), resId);
        }
        mLoadingDialog.show(resId, LoadingDialog.DURATION);
    }

    public void showLoading(int resId, long duration) {
        if (null == this.mLoadingDialog) {
            this.mLoadingDialog = new LoadingDialog(getActivity(), resId);
        }
        mLoadingDialog.show(resId, duration);
    }

    public void hideLoading() {
        if (null != this.mLoadingDialog) {
            try {
                this.mLoadingDialog.dismiss();
            } catch (IllegalArgumentException  e) {
                LogUtils.error(getActivity().getLocalClassName().toString() + ": mLoadingDialog-" + e.getMessage());
                this.mLoadingDialog = null;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }


}
