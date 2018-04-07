package com.auts.lcscli.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.auts.lcscli.R;
import com.auts.lcscli.listener.DialogOnClickListener;

/**
 * 通用Dialog
 * Created by xiaolei.yang on 2017/8/4.
 */

public class ComfirmDialog extends Dialog {
    private TextView mMessage;
    private TextView mPositiveBtn;
    private TextView mTitle;
    private int type;
    private Context mContext;

    public ComfirmDialog(Context context, String title, String message, int gravity) {
        this(context, title, message, null, gravity, null);
    }

    public ComfirmDialog(Context context, String title, String message, int color, int gravity) {
        this(context, title, message, null, gravity, null);
        mPositiveBtn.setTextColor(context.getResources().getColor(color));
    }

    public ComfirmDialog(Context context, String title, int message, int confirmText, int gravity, DialogOnClickListener leftListener) {
        this(context, title, context.getString(message), confirmText == 0 ? null : context.getString(confirmText), gravity, leftListener);
    }

    public ComfirmDialog(Context context, String title, String message, String comfirmText, int gravity, final DialogOnClickListener leftListener) {
        super(context);
        this.mContext = context;
        initView(title, gravity);
        if (!TextUtils.isEmpty(message)) {
            mMessage.setText(message);
        }
        if (!TextUtils.isEmpty(comfirmText)) {
            mPositiveBtn.setText(comfirmText);
        }
        mPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftListener != null) {
                    leftListener.onClickListener(ComfirmDialog.this, v);
                } else {
                    dismiss();
                }
            }
        });
    }

    private void initView(String title, int gravity) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_comfirm);
        mMessage = (TextView) findViewById(R.id.tv_message);
        mPositiveBtn = (TextView) findViewById(R.id.btn_positive);
        mTitle = (TextView) findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(title)) {
            mTitle.setVisibility(View.GONE);
        } else {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(title);
        }

        mMessage.setGravity(gravity);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void show() {
        if (mContext != null) {
            super.show();
        }
    }

}
