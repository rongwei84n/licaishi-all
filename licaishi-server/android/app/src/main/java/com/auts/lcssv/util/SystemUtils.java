package com.auts.lcssv.util;

import android.os.Build;

import java.util.Locale;

/**
 *
 * @author yanghaibin
 * @date 16/6/17
 */
public class SystemUtils {
    /**
     * 获取系统的版本号
     *
     */
    public static String getSystemVer() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取系统的语言
     *
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().toString();
    }

    /**
     * 获取手机型号
     *
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }
}
