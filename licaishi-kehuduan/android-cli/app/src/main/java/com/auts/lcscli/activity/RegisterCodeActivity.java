package com.auts.lcscli.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.auts.lcscli.R;
import com.auts.lcscli.base.BaseActivity;
import com.auts.lcscli.constants.AppConstans;
import com.auts.lcscli.manager.AccountManager;
import com.auts.lcscli.presenter.CloudAccountPresenter;
import com.auts.lcscli.presenter.viewback.CloudAccountView;
import com.auts.lcscli.presenter.viewback.ILoadingView;
import com.auts.lcscli.util.RegexUtils;
import com.auts.lcscli.util.ToastUtil;
import com.auts.lcscli.util.ViewUtils;
import com.auts.lcscli.views.MyEditText;
import com.auts.lcscli.views.VerifyDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册验证码页面
 *
 * @author qisheng.lv
 * @date 2017/7/10
 */
public class RegisterCodeActivity extends BaseActivity implements ILoadingView {
    @BindView(R.id.myet_phone)
    MyEditText mMyEtPhone;
    @BindView(R.id.myet_vercode)
    MyEditText mMyEtVerCode;
    @BindView(R.id.tv_get_vercode)
    TextView mTvGetVerCode;

    @BindView(R.id.view_line_vercode)
    View mViewLineVercode;

    @BindView(R.id.tv_next)
    TextView mTvNext;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;

    @BindView(R.id.my_password)
    MyEditText mMyPassword;

    @BindView(R.id.my_password_again)
    MyEditText mMyPasswordAgain;

    @BindView(R.id.ck_protocol)
    CheckBox mCkProtrol;

    private static final int REQUEST_CODE_REGISTER = 501;
    private String mPhone;
    private String mVerCode;
    private CloudAccountPresenter mPresenter;
    private int mCodeTime = 60;
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
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_code);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.register);
        mHandler = new Handler();
        initPresenter();
        mTvNext.setEnabled(true);
        mMyEtVerCode.setContent("");
        mViewLineVercode.setBackgroundResource(R.color.default_line);
        mMyEtVerCode.addContentFocusChangeListener(new MyEditText.ContentFocusChangeListener() {
            @Override
            public void onFocusChange(boolean hasFocus) {
                if (hasFocus) {
                    mViewLineVercode.setBackgroundResource(R.color.focused_line);
                } else {
                    mViewLineVercode.setBackgroundResource(R.color.default_line);
                }
            }
        });
        mMyPassword.setContent("");
        mMyPasswordAgain.setContent("");
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
    }

    protected void initPresenter() {
        mPresenter = new CloudAccountPresenter(this, new CloudAccountView() {
            @Override
            public void onAuthorizationError(String code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            @Override
            public void onAuthorizationSuccess(String authorizationcode) {
                hideLoading();
                doCheckPhone();
            }

            @Override
            public void onCheckPhoneError(String code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            @Override
            public void onCheckPhoneSuccess(boolean isExist) {
                hideLoading();
                if (isExist) {
                    ToastUtil.show(R.string.check_phone_already_register);
                    setResult(RESULT_OK, new Intent().putExtra("mobile_phone", mPhone));
                    finish();
                } else {
                    mPresenter.getVerCode("", "", mPhone);
                }
            }

            @Override
            public void onCheckVerCodeError(String code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            @Override
            public void onCheckVerCodeSuccess() {
                mPresenter.register(mMyPassword.getContent(), mMyEtPhone.getContent(), mMyEtVerCode.getContent());
                hideLoading();
                gotoRegister();
            }

            @Override
            public void onGetVerCodeError(String code, String msg) {
                ToastUtil.show("验证码发送失败");
            }

            @Override
            public void onGetVerCodeSuccess() {
                ToastUtil.show("验证码已发送");
                mHandler.postDelayed(mCodeTimeR, 0);
            }
        });
    }

    @OnClick(R.id.tv_get_vercode)
    public void tv_get_vercode() {
        mPhone = mMyEtPhone.getContent();
        if (!checkPhone()) {
            return;
        }
        doCheckPhone();
    }

    @OnClick(R.id.tv_next)
    public void tv_next() {
        if (TextUtils.isEmpty(mMyEtPhone.getContent())) {
            ToastUtil.show("手机号不能为空");
            mMyEtPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mMyEtVerCode.getContent())) {
            ToastUtil.show("验证码不能为空");
            mMyEtVerCode.requestFocus();
            return;
        }

        String strPwd = mMyPassword.getContent();
        if (TextUtils.isEmpty(strPwd)) {
            ToastUtil.show("登录密码不能为空");
            return;
        }

        String strPwdAgain = mMyPasswordAgain.getContent();
        if (!TextUtils.equals(strPwd, strPwdAgain)) {
            ToastUtil.show("两次输入密码不一致");
            return;
        }
        if (!mCkProtrol.isChecked()) {
            ToastUtil.show("请先同意《用户协议》");
            return;
        }

        mPhone = mMyEtPhone.getContent();
        mVerCode = mMyEtVerCode.getContent();
        if (checkAllInput()) {
            doCheckVerCode();
        }
//        gotoRegister();
    }

    @OnClick(R.id.tv_protocol)
    public void tv_protocol() {
        Intent intent = new Intent(RegisterCodeActivity.this, ProtocolActivity.class);
        startActivity(intent);
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
     * 校验短信验证码
     */
    private void doCheckVerCode() {
        mPresenter.checkVerCode(mPhone, mVerCode);
    }


    /**
     * 进入注册成功页面
     */
    private void gotoRegister() {
        Intent intent = new Intent(this, RegisterFinishActivity.class);
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
        if (TextUtils.isEmpty(mPhone)) {
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
