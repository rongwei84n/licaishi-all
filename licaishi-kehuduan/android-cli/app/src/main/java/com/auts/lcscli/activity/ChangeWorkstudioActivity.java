package com.auts.lcscli.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcscli.R;
import com.auts.lcscli.base.BaseActivity;
import com.auts.lcscli.bean.AccountDetailsBean;
import com.auts.lcscli.event.ChangeNicknameEvent;
import com.auts.lcscli.event.ChangeWorkstudioEvent;
import com.auts.lcscli.presenter.UserInfoPresenter;
import com.auts.lcscli.presenter.viewback.ILoadingView;
import com.auts.lcscli.presenter.viewback.UserInfoView;
import com.auts.lcscli.util.EditTextUtils;
import com.auts.lcscli.util.RegexUtils;
import com.auts.lcscli.util.ToastUtil;
import com.auts.lcscli.views.NullMenuEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更换工作室.
 */

public class ChangeWorkstudioActivity extends BaseActivity implements ILoadingView {

    @BindView(R.id.et_nickname)
    NullMenuEditText mEtNickname;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;

    UserInfoPresenter mUserInfoPresenter;


    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_nickname);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.tv_set_workstudio);
        setTvMenuVisible(View.VISIBLE);
        showTvMenu(R.string.save);
        setTvMenuColor(R.color.text_oringe);

        EditTextUtils.setInputLength(20, mEtNickname);
        mEtNickname.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        mEtNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0) {
                    mIvDelete.setVisibility(View.VISIBLE);
                } else {
                    mIvDelete.setVisibility(View.INVISIBLE);
                }
            }
        });


        String mNickname = getIntent().getStringExtra("workstudio");

        if (!TextUtils.isEmpty(mNickname)) {
            mEtNickname.setText(mNickname);
            mEtNickname.setSelection(mEtNickname.length());
        }

        mUserInfoPresenter = new UserInfoPresenter(this, new UserInfoView() {
            @Override
            public void propertySuccess() {
                ToastUtil.show(R.string.set_success);
                EventBus.getDefault().post(new ChangeWorkstudioEvent(mEtNickname.getText().toString()));
                finish();
            }

            @Override
            public void propertyError(String code, String msg) {
                ToastUtil.show(TextUtils.isEmpty(msg) ? getString(R.string.set_error) : msg);
            }
        });

    }

    @Override
    public void onTvMenuClick(TextView view) {
        String hint = "工作室名称";
        if (!RegexUtils.checkNameToast(mEtNickname.getText().toString(), hint)) {
            return;
        }
        AccountDetailsBean bean = new AccountDetailsBean();
        bean.setWorkstudio(mEtNickname.getText().toString());
        mUserInfoPresenter.property(bean);
    }

    @OnClick(R.id.iv_delete)
    public void ivDelete() {
        mEtNickname.setText("");
        mIvDelete.setVisibility(View.INVISIBLE);
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
