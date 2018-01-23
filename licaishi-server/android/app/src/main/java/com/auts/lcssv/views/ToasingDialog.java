package com.auts.lcssv.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcssv.R;

/**
 * 加载对话框
 * Created by weiming.zeng on 2017/7/25.
 */

public class ToasingDialog extends Dialog {

    private TextView mTvMessage;
    private ImageView mIvLoading;
    private Animation mAnim;
    private Context mContext;

    public ToasingDialog(Context context) {
        this(context, null);
    }

    public ToasingDialog(Context context, String message) {
        super(context, R.style.DialogStyle);
        this.mContext = context;
        initView(TextUtils.isEmpty(message) ? context.getString(R.string.loading_text) : message);
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
        setContentView(R.layout.dialog_toasing);
        mTvMessage = (TextView) findViewById(R.id.tv_message);
        mIvLoading = (ImageView) findViewById(R.id.iv_loading);
        if (!TextUtils.isEmpty(message)) {
            mTvMessage.setText(message);
        }

        startAni();
    }

    public void hide() {
        stopAni();
        dismiss();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAni();
    }

    private void startAni() {
        try {
            mAnim = AnimationUtils.loadAnimation(mContext, R.anim.rotate_anim);
            mIvLoading.setAnimation(mAnim);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopAni() {
        try {
            if (mAnim != null) {
                mAnim.cancel();
            }
            if (mIvLoading != null) {
                mIvLoading.clearAnimation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
