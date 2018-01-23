package com.auts.lcssv.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.auts.lcssv.BuildConfig;
import com.auts.lcssv.PhApplication;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.CloudAccount;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.presenter.CloudAccountPresenter;
import com.auts.lcssv.presenter.viewback.CloudAccountView;
import com.auts.lcssv.util.AppInfoUtils;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.ToastUtil;

import org.litepal.LitePal;

public class SplashActivity extends BaseActivity {
    private static final long SPLASH_TIME = 2000;
    Handler mHandler;
    private CloudAccountPresenter mPresenter;
    private String mAuthCode;

    Runnable mR = new Runnable() {
        @Override
        public void run() {
            gotoNextActivity(null);
        }
    };

    @Override
    public void initLayout(Bundle savedInstanceState) {

    }

    @Override
    public void afterInitView() {
        PhApplication.mHasCheckUpdate = false;
        LitePal.getDatabase(); //生成一个SQLiteDatabase对象，此处调用主要是为了生成创建的表。
        mHandler = new Handler();
        showTargetVersionAlert();
        refreshToken();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new CloudAccountPresenter(null, new CloudAccountView() {
            @Override
            public void onRefreshTokenError(String code, String msg) {
                LogUtils.debug("onRefreshTokenError: " + msg);
                gotoNextActivity(msg);
            }

            @Override
            public void onRefreshTokenSuccess(CloudAccount cloudAccount) {
                gotoNextActivity(null);
            }

        });
    }

    /**
     * 获取授权码
     */
    private void refreshToken() {
        mAuthCode = AccountManager.getInstance().getAuthCode();
        if (!TextUtils.isEmpty(mAuthCode) && !TextUtils.isEmpty(AccountManager.getInstance().getRefreshToken())) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPresenter.refreshToken();
                }
            }, 1200);
        } else {
            mHandler.postDelayed(mR, SPLASH_TIME);
        }
    }

    private void gotoNextActivity(String msg) {
        Intent intent = new Intent();
        if (TextUtils.isEmpty(AccountManager.getInstance().getRefreshToken())) {
            intent.setClass(this, LoginCloudActivity.class);
            if (!TextUtils.isEmpty(msg) && msg.startsWith("last_login_time")) {
                intent.putExtra("last_login_time", msg);
            }
        } else {
            intent.setClass(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }

//    private boolean loginExpire() {
//        long lastTime = (long) SpfUtils.get(AppConstans.Sp.LOGIN_TOKEN_TIME, 0L);
//        long now = System.currentTimeMillis();
//        boolean isExpire = (lastTime == 0) || (now - lastTime > 2592000000L); //30天免登录
//        LogUtils.show("loginExpire: " + (now - lastTime) + isExpire);
//        return isExpire;
//    }

    private void showTargetVersionAlert() {
        if (BuildConfig.isDebug && AppInfoUtils.getAppVersionCode() > 22) {
            ToastUtil.show("当前SDK版本为" + Build.VERSION.SDK_INT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mR != null) {
            mHandler.removeCallbacks(mR);
        }
    }

}
