package com.auts.lcssv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.AccountDetailsBean;
import com.auts.lcssv.bean.UploadBaseBean;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.event.ChangeNicknameEvent;
import com.auts.lcssv.event.ChangePhoneNumberSuccessEvent;
import com.auts.lcssv.event.UploadHeadPortraitEvent;
import com.auts.lcssv.listener.GetPhotoBeforeListener;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.manager.imageloader.GlideCircleTransform;
import com.auts.lcssv.manager.imageloader.ImageLoader;
import com.auts.lcssv.popup.GetPhotoPopup;
import com.auts.lcssv.presenter.CloudAccountPresenter;
import com.auts.lcssv.presenter.UserInfoPresenter;
import com.auts.lcssv.presenter.viewback.CloudAccountView;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.presenter.viewback.UserInfoView;
import com.auts.lcssv.service.CommonService;
import com.auts.lcssv.util.AppManager;
import com.auts.lcssv.util.Base64Utils;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.PathUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.util.UmengUtil;
import com.auts.lcssv.views.CommonDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人信息界面
 *
 * @author xiaolei.yang
 * @date 2017/7/19
 */
public class PersonalInformationActivity extends BaseActivity implements GetPhotoBeforeListener, ILoadingView {
    @BindView(R.id.ll_head_portrait)
    LinearLayout mLlHeadPortrait;

    @BindView(R.id.iv_head_portrait)
    ImageView mIvHeadPortrait;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;

    @BindView(R.id.tv_phone_number)
    TextView mTvPhoneNumber;

    private UserInfoPresenter mUploadBasePresenter;
    private CloudAccountPresenter mCloudAccountPresenter;

