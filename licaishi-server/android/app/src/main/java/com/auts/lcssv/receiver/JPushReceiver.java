package com.auts.lcssv.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.auts.lcssv.PhApplication;
import com.auts.lcssv.activity.MainActivity;
import com.auts.lcssv.util.SpfUtils;
import com.auts.lcssv.views.NotificationHelper;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

//import cn.jpush.android.api.JPushInterface;

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
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            boolean isAllowPush = (boolean) SpfUtils.get("isAllowPush", true);
            if (PhApplication.isBackGround() && isAllowPush) { //App处于后台时才弹出消息
                String title = bundle.getString(JPushInterface.EXTRA_TITLE);
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//保存服务器推送下来的附加字段。这是个 JSON 字符串。对应 API 消息内容的 extras 字段。
                String file = bundle.getString(JPushInterface.EXTRA_MSG_ID);//唯一标识消息的 ID
//                NotificationHelper.sendNotification(title, message);
                NotificationHelper.sendRemoteNotification(title, message);
            } else {
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            Intent i = new Intent(context, MainActivity.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }


}
