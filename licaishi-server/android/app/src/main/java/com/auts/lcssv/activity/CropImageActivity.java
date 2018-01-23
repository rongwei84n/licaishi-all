package com.auts.lcssv.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.views.clipview.ClipImageLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 图片裁剪
 *
 * @author xiaolei.yang
 * @date 2017/7/20
 */

public class CropImageActivity extends BaseActivity {
    @BindView(R.id.clip_image_layout)
    ClipImageLayout mClipImageLayout;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_crop_image);
    }

    @Override
    public void afterInitView() {

        String path = getIntent().getStringExtra("path");
        mClipImageLayout.setPath(path);
        mClipImageLayout.setHorizontalPadding(0);
    }

    @OnClick(R.id.tv_cancel)
    public void tvCancel() {
        finish();
    }

    @OnClick(R.id.tv_complete)
    public void tvComplete() {
        Intent intent = new Intent();
        Uri uri = mClipImageLayout.clipUri();
        intent.setData(uri);
        setResult(RESULT_OK, intent);
        finish();
    }
}
