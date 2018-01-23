package com.auts.lcssv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.presenter.CloudAccountPresenter;
import com.auts.lcssv.presenter.viewback.CloudAccountView;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.util.RegexUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.util.ViewUtils;
import com.auts.lcssv.views.MyEditText;
import com.auts.lcssv.views.VerifyDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码-验证码页面
 *
 * @author qisheng.lv
 * @date 2017/7/10
 */
public class ForgotPwdCodeActivity extends BaseActivity implements ILoadingView {
    @BindView(R.id.myet_phone)
    MyEditText mMyEtPhone;
    @BindView(R.id.myet_vercode)
    MyEditText mMyEtVerCode;
    @BindView(R.id.tv_get_vercode)
    TextView mTvGetVerCode;
    @BindView(R.id.tv_next)
    TextView mTvNext;

    private static final int REQUEST_CODE_REGISTER = 501;
    private String mPhone;
    private String mVerCode;
    private CloudAccountPresenter mPresenter;
    private int mCodeTime;
    private Handler mHandler;
    private VerifyDialog mDialog;

    private Runnable mCodeTimeR = new Runnable() {
        @Override
        public void run() {
            if (mCodeTime <= 0) {
                String content = mTvGetVerCode.getText().toString();
                mTvGetVerCode.setText(content.contains("S") ? getString(R.string.get_verification_re) : getString(R.string.get_verification_code));
                mPhone = mMyEtPhone.getContent();
                mTvGetVerCode.setTextColor(!TextUtils.isEmpty(mPhone) && mPhone.length() == 11 ? getResources().getColor(R.color.text_oringe) :
                        getResources().getColor(R.color.text_small));
                return;
            }
            mTvGetVerCode.setTextColor(getResources().getColor(R.color.text_oringe));
            mTvGetVerCode.setText(mCodeTime + " S  ");
            mCodeTime -= 1;
            AccountManager.getInstance().saveCountdownTime(mCodeTime, AppConstans.CountdownTimeKey.FORGOT_PWD_CODE_TIME);
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forgot_pwd_code);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.forgot_pwd_reset_login);
        mHandler = new Handler();
        initPresenter();
        ViewUtils.linkage(mMyEtPhone.getEt(), 13, mMyEtVerCode.getEt(), 6, mTvNext);
        mMyEtVerCode.setContent("");
        mMyEtPhone.getEt().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPhone = mMyEtPhone.getContent();
                if (!mTvGetVerCode.getText().toString().contains("S")) {
                    mTvGetVerCode.setTextColor(!TextUtils.isEmpty(mPhone) && mPhone.length() == 11
                            ? getResources().getColor(R.color.text_oringe) : getResources().getColor(R.color.text_small));
                }
            }
        });

        mCodeTime = AccountManager.getInstance().getCountdownTime(AppConstans.CountdownTimeKey.FORGOT_PWD_CODE_TIME);
        if (mCodeTime < AppConstans.Common.REGISTER_CODE_TIME) {
            mHandler.postDelayed(mCodeTimeR, 0);
        }

    }

    protected void initPresenter() {
        mPresenter = new CloudAccountPresenter(this, new CloudAccountView() {
            @Override
            public void onAuthorizationError(String code, String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onAuthorizationSuccess(String authorizationcode) {
                doCheckPhone();
            }

            @Override
            public void onCheckPhoneError(String code, String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onCheckPhoneSuccess(boolean isExist) {
                if (!isExist) {
                    ToastUtil.show(getString(R.string.check_phone_not_register));
                } else {
                    showDialog();
                }
            }

            @Override
            public void onCheckVerCodeError(String code, String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onCheckVerCodeSuccess() {
                gotoResetPwd();
            }
        });
    }

    @OnClick(R.id.tv_get_vercode)
    public void tv_get_vercode() {
        mPhone = mMyEtPhone.getContent();
        if (mTvGetVerCode.getText().toString().contains("S")
                || mTvGetVerCode.getCurrentTextColor() != getResources().getColor(R.color.text_oringe)
                || !checkPhone()) {
            return;
        }
        doCheckPhone();
    }


    @OnClick(R.id.tv_next)
    public void tv_next() {
        mPhone = mMyEtPhone.getContent();
        mVerCode = mMyEtVerCode.getContent();
        if (checkAllInput()) {
            doCheckVerCode();
        }
//        gotoResetPwd();
    }

    /**
     * 检查手机是否已注册
     */
    private void doCheckPhone() {
        if (AccountManager.getInstance().hasAuthCode()) {
            mPresenter.checkPhone(mPhone);
        } else {
            mPresenter.authorization();
        }
    }

    /**
     * 弹出图形验证码
     */
    private void showDialog() {
        mDialog = new VerifyDialog(this, mPhone, new VerifyDialog.VerifyCallBack() {
            @Override
            public void onGetVetCodeSuccess() {
                mCodeTime = AppConstans.Common.REGISTER_CODE_TIME;
                mHandler.postDelayed(mCodeTimeR, 0);
                mDialog.dismiss();
                mMyEtVerCode.requestFocus();
            }
        });
        mDialog.show();
    }

    /**
     * 校验短信验证码
     */
    private void doCheckVerCode() {
        mPresenter.checkVerCode(mPhone, mVerCode);
    }


    /**
     * 进入设置密码页面
     */
    private void gotoResetPwd() {
        Intent intent = new Intent(this, ResetPwdActivity.class);
        intent.putExtra("register_phone", mPhone);
        intent.putExtra("ver_code", mVerCode);
        startActivityForResult(intent, REQUEST_CODE_REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == RESULT_OK) {
            finish();
        }
    }


    private boolean checkPhone() {
        if (!RegexUtils.checkMobilePhone(mPhone)) {
            ToastUtil.show(R.string.login_user_illegal);
            return false;
        }
        return true;
    }

    private boolean checkAllInput() {
        if (!RegexUtils.checkMobilePhone(mPhone)) {
            ToastUtil.show(R.string.mobile_illegal);
            return false;
        }

        if (TextUtils.isEmpty(mVerCode) || mVerCode.length() < 6) {
            ToastUtil.show(R.string.verifiaction_code_illegal);
            return false;
        }

        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCodeTimeR != null) {
            mHandler.removeCallbacks(mCodeTimeR);
        }
    }

    @Override
    public void showLoadingDialog(int resId) {
        showLoading();
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
