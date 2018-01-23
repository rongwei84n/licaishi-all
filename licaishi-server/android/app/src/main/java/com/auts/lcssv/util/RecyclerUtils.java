package com.auts.lcssv.util;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcssv.R;

/**
 * RecyclerView的空白页面和错误页面
 *
 * @author xiaolei.yang
 * @date 2017/8/15
 */

public class RecyclerUtils {

    /**
     * 获取错误页
     *
     * @param mActivity activity
     * @param listener  errorView的点击事件
     * @return 错误页的view
     */
    public static View getErrorView(Activity mActivity, View.OnClickListener listener) {
        return getEmptyView(mActivity, R.drawable.error_view, listener);
    }

    /**
     * 获取空白页
     *
     * @param mActivity  activity
     * @param imageResId 图片的资源id
     * @param listener   emptyView的点击事件
     * @return 空白页view
     */
    public static View getEmptyView(Activity mActivity, int imageResId, View.OnClickListener listener) {
        if (mActivity == null) {
            return null;
        }
        View emptyView = mActivity.getLayoutInflater().inflate(R.layout.empty_view, null, false);
        emptyView.setOnClickListener(listener);
        ImageView imageView = (ImageView) emptyView.findViewById(R.id.iv_empty);
        imageView.setImageResource(imageResId);
        TextView textView = (TextView) emptyView.findViewById(R.id.tv_empty);
        textView.setVisibility(View.GONE);
        return emptyView;
    }
//
//    /**
//     * 获取空白页
//     *
//     * @param mActivity  activity
//     * @param imageResId 图片的资源id
//     * @param textResId  文字显示内容
//     * @param listener   emptyView的点击事件
//     * @return 空白页view
//     */
//    public static View getEmptyView(Activity mActivity, int imageResId, int textResId, View.OnClickListener listener) {
//        if (mActivity == null) {
//            return null;
//        }
//        View emptyView = mActivity.getLayoutInflater().inflate(R.layout.empty_view, null, false);
//        emptyView.setOnClickListener(listener);
//        ImageView imageView = (ImageView) emptyView.findViewById(R.id.iv_empty);
//        imageView.setImageResource(imageResId);
//        TextView textView = (TextView) emptyView.findViewById(R.id.tv_empty);
//        textView.setText(textResId);
//        return emptyView;
//    }




}
