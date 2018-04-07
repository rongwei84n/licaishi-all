package com.auts.lcscli.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;

import com.auts.lcscli.activity.CropImageActivity;
import com.auts.lcscli.constants.AppConstans;
import com.auts.lcscli.listener.GetPhotoAfterListener;
import com.auts.lcscli.manager.imageloader.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;

/**
 * 拍照、取图的工具类
 *
 * @author xiaolei.yang
 * @date 2017/7/20
 */

public class GetPhotoUtils {

    /**
     * 启动拍照
     */
    public static Uri startTakePhoto(Activity mContext) {
        if (mContext == null) {
            return null;
        }

        Uri imageUri;
        String dir = PathUtils.getCameraImageDir();
        if (TextUtils.isEmpty(dir)) {
            ToastUtil.show("创建照片路径失败，请检查应用读写权限或稍后再试。");
            return null;
        }

        File file = new File(dir, System.currentTimeMillis() + ".jpg");
        try {
            boolean createFileSuccess = file.createNewFile();
            if (!createFileSuccess) {
                ToastUtil.show("创建照片失败，请检查应用权限或稍后再试");
            }
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtil.show("创建照片失败，请检查应用读写权限或稍后再试。");
        }

        Map<String, Object> map = IntentUtils.getCameraIntent(file);
        imageUri = (Uri) map.get("uri");
        Intent intent = (Intent) map.get("intent");
        mContext.startActivityForResult(intent, AppConstans.GetPhoto.GET_PHOTO_FROM_CAMERA);
        return imageUri;
    }

    /**
     *  启动取图
     */
    public static void startChoosePhoto(Activity mContext) {
        if (mContext == null) {
            return;
        }
        Intent intent = IntentUtils.getAlbumIntent();
        mContext.startActivityForResult(intent, AppConstans.GetPhoto.GET_PHOTO_FROM_ALBUM);
    }

    /**
     * 启动裁剪
     */
    public static void startCropImage(Activity mContext, Uri imageUri) {
        String imagePath = ImgUploadUtils.getAbsoluteImagePath(mContext, imageUri);

        Intent intent = new Intent(mContext, CropImageActivity.class);
        intent.putExtra("path", imagePath);
        mContext.startActivityForResult(intent, AppConstans.GetPhoto.CROP_IMAGE);
    }

    /**
     * 启动压缩
     */
    public static void startCompressImage(Activity mContext, Uri imageUri, final GetPhotoAfterListener listener) {
        if (imageUri == null) {
            return;
        }

        String imagePath = ImgUploadUtils.getAbsoluteImagePath(mContext, imageUri);

        if (TextUtils.isEmpty(imagePath)) {
            return;
        }
        File file = new File(imagePath);
        if (!file.exists()) {
            return;
        }
        Luban.with(mContext)
                //传入要压缩的图片
                .load(file)
                //设置回调
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        if (listener != null) {
                            listener.compressPhotoCompleteStart();
                        }
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        if (listener != null) {
                            listener.compressPhotoCompleteSuccess(file);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        if (listener != null) {
                            listener.compressPhotoCompleteError(e);
                        }
                    }
                }).launch();    //启动压缩
    }

    /**
     * 启动转码
     */
    public static void startTranscode(Activity mContext, String filePath, GetPhotoAfterListener listener) {
        String fileString = "";
        File file = new File(filePath);
        if (!file.exists()) {
            listener.transcodeComplete(null);
            return;
        }
        InputStream inputStream = null;
        ByteArrayOutputStream swapStream = null;
        try {
            inputStream = new FileInputStream(file);
            swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] fileBytes = swapStream.toByteArray();
            fileString = Base64Utils.encode(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (swapStream != null) {
                    swapStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        listener.transcodeComplete(fileString);
    }

    /**
     * 拍照结果回调
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param imageUri    拍照的uri
     */
    public static void onActivityResultForTakePhoto(Activity activity, int requestCode, int resultCode,
                                                    Uri imageUri, GetPhotoAfterListener getPhotoListener) {
        if (requestCode != AppConstans.GetPhoto.GET_PHOTO_FROM_CAMERA) {
            return;
        }
        if (resultCode == RESULT_OK && getPhotoListener != null) {
            getPhotoListener.getPhotoFromCameraComplete(imageUri);
        } else {
            activity.finish();
        }
    }

    /**
     * 取图结果回调
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        取图数据
     */
    public static void onActivityResultForChoosePhoto(Activity activity, int requestCode, int resultCode, Intent data, GetPhotoAfterListener getPhotoListener) {
        if (requestCode != AppConstans.GetPhoto.GET_PHOTO_FROM_ALBUM) {
            return;
        }
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (getPhotoListener != null) {
                getPhotoListener.getPhotoFromAlbumComplete(uri);
            }
        } else {
            activity.finish();
        }

    }

    /**
     * 裁剪结果回调
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        取图数据
     */
    public static void onActivityResultForCropImage(Activity activity, int requestCode, int resultCode, Intent data,
                                                    final GetPhotoAfterListener listener) {
        if (requestCode != AppConstans.GetPhoto.CROP_IMAGE) {
            return;
        }
        if (resultCode == RESULT_OK && data != null && listener != null) {
            listener.cropPhotoComplete(data.getData());
        } else {
            activity.finish();
        }
    }

    /**
     * 取图后展示图片
     *
     * @param uri       onActivityResultForChoosePhoto中getPhotoListener.getPhotoFromAlbumComplete(uri)的参数uri
     * @param mContext  展示图片的控件所在的Activity
     * @param imageView 展示图片的控件
     */
    public static void handlerChooseImage(Uri uri, Activity mContext, ImageView imageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            handleImageOnKitKat(uri, mContext, imageView);
        } else {
            handleImageBeforeKitKat(uri, mContext, imageView);
        }
    }

    /**
     * 安卓4.4及以上处理取图结果
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void handleImageOnKitKat(Uri uri, Activity mContext, ImageView imageView) {
        String imagePath = ImgUploadUtils.getAbsoluteImagePath(mContext, uri);
        displayImage(imagePath, mContext, imageView);
    }

    /**
     * 安卓4.4以下处理取图结果
     */
    private static void handleImageBeforeKitKat(Uri uri, Activity mContext, ImageView imageView) {
        String imagePath = ImgUploadUtils.getAbsoluteImagePath(mContext, uri);
        displayImage(imagePath, mContext, imageView);
    }

    private static void displayImage(String imagePath, Activity mContext, ImageView imageView) {
        if (imagePath != null) {
            ImageLoader.getLoader(mContext).load(imagePath).into(imageView);
        } else {
            ToastUtil.show("获取图片失败 ");
        }
    }

}
