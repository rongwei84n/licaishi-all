package com.auts.lcscli.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.auts.lcscli.R;
import com.auts.lcscli.base.BaseActivity;
import com.auts.lcscli.manager.AccountManager;
import com.auts.lcscli.presenter.CloudAccountPresenter;
import com.auts.lcscli.presenter.viewback.CloudAccountView;
import com.auts.lcscli.presenter.viewback.ILoadingView;
import com.auts.lcscli.util.AppManager;
import com.auts.lcscli.util.RegexUtils;
import com.auts.lcscli.util.ToastUtil;
import com.auts.lcscli.util.ViewUtils;
import com.auts.lcscli.views.MyEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码-设置新密码
 *
 * @date 2018/02/19
 */
public class ResetPwdActivity extends BaseActivity implements ILoadingView {
    @BindView(R.id.myet_pwd)
    MyEditText mMyEtPwd;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    private String mVerCode;
    private String mPhone;
    private String mPwd;
    private CloudAccountPresenter mPresenter;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reset_pwd);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.forgot_pwd_reset_new);
        ViewUtils.linkage(null, 0, mMyEtPwd.getEt(), 6, mTvSubmit);
        mPhone = getIntent().getStringExtra("register_phone");
        mVerCode = getIntent().getStringExtra("ver_code");
        initPresenter();
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
                doResetPwd();
            }

            @Override
            public void onResetPwdError(String code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            @Override
            public void onResetPwdSuccess() {
                hideLoading();
//                ToastUtil.showAsync(ResetPwdActivity.this, R.string.forgot_pwd_reset_success, 1500, new_icon CommonOneListener() {
//                    @Override
//                    public void handle() {
//                        onResetPwdOk();
//                    }
//                });
                ToastUtil.show(R.string.forgot_pwd_reset_success);
                onResetPwdOk();
            }
        });
    }

    @OnClick(R.id.tv_submit)
    public void tv_submit() {
        mPwd = mMyEtPwd.getAllContent();
        if (RegexUtils.checkPwdToast(mPwd)) {
            doResetPwd();
        }
    }

    /**
     * 发起注册
     */
    private void doResetPwd() {
        showLoading();
        if (AccountManager.getInstance().hasAuthCode()) {
            mPresenter.resetPwd(mPwd, mPhone, mVerCode);
        } else {
            mPresenter.authorization();
        }
    }

    private void onResetPwdOk() {
        AppManager.getAppManager().finishAllActivity();
        Intent intent = new Intent(this, LoginCloudActivity.class);
        intent.putExtra("register_phone", mPhone);
        startActivity(intent);
        finish();
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
