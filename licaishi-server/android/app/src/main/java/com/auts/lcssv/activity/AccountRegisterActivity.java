package com.auts.lcssv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.presenter.CloudAccountPresenter;
import com.auts.lcssv.presenter.viewback.CloudAccountView;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.util.AppManager;
import com.auts.lcssv.util.RegexUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.util.ViewUtils;
import com.auts.lcssv.views.MyEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 斐讯云账号注册页
 *
 * @author qisheng.lv
 * @date 2017/4/12
 */
public class AccountRegisterActivity extends BaseActivity implements ILoadingView {
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
        setContentView(R.layout.activity_account_register);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.password_set);
        ViewUtils.linkage(null, 0, mMyEtPwd.getEt(), 6, mTvSubmit);
        mPhone = getIntent().getStringExtra("register_phone");
        mVerCode = getIntent().getStringExtra("ver_code");
    }

    @Override
    protected void initPresenter() {
        mPresenter = new CloudAccountPresenter(this, new CloudAccountView() {
            @Override
            public void onAuthorizationError(String code, String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onAuthorizationSuccess(String authorizationcode) {
                doRegister();
            }

            @Override
            public void onRegisterError(String code, String msg) {
                ToastUtil.show(msg);
//                onRegisterOk();
            }

            @Override
            public void onRegisterSuccess() {
                ToastUtil.show(R.string.register_success);
                onRegisterOk();
            }
        });
    }

    @OnClick(R.id.tv_submit)
    public void tv_submit() {
        mPwd = mMyEtPwd.getAllContent();
        if (RegexUtils.checkPwdToast(mPwd)) {
            doRegister();
        }
    }

    /**
     * 发起注册
     */
    private void doRegister() {
        if (AccountManager.getInstance().hasAuthCode()) {
            mPresenter.register(mPwd, mPhone, mVerCode);
        } else {
            mPresenter.authorization();
        }
    }

    private void onRegisterOk() {
//        SpfUtils.put(AppConstans.Sp.CLOUD_ACCOUNT_PHONE, mPhone);
//        setResult(RESULT_OK);
//        finish();
        AppManager.getAppManager().finishAllActivity();
        startActivity(new Intent(this, LoginCloudActivity.class).putExtra("register_phone", mPhone));
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
        showLoading();
    }

}
