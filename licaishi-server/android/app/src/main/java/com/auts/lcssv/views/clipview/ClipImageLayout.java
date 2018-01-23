package com.auts.lcssv.views.clipview;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.auts.lcssv.manager.imageloader.ImageLoader;

/**
 * 当前包里仿微信头像的裁剪方式，裁剪边框不动，图片可缩放移动。
 * 整合边框和图片。
 * Created by xiaolei.yang on 2017/7/20.
 */

public class ClipImageLayout extends RelativeLayout {
    private ClipZoomImageView mZoomImageView;
    private ClipImageBorderView mClipImageView;
    private int mHorizontalPadding = 20;

    private Context mContext;

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        mZoomImageView = new ClipZoomImageView(context);
        mClipImageView = new ClipImageBorderView(context);

        android.view.ViewGroup.LayoutParams lp = new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);

        this.addView(mZoomImageView, lp);
        this.addView(mClipImageView, lp);


        // 计算padding的px
        mHorizontalPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources()
                        .getDisplayMetrics());
        mZoomImageView.setHorizontalPadding(mHorizontalPadding);
        mClipImageView.setHorizontalPadding(mHorizontalPadding);
    }

    /**
     * 对外公布设置边距的方法,单位为dp
     *
     * @param mHorizontalPadding
     */
    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
        mZoomImageView.setHorizontalPadding(mHorizontalPadding);
        mClipImageView.setHorizontalPadding(mHorizontalPadding);
    }


    /**
     * 裁切图片并保存文件
     *
     * @return
     */
    public Uri clipUri() {
        return mZoomImageView.clipUri();
    }

    public void setPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            ImageLoader.getLoader(mContext).load(path).into(mZoomImageView);
        }
    }
}
