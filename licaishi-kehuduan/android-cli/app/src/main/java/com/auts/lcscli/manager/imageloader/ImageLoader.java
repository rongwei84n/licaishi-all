package com.auts.lcscli.manager.imageloader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.auts.lcscli.util.LogUtils;

/**
 * 图片加载工具类：目前封装的是Glide
 * Created by qisheng.lv on 2017/4/28.
 */

public class ImageLoader {

    public static RequestManager getLoader(Context context) {
        return Glide.with(context);
    }

    public static RequestManager getLoader(Activity activity) {
        return Glide.with(activity);
    }

    public static RequestManager getLoader(Fragment fragment) {
        return Glide.with(fragment);
    }

    // 清除图片磁盘缓存，调用Glide自带方法
    public static boolean clearDiskCache(final Context context) {
        try {
            Glide.get(context).clearDiskCache();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 清除Glide内存缓存
    public static boolean clearMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 预先加载图片
     */
    public static void preLoad(Context context, String url) {
        getLoader(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)    //缓存原始图片尺寸，等要加载到view中时，glide会对原始图片进行裁剪。否则glide只缓存裁剪后的尺寸
                .preload();
        LogUtils.debug("ImageLoad", "已缓存:" + url);
    }

    public static void loadToView(String url, ImageView view) {
        getLoader(view.getContext()).load(url).into(view);
    }

}

