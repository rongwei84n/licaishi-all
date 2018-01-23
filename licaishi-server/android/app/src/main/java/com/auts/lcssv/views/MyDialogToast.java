package com.auts.lcssv.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.auts.lcssv.R;


/**
 * Created by dongchang.tang on 2017/9/13.
 */

public class MyDialogToast extends Dialog {

    private TextView mTvMsg;

    private String mToastMsg;

    private Context mContext;

    protected MyDialogToast(Context context, String message) {
        super(context, R.style.DialogStyle);
        mContext = context;
        mToastMsg = message;
        initView();
    }

    private void initView() {
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_my_toast);
        mTvMsg = (TextView) findViewById(R.id.tv_message);
        mTvMsg.setText(mToastMsg);
    }

    /**
     * 调用此方法显示dialog
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void showToast(Context context, String message, final int duration) {
        final MyDialogToast dialogToast = new MyDialogToast(context, message);
        dialogToast.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dialogToast.dismiss();
            }
        }).start();
    }
}
