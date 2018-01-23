package com.auts.lcssv.listener;

import android.graphics.Bitmap;

/**
 * 压缩图片的回调
 * Created by xiaolei.yang on 2017/7/20.
 */

public interface ImgCompressCallback {
    void onImgCompressSuccess(Bitmap bitmap, String uploadPath);

    void onImgCompressFail(String errorMsg);
}
