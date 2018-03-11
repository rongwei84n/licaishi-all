package com.auts.lcssv.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.auts.lcssv.PhApplication;
import com.auts.lcssv.R;
import com.auts.lcssv.base.BasePresenter;
import com.auts.lcssv.bean.Authorization;
import com.auts.lcssv.bean.Captcha;
import com.auts.lcssv.bean.CloudAccount;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.model.CloudAccountModel;
import com.auts.lcssv.net.callback.BaseCallback;
import com.auts.lcssv.net.callback.BeanCallback;
import com.auts.lcssv.presenter.viewback.CloudAccountView;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.util.CommonUtils;
import com.auts.lcssv.util.EntryUtils;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.SpfUtils;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Request;

/**
 * 云账号Presenter
 * Created by qisheng.lv on 2017/4/12.
 */
public class CloudAccountPresenter extends BasePresenter {
    //    private static final String CLIENT_ID = "7";
//    private static final String CLIENT_SECRET = "feixun*123";
    private static final String CLIENT_ID = "9167601";
    private static final String CLIENT_SECRET = "C6D63F7BA902865E7EEB024BA21DAD39";
    private static final String RESPONSE_TYPE = "code";
    private static final String SCOPE = "write";
    private static final String VER_CODE_TYPE = "0";
    private static final String REGISTER_SOURCE = "9167601";

    private CloudAccountView mView;
    private CloudAccountModel mModel;
    private AccountManager mManager;

    public CloudAccountPresenter(ILoadingView loadingView, CloudAccountView accountView) {
        this.mILoadingView = loadingView;
        this.mView = accountView;
        mModel = new CloudAccountModel();
        mManager = AccountManager.getInstance();
    }

    /**
     * 获取授权码
     */
    public void authorization() {
        showLoading(0);
        mModel.authorization(CLIENT_ID, CLIENT_SECRET, null, RESPONSE_TYPE, SCOPE, new BeanCallback<Authorization>() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mView != null) {
                    mView.onAuthorizationError(code, msg);
                }
            }

