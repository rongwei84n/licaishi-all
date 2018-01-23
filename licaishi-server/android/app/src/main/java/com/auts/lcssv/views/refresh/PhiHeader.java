package com.auts.lcssv.views.refresh;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.auts.lcssv.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by jiang.wang on 2017/10/13.
 */

public class PhiHeader extends RelativeLayout implements PtrUIHandler {

    private ObjectAnimator rotateAnimator;
    private long mCurrentRotateTime; //缓存已旋转动画时间

    private ImageView animIcon;

    public PhiHeader(Context context) {
        super(context);
        initView();
    }

    public PhiHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        animIcon = new ImageView(getContext());
        animIcon.setImageResource(R.drawable.get_wifi_loading);
        LayoutParams layoutParams = new LayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        addView(animIcon,layoutParams);
        rotateAnimator = createRotateAnimation();
    }

    /**
     * 创建旋转动画
     *
     * @return
     */
    private ObjectAnimator createRotateAnimation() {
        ObjectAnimator mRotateAnimator = ObjectAnimator.ofFloat(animIcon, "rotation", 0.0f, 360.0f);
        mRotateAnimator.setDuration(1000);
        mRotateAnimator.setInterpolator(new LinearInterpolator());
        mRotateAnimator.setRepeatCount(-1);
        mRotateAnimator.setRepeatMode(ObjectAnimator.RESTART);
        return mRotateAnimator;
    }

    /**
     * 重新开始动画
     */
    private void startAnim() {
        rotateAnimator.start();
    }

    /**
     * 暂停动画
     */
    private void pauseAnim() {
        mCurrentRotateTime = rotateAnimator.getCurrentPlayTime();
        rotateAnimator.cancel();
    }

    /**
     * 继续动画
     */
    private void resumeAnim() {
        rotateAnimator.start();
        rotateAnimator.setCurrentPlayTime(mCurrentRotateTime);
    }

    /**
     * 结束动画，控件属性还原
     */
    private void endAnim() {
        mCurrentRotateTime = 0;
        rotateAnimator.end();
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        endAnim();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        startAnim();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {
        pauseAnim();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
    }

}
