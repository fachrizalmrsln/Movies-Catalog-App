package com.example.zul.moviesmade.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import com.example.zul.moviesmade.R;
import com.example.zul.moviesmade.activity.MainActivity;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final int REQUEST_CODE_DAILY = 1;
    public static final int REQUEST_CODE_UPDATE = 2;
    private static final String TAG = "NotificationHelper";
    private static final String CHANNEL_DAILY = "Channel Daily";
    private static final String CHANNEL_UPDATE = "Channel Update";
    private final String ID_CHANNEL_DAILY = "ID_CHANNEL_DAILY";
    private final String ID_CHANNEL_UPDATE = "ID_CHANNEL_UPDATE";
    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
        createChannelDaily();
        createChannelUpdate();
    }

    public void createChannelDaily() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "createChannelDaily: created");
            NotificationChannel notificationChannelDaily = new NotificationChannel(
                    ID_CHANNEL_DAILY, CHANNEL_DAILY, NotificationManager.IMPORTANCE_HIGH);
            notificationChannelDaily.enableLights(true);
            notificationChannelDaily.enableVibration(true);
            notificationChannelDaily.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationChannelDaily.setLightColor(R.color.colorPrimary);
            notificationChannelDaily.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getNotificationManager().createNotificationChannel(notificationChannelDaily);
        }
    }

    public void createChannelUpdate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "createChannelUpdate: created");
            NotificationChannel notificationChannelUpdate = new NotificationChannel(
                    ID_CHANNEL_UPDATE, CHANNEL_UPDATE, NotificationManager.IMPORTANCE_HIGH);
            notificationChannelUpdate.enableLights(true);
            notificationChannelUpdate.enableVibration(true);
            notificationChannelUpdate.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationChannelUpdate.setLightColor(R.color.colorPrimary);
            notificationChannelUpdate.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getNotificationManager().createNotificationChannel(notificationChannelUpdate);
        }
    }

    public NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            Log.d(TAG, "getNotificationManager: created new NotificationManager");
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getChannelDailyNotification() {
        Log.d(TAG, "getChannelDailyNotification: called");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                REQUEST_CODE_DAILY, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), ID_CHANNEL_DAILY)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(getResources().getString(R.string.daily_reminder))
                .setContentText(getResources().getString(R.string.daily_content))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setChannelId(ID_CHANNEL_DAILY)
                .setContentIntent(pendingIntent);
    }

    public NotificationCompat.Builder getChannelUpdateNotification(int id, String title) {
        Log.d(TAG, "getChannelUpdateNotification: called " + id + " " + title);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), ID_CHANNEL_UPDATE)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(title)
                .setContentText(getResources().getString(R.string.update_content))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }

}
