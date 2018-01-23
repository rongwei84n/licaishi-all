package com.auts.lcssv.manager;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.auts.lcssv.PhApplication;
import com.auts.lcssv.activity.AboutActivity;
import com.auts.lcssv.bean.AppUpdate;
import com.auts.lcssv.event.NetworkRecoveryEvent;
import com.auts.lcssv.listener.DialogOnClickListener;
import com.auts.lcssv.net.engine.Err2MsgUtils;
import com.auts.lcssv.presenter.UpdatePresenter;
import com.auts.lcssv.presenter.viewback.UpdateView;
import com.auts.lcssv.util.AppInfoUtils;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.PathUtils;
import com.auts.lcssv.util.SpfUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.views.AppUpdateDialog;
import com.auts.lcssv.views.ToasingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * 检查更新Manager
 * Created by qisheng.lv on 2017/8/18.
 */

public class UpdateManager {
    private Activity mContext;
    private UpdatePresenter mPresenter;
    private boolean mIsSelf;//是否主动检查更新
    private AppUpdateDialog mDialog;
    private ToasingDialog mToasing;
    private Callback.Cancelable mTask;
    private AppUpdate mAppUpdate;

    public UpdateManager(Activity context) {
        this.mContext = context;
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new UpdatePresenter(new UpdateView() {
            @Override
            public void onAppCheckUpdateError(String code, String msg) {
                if (!code.equals(Err2MsgUtils.CODE_UNKNOW_ERROR)
                        && !code.equals(Err2MsgUtils.CODE_NET_DISABLE)
                        && !code.equals(Err2MsgUtils.CODE_NET_TIMEOUT)) {
                    PhApplication.mHasCheckUpdate = true;
                }
                hideToasing();
                if (mIsSelf) {
                    ToastUtil.show(msg);
                }
            }

            @Override
            public void onAppCheckUpdateSuccess(AppUpdate appUpdate) {
                PhApplication.mHasCheckUpdate = true;
                hideToasing();
                LogUtils.debug(appUpdate.toString());
                if (appUpdateError(appUpdate)) {
                    return;
                }
                SpfUtils.put("app_new_vercode", appUpdate.getVer_code());
                if (!mContext.isFinishing()) {
                    showDialog(appUpdate);
                }
            }
        });
    }


    public void appUpdate(boolean isSelf) {
        if (!isSelf && PhApplication.mHasCheckUpdate) { //非手动已经检测过就不再检测版本
            return;
        }
        this.mIsSelf = isSelf;
        initToasing();
        checkAppUpdate();
    }

    private void checkAppUpdate() {
        showToasing();
        mPresenter.checkAppUpdate();
    }

