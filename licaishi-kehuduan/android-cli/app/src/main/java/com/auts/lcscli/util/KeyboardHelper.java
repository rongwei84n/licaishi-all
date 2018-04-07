package com.auts.lcscli.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * Created by jiang.wang on 2017/9/20.
 */

public class KeyboardHelper {

    private Activity activity;
    private OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener;
    private int screenHeight;
    // 空白高度 = 屏幕高度 - 当前 Activity 的可见区域的高度
    // 当 blankHeight 不为 0 即为软键盘高度。
    private int blankHeight = 0;

    public KeyboardHelper(Activity activity) {
        this.activity = activity;
        screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void onCreate() {
        View content = activity.findViewById(android.R.id.content);
        // content.addOnLayoutChangeListener(listener); 这个方法有时会出现一些问题
        content.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    public void onDestroy() {
        View content = activity.findViewById(android.R.id.content);
        content.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int newBlankHeight = screenHeight - rect.bottom;
            if (onKeyBoardStatusChangeListener != null) {
                if (newBlankHeight != blankHeight) {
                    if (newBlankHeight != 0) {
                        onKeyBoardStatusChangeListener.onKeyBoardPop(newBlankHeight);
                    } else {
                        onKeyBoardStatusChangeListener.onKeyBoardClose(blankHeight);
                    }
                }
            }
            blankHeight = newBlankHeight;
        }
    };

    public void setOnKeyBoardStatusChangeListener(
            OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener) {
        this.onKeyBoardStatusChangeListener = onKeyBoardStatusChangeListener;
    }

    public interface OnKeyBoardStatusChangeListener {

        void onKeyBoardPop(int keyBoardHeight);

        void onKeyBoardClose(int oldKeyBoardHeight);
    }
}

