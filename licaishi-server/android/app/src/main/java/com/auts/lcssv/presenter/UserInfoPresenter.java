package com.auts.lcssv.presenter;

import com.alibaba.fastjson.JSON;
import com.auts.lcssv.PhApplication;
import com.auts.lcssv.R;
import com.auts.lcssv.base.BasePresenter;
import com.auts.lcssv.bean.AccountBean;
import com.auts.lcssv.bean.AccountDetailsBean;
import com.auts.lcssv.bean.FxResponse;
import com.auts.lcssv.bean.UploadBaseBean;
import com.auts.lcssv.model.UserInfoModel;
import com.auts.lcssv.net.callback.BaseCallback;
import com.auts.lcssv.net.callback.BeanCallback;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.presenter.viewback.UserInfoView;
import com.auts.lcssv.util.EntryUtils;
import com.auts.lcssv.util.UmengUtil;

import okhttp3.Request;

import static com.auts.lcssv.util.UmengUtil.TEL_SAVE_SUCCESS;

/**
 * 用户信息
 * Created by xiaolei.yang on 2017/7/24.
 */

public class UserInfoPresenter extends BasePresenter {
    private UserInfoModel mUserInfoModel;
    private UserInfoView mUserInfoView;

    public UserInfoPresenter(ILoadingView loadingView, UserInfoView userInfoView) {
        mILoadingView = loadingView;
        mUserInfoView = userInfoView;
        mUserInfoModel = new UserInfoModel();
    }

    public void uploadBase64(String imgBase64, String type) {
        showLoading(R.string.loading_text);
        mUserInfoModel.uploadBase64(imgBase64, type, new BeanCallback<UploadBaseBean>() {
            @Override
            public void onSuccess(UploadBaseBean uploadBaseBean) {
                hideLoading();
                if (mUserInfoView != null) {
                    mUserInfoView.uploadBaseSuccess(uploadBaseBean);
                }
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mUserInfoView != null) {
                    mUserInfoView.uploadBaseError(code, msg);
                }
            }
        });
    }

    public void avatarUrl() {
        showLoading(R.string.loading_text);
        mUserInfoModel.avatarUrl(new BeanCallback<UploadBaseBean>() {
            @Override
            public void onSuccess(UploadBaseBean uploadBaseBean) {
                hideLoading();
                if (mUserInfoView != null) {
                    mUserInfoView.avatarUrlSuccess(uploadBaseBean);
                }
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mUserInfoView != null) {
                    mUserInfoView.avatarUrlError(code, msg);
                }
            }
        });
    }

    public void accountDetail() {
        showLoading(R.string.loading_text);
        mUserInfoModel.accountDetail(new BeanCallback<AccountBean>() {
            @Override
            public void onSuccess(AccountBean accountBean) {
                hideLoading();
                if (accountBean != null) {
                    AccountDetailsBean accountDetailsBean = accountBean.getData();
                    if (accountDetailsBean != null) {
                        if (mUserInfoView != null) {
                            mUserInfoView.accountDetailSuccess(accountDetailsBean);
                        }
                    } else {
                        onError("0", null);
                    }
                } else {
                    onError("0", null);
                }
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mUserInfoView != null) {
                    mUserInfoView.accountDetailError(code, msg);
                }
            }
        });
    }

    public void property(AccountDetailsBean accountDetailsBean) {
        showLoading(R.string.loading_text);
        mUserInfoModel.property(JSON.toJSONString(accountDetailsBean), new BeanCallback<FxResponse>() {
            @Override
            public void onSuccess(FxResponse fxResponse) {
                hideLoading();
                if (mUserInfoView != null) {
                    mUserInfoView.propertySuccess();
                }
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mUserInfoView != null) {
                    mUserInfoView.propertyError(code, msg);
                }
            }
        });
    }

    public void password(String oldPassword, String newPassword) {
        showLoading(R.string.loading_text);
        mUserInfoModel.password(EntryUtils.getMd5(oldPassword), EntryUtils.getMd5(newPassword), new BeanCallback<FxResponse>() {
            @Override
            public void onSuccess(FxResponse fxResponse) {
                hideLoading();
                if (mUserInfoView != null) {
                    mUserInfoView.modifyPasswordSuccess(fxResponse);
                }
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mUserInfoView != null) {
                    mUserInfoView.modifyPasswordError(code, msg);
                }
            }
        });
    }

    public void oldAccountVerification(String mailAddress, String phoneNumber, String verificationCode) {
        showLoading(R.string.loading_text);
        mUserInfoModel.oldAccountVerification(mailAddress, phoneNumber, verificationCode, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mUserInfoView != null) {
                    mUserInfoView.oldAccountVerificationError(code, msg);
                }
            }

            @Override
            public void onSuccess(String result, Request request) {
                hideLoading();
                if (mUserInfoView != null) {
                    mUserInfoView.oldAccountVerificationSuccess();
                }
            }
        });
    }


    public void rebind(String mailAddress, String phoneNumber, String verificationCode) {
        showLoading(R.string.loading_text);
        mUserInfoModel.rebind(mailAddress, phoneNumber, verificationCode, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                if (mUserInfoView != null) {
                    UmengUtil.onEvent(PhApplication.getPhApplication(), UmengUtil.TEL_SAVE_FAIL);
                    mUserInfoView.rebindError(code, msg);
                }
            }

            @Override
            public void onSuccess(String result, Request request) {
                hideLoading();
                if (mUserInfoView != null) {
                    UmengUtil.onEvent(PhApplication.getPhApplication(), TEL_SAVE_SUCCESS);
                    mUserInfoView.rebindSuccess();
                }
            }
        });
    }


}
