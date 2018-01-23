package com.auts.lcssv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.event.ChangePhoneNumberSuccessEvent;
import com.auts.lcssv.manager.AccountManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定手机号
 *
 * @author xiaolei.yang
 * @date 2017/7/25
 */

public class BindingPhoneActivity extends BaseActivity {

    @BindView(R.id.tv_binding_phone)
    TextView mTvBindingPhone;

    @BindView(R.id.ll_binding_phone_number)
    LinearLayout mLlBindingPhoneNumber;
    @BindView(R.id.tv_change_phone_number)
    TextView mTvChangePhoneNumber;
    /**
     * 显示出来中间四位隐藏的手机号码
     */
    private String showPhoneNumber = "";
    private final static int PHONE_NUMBER_LENGTH = 11;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_binding_phone);
    }

    @Override
    public void afterInitView() {
        EventBus.getDefault().register(this);
        setPageTitle(R.string.binding_phone_number);
        //手机号码的真实值
        String phoneNumber = AccountManager.getInstance().getLoginPhone();
        if (phoneNumber == null) {
            phoneNumber = "";
        }

        if (phoneNumber.length() == PHONE_NUMBER_LENGTH) {
            StringBuilder builder = new StringBuilder(phoneNumber);
            showPhoneNumber = builder.replace(3, 7, "****").toString();
        }
        mTvBindingPhone.setText(showPhoneNumber);
    }

    @OnClick(R.id.tv_change_phone_number)
    public void tvChangePhoneNumber() {
        Intent intent = new Intent(this, SecurityVerificationActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void onEventMainThread(ChangePhoneNumberSuccessEvent event) {
        if (1 == event.getType()) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
