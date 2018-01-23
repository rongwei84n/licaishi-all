package com.auts.lcssv.util;

import android.os.Environment;

import com.auts.lcssv.PhApplication;

import java.io.File;

/**
 * 文件路径管理类
 *
 * @author weiming.zeng
 * @date 2017/4/25
 */

public class PathUtils {
    /**
     * 内部存储目录
     */
    public static final String INTERNAL_CACHE = PhApplication.getAppContext().getCacheDir().getAbsolutePath() + File.separator;
    public static final String INTERNAL_FILE = PhApplication.getAppContext().getFilesDir().getAbsolutePath() + File.separator;

    /**
     * 外部公共存储目录
     */
    public static final String EXTERNAL_PHIHOME = Environment.getExternalStorageDirectory() == null ? INTERNAL_FILE : Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "phihome" + File.separator;

    /**
     * 外部存储私有目录
     */
    public static final String EXTERNAL_PRIVATE_PHIHOME = PhApplication.getAppContext().getExternalFilesDir(null) == null ? INTERNAL_FILE : PhApplication.getAppContext().getExternalFilesDir(null).getAbsolutePath() + File.separator + "phihome" + File.separator;


    public static String getCommonPhihome() {
        String path;
        if (FileUtils.isSDCardAvailable()) {
            path = EXTERNAL_PRIVATE_PHIHOME;
        } else {
            path = INTERNAL_FILE;
        }
        LogUtils.debug("getCommonPhihome: " + path);
        return path;
    }

    /**
     * 图片存储目录
     */
    public static String getImageCache() {
        String imageCache = getCommonPhihome() + "image";
        File file = new File(imageCache);
        if (!file.exists()) {
            file.mkdirs();
        }
        return imageCache;
    }


    /**
     * 拍照存储目录
     */
    public static String getCameraImageDir() {
        String imageCacheDir = getCommonPhihome() + "camera" + File.separator;
        File file = new File(imageCacheDir);
        if (file.exists() && file.isDirectory()) {
            return imageCacheDir;
        }
        boolean success = file.mkdirs();
        if (success) {
            return imageCacheDir;
        }
        return null;

    }


    /**
     * 下载存储目录
     */
    public static String getDownload() {
        return getCommonPhihome() + "download";

    }

    /**
     * APK安装包下载存储目录
     *
     * @param fileName
     */
    public static String getApkDownload(String fileName) {
        String dirPath = getDownload() + File.separator;
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dirPath + fileName;
    }


}