            @Override
            public void onSuccess(Authorization authorization) {
                hideLoading();
                if (mView != null) {
                    if (TextUtils.isEmpty(authorization.getAuthorizationcode())) {
                        mView.onAuthorizationError("0", CommonUtils.getString(R.string.login_auth_fail));
                    } else {
                        mManager.saveAuthCode(authorization.getAuthorizationcode());
                        mView.onAuthorizationSuccess(authorization.getAuthorizationcode());
                    }
                }
            }

        });

    }

    /**
     * 登陆云账号
     *
     * @param phonenumber 账号
     * @param password    密码
     */
    public void loginCloud(final String phonenumber, final String password) {
        showLoading(0);
        mModel.loginCloud(mManager.getAuthCode(), null, EntryUtils.getMd5(password), phonenumber, null, new BeanCallback<CloudAccount>() {

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mView != null) {
                    mView.onLoginError(code, msg);
                }
            }

            @Override
            public void onSuccess(CloudAccount cloudLogin) {
                hideLoading();
                SpfUtils.put(AppConstans.Sp.LOGIN_TOKEN_TIME, System.currentTimeMillis());
                mManager.saveLoginPhone(phonenumber);
                if (mView != null) {
                    if (TextUtils.isEmpty(cloudLogin.getAccess_token()) || TextUtils.isEmpty(cloudLogin.getUid())) {
                        mView.onLoginError("0", CommonUtils.getString(R.string.login_token_fail));
                    } else {
                        mManager.saveValue(AppConstans.Sp.CLOUD_ACCOUNT_UID, cloudLogin.getUid());
                        mManager.saveValue(AppConstans.Sp.ACCESS_TOKEN, cloudLogin.getAccess_token());
                        mManager.saveValue(AppConstans.Sp.REFRESH_TOKEN, cloudLogin.getRefresh_token());
                        mManager.saveValue(AppConstans.Sp.REFRESH_TOKEN_EXPIRE, cloudLogin.getRefresh_token_expire());
                        mManager.saveValue(AppConstans.Sp.LOGIN_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
                        mView.onLoginSuccess(cloudLogin);
                    }
                }

                //上传RegistrationID
//                upLoadRegistrationId(JPushInterface.getRegistrationID(PhApplication.getAppContext()));
            }

        });

    }

    /**
     * 上传registrationId到服务器
     */
    public void upLoadRegistrationId(final String registrationId) {
        mModel.upLoadRegistrationId(registrationId, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                Log.d(LogUtils.TAG, "upLoadRegistrationId error: " + msg);
            }

            @Override
            public void onSuccess(String result, Request request) {
                Log.d(LogUtils.TAG, "upLoadRegistrationId onSuccess: " + registrationId);
            }
        });
    }


    public void exitLogin() {
        String token = mManager.getValue(AppConstans.Sp.ACCESS_TOKEN);
        if (TextUtils.isEmpty(token) && mView != null) {
            mView.onLogoutSuccess();
            return;
        }

        showLoading(0);
        mModel.logoutCloud(new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mView != null) {
                    mView.onLogoutError(code, msg);
                }
            }

            @Override
            public void onSuccess(String result, Request request) {
                hideLoading();
                if (mView != null) {
                    mView.onLogoutSuccess();
                }
            }
        });

    }

    /**
     * 检查手机号是否已注册
     *
     * @param phonenumber
     */
    public void checkPhone(String phonenumber) {
        showLoading(0);
        mModel.checkPhone(mManager.getAuthCode(), null, phonenumber, new BaseCallback() {

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mView != null) {
                    if (code.equals("14")) {
                        mView.onCheckPhoneSuccess(true);
                    } else {
                        mView.onCheckPhoneError(code, msg);
                    }
                }
            }

            @Override
            public void onSuccess(String result, Request request) {
                hideLoading();
                if (mView != null) {
                    mView.onCheckPhoneSuccess(false);
                }
            }
        });

    }

    public void getCaptcha() {
        showLoading(R.string.wait);
        mModel.getCaptcha(mManager.getAuthCode(), new BeanCallback<Captcha>() {

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mView != null) {
                    mView.onGetCaptchaError(code, msg);
                }
            }

            @Override
            public void onSuccess(Captcha captcha) {
                hideLoading();
                if (mView != null) {
                    mView.onGetCaptchaSuccess(captcha);
                }
            }
        });
    }

    public void getVerCode(String captcha, String captchaId, String phonenumber) {
        showLoading(0);
        mModel.getVerCode(mManager.getAuthCode(), captcha, captchaId, null, null, phonenumber, VER_CODE_TYPE, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mView != null) {
                    mView.onGetVerCodeError(code, msg);
                }
            }

            @Override
            public void onSuccess(String result, Request request) {
                hideLoading();
                if (mView != null) {
                    mView.onGetVerCodeSuccess();
                }
            }
        });

    }

    public void checkVerCode(String phonenumber, String verificationcode) {
        showLoading(0);
        mModel.checkVerCode(mManager.getAuthCode(), phonenumber, verificationcode, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mView != null) {
                    mView.onCheckVerCodeError(code, msg);
                }
            }

            @Override
            public void onSuccess(String result, Request request) {
                hideLoading();
                if (mView != null) {
                    mView.onCheckVerCodeSuccess();
                }
            }
        });
    }

    public void register(String password, String phonenumber, String verificationcode) {
        showLoading(0);
        mModel.register(mManager.getAuthCode(), null, null, EntryUtils.getMd5(password), phonenumber, REGISTER_SOURCE, null, verificationcode, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mView != null) {
                    mView.onRegisterError(code, msg);
                }
            }

            @Override
            public void onSuccess(String result, Request request) {
                hideLoading();
                if (mView != null) {
                    mView.onRegisterSuccess();
                }
            }
        });

    }


    public void resetPwd(String newpassword, String phonenumber, String verificationcode) {
        showLoading(0);
        mModel.resetPwd(mManager.getAuthCode(), null, EntryUtils.getMd5(newpassword), phonenumber, verificationcode, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mView != null) {
                    mView.onResetPwdError(code, msg);
                }
            }

            @Override
            public void onSuccess(String result, Request request) {
                hideLoading();
                if (mView != null) {
                    mView.onResetPwdSuccess();
                }
            }
        });
    }

    public void refreshToken() {
        mModel.refreshToken(mManager.getRefreshToken(), mManager.getAuthCode(), "refresh_token", new BeanCallback<CloudAccount>() {

            @Override
            public void onError(String code, String msg) {
                if (mView != null) {
                    mView.onRefreshTokenError(code, msg);
                }
            }

            @Override
            public void onSuccess(CloudAccount cloudAccount) {
                if (mView != null) {
                    mManager.saveValue(AppConstans.Sp.ACCESS_TOKEN, cloudAccount.getAccess_token());
                    mView.onRefreshTokenSuccess(cloudAccount);
                }
            }
        });
    }


}
