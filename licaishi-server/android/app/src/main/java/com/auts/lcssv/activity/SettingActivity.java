package com.auts.lcssv.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置开关
 *
 * @author weiming.zeng
 * @date 2017/09/11
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.iv_open)
    ImageView mIvOpen;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    Intent intent = new Intent();
    private int position;
    public static final int RESULTCODE = 1023;


    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.set_action);
        position = getIntent().getIntExtra("position", 0);
        if (getIntent().getBooleanExtra("state", false)) {
            mIvOpen.setVisibility(View.VISIBLE);
        } else {
            mIvClose.setVisibility(View.VISIBLE);
        }
    }

    public static void actionStartActivity(Context context, boolean state, int position) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.putExtra("state", state);
        intent.putExtra("position", position);
        ((Activity) context).startActivityForResult(intent, SettingActivity.RESULTCODE);
    }

    @OnClick(R.id.rl_open)
    public void setOpen() {
        mIvOpen.setVisibility(View.VISIBLE);
        mIvClose.setVisibility(View.GONE);
        intent.putExtra("state", true);
        intent.putExtra("position", position);
        setResult(RESULTCODE, intent);
        finish();
    }

    @OnClick(R.id.rl_close)
    public void setClose() {
        mIvClose.setVisibility(View.VISIBLE);
        mIvOpen.setVisibility(View.GONE);
        intent.putExtra("state", false);
        intent.putExtra("position", position);
        setResult(RESULTCODE, intent);
        finish();
    }
}
