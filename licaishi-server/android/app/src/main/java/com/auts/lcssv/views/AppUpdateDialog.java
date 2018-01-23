package com.auts.lcssv.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.listener.DialogOnClickListener;
import com.auts.lcssv.util.AppInfoUtils;
import com.auts.lcssv.util.NetworkUtils;
import com.auts.lcssv.util.SpfUtils;
import com.auts.lcssv.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 通用Dialog
 * Created by xiaolei.yang on 2017/8/4.
 */

public class AppUpdateDialog extends Dialog {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_download)
    TextView mTvDownload;
    @BindView(R.id.pb_download)
    ProgressBar mPb;
    @BindView(R.id.ll_download)
    LinearLayout mLlDownload;
    @BindView(R.id.ll_btn)
    LinearLayout mLlBtn;
    @BindView(R.id.view_line1)
    View mViewLine1;
    @BindView(R.id.view_line2)
    View mViewLine2;
    @BindView(R.id.tv_progress)
    TextView mTvProgress;
    @BindView(R.id.tv_extra)
    TextView mTvExtra;
    @BindView(R.id.view_place)
    View mViewPlace;

    private Context mContext;
    private DialogOnClickListener mListener;
    private boolean mIsForce;
    private CancelListener mCancelListener;
    private String mMessage;
    private String mLeftText;
    private String mRightText;
    //是否正在取消下载
    private boolean mIsCancelView;
    private String mVerCoce;

    public interface CancelListener {
        void onCancel();

        void onResume();
    }

    public AppUpdateDialog(Context context, @NonNull String title, @NonNull String message, String cancelText, @NonNull String downloadText,
                           boolean isForce, String verCode, final DialogOnClickListener rightListener) {
        super(context);
        mContext = context;
        mIsForce = isForce;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_app_update);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
        mListener = rightListener;
        mTvTitle.setText(title);
        mTvMessage.setText(message);
        if (TextUtils.isEmpty(cancelText)) {
            mTvCancel.setVisibility(View.GONE);
            mViewLine2.setVisibility(View.GONE);
        } else {
            mTvCancel.setText(cancelText);
        }
        mTvDownload.setText(downloadText);
        mMessage = message;
        mLeftText = cancelText;
        mRightText = downloadText;
        mVerCoce = verCode;
    }

    @OnClick(R.id.tv_cancel)
    public void tv_cancel() {
        if (mIsCancelView) {
            if (mCancelListener != null) {
                mCancelListener.onResume();
            }
            hideCancelView();
        } else {
            //点击"暂不更新"要将点击的时间保存下来，15天内不再弹窗
            if (mTvCancel.getText().equals("暂不更新")) {
                SpfUtils.put("app_update_show_" + AppInfoUtils.getAppVersionCode(), System.currentTimeMillis() / 1000 + "_" + mVerCoce);
            }
            dismiss();
        }
    }

    @OnClick(R.id.tv_extra)
    public void tv_extra() {
        String extra = mTvExtra.getText().toString();
        if (TextUtils.isEmpty(extra)) {
            return;
        }

        if (extra.equals(mContext.getString(R.string.cancel))) {
            showCancelView();
        } else {
            dismiss();
        }
    }

    @OnClick(R.id.tv_download)
    public void tv_download() {
        if (mIsCancelView) {
//            if (mCancelListener != null) {
//                mCancelListener.onCancel();
//            }
            dismiss();
            return;
        }
        if (!NetworkUtils.isNetAvailable()) {
            ToastUtil.show("当前网络不可用，请检查网络设置");
            return;
        }
        if (mListener != null) {
            mListener.onClickListener(this, mTvDownload);
        }
        mViewLine1.setVisibility(View.INVISIBLE);
        mLlBtn.setVisibility(View.INVISIBLE);
        mLlDownload.setVisibility(View.VISIBLE);
        if (!mIsForce) {
            mTvExtra.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示取消下载确定框
     */
    private void showCancelView() {
        mIsCancelView = true;
        mLlDownload.setVisibility(View.GONE);
        mViewLine1.setVisibility(View.VISIBLE);
        mLlBtn.setVisibility(View.VISIBLE);

        mTvTitle.setVisibility(View.GONE);
        mViewPlace.setVisibility(View.VISIBLE);
        mTvMessage.setText("正在下载最新版本，确定退出下载吗？");
        mTvCancel.setText("取消");
        mTvDownload.setText("确定");
        if (mCancelListener != null) {
            mCancelListener.onCancel();
        }
    }

    /**
     * 隐藏取消下载确定框
     */
    private void hideCancelView() {
        mIsCancelView = false;
        mViewLine1.setVisibility(View.INVISIBLE);
        mLlBtn.setVisibility(View.INVISIBLE);
        mLlDownload.setVisibility(View.VISIBLE);
        mViewPlace.setVisibility(View.GONE);

        mTvTitle.setVisibility(View.VISIBLE);
        mTvMessage.setText(mMessage);
        if (TextUtils.isEmpty(mLeftText)) {
            mTvCancel.setText(mLeftText);
        }
        mTvDownload.setText(mRightText);
    }

    public void setCancelListener(CancelListener listener) {
        this.mCancelListener = listener;
    }

    public void setProgress(int progress) {
        mPb.setProgress(progress);
        mTvProgress.setTextColor(mContext.getResources().getColor(R.color.green));
        mTvProgress.setText(progress + "% 正在下载...");
        if (!mIsForce) {
            mTvExtra.setText("取消");
        } else {
            mTvExtra.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 强制模式下载出错时不显示关闭按钮
     *
     * @param msg
     */
    public void showError(String msg) {
        mTvProgress.setTextColor(Color.RED);
        mTvProgress.setText(msg);
        if (mIsForce) {
            mTvExtra.setVisibility(View.INVISIBLE);
        } else {
            mTvExtra.setVisibility(View.VISIBLE);
            mTvExtra.setText("关闭");
        }
    }

}
