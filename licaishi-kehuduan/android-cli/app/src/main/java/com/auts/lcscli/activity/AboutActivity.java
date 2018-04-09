package com.auts.lcscli.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.auts.lcscli.BuildConfig;
import com.auts.lcscli.R;
import com.auts.lcscli.base.BaseActivity;
import com.auts.lcscli.manager.UpdateManager;
import com.auts.lcscli.util.AppInfoUtils;
import com.auts.lcscli.util.LogUtils;
import com.auts.lcscli.util.NetworkUtils;
import com.auts.lcscli.util.SpfUtils;
import com.auts.lcscli.util.ToastUtil;
import com.auts.lcscli.views.ItemBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于界面
 * 
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.iv_app_icon)
    ImageView mIvAppIcon;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    @BindView(R.id.ib_feedback)
    ItemBar mIbFeedBack;
    @BindView(R.id.ib_instruction)
    ItemBar mIbInstruction;
    @BindView(R.id.iv_new)
    ImageView mTvNew;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.about);
        mTvVersion.setText("当前版本" + "PhiHome " + BuildConfig.VERSION_NAME);
        showNew();
    }

    /**
     * 显示"new字样"
     */
    public void showNew() {
        try {
            String verCode = (String) SpfUtils.get("app_new_vercode", "");
            LogUtils.show(verCode + " * " + AppInfoUtils.getAppVersionCode());
            if (!TextUtils.isEmpty(verCode) && Integer.valueOf(verCode) > AppInfoUtils.getAppVersionCode()) {
                mTvNew.setVisibility(View.VISIBLE);
            } else {
                mTvNew.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            mTvNew.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.ib_instruction)
    public void getInstructionDetail() {
        if (!NetworkUtils.isNetAvailable()) {
            ToastUtil.show(R.string.no_network_tips);
            return;
        }
        startActivity(new Intent(AboutActivity.this, IntroductionActivity.class));
    }

    @OnClick(R.id.ib_update)
    public void ib_update() {
        new UpdateManager(this).appUpdate(true);
    }

    @OnClick(R.id.ib_feedback)
    public void ib_feedBack() {
        if (!NetworkUtils.isNetAvailable()) {
            ToastUtil.show(R.string.no_network_tips);
            return;
        }
        FeedbackAPI.setBackIcon(R.drawable.arrow_back);
        FeedbackAPI.openFeedbackActivity();
    }

}
