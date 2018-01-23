package com.auts.lcssv.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.auts.lcssv.PhApplication;


/**
 * Toast工具类
 *
 * @author qisheng.lv
 * @date 2017/4/12
 */
public class ToastUtil {
    /**
     * Toast实例
     */
    private static Toast mToast;

    public static void show(String message) {
        showCenter(PhApplication.getAppContext(), message, Toast.LENGTH_SHORT);
    }

    public static void show(int resId) {
        showCenter(PhApplication.getAppContext(), PhApplication.getAppContext().getString(resId), Toast.LENGTH_SHORT);
    }

    @SuppressLint("ShowToast")
    public static void show(Context context, String message, int gravity, int xOffset, int yOffset, int duration) {
        if (TextUtils.isEmpty(message)) {
            return;
        }

        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, mToast.getXOffset() / 2, mToast.getYOffset() / 2);
        } else {
            mToast.setText(message);
        }
        mToast.setGravity(gravity, xOffset, yOffset);
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void showLong(String message) {
        showCenter(PhApplication.getAppContext(), message, Toast.LENGTH_LONG);
    }

    public static void showLong(int resId) {
        showCenter(PhApplication.getAppContext(), PhApplication.getAppContext().getString(resId), Toast.LENGTH_LONG);
    }

    public static void showCenter(Context context, String message, int duration) {
        show(context, message, Gravity.CENTER, 0, 0, duration);
    }

}
