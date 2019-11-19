package com.example.cameraapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

class NotificationUtil {

    public void createNotification(Context context, Class targetClass, String title, String content, int icon) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "")
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(content);

        Intent notificationIntent;
        if (targetClass != null) {
            notificationIntent = new Intent(context, targetClass);
        } else {
            notificationIntent = new Intent();
        }

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}
