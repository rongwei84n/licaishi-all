package com.auts.lcscli.listener;

import android.net.Uri;

import java.io.File;

/**
 * 拍照、取图结果回调
 * Created by xiaolei.yang on 2017/7/21.
 */

public interface GetPhotoAfterListener {
    //拍照完成
    void getPhotoFromCameraComplete(Uri uri);

    //相册取图完成
    void getPhotoFromAlbumComplete(Uri uri);

    //图片裁剪完成
    void cropPhotoComplete(Uri uri);

    //图片压缩开始
    void compressPhotoCompleteStart();

    //图片压缩成功
    void compressPhotoCompleteSuccess(File file);

    //图片压缩失败
    void compressPhotoCompleteError(Throwable e);

    //图片转码为Base64完成
    void transcodeComplete(String base64String);
}
