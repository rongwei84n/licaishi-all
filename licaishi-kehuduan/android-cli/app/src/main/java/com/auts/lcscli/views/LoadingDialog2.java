package com.auts.lcscli.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcscli.R;
import com.auts.lcscli.constants.AppConstans;
import com.auts.lcscli.util.ToastUtil;

/**
 * 加载对话框
 * Created by weiming.zeng on 2017/7/25.
 */

public class LoadingDialog2 extends Dialog {

    private TextView mMessage;
    private ImageView mLoading;
    public static long DURATION = AppConstans.NetConfig.HTTP_CONNECT_TIME_OUT + 800;
    private Context mContext;
    private String errorText;
    AnimationDrawable anim;
    Handler mHandler = new Handler();
    Runnable loadingRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                dismiss();
                if (TextUtils.isEmpty(errorText)) {
                    ToastUtil.show(errorText);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public LoadingDialog2(Context context) {
        this(context, null);
    }

    public LoadingDialog2(Context context, String message) {
        super(context, R.style.DialogStyle);
        mContext = context;
        initView(message);
    }

    public LoadingDialog2(Context context, int resId) {
        super(context, R.style.DialogStyle);
        mContext = context;
        String message = resId == 0 ? context.getString(R.string.loading_text) : context.getString(resId);
        initView(message);
    }

    /**
     * @param context
     * @param message  加载框显示信息
     * @param duration 加载框显示时长
     */
    public LoadingDialog2(Context context, String message, long duration) {
        super(context, R.style.DialogStyle);
        this.DURATION = duration;
        initView(message);
    }

    /**
     * 初始化界面
     *
     * @param message 加载框的提示消息，如果为空，则显示默认的
     */
    private void initView(String message) {
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_loading2);
        mMessage = (TextView) findViewById(R.id.tv_message);
        if (!TextUtils.isEmpty(message)) {
            mMessage.setText(message);
        }
        mLoading = (ImageView) findViewById(R.id.iv_loading);
        anim = (AnimationDrawable) mLoading.getBackground();
    }

    @Override
    public void dismiss() {
        if (anim != null && anim.isRunning()) {
            anim.stop();
        }
        super.dismiss();
        try {
            mHandler.removeCallbacks(loadingRunnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(String message, long duration) {
        this.show(message, duration, null);
    }

    /**
     * 该show方法不显示提示文字，如需要显示提示文字，用另一个方法
     *
     * @param message
     * @param duration
     * @param error
     */
    public void show(String message, long duration, final String error) {
        if (!TextUtils.isEmpty(message)) {
//            this.mMessage.setVisibility(View.VISIBLE);
            this.mMessage.setVisibility(View.INVISIBLE);
            this.mMessage.setText(message);
        } else {
            this.mMessage.setVisibility(View.INVISIBLE);
        }
        this.errorText = error;
        if (anim != null && !anim.isRunning()) {
            anim.start();
        }
        mHandler.postDelayed(loadingRunnable, duration);
        super.show();
    }

    /**
     * 该方法可以决定是否显示提示文字
     *
     * @param message
     * @param duration
     * @param showMsg
     */
    public void show(String message, long duration, boolean showMsg) {
        if (showMsg && !TextUtils.isEmpty(message)) {
            this.mMessage.setVisibility(View.VISIBLE);
            this.mMessage.setText(message);
        } else {
            this.mMessage.setVisibility(View.INVISIBLE);
        }
        if (anim != null && !anim.isRunning()) {
            anim.start();
        }
        mHandler.postDelayed(loadingRunnable, duration);
        super.show();
    }

    public void show(int resId, long duration) {
        String message = resId == 0 ? mContext.getString(R.string.loading_text) : mContext.getString(resId);
        this.show(message, duration, null);
    }

    @Override
    public void show() {
        try {
            mHandler.postDelayed(loadingRunnable, DURATION);
            this.show(null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
