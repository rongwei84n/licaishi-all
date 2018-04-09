package com.auts.lcscli.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.auts.lcscli.PhApplication;

/**
 * 软键盘工具类
 *
 * @author xiaolei.yang
 * @date 2017/8/3
 */

public class KeyboardUtils {
    public static void showSoftInputFromWindow(View view) {
        InputMethodManager imm = (InputMethodManager) PhApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null == imm) {
            return;
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }


    /**
     * 隐藏键盘
     */
    public static boolean hideSoftInputFromWindow(View v) {
        InputMethodManager imm = (InputMethodManager) PhApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null == imm) {
            return false;
        }
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        }
        return false;
    }

    public static boolean toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) PhApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null == imm) {
            return false;
        }
        if (imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }

}