    private AccountDetailsBean mAccountDetailsBean;
    /**
     * 手机号码的真实值
     */
    private String phoneNumber = "";
    /**
     * 显示出来中间四位隐藏的手机号码
     */
    private String showPhoneNumber = "";


    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_personal_information);
    }

    @Override
    public void afterInitView() {
        EventBus.getDefault().register(this);
        setPageTitle(R.string.person_info);

        mUploadBasePresenter = new UserInfoPresenter(this, new UserInfoView() {
            @Override
            public void uploadBaseSuccess(UploadBaseBean uploadBaseBean) {
                UmengUtil.onEvent(PersonalInformationActivity.this, UmengUtil.AVATAR_SAVE_SUCCESS);
                if (uploadBaseBean == null) {
                    uploadBaseError("0", null);
                } else {
                    String url = uploadBaseBean.getUrl();
                    EventBus.getDefault().post(new UploadHeadPortraitEvent(url));
                }
            }

            @Override
            public void uploadBaseError(String code, String msg) {
                UmengUtil.onEvent(PersonalInformationActivity.this, UmengUtil.AVATAR_SAVE_FAIL);
                ToastUtil.show(TextUtils.isEmpty(msg) ? getString(R.string.upload_head_portrait_fail) : msg);
            }

            @Override
            public void avatarUrlSuccess(UploadBaseBean uploadBaseBean) {
                if (uploadBaseBean == null) {
                    avatarUrlError("0", null);
                } else {
                    String imageUrl = uploadBaseBean.getAvatarUrl();
                    if (TextUtils.isEmpty(imageUrl)) {
                        imageUrl = "";
                    }
                    LogUtils.error("=====imageUrl3==", imageUrl + "===");
                    ImageLoader.getLoader(PersonalInformationActivity.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.head_portrait)
                            .transform(new GlideCircleTransform(PersonalInformationActivity.this))
                            .into(mIvHeadPortrait);
                }
            }

            @Override
            public void avatarUrlError(String code, String msg) {
                ToastUtil.show(TextUtils.isEmpty(msg) ? getString(R.string.get_head_portrait_fail) : msg);
            }

            @Override
            public void accountDetailSuccess(AccountDetailsBean accountDetailsBean) {
                if (accountDetailsBean != null) {
                    mAccountDetailsBean = accountDetailsBean;

                    String imageUrl = mAccountDetailsBean.getImg();
                    if (TextUtils.isEmpty(imageUrl)) {
                        imageUrl = "";
                    }
                    LogUtils.error("=====imageUrl4==", imageUrl + "===");
                    ImageLoader.getLoader(PersonalInformationActivity.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.head_portrait)
                            .transform(new GlideCircleTransform(PersonalInformationActivity.this))
                            .into(mIvHeadPortrait);

                    mTvNickname.setText(TextUtils.isEmpty(mAccountDetailsBean.getNickname()) ? getString(R.string.not_set) : mAccountDetailsBean.getNickname());
                    phoneNumber = TextUtils.isEmpty(mAccountDetailsBean.getPhonenumber()) ? "" : mAccountDetailsBean.getPhonenumber();
                    if (phoneNumber.length() == 11) {
                        StringBuilder builder = new StringBuilder(phoneNumber);
                        showPhoneNumber = builder.replace(3, 7, "****").toString();
                    }
                    mTvMobile.setText(showPhoneNumber);
                    mTvPhoneNumber.setText(TextUtils.isEmpty(mAccountDetailsBean.getNickname())
                            ? showPhoneNumber : mAccountDetailsBean.getNickname());
                } else {
                    accountDetailError("0", null);
                }
            }

            @Override
            public void accountDetailError(String code, String msg) {
                ToastUtil.show(TextUtils.isEmpty(msg) ? getString(R.string.get_person_info_fail) : msg);
            }
        });

        mCloudAccountPresenter = new CloudAccountPresenter(this, new CloudAccountView() {
            @Override
            public void onLogoutError(String code, String msg) {
                UmengUtil.onEvent(PersonalInformationActivity.this, UmengUtil.LOGOUT_FAIL);
                ToastUtil.show(msg);
            }

            @Override
            public void onLogoutSuccess() {
                UmengUtil.onEvent(PersonalInformationActivity.this, UmengUtil.LOGOUT_SUCCESS);
                logout();
            }
        });

//        mUploadBasePresenter.avatarUrl();//单独获取头像url的接口，可以使用获取账号的接口，不用这个
        mAccountDetailsBean = (AccountDetailsBean) getIntent().getSerializableExtra("account_details_bean");
        if (mAccountDetailsBean == null) {
            mUploadBasePresenter.accountDetail();
        } else {
            String imageUrl = mAccountDetailsBean.getImg();
            if (TextUtils.isEmpty(imageUrl)) {
                imageUrl = "";
            }
//                    打印图片的url和缓存路径
//            final String url = imageUrl;
//            hasnew Thread(hasnew Runnable() {
//                @Override
//                public void run() {
//                    Log.e("======imageUrl2", "run: " + url);
//                    FutureTarget<File> future = Glide.with(PersonalInformationActivity.this)
//                            .load(url)
//                            .downloadOnly(100, 100);
//                    try {
//                        File cacheFile = future.get();
//                        String path = cacheFile.getAbsolutePath();
//                        Log.e("======path2", "run: " + path);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
            LogUtils.error("=====imageUrl5==", imageUrl + "===");
            ImageLoader.getLoader(this)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.head_portrait)
                    .transform(new GlideCircleTransform(this))
                    .into(mIvHeadPortrait);
            mTvNickname.setText(TextUtils.isEmpty(mAccountDetailsBean.getNickname()) ? getString(R.string.not_set) : mAccountDetailsBean.getNickname());

            mTvNickname.setText(TextUtils.isEmpty(mAccountDetailsBean.getNickname()) ? getString(R.string.not_set) : mAccountDetailsBean.getNickname());
            phoneNumber = TextUtils.isEmpty(mAccountDetailsBean.getPhonenumber()) ? "" : mAccountDetailsBean.getPhonenumber();
            if (phoneNumber.length() == 11) {
                StringBuilder builder = new StringBuilder(phoneNumber);
                showPhoneNumber = builder.replace(3, 7, "****").toString();
            }
            mTvMobile.setText(showPhoneNumber);
            mTvPhoneNumber.setText(TextUtils.isEmpty(mAccountDetailsBean.getNickname())
                    ? showPhoneNumber : mAccountDetailsBean.getNickname());
        }

    }

    private void logout() {
        stopService(new Intent(this, CommonService.class));
        AccountManager.getInstance().clearRefreshToken();
        String phone = AccountManager.getInstance().getLoginPhone();
        AppManager.getAppManager().finishAllActivity();
        startActivity(new Intent(this, LoginCloudActivity.class).putExtra("register_phone", phone));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(ChangeNicknameEvent event) {
        if (mAccountDetailsBean != null) {
            mAccountDetailsBean.setNickname(event.getNickname());
            if (TextUtils.isEmpty(mAccountDetailsBean.getNickname())) {
                mTvNickname.setText(R.string.not_set);
            } else {
                mTvNickname.setText(mAccountDetailsBean.getNickname());
                mTvPhoneNumber.setText(mAccountDetailsBean.getNickname());
            }
        }
    }

    @Subscribe
    public void onEventMainThread(ChangePhoneNumberSuccessEvent event) {
        if (mAccountDetailsBean != null) {
            mAccountDetailsBean.setPhonenumber(event.getPhoneNumber());

            mTvNickname.setText(TextUtils.isEmpty(mAccountDetailsBean.getNickname()) ? getString(R.string.not_set) : mAccountDetailsBean.getNickname());
            phoneNumber = TextUtils.isEmpty(mAccountDetailsBean.getPhonenumber()) ? "" : mAccountDetailsBean.getPhonenumber();
            if (phoneNumber.length() == 11) {
                StringBuilder builder = new StringBuilder(phoneNumber);
                showPhoneNumber = builder.replace(3, 7, "****").toString();
            }
            mTvMobile.setText(showPhoneNumber);
            mTvPhoneNumber.setText(TextUtils.isEmpty(mAccountDetailsBean.getNickname()) ? showPhoneNumber : mAccountDetailsBean.getNickname());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.ll_head_portrait)
    public void ll_head_portrait() {
        GetPhotoPopup photoPopup = new GetPhotoPopup(this, this);
        photoPopup.showAsDropDown(mLlHeadPortrait);
    }

    @OnClick(R.id.rl_nickname)
    public void tv_nickname() {
        Intent intent = new Intent(this, ChangeNicknameActivity.class);
        if (mAccountDetailsBean != null) {
            intent.putExtra("nickname", mAccountDetailsBean.getNickname());
        }
        UmengUtil.onEvent(this, UmengUtil.NICK_NAME_SAVE);
        startActivity(intent);
    }

    @OnClick(R.id.rl_modify_password)
    public void rl_modify_password() {
        Intent intent = new Intent(this, ModifyPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_mobile)
    public void rl_mobile() {
        Intent intent = new Intent(this, BindingPhoneActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_exit)
    public void tv_exit() {
        new CommonDialog(this, getString(R.string.are_you_sure_to_exit), getString(R.string.confirm),
                getString(R.string.cancel), R.color.text_oringe, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        }, null).show();
    }

    @Override
    public void iv_back() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public void onBackPressed() {
        iv_back();
    }

    /**
     * 连击数
     */
    int doubleHitCount;
    long startTime;

    @OnClick(R.id.ll_root)
    public void ll_root() {
        if (doubleHitCount == 4) {
            doubleHitCount = 0;
            startTime = 0;
            Intent intent = new Intent(this, FactoryActivity.class);
            startActivity(intent);
        } else {
            long lastTime = System.currentTimeMillis();
            if (startTime == 0) {
                startTime = lastTime;
                doubleHitCount = 1;
            } else {
                if (lastTime - startTime > 500) {
                    startTime = lastTime;
                    doubleHitCount = 1;
                } else {
                    startTime = lastTime;
                    doubleHitCount++;
                }
            }
        }
    }

    @Override
    public void getPhotoFromCamera() {
        Intent intent = new Intent(this, GetPhotoActivity.class);
        intent.putExtra("type", AppConstans.GetPhoto.GET_PHOTO_FROM_CAMERA);
        startActivityForResult(intent, AppConstans.GetPhoto.ONE_DRAGON);
    }

    @Override
    public void getPhotoFromAlbum() {
        Intent intent = new Intent(this, GetPhotoActivity.class);
        intent.putExtra("type", AppConstans.GetPhoto.GET_PHOTO_FROM_ALBUM);
        startActivityForResult(intent, AppConstans.GetPhoto.ONE_DRAGON);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AppConstans.GetPhoto.ONE_DRAGON:
                if (resultCode == RESULT_OK && data != null) {
                    String imageString = data.getStringExtra("image_string");
                    byte[] buf = Base64Utils.decode(imageString);
                    InputStream inputStream = new ByteArrayInputStream(buf);

                    String dir = PathUtils.getCameraImageDir();
                    File file = new File(dir, System.currentTimeMillis() + ".jpg");
                    try {
                        OutputStream os = new FileOutputStream(file);
                        int bytesRead;
                        byte[] buffer = new byte[8192];
                        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        os.close();
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String imageUrl = file.getAbsolutePath();
                    if (TextUtils.isEmpty(imageUrl)) {
                        imageUrl = "";
                    }
                    LogUtils.error("=====imageUrl6==", imageUrl + "===");
                    ImageLoader.getLoader(this)
                            .load(imageUrl)
                            .placeholder(R.drawable.head_portrait)
                            .transform(new GlideCircleTransform(this))
                            .into(mIvHeadPortrait);
                    mUploadBasePresenter.uploadBase64(imageString, "1");
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
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
