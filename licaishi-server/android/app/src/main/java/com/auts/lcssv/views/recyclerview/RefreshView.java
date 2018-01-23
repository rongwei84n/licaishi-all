package com.auts.lcssv.views.recyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.auts.lcssv.R;

/**
 * 刷新View
 * Created by weiming.zeng on 2017/9/5.
 */

public class RefreshView extends android.support.v7.widget.AppCompatImageView implements MaxRefreshView.RefreshAnimateView {

    private ValueAnimator valueAnimator;
    private float originRotation;

    public RefreshView(Context context) {
        super(context);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.drawable.get_wifi_loading);
    }

    public void setResource(int res) {
        setImageResource(res);
    }

    @Override
    public void startAnimation() {
        originRotation = getRotation();
        valueAnimator = ValueAnimator.ofFloat(0, 360);
        valueAnimator.setDuration(2 * 1000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setRotation(Float.parseFloat(animation.getAnimatedValue().toString()
                ));
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    public void stopAnimation() {
        valueAnimator.cancel();
        setRotation(originRotation);
        valueAnimator.end();
    }
}
