package com.example.zul.moviesmade.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import androidx.core.app.NotificationCompat;

public class NotificationUpdate extends BroadcastReceiver {

    private static final String TAG = "NotificationUpdate";
    private static final int HOUR_OF_DAY = 8;
    private static final int MINUTE = 0;
    private static final int SECOND = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: called");
        setChannelUpdate(context);
    }

    public void setChannelUpdate(Context context) {
        Log.d(TAG, "setChannelUpdate: called");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelUpdateNotification();
        notificationHelper.getNotificationManager().notify(NotificationHelper.REQUEST_CODE_UPDATE,
                nb.build());
    }

    public void setUpdateNotification(Context context) {
        Log.d(TAG, "setUpdateNotification: called");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);
        calendar.set(Calendar.MINUTE, MINUTE);
        calendar.set(Calendar.SECOND, SECOND);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationUpdate.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                NotificationHelper.REQUEST_CODE_UPDATE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancelUpdateNotification(Context context) {
        Log.d(TAG, "cancelUpdateNotification: called");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationUpdate.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                NotificationHelper.REQUEST_CODE_UPDATE,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.cancel(pendingIntent);
    }

}
