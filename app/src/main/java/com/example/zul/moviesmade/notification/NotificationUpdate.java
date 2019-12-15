package com.example.zul.moviesmade.notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.zul.moviesmade.BuildConfig;
import com.example.zul.moviesmade.api.MovieClient;
import com.example.zul.moviesmade.api.MovieService;
import com.example.zul.moviesmade.model.Response;
import com.example.zul.moviesmade.model.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * copyright zul
 **/

public class NotificationUpdate extends BroadcastReceiver {

    private static final String TAG = "NotificationUpdate";
    private static final int HOUR_OF_DAY = 8;
    private static final int MINUTE = 0;
    private static final int SECOND = 0;
    private static final String ID = "id";
    private static final String TITLE = "title";
    private List<Result> mArrayList = new ArrayList<>();
    private List<Result> mMovieName = new ArrayList<>();
    private int id;
    private int delay;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: called");
        id = intent.getIntExtra(ID, 0);
        String title = intent.getStringExtra(TITLE);
        setChannelUpdate(context, id, title);
    }

    public void setChannelUpdate(Context context, int id, String title) {
        Log.d(TAG, "setChannelUpdate: called");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelUpdateNotification(id, title);
        notificationHelper.getNotificationManager().notify(id, nb.build());
    }

    public void setUpdateNotification(Context context, List<Result> results) {
        Log.d(TAG, "setUpdateNotification: called");

        for (Result result : results) {
            Log.d(TAG, "setUpdateNotification: called");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);
            calendar.set(Calendar.MINUTE, MINUTE);
            calendar.set(Calendar.SECOND, SECOND);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, NotificationUpdate.class);
            intent.putExtra(ID, id);
            intent.putExtra(TITLE, result.getTitle());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    id,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() + delay,
                    AlarmManager.INTERVAL_DAY, pendingIntent);

            id = id + 1;
            delay = delay + 2000;
            Log.d(TAG, "called movie " + id + " " + result.getTitle());
        }
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

    public void checkUpComingMovie(Context context) {
        Log.d(TAG, "checkUpComingMovie: called");

        MovieService mApiMovieService = MovieClient.getRetrofit()
                .create(MovieService.class);

        Call<Response> responseCall = mApiMovieService
                .getUpComingMovie(
                        BuildConfig.API_KEY,
                        "en-US"
                );

        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call,
                                   @NonNull retrofit2.Response<Response> response) {
                Log.d(TAG, "onResponse: called");

                if (response.body() != null) {
                    Log.d(TAG, "onResponse: data in api not null");

                    List<Result> results = response.body().getResults();

                    mArrayList.clear();
                    mArrayList.addAll(results);

                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String dateNow = simpleDateFormat.format(date);
                    mMovieName.clear();
                    for (Result result : mArrayList) {
                        Log.d(TAG, "called for");
                        if (result.getReleaseDate().equals(dateNow)) {
                            Log.d(TAG, " checking");
                            mMovieName.add(result);
                            Log.d(TAG, "found movie: " + mMovieName.size());
                        } else {
                            Log.d(TAG, "not find movie this day");
                        }
                    }
                    setUpdateNotification(context, mMovieName);
                } else
                    Log.d(TAG, "onResponse: data in api not found");
            }

            @Override
            public void onFailure(@NonNull Call<Response> call,
                                  @NonNull Throwable t) {
                Log.d(TAG, "onFailure: called");
            }
        });

    }

}