    private void showDialog(final AppUpdate appUpdate) {
        //是否强制升级
        boolean isForce = appUpdate.getVer_type().equals("1");
//        boolean isForce = false;

        if (noNotify(isForce, appUpdate.getVer_code())) {
            return;
        }

        String title = "检查到最新版本 PHiHome " + appUpdate.getVer_name();
        String msg = "更新内容：" + appUpdate.getVer_infos();
        String cancelText = isForce ? null : mIsSelf ? "取消" : "暂不更新";
        String downloadText = mIsSelf ? "下载安装" : "立即更新";

        mDialog = new AppUpdateDialog(mContext, title, msg, cancelText, downloadText, isForce, appUpdate.getVer_code(), new DialogOnClickListener() {
            @Override
            public void onClickListener(Dialog dialog, View v) {
                mAppUpdate = appUpdate;
                startDownload();
            }
        });

        mDialog.show();
        //如果是用户主动检查更新，显示“new”字样
        if (mIsSelf) {
            try {
                ((AboutActivity) mContext).showNew();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mDialog.setCancelListener(new AppUpdateDialog.CancelListener() {
            @Override
            public void onCancel() {
                if (mTask != null) {
                    mTask.cancel();
                }
            }

            @Override
            public void onResume() {
                startDownload();
            }
        });

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                unRegisterEventBus();
            }
        });
    }

    /**
     * 15天内不显示升级弹窗
     * 1，后台静默检查
     * 2，非强制更新
     * 3，处于15天内
     *
     * @return
     */
    private boolean noNotify(boolean isForce, String newVerCode) {
        String info = (String) SpfUtils.get("app_update_show_" + AppInfoUtils.getAppVersionCode(), "");
        boolean out15Days = true; //是否超过了15天
        try {
            if (!TextUtils.isEmpty(info) && info.contains("_")) {
                String[] split = info.split("_");
                if (Integer.valueOf(newVerCode) > (Integer.valueOf(split[1]))) {
                    out15Days = true;
                } else {
                    int lastTime = Integer.valueOf(split[0]);
                    int diff = (int) (System.currentTimeMillis() / 1000 - lastTime);
                    if (diff < 15 * 24 * 60 * 60) {
                        out15Days = false;
                    }
                }
            }
        } catch (Exception e) {
            out15Days = true;
        }
        LogUtils.show("out15Days: " + out15Days);
        if (!mIsSelf && !isForce && !out15Days) {
            return true;
        }
        return false;
    }

    private boolean eventBusRegistered;

    public void registerEventBus() {
        if (!eventBusRegistered) {
            EventBus.getDefault().register(this);
            eventBusRegistered = true;
        }
    }

    public void unRegisterEventBus() {
        if (eventBusRegistered) {
            EventBus.getDefault().unregister(this);
            eventBusRegistered = false;
        }
    }

    private void startDownload() {
        if (mAppUpdate == null) {
            return;
        }
        registerEventBus();
        String apkName = "PhiHome." + mAppUpdate.getVer_name() + ".apk";
        String downloadPath = PathUtils.getApkDownload(apkName);
        //如果文件还存在，直接跳转到安装
        File oldFile = new File(downloadPath);
        if (oldFile.exists()) {
            installApk(oldFile);
            return;
        }
        RequestParams requestParams = new RequestParams(mAppUpdate.getVer_down_url());
        requestParams.setSaveFilePath(downloadPath);
        mTask = x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                LogUtils.show("onSuccess: " + (result == null || !result.exists() ? "" : result.getAbsolutePath()));
                installApk(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.debug("onError: " + ex.toString());
                if (mDialog != null) {
                    mDialog.showError("下载失败，请检查网络设置");
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (mDialog != null) {
                    LogUtils.debug("onCancelled: " + cex.toString());
//                    mDialog.showError("下载失败，请检查网络设置");
                }
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (mDialog != null) {
                    int progress = (int) ((current * 100 / total));
                    mDialog.setProgress(progress);
                    LogUtils.debug("onDownLoading: " + progress);
                }
            }
        });
    }

    @Subscribe
    public void onEventMainThread(NetworkRecoveryEvent event) {
        if (mDialog.isShowing()) {
            startDownload();
        }
    }

    private boolean appUpdateError(AppUpdate appUpdate) {
        if (appUpdate == null || TextUtils.isEmpty(appUpdate.getVer_name()) || TextUtils.isEmpty(appUpdate.getVer_type()) || TextUtils.isEmpty(appUpdate.getVer_down_url())) {
            if (mIsSelf) {
                ToastUtil.show("当前版本已是最新版本");
            }
            //清除之前保存在本地的最新版本信息
            SpfUtils.put("app_new_vercode", AppInfoUtils.getAppVersionCode());
            return true;
        }

        return false;
    }


    private void installApk(final File file) {
        if (file != null && file.exists()) {
            if (mDialog != null) {
                if (mAppUpdate.getVer_type().equals("1") && mAppUpdate != null) {  //强制升级，不取消对话框，且恢复到可点“立即升级”状态
                    showDialog(mAppUpdate);
                } else {
                    mDialog.dismiss();
                }
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            mContext.startActivity(intent);

//            new_icon Handler().postDelayed(new_icon Runnable() {
//                @Override
//                public void run() {
//                    file.delete();
//                }
//            }, 12 * 1000);

        }


    }

    private void initToasing() {
        if (mIsSelf) {
            if (mToasing == null) {
                mToasing = new ToasingDialog(mContext, "正在获取最新版本信息");
            }
        }
    }

    private void showToasing() {
        if (mIsSelf && mToasing != null) {
            mToasing.show();
        }
    }

    private void hideToasing() {
        if (mToasing != null) {
            mToasing.hide();
        }
    }

}
