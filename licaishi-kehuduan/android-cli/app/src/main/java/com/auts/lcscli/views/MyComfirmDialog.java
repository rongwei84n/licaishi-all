package com.auts.lcscli.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.auts.lcscli.PhApplication;
import com.auts.lcscli.R;
import com.auts.lcscli.listener.DialogOnClickListener;

/**
 * 通用Dialog
 * Created by xiaolei.yang on 2017/8/4.
 */

public class MyComfirmDialog extends Dialog {
    private TextView mMessage;
    private TextView mPositiveBtn;

    public MyComfirmDialog(Context context, String message, String confirmText, int gravity, final DialogOnClickListener leftListener) {
        super(context);
        initView(gravity);
        if (!TextUtils.isEmpty(message)) {
            mMessage.setText(message);
        }
        if (!TextUtils.isEmpty(confirmText)) {
            mPositiveBtn.setText(confirmText);
        }
        mPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftListener != null) {
                    leftListener.onClickListener(MyComfirmDialog.this, v);
                } else {
                    dismiss();
                }
            }
        });
    }

    public MyComfirmDialog(Context context, String message, String confirmText, int gravity,int color, final DialogOnClickListener leftListener) {
        this(context,message,confirmText,gravity,leftListener);
        mPositiveBtn.setTextColor(PhApplication.getAppContext().getResources().getColor(color));
    }

    private void initView(int gravity) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_comfirm);
        mMessage = (TextView) findViewById(R.id.tv_message);
        mPositiveBtn = (TextView) findViewById(R.id.btn_positive);
        mMessage.setGravity(gravity);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }


}
