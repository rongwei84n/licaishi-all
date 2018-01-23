package com.auts.lcssv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.DevicePic;
import com.auts.lcssv.presenter.DevicesPresenter;
import com.auts.lcssv.presenter.viewback.DevicesView;
import com.auts.lcssv.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class DevicePicActivity extends BaseActivity {

    @BindView(R.id.rv_device)
    RecyclerView mRecyclerView;
    private DevicesPresenter presenter;
    private String mDeviceId;
    private String mUrl;
    private String mGroupId;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_device_pic);
    }

    @Override
    public void afterInitView() {
        initPresenter();
        setPageTitle(R.string.device_icon_label);
        mDeviceId = getIntent().getStringExtra("deviceId");
        mGroupId = getIntent().getStringExtra("picGid");
        presenter.getDevicePic();
    }

    protected void initPresenter() {
        presenter = new DevicesPresenter(this, this, new DevicesView() {
            @Override
            public void onGetDevicePicSuccess(List<DevicePic> data) {
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(DevicePicActivity.this, 3);
                mRecyclerView.setAdapter(new DevicePicAdapter(DevicePicActivity.this, data, layoutManager));
                mRecyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onGetDevicePicError(String code, String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onSaveDevicePicError(String code, String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onSaveDevicePicSuccess(String msg) {
                //返回url和gid给上一级Activity
                if (!TextUtils.isEmpty(mUrl)) {
                    Intent intent = new Intent().putExtra("picUrl", mUrl).putExtra("picGid", mGroupId);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
    }

    public void saveUrl(String url, String gid) {
        this.mUrl = url;
        this.mGroupId = gid;
        presenter.saveDeviceInfo(mDeviceId, null, -1, null, -1, gid);
    }

    public String getGid() {
        return this.mGroupId;
    }

    public static void actionStartActivity(Activity activity, String deviceId, String gid, int retCode) {
        Intent intent = new Intent(activity, DevicePicActivity.class);
        intent.putExtra("deviceId", deviceId);
        intent.putExtra("picGid", gid);
        activity.startActivityForResult(intent, retCode);
    }

    @Override
    public void showLoadingDialog(int resId) {
        showLoading();
    }

    @Override
    public void hideLoadingDialog() {
        hideLoading();
    }

}
