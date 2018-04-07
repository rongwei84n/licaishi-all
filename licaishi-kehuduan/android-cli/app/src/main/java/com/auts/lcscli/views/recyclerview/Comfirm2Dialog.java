package com.auts.lcscli.views.recyclerview;

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

public class Comfirm2Dialog extends Dialog {
    private TextView mMessage;
    private TextView mPositiveBtn;
    private int type = 0;

    public Comfirm2Dialog(Context context, String message, String comfirmText, final DialogOnClickListener leftListener) {
        super(context);
        initView();
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
                    leftListener.onClickListener(Comfirm2Dialog.this, v);
                } else {
                    dismiss();
                }
            }
        });
    }

    public void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_comfirm2);
        mMessage = (TextView) findViewById(R.id.tv_message);
        mPositiveBtn = (TextView) findViewById(R.id.btn_positive);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
