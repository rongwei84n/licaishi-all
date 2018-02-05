package com.auts.lcssv.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auts.lcssv.PhApplication;
import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.CloudAccount;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.listener.DialogOnClickListener;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.manager.UpdateManager;
import com.auts.lcssv.presenter.CloudAccountPresenter;
import com.auts.lcssv.presenter.viewback.CloudAccountView;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.service.CommonService;
import com.auts.lcssv.util.KeyboardHelper;
import com.auts.lcssv.util.KeyboardUtils;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.RegexUtils;
import com.auts.lcssv.util.ShadowUtil;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.util.UmengUtil;
import com.auts.lcssv.util.ViewUtils;
import com.auts.lcssv.views.LogoutDialog;
import com.auts.lcssv.views.MyEditText;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 登陆云账号
 *
 * @author qisheng.lv
 * @date 2017/4/12
 */
public class LoginCloudActivity extends BaseActivity implements ILoadingView {

    @BindView(R.id.ll_top)
    LinearLayout mLlTop;
    @BindView(R.id.myet_user)
    MyEditText mMyEtUser;
    @BindView(R.id.myet_pwd)
    MyEditText mMyEtPwd;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.ll_bottom)
    RelativeLayout mLlBottom;
    @BindView(R.id.tv_forget_pwd)
    TextView forget;

    @BindView(R.id.iv_back)
    View mIvBack;

    @BindView(R.id.tv_title)
    TextView mTvtitle;

    @BindView(R.id.view_div)
    View mViewdiv;

    private CloudAccountPresenter mPresenter;
    private String mUser;
    private String mPwd;
    private KeyboardHelper keyboardHelper;
