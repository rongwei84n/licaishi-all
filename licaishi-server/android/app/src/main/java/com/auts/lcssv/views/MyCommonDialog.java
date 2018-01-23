package com.auts.lcssv.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.listener.DialogOnClickListener;

/**
 * 通用Dialog
 * Created by xiaolei.yang on 2017/8/4.
 */

public class MyCommonDialog extends Dialog {
    private TextView mMessage;
    private TextView mLeftBtn;
    private TextView mRightBtn;

    public MyCommonDialog(Context context, String message, @NonNull String leftText,
                          final DialogOnClickListener leftListener,
                          @NonNull String rightText, int rightTextColor, final DialogOnClickListener rightListener) {
        super(context);
        initView();
        mMessage.setText(message);
        mLeftBtn.setText(leftText);
        mRightBtn.setText(rightText);
        if (rightTextColor != 0) {
            mRightBtn.setTextColor(context.getResources().getColor(rightTextColor));
        }

        mLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftListener != null) {
                    leftListener.onClickListener(MyCommonDialog.this, v);
                } else {
                    dismiss();
                }
            }
        });
        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightListener != null) {
                    rightListener.onClickListener(MyCommonDialog.this, v);
                } else {
                    dismiss();
                }
            }
        });
        setCancelable(false);
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_my_common);
        mMessage = (TextView) findViewById(R.id.tv_message);
        mLeftBtn = (TextView) findViewById(R.id.btn_positive);
        mMessage.setGravity(Gravity.CENTER);
        mRightBtn = (TextView) findViewById(R.id.btn_negative);
    }

}
