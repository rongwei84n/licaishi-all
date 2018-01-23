package com.auts.lcssv.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.auts.lcssv.PhApplication;
import com.auts.lcssv.model.CloudAccountModel;
import com.auts.lcssv.net.callback.BaseCallback;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.views.LogoutDialog;

import okhttp3.Request;

/**
 * 后台常驻Service
 * Created by qisheng.lv on 2017/7/5.
 */
public class CommonService extends Service {
    private CloudAccountModel mAccountModel = new CloudAccountModel();
    private Handler mHandler = new Handler();
    private static final int CHECK_TOKEN_INTERVAL = 30 * 1000;

    Runnable mTokenR = new Runnable() {
        @Override
        public void run() {
            if (!LogoutDialog.checkShow()) {
                checkToken();
            }
            mHandler.postDelayed(this, CHECK_TOKEN_INTERVAL);
        }
    };

    public CommonService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTask();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startTask() {
        if (mHandler != null && mTokenR != null) {
            mHandler.removeCallbacks(mTokenR);
            mHandler.postDelayed(mTokenR, CHECK_TOKEN_INTERVAL);
        }
    }

    private void checkToken() {
        if (PhApplication.isBackGround()) {
            LogUtils.debug("checkToken isBackGround");
            return;
        }
        LogUtils.debug("checkToken");
        mAccountModel.checkToken(new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
            }

            @Override
            public void onSuccess(String result, Request request) {
            }
        });
    }

    @Override
    public void onDestroy() {
        LogUtils.debug("onDestroy");
        if (mHandler != null && mTokenR != null) {
            mHandler.removeCallbacks(mTokenR);
        }
        startService(new Intent(this, CommonService.class));
        super.onDestroy();
    }

}
