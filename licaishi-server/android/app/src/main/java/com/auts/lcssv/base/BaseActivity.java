package com.auts.lcssv.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.auts.lcssv.R;
import com.auts.lcssv.manager.imageloader.ImageLoader;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.util.AppManager;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.views.LoadingDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Activity基类
 *
 * @author qisheng.lv
 * @date 2017/4/12
 * <p>
 * showLoading用法：
 * showLoading()：不传参数，没有加载文字。
 * showLoading(String)：显示加载文字。
 * showLoading(0)：显示“加载中...”
 * showLoading(resId)：显示引用文字
 */
public abstract class BaseActivity extends AppCompatActivity implements ILoadingView {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_menu)
    TextView mTvMenu;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.iv_back)
    public ImageView mIvBack;
    @BindView(R.id.tv_back)
    public TextView mTvBack;

    protected LoadingDialog mLoadingDialog;
    private Unbinder mUnbinder;

    public abstract void initLayout(Bundle savedInstanceState);

    public abstract void afterInitView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        // 设置只能竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setMiuiStatusBarDarkMode(true);
        initPresenter();
        initLayout(savedInstanceState);

        try {
            mUnbinder = ButterKnife.bind(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        afterInitView();
    }

    protected void initPresenter() {

    }

    public void loadImage(String url, ImageView imageView) {
        imageView.setVisibility(View.VISIBLE);
        ImageLoader.getLoader(this).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        LogUtils.debug("ImageLoad", "加载图片" + url);
    }

    public void loadImage(String url, ImageView imageView, int defImg) {
        imageView.setVisibility(View.VISIBLE);
        ImageLoader.getLoader(this).load(url).placeholder(defImg).into(imageView);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.iv_back)
    public void iv_back() {
        onGoback();
    }

    @Override
    public void onBackPressed() {
        onGoback();
    }

    public void onGoback() {
        finish();
    }

    public void hideBack() {
        try {
            mIvBack.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            LogUtils.error(e);
        }
    }

    public void setPageTitle(String title) {
        try {
            mTvTitle.setText(title);
        } catch (Exception e) {
            LogUtils.debug(e);
        }
    }

    public void setPageTitle(int titleResId) {
        String title = this.getResources().getString(titleResId);
        try {
            mTvTitle.setText(title);
        } catch (Exception e) {
            LogUtils.debug(e);
        }
    }

    public void showTvMenu(String menu) {
        try {
            setTvMenuVisible(View.VISIBLE);
            mTvMenu.setText(menu);
        } catch (Exception e) {
            LogUtils.debug(e);
        }
    }

    public void showTvMenu(int resId) {
        try {
            setTvMenuVisible(View.VISIBLE);
            mTvMenu.setText(resId);
        } catch (Exception e) {
            LogUtils.debug(e);
        }
    }

    public void setTvMenuVisible(int visible) {
        mTvMenu.setVisibility(visible);
    }

    public void setTvMenuColor(int resId) {
        mTvMenu.setTextColor(getResources().getColor(resId));
    }


    @OnClick(R.id.tv_menu)
    public void tv_menu(TextView view) {
        onTvMenuClick(view);
    }

    public void onTvMenuClick(TextView view) {

    }

    public void showIvMenu(int ico) {
        try {
            mIvMenu.setVisibility(View.VISIBLE);
            mIvMenu.setImageResource(ico);
        } catch (Exception e) {
            LogUtils.debug(e);
        }
    }

    @OnClick(R.id.iv_menu)
    public void iv_menu(ImageView view) {
        onIvMenuClick(view);
    }

    public void onIvMenuClick(ImageView view) {

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
        mLoadingDialog.show(message, duration > 0 ? duration : 10 * 1000);
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
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void showLoadingDialog(int resId) {
        showLoading(resId);
    }

    @Override
    public void hideLoadingDialog() {
        hideLoading();
    }

    @Override
    public void updateLoadingMessage(String message) {
        showLoading(message);
    }


}
