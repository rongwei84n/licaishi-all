package com.auts.lcscli.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcscli.R;
import com.auts.lcscli.base.BaseActivity;
import com.auts.lcscli.util.AppManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 账户注册成功界面.
 * Created by huangrongwei on 2018/2/10.
 *
 */

public class RegisterFinishActivity extends BaseActivity {

    String mPhoneNumber;

    @BindView(R.id.tv_done)
    TextView mTvDone;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_finish);
    }

    @Override
    public void afterInitView() {
        mPhoneNumber = getIntent().getStringExtra("register_phone");

        mTvDone.setEnabled(true);
        mTvTitle.setText("注册");
    }

    @OnClick(R.id.tv_done)
    public void tvdone_onclick() {
        gotoLogin();
    }

    @OnClick(R.id.iv_back)
    public void iv_back_onclick() {
        gotoLogin();
    }

    private void gotoLogin() {
        AppManager.getAppManager().finishAllActivity();
        startActivity(new Intent(this, LoginCloudActivity.class).putExtra("register_phone", mPhoneNumber));
        finish();
    }
}
