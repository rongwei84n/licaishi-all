package com.auts.lcssv.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.CloudAccount;
import com.auts.lcssv.listener.DialogOnClickListener;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.manager.UpdateManager;
import com.auts.lcssv.presenter.CloudAccountPresenter;
import com.auts.lcssv.presenter.viewback.CloudAccountView;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.service.CommonService;
import com.auts.lcssv.util.KeyboardHelper;
import com.auts.lcssv.util.KeyboardUtils;
import com.auts.lcssv.util.RegexUtils;
import com.auts.lcssv.util.ShadowUtil;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.views.LogoutDialog;
import com.auts.lcssv.views.MyEditText;

import butterknife.BindView;
import butterknife.OnClick;
import java.util.HashMap;
//
//import cn.jiguang.share.wechat.*;
//
//import cn.jiguang.share.android.api.*;

/**
 * 登陆，注册.
 */
public class LoginCloudActivity extends BaseActivity implements ILoadingView {

    private static final String TAG = "LoginCloudActivity";

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
        mTvtitle.setText("嘿牛理财师登录");
        new UpdateManager(this).appUpdate(false);
        initPresenter();
        getExtra();
//        ViewUtils.changeBtn(mMyEtUser.getEt(), 13, mMyEtPwd.getEt(), 6, mTvLogin);
        ShadowUtil.setShadowSelectorBg(mTvLogin, getResources().getColor(R.color.btn_enable_bg));
        mMyEtPwd.setContent("");
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
                ToastUtil.show(msg);
            }

            //登录成功
            @Override
            public void onLoginSuccess(CloudAccount cloudAccount) {
//                mPresenter.upLoadRegistrationId(JPushInterface.getRegistrationID(LoginCloudActivity.this));
                gotoMainActivity();
            }
        });
    }

    //点击登录
    @OnClick(R.id.tv_login)
    public void tv_login() {
        mUser = mMyEtUser.getContent();
        mPwd = mMyEtPwd.getAllContent();
        if (checkInput()) {
            loginPrepare();
            mTvLogin.requestFocus();
        }
//        shareToWechat();
    }

//    private void shareToWechat() {
////        //创建分享参数
//        ShareParams shareParams = new ShareParams();
//        //设置分享的数据类型
//        shareParams.setShareType(Platform.SHARE_TEXT);
//        shareParams.setText("分享的文本！！");
//        shareParams.setTitle("分享的标题！！");
//        //调用分享接口share ，分享到微信平台。
//        JShareInterface.share(Wechat.Name, shareParams, new PlatActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                Log.d(TAG, "onComplete");
//            }
//
//            @Override
//            public void onError(Platform platform, int i, int i1, Throwable throwable) {
//                Log.d(TAG, "onError");
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                Log.d(TAG, "onCancel");
//            }
//        });
//    }

    @OnClick(R.id.tv_register)
    public void tv_register() {
        startActivityForResult(new Intent(this, RegisterCodeActivity.class), 601);
    }

    @OnClick(R.id.tv_forget_pwd)
    public void tv_forget_pwd() {
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
