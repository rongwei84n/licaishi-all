package com.auts.lcssv.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.auts.lcssv.R;

/**
 * 通用Dialog
 * Created by weiming.zeng on 2017/7/27.
 */

public class CommonDialog extends Dialog {
    private TextView mMessage;
    private Button mPositiveBtn;
    private Button mNegativeBtn;

    public CommonDialog(Context context) {
        super(context);
        initView();
    }

    public CommonDialog(Context context, String message, String positbieText, String negativeText, int negativeColor,
                        final View.OnClickListener positiveCallBack, final View.OnClickListener negativeCallBack) {
        this(context, message, positbieText, negativeText, positiveCallBack, negativeCallBack);
        if (mNegativeBtn != null) {
            mNegativeBtn.setTextColor(context.getResources().getColor(negativeColor));
        }
    }

    public CommonDialog(Context context, String message, @NonNull String positbieText,
                        @NonNull String negativeText, final View.OnClickListener positiveCallBack,
                        final View.OnClickListener negativeCallBack) {
        super(context);
        initView();
        mMessage.setText(message);
        mPositiveBtn.setText(positbieText);
        mNegativeBtn.setText(negativeText);
        if (null != positiveCallBack) {
            mPositiveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    positiveCallBack.onClick(v);
                }
            });
        }
        if (null != negativeCallBack) {
            mNegativeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    negativeCallBack.onClick(v);
                }
            });
        } else {
            mNegativeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

    }

    public CommonDialog(Context context, String message, View.OnClickListener positiveCallBack, View.OnClickListener negativeCallBack) {
        super(context);
        initView();
        mMessage.setText(message);
        if (null != positiveCallBack) {
            mPositiveBtn.setOnClickListener(positiveCallBack);
        }
        if (null != negativeCallBack) {
            mNegativeBtn.setOnClickListener(negativeCallBack);
        } else {
            mPositiveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

    }


    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_common);
        mMessage = (TextView) findViewById(R.id.tv_message);
        mPositiveBtn = (Button) findViewById(R.id.btn_positive);
        mNegativeBtn = (Button) findViewById(R.id.btn_negative);
    }

    public void setPositiveListener(View.OnClickListener listener) {
        mPositiveBtn.setOnClickListener(listener);
    }

    public void setNegativetiveListener(View.OnClickListener listener) {
        mNegativeBtn.setOnClickListener(listener);
    }

    public Button getmNegativeBtn() {
        return mNegativeBtn;
    }

}
