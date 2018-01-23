package com.auts.lcssv.manager.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.auts.lcssv.util.PathUtils;

/**
 * Glide配置相关
 * Created by qisheng.lv on 2017/4/28.
 */
public class GlideConfig implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置图片的显示格式RGB_565(每像素2kb)
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        //设置磁盘缓存目录（和创建的缓存目录相同）
//        String diskCachePath = PathUtils.getImageCache();
        String diskCachePath = PathUtils.getImageCache();
        //设置磁盘缓存的大小为30M
        int cacheSize = 30 * 1024 * 1024;
        builder.setDiskCache(new DiskLruCacheFactory(diskCachePath, cacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }

}
