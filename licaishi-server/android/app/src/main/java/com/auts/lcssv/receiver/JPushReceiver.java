package com.auts.lcssv.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by weiming.zeng on 2017/9/20.
 */

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushReceiver";

    public JPushReceiver() {
//        EventBus.getDefault().register(PhApplication.getPhApplication());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    }
}
