package com.auts.lcssv.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;

import com.auts.lcssv.PhApplication;
import com.auts.lcssv.constants.AppConstans;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author QiSheng
 * @date 2016-6-13上午10:24:51
 */
public class IntentUtils {

    /**
     * 启动相册选择图片
     */
    public static Intent getCategoryIntent() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setType("image/*");
        return intent;
    }

    /**
     * 启动相册选择图片
     */
    public static Intent getAlbumIntent() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        return intent;
    }


    /**
     * 打开相机获取照片
     */
    public static Map<String, Object> getCameraIntent(File saveFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            uri = FileProvider.getUriForFile(PhApplication.getAppContext(), AppConstans.GetPhoto.FILE_PROVIDER_AUTHORITY, saveFile);
        } else {
            uri = Uri.fromFile(saveFile);
        }
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        Map<String, Object> map = new HashMap<>();
        map.put("uri", uri);
        map.put("intent", intent);
        return map;
    }

    /**
     * 调用系统图库查看图片
     *
     * @param context
     * @param file
     */
    public static void playPicture(Context context, File file) {
        Intent picIntent = new Intent("android.intent.action.VIEW");
        picIntent.addCategory("android.intent.category.DEFAULT");
        picIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri picUri = Uri.fromFile(file);
        picIntent.setDataAndType(picUri, "image/*");
        context.startActivity(picIntent);
    }

    public static void callPhone(Context context, String phone) {
        Uri uri = Uri.parse("tel:" + phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(intent);
    }

    //设置界面
    public static Intent gotoSettings() {
        return new Intent(Settings.ACTION_SETTINGS);
    }

    public static void gotoActivity(Context context, Intent intent, String clsName) {
        try {
            intent.setClassName("com.auts.lcssv", clsName);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openDownload(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
