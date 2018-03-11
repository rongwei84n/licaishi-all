package com.auts.lcssv.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioButton;

import com.auts.lcssv.R;
import com.auts.lcssv.adapter.MainVpAdapter;
import com.auts.lcssv.base.BaseFragmentActivity;
import com.auts.lcssv.manager.UpdateManager;
import com.auts.lcssv.service.CommonService;
import com.auts.lcssv.util.AppInfoUtils;
import com.auts.lcssv.util.AppManager;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.views.MyViewPager;


import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

/**
 * 主页，包含三个Fragment：设备、场景、我的
 *
 * @author qisheng.lv
 * @date 2017/7/5
 */
public class MainActivity extends BaseFragmentActivity {
    @BindView(R.id.vp_main)
    public MyViewPager mViewPager;
    @BindView(R.id.rb_device)
    RadioButton mRbDevice;
    @BindView(R.id.rb_scene)
    RadioButton mRbScene;
    @BindView(R.id.rb_mine)
    RadioButton mRbMine;
    private long mFirstTime;
    private MainVpAdapter mAdapter;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void afterInitView() {
        mAdapter = new MainVpAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        new UpdateManager(this).appUpdate(false);
        startService(new Intent(this, CommonService.class));
        justTest();
    }

    private void justTest() {
        LogUtils.debug("log ok!");
        String channel = AppInfoUtils.getChannelName();
        if (channel.equals("qa")) {
            ToastUtil.showLong("你使用的版本是测试环境");
        } else if (channel.equals("szrd")) {
            ToastUtil.showLong("你使用的版本是开发环境");
        }
    }

    @OnPageChange(R.id.vp_main)
    public void vp_main_OnPageChange(int position) {
        togglePage(position);
    }

    @OnClick(R.id.rb_device)
    public void rb_device() {
//        togglePage(0);
        mViewPager.setCurrentItem(0);
    }

    @OnClick(R.id.rb_scene)
    public void rb_scene() {
//        togglePage(1);
        mViewPager.setCurrentItem(1);
    }

    @OnClick(R.id.rb_mine)
    public void rb_my() {
//        togglePage(2);
        mViewPager.setCurrentItem(2);
    }

    /**
     * 切换Fragment
     *
     * @param position
     */
    private void togglePage(int position) {
        toggleRb(getResources().getDrawable(R.drawable.tag_device), mRbDevice, getResources().getColor(R.color.text));
        toggleRb(getResources().getDrawable(R.drawable.tag_scene), mRbScene, getResources().getColor(R.color.text));
        toggleRb(getResources().getDrawable(R.drawable.tag_mine), mRbMine, getResources().getColor(R.color.text));

//        if (position == 0) {
//            toggleRb(getResources().getDrawable(R.drawable.tag_device_selected), mRbDevice, getResources().getColor(R.color.text_oringe));
//        } else if (position == 1) {
//            toggleRb(getResources().getDrawable(R.drawable.tag_scene_selected), mRbScene, getResources().getColor(R.color.text_oringe));
//        } else {
//            toggleRb(getResources().getDrawable(R.drawable.tag_mine_selected), mRbMine, getResources().getColor(R.color.text_oringe));
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, CommonService.class));
    }

    /**
     * 切换底部Tab状态
     *
     * @param drawable
     * @param rb
     * @param color
     */
    private void toggleRb(Drawable drawable, RadioButton rb, int color) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rb.setCompoundDrawables(null, drawable, null, null);
        rb.setTextColor(color);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (0.0 != mFirstTime && secondTime - mFirstTime < 2000) {
                AppManager.getAppManager().finishAllActivity();
//                System.exit(0);
            } else {
                ToastUtil.show(R.string.app_exit_hit);
                mFirstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public MainVpAdapter getmAdapter() {
        return mAdapter;
    }

}
