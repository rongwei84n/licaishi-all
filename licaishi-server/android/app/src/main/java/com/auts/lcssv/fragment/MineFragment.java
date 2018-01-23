package com.auts.lcssv.fragment;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.activity.AboutActivity;
import com.auts.lcssv.activity.HouseManageActivity;
import com.auts.lcssv.activity.MessageActivity;
import com.auts.lcssv.activity.PersonalInformationActivity;
import com.auts.lcssv.base.BaseFragment;
import com.auts.lcssv.bean.AccountDetailsBean;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.event.ChangeNicknameEvent;
import com.auts.lcssv.event.ChangePhoneNumberSuccessEvent;
import com.auts.lcssv.event.UploadHeadPortraitEvent;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.manager.imageloader.GlideCircleTransform;
import com.auts.lcssv.manager.imageloader.ImageLoader;
import com.auts.lcssv.presenter.MessagePresenter;
import com.auts.lcssv.presenter.UserInfoPresenter;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.presenter.viewback.MessageView;
import com.auts.lcssv.presenter.viewback.UserInfoView;
import com.auts.lcssv.util.EditTextUtils;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.SpfUtils;
import com.auts.lcssv.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * “我的”页
 *
 * @author qisheng.lv
 * @date 2017/7/5
 */
public class MineFragment extends BaseFragment implements ILoadingView {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_menu)
    TextView mTvMenu;

    @BindView(R.id.iv_head_portrait)
    ImageView mIvHeadPortrait;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_home_name)
    TextView mTvHomeName;
    @BindView(R.id.iv_red_dot)
    ImageView mRedDot;

    UserInfoPresenter mUploadBasePresenter;
    MessagePresenter mMessagePresenter;
    AccountDetailsBean mAccountDetailsBean;
    public static final int REQUESTCODE = 1046;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_mine, null);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void afterInitView() {
        EventBus.getDefault().register(this);
        mTvTitle.setText(R.string.me);
        setPhoneNumber();
        mTvHomeName.setFilters(new InputFilter[]{EditTextUtils.getMaskLenthFilter(18, "...")});
        mTvHomeName.setText(SpfUtils.get(AppConstans.Sp.SELECTED_HOUSE, "我的家").toString());
        mIvBack.setVisibility(View.GONE);
        mTvMenu.setVisibility(View.GONE);

        mUploadBasePresenter = new UserInfoPresenter(this, new UserInfoView() {
            @Override
            public void accountDetailSuccess(AccountDetailsBean accountDetailsBean) {
                if (accountDetailsBean != null) {
                    mAccountDetailsBean = accountDetailsBean;
                    String imageUrl = mAccountDetailsBean.getImg();
                    if (TextUtils.isEmpty(imageUrl)) {
                        imageUrl = "";
                    }
                    LogUtils.error("=====imageUrl1==",imageUrl+"===");
                    ImageLoader.getLoader(getActivity())
                            .load(imageUrl)
                            .placeholder(R.drawable.head_portrait)
                            .transform(new GlideCircleTransform(getActivity()))
                            .into(mIvHeadPortrait);
                    if (!TextUtils.isEmpty(mAccountDetailsBean.getNickname())) {
                        mTvNickname.setText(mAccountDetailsBean.getNickname());
                    } else {
                        setPhoneNumber();
                    }
                } else {
                    accountDetailError("0", null);
                }
            }

            @Override
            public void accountDetailError(String code, String msg) {
                setPhoneNumber();
                ToastUtil.show(TextUtils.isEmpty(msg) ? getString(R.string.get_person_info_fail) : msg);
            }
        });
        mMessagePresenter = new MessagePresenter(this, new MessageView() {
            @Override
            public void checkMessageSuccess(String result) {
                try {
                    if (0 == new JSONObject(result).getInt("has_unread_msg")) {
                        mRedDot.setVisibility(View.INVISIBLE);
                    } else {
                        mRedDot.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void checkMessageError(String msg) {
//                ToastUtil.show(msg);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mMessagePresenter != null) {
            mRedDot.setVisibility(View.INVISIBLE);
            mTvHomeName.setText(SpfUtils.get(AppConstans.Sp.SELECTED_HOUSE, "我的家").toString());
            //屏幕显示的时候检查消息提醒是否更新
            if (mMessagePresenter != null) {
                mMessagePresenter.checkMsgReadStatus();
            }
            if (mUploadBasePresenter != null) {
                mUploadBasePresenter.accountDetail();
            }
        }
    }

    private void setPhoneNumber() {
        String phoneNumber = AccountManager.getInstance().getLoginPhone();
        if (phoneNumber.length() == 11) {
            StringBuilder builder = new StringBuilder(phoneNumber);
            String showPhoneNumber = builder.replace(3, 7, "****").toString();
            mTvNickname.setText(showPhoneNumber);
        }
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onEventMainThread(UploadHeadPortraitEvent event) {
        if (mAccountDetailsBean != null) {
            mAccountDetailsBean.setImg(event.getUrl());
            String imageUrl = mAccountDetailsBean.getImg();
            if (TextUtils.isEmpty(imageUrl)) {
                imageUrl = "";
            }
            LogUtils.error("=====imageUrl2==",imageUrl+"===");
            ImageLoader.getLoader(getActivity())
                    .load(imageUrl)
                    .placeholder(R.drawable.head_portrait)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(mIvHeadPortrait);
        }
    }

    @Subscribe
    public void onEventMainThread(ChangeNicknameEvent event) {
        if (mAccountDetailsBean != null) {
            mAccountDetailsBean.setNickname(event.getNickname());
            if (!TextUtils.isEmpty(mAccountDetailsBean.getNickname())) {
                mTvNickname.setText(mAccountDetailsBean.getNickname());
            }
        }
    }

    @Subscribe
    public void onEventMainThread(ChangePhoneNumberSuccessEvent event) {
        if (mAccountDetailsBean != null) {
            mAccountDetailsBean.setPhonenumber(event.getPhoneNumber());
        }
    }


    @OnClick(R.id.ll_nickname)
    public void ll_nickname() {
        Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
        intent.putExtra("account_details_bean", mAccountDetailsBean);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), mIvHeadPortrait, getString(R.string.head_portrait));
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    @OnClick(R.id.rl_home_manage)
    public void rl_home_manage() {
        Intent intent = new Intent(getActivity(), HouseManageActivity.class);
        startActivityForResult(intent, REQUESTCODE);
    }

    @OnClick(R.id.rl_about)
    public void ll_about() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_message_reminding)
    public void ll_message() {
        startActivityForResult(new Intent(getActivity(), MessageActivity.class), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MessageActivity.RESULTCODE) {
            mRedDot.setVisibility(View.INVISIBLE);
        }
        if (resultCode == HouseManageActivity.RESULTCODE) {
            mTvHomeName.setText(SpfUtils.get(AppConstans.Sp.SELECTED_HOUSE, "我的家").toString());
        }
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
