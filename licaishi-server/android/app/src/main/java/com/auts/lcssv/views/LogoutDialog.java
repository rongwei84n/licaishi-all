package com.auts.lcssv.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.listener.DialogOnClickListener;

/**
 * 账号被踢出时显示的Dialog
 * Created by xiaolei.yang on 2017/8/4.
 */

public class LogoutDialog extends Dialog {
    private TextView mMessage;
    private TextView mPositiveBtn;
    private static boolean isShowed;    //判断当前App是否已经显示该dialog

    public LogoutDialog(Context context, String message, String confirmText, int gravity, final DialogOnClickListener leftListener) {
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
                    leftListener.onClickListener(LogoutDialog.this, v);
                } else {
                    dismiss();
                }
            }
        });
    }

    public LogoutDialog(Context context, String message, String confirmText, int gravity, int color, final DialogOnClickListener leftListener) {
        this(context,message,confirmText,gravity,leftListener);
        mPositiveBtn.setTextColor(context.getResources().getColor(color));
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

    @Override
    public void show() {
        isShowed = true;
        super.show();
    }

    @Override
    public void dismiss() {
        isShowed = false;
        super.dismiss();
    }

    public static boolean checkShow() {
        return isShowed;
    }

}
