package com.auts.lcssv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.FxResponse;
import com.auts.lcssv.presenter.UserInfoPresenter;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.presenter.viewback.UserInfoView;
import com.auts.lcssv.util.AppManager;
import com.auts.lcssv.util.RegexUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.util.ViewUtils;
import com.auts.lcssv.views.MyEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改密码界面
 */

public class ModifyPasswordActivity extends BaseActivity implements ILoadingView {
    @BindView(R.id.met_old_password)
    MyEditText mMetOldPassword;
    @BindView(R.id.met_new_password)
    MyEditText mMetNewPassword;
    @BindView(R.id.tv_save)
    TextView mTvSave;

    UserInfoPresenter mUserInfoPresenter;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_modify_password);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.set_new_password);
        ViewUtils.linkage(mMetOldPassword.getEt(), 6, mMetNewPassword.getEt(), 6, mTvSave);
        mMetNewPassword.setContent("");
        mUserInfoPresenter = new UserInfoPresenter(this, new UserInfoView() {
            @Override
            public void modifyPasswordSuccess(FxResponse fxResponse) {
//                ToastUtil.showAsync(ModifyPasswordActivity.this, R.string.password_modify_success, 1500, new_icon CommonOneListener() {
//                    @Override
//                    public void handle() {
//                        AppManager.getAppManager().finishAllActivity();
//                        startActivity(new_icon Intent(ModifyPasswordActivity.this, LoginCloudActivity.class));
//                    }
//                });
                ToastUtil.show(R.string.password_modify_success);
                AppManager.getAppManager().finishAllActivity();
                startActivity(new Intent(ModifyPasswordActivity.this, LoginCloudActivity.class));
            }

            @Override
            public void modifyPasswordError(String code, String msg) {
                if ("8".equals(code)) {
                    ToastUtil.show(R.string.input_right_old_password);
                    return;
                }
                ToastUtil.show(TextUtils.isEmpty(msg) ? getString(R.string.password_modify_fail) : msg);
            }
        });

    }

    @OnClick(R.id.tv_save)
    public void tv_save() {
        String oldPwd = mMetOldPassword.getContent();
        String newPwd = mMetNewPassword.getAllContent();
        if (RegexUtils.checkPwdToast(newPwd)) {
            mUserInfoPresenter.password(oldPwd, newPwd);
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

//    //禁用ViewGroup下的所有子控件（貌似对MyEditText支持不太好）
//    private void disableSubControls(ViewGroup viewGroup) {
//        for (int i = 0; i < viewGroup.getChildCount(); i++) {
//            View v = viewGroup.getChildAt(i);
//            if (v instanceof ViewGroup) {
//                if (v instanceof Spinner) {
//                    Spinner spinner = (Spinner) v;
//                    spinner.setClickable(false);
//                    spinner.setEnabled(false);
//                } else if (v instanceof ListView) {
//                    v.setClickable(false);
//                    v.setEnabled(false);
//                } else {
//                    disableSubControls((ViewGroup) v);
//                }
//            } else if (v instanceof EditText) {
//                v.setEnabled(false);
//                v.setClickable(false);
//            } else if (v instanceof Button) {
//                v.setEnabled(false);
//            }
//        }
//    }
}
