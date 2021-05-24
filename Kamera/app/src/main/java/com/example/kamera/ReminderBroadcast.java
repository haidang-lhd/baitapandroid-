package com.example.kamera;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifi")
                .setTimeoutAfter(60)
                .setSmallIcon(R.drawable.ic_baseline_camera_enhance_24)
                .setContentTitle("Remind you to Selfiee")
                .setContentText("hãy chụp hình")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notiManager= NotificationManagerCompat.from(context);
        notiManager.notify(200,builder.build());
    }
}
