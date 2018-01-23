package com.auts.lcssv.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcssv.PhApplication;
import com.auts.lcssv.R;
import com.auts.lcssv.bean.Captcha;
import com.auts.lcssv.presenter.CloudAccountPresenter;
import com.auts.lcssv.presenter.viewback.CloudAccountView;
import com.auts.lcssv.util.Base64Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 图形验证码Dialog
 * Created by weiming.zeng on 2017/7/21.
 */
public class VerifyDialog extends Dialog implements TextWatcher, View.OnKeyListener {

    private VerifyCallBack mCallback;

    private EditText captcha1;
    private EditText captcha2;
    private EditText captcha3;
    private EditText captcha4;
    private int current;
    private List<EditText> editTexts;

    private ImageView iv_close;
    private TextView tv_error;
    private ImageView mIvCaptcha;
    private ImageView mIvRefresh;

    private String mPhoneStr;
    private CloudAccountPresenter mPresenter;
    private Captcha mCaptcha;
    private Context mContext;
    private Animation mAnimation;

    public VerifyDialog(Context context, String mobilePhone, VerifyCallBack callBack) {
        this(context, mobilePhone, null, callBack);
    }

    public VerifyDialog(Context context, String mobilePhone, Captcha captcha, VerifyCallBack callBack) {
        super(context);
        mContext = context;
        mPhoneStr = mobilePhone;
        this.mCallback = callBack;
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView();
        initPresenter();
        if (captcha == null) {
            doGetCaptcha();
        } else {
            mCaptcha = captcha;
            showCaptcha(captcha);
        }
    }

    private void initView() {
        setContentView(R.layout.dialog_captcha);
        mIvRefresh = (ImageView) findViewById(R.id.iv_refresh);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        captcha1 = (EditText) findViewById(R.id.captcha1);
        captcha2 = (EditText) findViewById(R.id.captcha2);
        captcha3 = (EditText) findViewById(R.id.captcha3);
        captcha4 = (EditText) findViewById(R.id.captcha4);
        tv_error = (TextView) findViewById(R.id.tv_error);
        mIvCaptcha = (ImageView) findViewById(R.id.iv_captcha);
        editTexts = new ArrayList<>();

        editTexts.add(captcha1);
        editTexts.add(captcha2);
        editTexts.add(captcha3);
        editTexts.add(captcha4);

        captcha1.addTextChangedListener(this);
        captcha2.addTextChangedListener(this);
        captcha3.addTextChangedListener(this);
        captcha4.addTextChangedListener(this);

        captcha1.setOnKeyListener(this);
        captcha2.setOnKeyListener(this);
        captcha3.setOnKeyListener(this);
        captcha4.setOnKeyListener(this);

        findViewById(R.id.fl_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRefresh();
            }
        });

        focus(captcha1);

        initAn();
    }

    private void initAn() {
        mAnimation = new RotateAnimation(0, 3600, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setDuration(8 * 1000);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.RESTART);
        LinearInterpolator lin = new LinearInterpolator();
        mAnimation.setInterpolator(lin);
    }


    private void initPresenter() {
        mPresenter = new CloudAccountPresenter(null, new CloudAccountView() {
            @Override
            public void onGetCaptchaError(String code, String msg) {
                toggleRefreshing(false);
                showOrHideError(msg);
            }

            @Override
            public void onGetCaptchaSuccess(Captcha captcha) {
                toggleRefreshing(false);
                mCaptcha = captcha;
                showCaptcha(captcha);
            }

            @Override
            public void onGetVerCodeError(String code, String msg) {
                toggleRefreshing(false);
                showOrHideError(msg);
                clearInput();
            }

            @Override
            public void onGetVerCodeSuccess() {
                toggleRefreshing(false);
                MyDialogToast.showToast(getContext(), getContext().getString(R.string.captcha_sent), 1100);
                if (mCallback != null) {
                    mCallback.onGetVetCodeSuccess();
                }
            }
        });
    }

    private void showCaptcha(Captcha captcha) {
        if (captcha == null || TextUtils.isEmpty(captcha.getCaptcha()) || TextUtils.isEmpty(captcha.getCaptchaid())) {
//            ToastUtil.show(R.string.captcha_failure);
            showOrHideError(mContext.getString(R.string.captcha_failure));
            return;
        }
        byte[] imageByte = Base64Utils.decode(captcha.getCaptcha());
        Drawable image = new BitmapDrawable(PhApplication.getAppContext().getResources(), BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
        mIvCaptcha.setImageDrawable(image);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if ("".equals(s.toString())) {
            return;
        }
        if (current > 2) {
            doGetVerCode();
            return;
        }
        focus(editTexts.get(++current));
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //按下键盘上的退格键回删验证码
        if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (current == 0) {
                return false;
            }
            editTexts.get(--current).setText("");
            focus(editTexts.get(current));
            return true;
        }
        return false;
    }

    public interface VerifyCallBack {
        void onGetVetCodeSuccess();
    }

    private void focus(EditText target) {
        for (EditText editText : editTexts) {
            editText.setFocusableInTouchMode(false);
        }
        target.setFocusable(true);
        target.setFocusableInTouchMode(true);
        target.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private String getCaptchaContent() {
        StringBuilder captcha4 = new StringBuilder();
        for (EditText et : editTexts) {
            String tempCode = et.getText().toString().trim();
            captcha4.append(tempCode);
        }
        return captcha4.toString();
    }

    public void showOrHideError(String errorHint) {
        if (TextUtils.isEmpty(errorHint)) {
            tv_error.setVisibility(View.INVISIBLE);
        } else {
            tv_error.setVisibility(View.VISIBLE);
            tv_error.setText(errorHint);
        }
    }

    //校验失败清空数据
    public void clearInput() {
        captcha1.setText("");
        captcha2.setText("");
        captcha3.setText("");
        captcha4.setText("");
        current = 0;
        focus(captcha1);
    }

    public void doRefresh() {
        showOrHideError(null);
        clearInput();
        doGetCaptcha();
    }

    /**
     * 获取图形验证码
     */
    private void doGetCaptcha() {
        toggleRefreshing(true);
        mPresenter.getCaptcha();
    }

    /**
     * 获取短信验证码
     */
    private void doGetVerCode() {
        if (TextUtils.isEmpty(getCaptchaContent()) || TextUtils.isEmpty(mCaptcha.getCaptchaid())) {
//            ToastUtil.show(R.string.captcha_failure);
            showOrHideError(mContext.getString(R.string.captcha_failure));
            return;
        }
        showOrHideError(null);
        toggleRefreshing(true);
        mPresenter.getVerCode(getCaptchaContent(), mCaptcha.getCaptchaid(), mPhoneStr);
    }


    private void toggleRefreshing(boolean isStart) {
        if (mAnimation == null || mIvRefresh == null) {
            return;
        }
        if (isStart) {
            mIvRefresh.setImageResource(R.drawable.loading2);
            mIvRefresh.startAnimation(mAnimation);
        } else {
            mIvRefresh.clearAnimation();
            mIvRefresh.setImageResource(R.drawable.refresh);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mIvRefresh != null) {
            mIvRefresh.clearAnimation();
        }
    }
}
