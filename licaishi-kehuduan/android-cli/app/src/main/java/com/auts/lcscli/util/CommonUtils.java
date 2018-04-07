package com.auts.lcscli.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.auts.lcscli.PhApplication;
import com.auts.lcscli.bean.Picture;

import java.util.List;

/**
 * 通用工具类
 * Created by qisheng.lv on 2017/4/12.
 */

public class CommonUtils {

    public static String getString(int resId) {
        if (resId > 0) {
            return PhApplication.getAppContext().getString(resId);
        }
        return "";
    }

    /**
     * 从 manifest 获取 meta 数据
     *
     * @param context
     * @param key
     * @return
     */
    public static Object getMetaDataByKey(Context context, String key) {
        Object obj = null;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            obj = info.metaData.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 获取list中的指定类型的url
     * @return
     */
    public static String getPicUrlFromList(List<Picture> pictures, String type) {
        if (pictures == null || pictures.size() == 0) {
            return null;
        }
        for (Picture pic : pictures) {
            if (pic.getPic_type() != null & pic.getPic_type().equals(type)) {
                return pic.getPic_url();
            }
        }
        return null;
    }

}
