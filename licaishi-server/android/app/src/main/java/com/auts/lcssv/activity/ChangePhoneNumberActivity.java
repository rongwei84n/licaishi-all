package com.auts.lcssv.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.event.ChangePhoneNumberSuccessEvent;
import com.auts.lcssv.listener.DialogOnClickListener;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.presenter.CloudAccountPresenter;
import com.auts.lcssv.presenter.UserInfoPresenter;
import com.auts.lcssv.presenter.viewback.CloudAccountView;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.presenter.viewback.UserInfoView;
import com.auts.lcssv.util.RegexUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.util.ViewUtils;
import com.auts.lcssv.views.MyEditText;
import com.auts.lcssv.views.VerifyDialog;
import com.auts.lcssv.views.MyComfirmDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更换手机号
 *
 * @author xiaolei.yang
 * @date 2017/7/25
 */

public class ChangePhoneNumberActivity extends BaseActivity implements ILoadingView {
    @BindView(R.id.met_new_phone_number)
    MyEditText mMetPhoneNumber;
    @BindView(R.id.met_short_message_verification_code)
    MyEditText mMetVerificationCode;
    @BindView(R.id.tv_get_verification_code)
    TextView mTvGetVerificationCode;
    @BindView(R.id.tv_next_step)
    TextView mTvNextStep;

    private String mPhone;
    private String mVerCode;
    private CloudAccountPresenter mPresenter;
    private UserInfoPresenter mUserInfoPresenter;
    private int mCodeTime;
    private Handler mHandler;
    private VerifyDialog mDialog;

    private Runnable mCodeTimeR = new Runnable() {
        @Override
        public void run() {
            if (mCodeTime <= 0) {
                mTvGetVerificationCode.setText(getString(R.string.regain));
                mPhone = mMetPhoneNumber.getContent();
                mTvGetVerificationCode.setTextColor(RegexUtils.checkPhone(mPhone) ? getResources().getColor(R.color.text_oringe) :
                        getResources().getColor(R.color.text_small));
                return;
            }
            mTvGetVerificationCode.setTextColor(getResources().getColor(R.color.text_oringe));
            String showTime = mCodeTime + " S  ";
            mTvGetVerificationCode.setText(showTime);
            mCodeTime -= 1;
            AccountManager.getInstance().saveCountdownTime(mCodeTime, AppConstans.CountdownTimeKey.CHANGE_PHONE_NUMBER_CODE_TIME);
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_phone_number);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.change_phone_number);