//    private Integer mLlBottomInitHeight;//mllBottom布局初始高度

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_cloud);
    }

    @Override
    public void afterInitView() {
        mViewdiv.setVisibility(View.GONE);
        mTvtitle.setText("理财社区登录");
        new UpdateManager(this).appUpdate(false);
        initPresenter();
        getExtra();
//        ViewUtils.changeBtn(mMyEtUser.getEt(), 13, mMyEtPwd.getEt(), 6, mTvLogin);
        ShadowUtil.setShadowSelectorBg(mTvLogin, getResources().getColor(R.color.btn_enable_bg));
        mMyEtPwd.setContent("");
        if (!JPushInterface.isPushStopped(PhApplication.getPhApplication())) {
            JPushInterface.stopPush(PhApplication.getPhApplication());
        }
//        mLlBottom.post(new Runnable() {
//            @Override
//            public void run() {
//                mLlBottomInitHeight = mLlBottom.getHeight();
//            }
//        });
        keyboardHelper = new KeyboardHelper(this);
        keyboardHelper.onCreate();
//        keyboardHelper.setOnKeyBoardStatusChangeListener(new KeyboardHelper.OnKeyBoardStatusChangeListener() {
//            @Override
//            public void onKeyBoardPop(int keyBoardHeight) {
//                if (null != mLlBottomInitHeight && mLlBottomInitHeight < keyBoardHeight) {
//                    mLlTop.scrollTo(0, keyBoardHeight - mLlBottomInitHeight);
//                }
//            }
//
//            @Override
//            public void onKeyBoardClose(int oldKeyBoardHeight) {
//                mLlTop.scrollTo(0, 0);
//            }
//        });
    }

    private void getExtra() {
        mMyEtUser.requestFocus();
        String phone = getIntent().getStringExtra("register_phone");
        if (!TextUtils.isEmpty(phone)) {
            mMyEtUser.setContent(phone);
            mMyEtPwd.requestFocus();
            return;
        }

        String user = AccountManager.getInstance().getLoginPhone();
        if (!TextUtils.isEmpty(user)) {
            mMyEtUser.setContent(user);
            mMyEtPwd.requestFocus();
        }

        String lastLoginTime = getIntent().getStringExtra("last_login_time");
        if (!TextUtils.isEmpty(lastLoginTime)) {
            showDialog(lastLoginTime);
        }
    }


    protected void initPresenter() {
        mPresenter = new CloudAccountPresenter(this, new CloudAccountView() {
            //获取授权码失败，终止整个流程
            @Override
            public void onAuthorizationError(String code, String msg) {
                ToastUtil.show(msg);
            }

            //获取授权码成功，发起登录
            @Override
            public void onAuthorizationSuccess(String authorizationcode) {
                doLogin();
            }

            //登录失败
            @Override
            public void onLoginError(String code, String msg) {
                UmengUtil.onEvent(LoginCloudActivity.this,"LOGIN_FAIL");
                ToastUtil.show(msg);
            }

            //登录成功
            @Override
            public void onLoginSuccess(CloudAccount cloudAccount) {
//                mPresenter.upLoadRegistrationId(JPushInterface.getRegistrationID(LoginCloudActivity.this));
                UmengUtil.onEvent(LoginCloudActivity.this,"LOGIN_SUCESS");
                gotoMainActivity();
            }
        });
    }

    //点击登录
    @OnClick(R.id.tv_login)
    public void tv_login() {
//        String url = mEdtNetUrl.getText().toString();
//        if (TextUtils.isEmpty(url)) {
//            ToastUtil.show("输入vue的部署地址-调试版本");
//            return;
//        }

        mUser = mMyEtUser.getContent();
        mPwd = mMyEtPwd.getAllContent();
        if (checkInput()) {
            loginPrepare();
            mTvLogin.requestFocus();
        }
    }

    @OnClick(R.id.tv_register)
    public void tv_register() {
        UmengUtil.onEvent(this, "REGIST_CLICK");
        startActivityForResult(new Intent(this, RegisterCodeActivity.class), 601);
    }

    @OnClick(R.id.tv_forget_pwd)
    public void tv_forget_pwd() {
        UmengUtil.onEvent(this, "FORGET_PWD");
        startActivity(new Intent(this, ForgotPwdCodeActivity.class));
    }


    /**
     * 有授权码，直接登陆，否则先获取授权码。
     */
    private void loginPrepare() {
        if (AccountManager.getInstance().hasAuthCode()) {
            doLogin();
        } else {
            authorization();
        }
    }

    /**
     * 获取授权码
     */
    private void authorization() {
        mPresenter.authorization();
    }

    /**
     * 发起登陆
     */
    private void doLogin() {
        mPresenter.loginCloud(mUser, mPwd);
    }

    /**
     * 进入主页
     */
    private void gotoMainActivity() {
        if (JPushInterface.isPushStopped(PhApplication.getPhApplication())) {
            JPushInterface.resumePush(PhApplication.getPhApplication());
        }

        Intent intent = new Intent(this, JsBridgeActivity.class);
//        intent.putExtra(AppConstans.Common.INTENT_URL,url);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 601 && resultCode == RESULT_OK) {
            String phone = data.getStringExtra("mobile_phone");
            if (!TextUtils.isEmpty(phone)) {
                mMyEtUser.setContent(phone);
                mMyEtPwd.setContent("");
                mMyEtPwd.getEt().requestFocus();
                KeyboardUtils.toggleSoftInput();
            }
        }
    }

    private boolean checkInput() {
        if (!RegexUtils.checkMobilePhone(mUser) || !RegexUtils.checkPassword(mPwd)) {
            ToastUtil.show(R.string.login_fail);
            return false;
        }
        return true;
    }

    /**
     * 登录被踢dialog
     *
     * @param reason
     */
    private void showDialog(String reason) {
        try {
            if (LogoutDialog.checkShow()) {
                return;
            }
            String alertText = "你的帐户于" + getLogTime(reason) + "在其它设备上登录。如非本人操作，登录密码可能已泄露，请修改密码。";
            new LogoutDialog(this, alertText, "确定", Gravity.CENTER, new DialogOnClickListener() {
                @Override
                public void onClickListener(Dialog dialog, View v) {
                    dialog.dismiss();
                }
            }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLogTime(String reason) {
        if (TextUtils.isEmpty(reason) || !reason.startsWith("last_login_time")) {
            return "";
        }
        try {
            String time = reason.substring(reason.indexOf(":") + 2);
//        //2017-09-29 11:33:31
            String[] split = time.split(" ");
            String[] before = split[0].split("-");
            String[] after = split[1].split(":");
            return before[0] + "年" + before[1] + "月" + before[2] + "日" + after[0] + "时" + after[1] + "分" + after[2] + "秒";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, CommonService.class));
        keyboardHelper.onDestroy();
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
