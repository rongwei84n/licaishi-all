package com.auts.lcscli.base;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.auts.lcscli.presenter.viewback.ILoadingView;
import com.auts.lcscli.util.AppManager;
import com.auts.lcscli.views.LoadingDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * FragmentActivity基类
 *
 * @author qisheng.lv
 * @date 2017/4/12
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements ILoadingView {

    private Unbinder mUnbinder;

    protected LoadingDialog mLoadingDialog;

    public abstract void initLayout(Bundle savedInstanceState);

    public abstract void afterInitView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        // 设置只能竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setMiuiStatusBarDarkMode(true);
        initLayout(savedInstanceState);

        try {
            mUnbinder = ButterKnife.bind(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        afterInitView();
    }

    public boolean setMiuiStatusBarDarkMode(boolean darkmode) {
        Class<? extends Window> clazz = getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);

            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        DataStatisticsManager.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        DataStatisticsManager.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    public void showLoading() {
        showLoading(null);
    }

    public void showLoading(String message) {
        if (null == this.mLoadingDialog) {
            this.mLoadingDialog = new LoadingDialog(this, message);
        }
        mLoadingDialog.show(message, LoadingDialog.DURATION);
    }

    public void showLoading(String message, long duration) {
        if (null == this.mLoadingDialog) {
            this.mLoadingDialog = new LoadingDialog(this, message);
        }
        mLoadingDialog.show(message, duration);
    }

    public void showLoading(int resId) {
        if (null == this.mLoadingDialog) {
            this.mLoadingDialog = new LoadingDialog(this, resId);
        }
        mLoadingDialog.show(resId, LoadingDialog.DURATION);
    }

    public void showLoading(int resId, long duration) {
        if (null == this.mLoadingDialog) {
            this.mLoadingDialog = new LoadingDialog(this, resId);
        }
        mLoadingDialog.show(resId, duration);
    }

    public void hideLoading() {
        if (null != this.mLoadingDialog) {
            this.mLoadingDialog.dismiss();
        }
    }

    public void showLoadingDialog(int resId) {
        showLoading(resId);
    }

    public void hideLoadingDialog() {
        hideLoading();
    }

    public void updateLoadingMessage(String message) {

    }
}
