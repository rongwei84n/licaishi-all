package com.auts.lcscli.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.auts.lcscli.R;
import com.auts.lcscli.adapter.RemindSettingAdapter;
import com.auts.lcscli.base.BaseActivity;
import com.auts.lcscli.bean.DeviceDetail;
import com.auts.lcscli.bean.MessageSettingBean;
import com.auts.lcscli.bean.MyDevice;
import com.auts.lcscli.presenter.DevicesPresenter;
import com.auts.lcscli.presenter.viewback.DevicesView;
import com.auts.lcscli.util.SpfUtils;
import com.auts.lcscli.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RemindSettingActivity extends BaseActivity {

    @BindView(R.id.rv_remind)
    RecyclerView mRecyclerView;
    private List<MessageSettingBean> data;
    @BindView(R.id.trigger)
    SwitchCompat mScTrigger;
    private RemindSettingAdapter adapter;

    private DevicesPresenter presenter;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_remind_setting);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.reminding_setting);
        initPresenter();
        presenter.getMyDevices();
        mScTrigger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    SpfUtils.put("isAllowPush", false);
                    if (adapter != null) {
                        adapter.closeAllReminding();
                    }
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    presenter.changeAllTaskRemind(false);
                } else {
                    SpfUtils.put("isAllowPush", true);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    if (adapter != null) {
                        adapter.openAllReminding();
                    }
                }
            }
        });
    }


    protected void initPresenter() {
        presenter = new DevicesPresenter(this, this, new DevicesView() {
            @Override
            public void onGetMyDevicesSuccess(MyDevice myDevice) {
                if (RemindSettingActivity.this.isFinishing() || RemindSettingActivity.this.isDestroyed()) { //如果Activity被回收，则该页面的view都为空导致报错
                    return;
                }
                initAdapter(myDevice);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(RemindSettingActivity.this));
            }

            @Override
            public void onGetDevicePicError(String code, String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onChangeTriggerError(String code, String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onChangeTriggerSuccess(String result) {
            }
        });
    }

    private void initAdapter(MyDevice myDevice) {
        data = new ArrayList<>();
        for (DeviceDetail detail : myDevice.devices) {
            data.add(new MessageSettingBean(detail.device_nickname, detail.task_remind.equals("1"), detail.device_id));
            if (detail.task_remind.equals("1") && !mScTrigger.isChecked()) {
                mScTrigger.setChecked(true);    //只要子项有打开的，总开关置为开
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
        adapter = new RemindSettingAdapter(data, this);
    }

    //改变顶部消息提醒总开关状态
    public void changeMainTrigger(boolean isCheck) {
        mScTrigger.setChecked(isCheck);
    }

    public void changeItemTrigger(String deviceId, boolean taskRemind) {
        presenter.saveDeviceInfo(deviceId, null, taskRemind ? 1 : 0, null, -1, null);
    }

}
