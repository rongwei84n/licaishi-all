package com.auts.lcssv.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.auts.lcssv.PhApplication;
import com.auts.lcssv.R;

/**
 * Created by weiming.zeng on 2017/9/27.
 */

public class NotificationHelper {
    private static int id = 1;

    public static void sendNotification(String title, String content) {
        NotificationManager notifyManager = (NotificationManager) PhApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(PhApplication.getAppContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(content)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSubText(" ");
        notifyManager.notify(id++, builder.build());
    }

    public static void sendRemoteNotification(String title, String content) {
        NotificationManager notifyManager = (NotificationManager) PhApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        RemoteViews remoteView = new RemoteViews(PhApplication.getAppContext().getPackageName(), R.layout.view_notification);
        remoteView.setTextViewText(R.id.tv_content, content);
        remoteView.setImageViewResource(R.id.iv_icon, R.mipmap.ic_launcher);
        Intent intent=new Intent();
        PendingIntent mPendingIntent = PendingIntent.getActivity(PhApplication.getAppContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(PhApplication.getAppContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContent(remoteView);
        notifyManager.notify(1, builder.build());
    }
}