        mHandler = new Handler();
        ViewUtils.linkage(mMetPhoneNumber.getEt(), 13, mMetVerificationCode.getEt(), 6, mTvNextStep);
        mMetPhoneNumber.getEt().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPhone = mMetPhoneNumber.getContent();
                String containsSuffix = "S";
                if (!mTvGetVerificationCode.getText().toString().contains(containsSuffix)) {
                    mTvGetVerificationCode.setTextColor(!TextUtils.isEmpty(mPhone) && mPhone.length() == 11
                            ? getResources().getColor(R.color.text_oringe) : getResources().getColor(R.color.text_small));
                }
            }
        });
        mMetPhoneNumber.getEt().setText("");
        mMetVerificationCode.getEt().setText("");

        mCodeTime = AccountManager.getInstance().getCountdownTime(AppConstans.CountdownTimeKey.CHANGE_PHONE_NUMBER_CODE_TIME);
        if (mCodeTime < AppConstans.Common.REGISTER_CODE_TIME) {
            mHandler.postDelayed(mCodeTimeR, 0);
        }

    }

    @Override
    protected void initPresenter() {
        mPresenter = new CloudAccountPresenter(this, new CloudAccountView() {
            @Override
            public void onCheckPhoneSuccess(boolean isExist) {
                if (isExist) {
                    ToastUtil.show(getString(R.string.check_phone_already_register2));
                } else {
                    showDialog();
                }
            }

            @Override
            public void onCheckPhoneError(String code, String msg) {
                ToastUtil.show(TextUtils.isEmpty(msg) ? getString(R.string.login_user_illegal) : msg);
                mMetPhoneNumber.getEt().requestFocus();
            }

        });

        mUserInfoPresenter = new UserInfoPresenter(this, new UserInfoView() {
            @Override
            public void rebindSuccess() {
                AccountManager.getInstance().saveLoginPhone(mMetPhoneNumber.getContent());
                EventBus.getDefault().post(new ChangePhoneNumberSuccessEvent(0, mMetPhoneNumber.getContent()));
                showSuccessDialog();

//                View view = LayoutInflater.from(ChangePhoneNumberActivity.this)
//                        .inflate(R.layout.popup_change_phone_number_success, null);
//                TextView mTvMessage = (TextView) view.findViewById(R.id.tv_message);
//                TextView mTvComplete = (TextView) view.findViewById(R.id.tv_complete);
//                mTvMessage.setText(getString(R.string.phone_number_change_success)
//                        + "\n"
//                        + getString(R.string.next_use_new_phone_number));
//                mTvComplete.setText(R.string.i_know);
//                final AlertDialog dialog = new_icon AlertDialog.Builder(ChangePhoneNumberActivity.this)
//                        .setView(view)
//                        .setCancelable(false)
//                        .create();
//
//                Window window = dialog.getWindow();
//                WindowManager.LayoutParams lp = window.getAttributes();
//                window.setAttributes(lp);
//
//                mTvComplete.setOnClickListener(new_icon View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        EventBus.getDefault().post(new_icon ChangePhoneNumberSuccessEvent(1, mMetPhoneNumber.getContent()));
//                        finish();
//                    }
//                });
//                dialog.show();
            }

            @Override
            public void rebindError(String code, String msg) {
                ToastUtil.show(TextUtils.isEmpty(msg) ? getString(R.string.phone_number_change_fail) : msg);
            }
        });
    }

    @OnClick(R.id.tv_get_verification_code)
    public void tvGetVerificationCode() {
        boolean getVerCodeAble = (getString(R.string.get_verification_code).equals(mTvGetVerificationCode.getText().toString())
                || getString(R.string.regain).equals(mTvGetVerificationCode.getText().toString()))
                && mTvGetVerificationCode.getCurrentTextColor() == getResources().getColor(R.color.text_oringe);
        if (getVerCodeAble) {
            mPhone = mMetPhoneNumber.getContent();
            if (!RegexUtils.checkMobilePhone(mPhone)) {
                ToastUtil.show(R.string.login_user_illegal);
            } else {
                doCheckPhone();
            }
        }
    }


    @OnClick(R.id.tv_next_step)
    public void tvNextStep() {
        mPhone = mMetPhoneNumber.getContent();
        mVerCode = mMetVerificationCode.getContent();
        if (checkAllInput()) {
            doCheckVerCode();
        }
    }

    private void showSuccessDialog() {
        new MyComfirmDialog(ChangePhoneNumberActivity.this,
                getString(R.string.phone_number_change_success) + "\n" + getString(R.string.next_use_new_phone_number),
                getString(R.string.i_know), Gravity.CENTER, R.color.text_oringe,
                new DialogOnClickListener() {
                    @Override
                    public void onClickListener(Dialog dialog, View v) {
                        dialog.dismiss();
                        EventBus.getDefault().post(new ChangePhoneNumberSuccessEvent(1, mMetPhoneNumber.getContent()));
                        finish();
                    }
                }).show();
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
            }
        });
        mDialog.show();
    }

    /**
     * 校验短信验证码
     */
    private void doCheckVerCode() {
        mUserInfoPresenter.rebind(null, mMetPhoneNumber.getContent(), mMetVerificationCode.getContent());
    }

    private boolean checkAllInput() {
        if (!RegexUtils.checkMobilePhone(mPhone)) {
            ToastUtil.show(R.string.login_user_illegal);
            return false;
        }
        //验证码长度
        int verCodeLength = 6;
        if (TextUtils.isEmpty(mVerCode) || mVerCode.length() < verCodeLength) {
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
