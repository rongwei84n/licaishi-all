package com.auts.lcssv.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.auts.lcssv.R;

/**
 * /**
 * 一个大虚圆，三个中圆，加一个小圆球的动画。
 * 没有重写onLayout和onMeasure方法，因此需要限定宽高为290dp,290dp
 * Created by xiaolei.yang on 2017/8/12.
 */

public class MyThreeCircleAnimationView extends View {

    Paint mPaint = new Paint();

    Bitmap dashedCircleBitmap;
    Bitmap deviceBitmap;
    Bitmap phoneBitmap;
    Bitmap networkBitmap;
    Bitmap ballBitmap;

    private float bitmapRadius;//左右图片的半径
    private float dashedCircleRadius;//虚线半径
    private float ballRadius; //圆球半径


    private float currentAngle;

    private Runnable runnable;

    public MyThreeCircleAnimationView(Context context) {
        super(context);
    }

    public MyThreeCircleAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        bitmapRadius = context.getResources().getDimension(R.dimen.bitmap_radius_for_three);
        dashedCircleRadius = context.getResources().getDimension(R.dimen.dashed_circle_radius_for_three);
        ballRadius = context.getResources().getDimension(R.dimen.ball_radius_for_three);


        dashedCircleBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dashed_circle_for_three);
        deviceBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_animation_device);
        phoneBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_animation_phone);
        networkBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_animation_network);
        ballBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_animation_ball);


        runnable = new Runnable() {
            @Override
            public void run() {
//                currentAngle += (0.18 + incremental);
                //大圆2秒一圈
                currentAngle += 3;
                invalidate();
                postDelayed(this, 10);
            }
        };
        post(runnable);
    }

    public MyThreeCircleAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        double radians = currentAngle * 3.14 / 180;
        canvas.drawBitmap(ballBitmap,
                (float) (bitmapRadius + dashedCircleRadius + dashedCircleRadius * Math.cos(radians) - ballRadius),
                (float) (bitmapRadius + dashedCircleRadius + dashedCircleRadius * Math.sin(radians) - ballRadius),
                mPaint);
        //画图片
        canvas.drawBitmap(dashedCircleBitmap, bitmapRadius, bitmapRadius, mPaint);
        canvas.drawBitmap(networkBitmap, dashedCircleRadius, 0, mPaint);
        canvas.drawBitmap(deviceBitmap, 0, (float) (dashedCircleRadius + dashedCircleRadius * Math.tan(30 * 3.14 / 180)), mPaint);
        canvas.drawBitmap(phoneBitmap, bitmapRadius + dashedCircleRadius + dashedCircleRadius - bitmapRadius, (float) (dashedCircleRadius + dashedCircleRadius * Math.tan(30 * 3.14 / 180)), mPaint);

    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(runnable);
        super.onDetachedFromWindow();
    }
}
