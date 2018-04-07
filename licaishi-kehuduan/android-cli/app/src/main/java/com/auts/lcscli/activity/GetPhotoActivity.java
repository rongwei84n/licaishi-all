package com.auts.lcscli.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.auts.lcscli.base.BaseActivity;
import com.auts.lcscli.constants.AppConstans;
import com.auts.lcscli.listener.GetPhotoAfterListener;
import com.auts.lcscli.util.GetPhotoUtils;

import java.io.File;

/**
 * 拍照、相册取图后，进行裁剪并压缩、编码为base64
 *
 * @author xiaolei.yang
 * @date 2017/7/21
 */

public class GetPhotoActivity extends BaseActivity implements GetPhotoAfterListener {
    Uri imageUri;

    @Override
    public void initLayout(Bundle savedInstanceState) {

    }

    @Override
    public void afterInitView() {
        if (AppConstans.GetPhoto.GET_PHOTO_FROM_CAMERA == getIntent().getIntExtra("type", 0)) {
            imageUri = GetPhotoUtils.startTakePhoto(this);
        } else if (AppConstans.GetPhoto.GET_PHOTO_FROM_ALBUM == getIntent().getIntExtra("type", 0)) {
            GetPhotoUtils.startChoosePhoto(this);
        } else {
            finish();
        }
    }

    @Override
    public void getPhotoFromCameraComplete(Uri uri) {
        GetPhotoUtils.startCropImage(this, uri);
    }

    @Override
    public void getPhotoFromAlbumComplete(Uri uri) {
        GetPhotoUtils.startCropImage(this, uri);
    }

    @Override
    public void cropPhotoComplete(Uri uri) {
        GetPhotoUtils.startCompressImage(this, uri, this);
    }

    @Override
    public void compressPhotoCompleteStart() {

    }

    @Override
    public void compressPhotoCompleteSuccess(File file) {
        GetPhotoUtils.startTranscode(this, file == null || !file.exists() ? "" : file.getAbsolutePath(), this);
    }

    @Override
    public void compressPhotoCompleteError(Throwable e) {
    }

    @Override
    public void transcodeComplete(String base64String) {
        Intent intent = new Intent();
        intent.putExtra("image_string", base64String);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstans.GetPhoto.GET_PHOTO_FROM_CAMERA) {
            GetPhotoUtils.onActivityResultForTakePhoto(this, requestCode, resultCode, imageUri, this);
        } else if (requestCode == AppConstans.GetPhoto.GET_PHOTO_FROM_ALBUM) {
            GetPhotoUtils.onActivityResultForChoosePhoto(this, requestCode, resultCode, data, this);
        } else if (requestCode == AppConstans.GetPhoto.CROP_IMAGE) {
            GetPhotoUtils.onActivityResultForCropImage(this, requestCode, resultCode, data, this);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
