package com.auts.lcscli.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;

import com.auts.lcscli.R;
import com.auts.lcscli.base.BaseActivity;
import com.auts.lcscli.bean.Captcha;
import com.auts.lcscli.constants.AppConstans;
import com.auts.lcscli.event.ChangePhoneNumberSuccessEvent;
import com.auts.lcscli.manager.AccountManager;
import com.auts.lcscli.presenter.CloudAccountPresenter;
import com.auts.lcscli.presenter.UserInfoPresenter;
import com.auts.lcscli.presenter.viewback.CloudAccountView;
import com.auts.lcscli.presenter.viewback.ILoadingView;
import com.auts.lcscli.presenter.viewback.UserInfoView;
import com.auts.lcscli.util.ToastUtil;
import com.auts.lcscli.util.ViewUtils;
import com.auts.lcscli.views.MyEditText;
import com.auts.lcscli.views.VerifyDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定手机安全验证界面
 *
 * @author xiaolei.yang
 * @date 2017/7/25
 */

public class SecurityVerificationActivity extends BaseActivity implements ILoadingView {
    @BindView(R.id.tv_binding_phone)
    TextView mTvBindingPhone;

    @BindView(R.id.met_short_message_verification_code)
    MyEditText mMetVerificationCode;
    @BindView(R.id.tv_send_verification_code)
    TextView mTvSendVerificationCode;
    @BindView(R.id.tv_next_step)
    TextView mTvNextStep;

    private String phoneNumber = "";//手机号码的真实值
    private String showPhoneNumber = "";//显示出来中间四位隐藏的手机号码

    private UserInfoPresenter mPresenter;
    private CloudAccountPresenter mAcountPresenter;
    private VerifyDialog mDialog;
    private Handler mHandler;

    private int mCodeTime;
    private String mVerCode;

    private Runnable mCodeTimeR = new Runnable() {
        @Override
        public void run() {
            if (mCodeTime <= 0) {
                mTvSendVerificationCode.setText(getString(R.string.regain));
                return;
            }
            mTvSendVerificationCode.setText(mCodeTime + " S  ");
            mCodeTime--;
            AccountManager.getInstance().saveCountdownTime(mCodeTime, AppConstans.CountdownTimeKey.SECURITY_VERIFICATION_CODE_TIME);
            mHandler.postDelayed(this, 1000);
        }
    };


    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_security_verification);
    }

    @Override
    public void afterInitView() {
        EventBus.getDefault().register(this);
        setPageTitle(R.string.security_verification);

        ViewUtils.linkage(null, 0, mMetVerificationCode.getEt(), 6, mTvNextStep);

        mHandler = new Handler();
        phoneNumber = AccountManager.getInstance().getLoginPhone();
        if (phoneNumber == null) {
            phoneNumber = "";
        }
        if (phoneNumber.length() == 11) {
            StringBuilder builder = new StringBuilder(phoneNumber);
            showPhoneNumber = builder.replace(3, 7, "****").toString();
        }
        mTvBindingPhone.setText(showPhoneNumber);

        mCodeTime = AccountManager.getInstance().getCountdownTime(AppConstans.CountdownTimeKey.SECURITY_VERIFICATION_CODE_TIME);
        if (mCodeTime < AppConstans.Common.REGISTER_CODE_TIME) {
            mHandler.postDelayed(mCodeTimeR, 0);
        }
    }

    protected void initPresenter() {
        mPresenter = new UserInfoPresenter(this, new UserInfoView() {

            @Override
            public void oldAccountVerificationSuccess() {
                gotoChangePhoneNumber();
            }

            @Override
            public void oldAccountVerificationError(String code, String msg) {
                ToastUtil.show(TextUtils.isEmpty(msg) ? getString(R.string.please_enter_the_correct_verification_code) : msg);
            }
        });

        mAcountPresenter = new CloudAccountPresenter(this, new CloudAccountView() {
            @Override
            public void onGetCaptchaError(String code, String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onGetCaptchaSuccess(Captcha captcha) {
                showDialog(captcha);
            }
        });
    }

    /**
     * 获取图形验证码
     */
    @OnClick(R.id.tv_send_verification_code)
    public void tv_send_verification_code() {
        if (!getString(R.string.regain).equals(mTvSendVerificationCode.getText().toString())
                && !getString(R.string.get_verification_code).equals(mTvSendVerificationCode.getText().toString())) {
            return;
        }
        mAcountPresenter.getCaptcha();
    }


    @OnClick(R.id.tv_next_step)
    public void tv_next_step() {
        mVerCode = mMetVerificationCode.getContent();
        if (checkAllInput()) {
            doCheckVerCode();
        } else {
            ToastUtil.show(R.string.please_enter_the_correct_verification_code);
        }
    }

    /**
     * 弹出图形验证码
     */
    private void showDialog(Captcha captcha) {
        mDialog = new VerifyDialog(this, phoneNumber, captcha, new VerifyDialog.VerifyCallBack() {
            @Override
            public void onGetVetCodeSuccess() {
                mCodeTime = AppConstans.Common.REGISTER_CODE_TIME;
                mHandler.postDelayed(mCodeTimeR, 0);
                mDialog.dismiss();
                mMetVerificationCode.requestFocus();
            }
        });
        mDialog.show();
    }

    /**
     * 校验短信验证码
     */
    private void doCheckVerCode() {
        mPresenter.oldAccountVerification(null, phoneNumber, mVerCode);
    }


    /**
     * 进入更改密码页面
     */
    private void gotoChangePhoneNumber() {
        Intent intent = new Intent(this, ChangePhoneNumberActivity.class);
        startActivity(intent);
    }

    private boolean checkAllInput() {

        if (TextUtils.isEmpty(mVerCode) || mVerCode.length() < 6) {
            ToastUtil.show(R.string.please_enter_the_correct_verification_code);
            return false;
        }

        return true;
    }

    @Subscribe
    public void onEventMainThread(ChangePhoneNumberSuccessEvent event) {
        if (0 == event.getType()) {
            phoneNumber = event.getPhoneNumber();
            if (phoneNumber == null) {
                phoneNumber = "";
            }
            if (phoneNumber.length() == 11) {
                StringBuilder builder = new StringBuilder(phoneNumber);
                showPhoneNumber = builder.replace(3, 7, "****").toString();
            }
            mTvBindingPhone.setText(showPhoneNumber);
        } else if (1 == event.getType()) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mCodeTimeR != null) {
            mHandler.removeCallbacks(mCodeTimeR);
        }
        super.onDestroy();
    }

    @Override
    public void showLoadingDialog(int resId) {
        showLoading(resId);
    }

    @Override
    public void hideLoadingDialog() {
        hideLoading();
    }

    @Override
    public void updateLoadingMessage(String message) {
        showLoading(message);
    }
}
