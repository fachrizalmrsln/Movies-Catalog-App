package com.example.zul.moviesmade.notification;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationPreference {

    private static final String KeyDaily = "KeyDaily";
    private static final String KeyUpdate = "KeyUpdate";
    private SharedPreferences sharedPreferences;

    public NotificationPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("NotificationPreference",
                Context.MODE_PRIVATE);
    }

    public Boolean getDailyPref() {
        return sharedPreferences.getBoolean(KeyDaily, false);
    }

    public void setDailyPref(boolean isTrueOr) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KeyDaily, isTrueOr);
        editor.apply();
    }

    public Boolean getUpdatePref() {
        return sharedPreferences.getBoolean(KeyUpdate, false);
    }

    public void setUpdatePref(boolean isTrueOr) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KeyUpdate, isTrueOr);
        editor.apply();
    }

}
