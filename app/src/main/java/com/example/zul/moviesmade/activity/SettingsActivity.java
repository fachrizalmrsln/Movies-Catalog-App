package com.example.zul.moviesmade.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zul.moviesmade.R;
import com.example.zul.moviesmade.notification.NotificationDaily;
import com.example.zul.moviesmade.notification.NotificationPreference;
import com.example.zul.moviesmade.notification.NotificationUpdate;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * copyright zul
 **/

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    @BindView(R.id.current_language)
    TextView mTextViewCurrentLanguage;
    @BindView(R.id.linear_change_language)
    LinearLayout mLinearChangeLanguage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switch_1)
    Switch aSwitchDaily;
    @BindView(R.id.switch_2)
    Switch aSwitchUpdate;
    private Context mContext;
    private NotificationPreference notificationPreference;
    private NotificationDaily notificationDaily;
    private NotificationUpdate notificationUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "onCreate: called");

        ButterKnife.bind(this);
        mContext = this;

        notificationPreference = new NotificationPreference(this);
        notificationDaily = new NotificationDaily();
        notificationUpdate = new NotificationUpdate();

        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_settings);

        checkLanguage();

        checkChecked();

        mLinearChangeLanguage.setOnClickListener(v -> {
            Intent intentLanguage = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intentLanguage);
        });

        aSwitchDaily.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Log.d(TAG, "onCreate: DailyReminder set on");
                notificationDaily.setDailyNotification(mContext);
                Toast.makeText(mContext, R.string.toastDaily,
                        Toast.LENGTH_SHORT).show();
                notificationPreference.setDailyPref(true);
            } else {
                Log.d(TAG, "onCreate: DailyReminder set off");
                notificationDaily.cancelDailyNotification(mContext);
                notificationPreference.setDailyPref(false);
            }
        });

        aSwitchUpdate.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                Log.d(TAG, "onCreate: DailyUpdate set on");
                notificationUpdate.checkUpComingMovie(mContext);
                Toast.makeText(mContext, R.string.toastUpdate,
                        Toast.LENGTH_SHORT).show();
                notificationPreference.setUpdatePref(true);
            } else {
                Log.d(TAG, "onCreate: DailyUpdate set off");
                notificationUpdate.cancelUpdateNotification(mContext);
                notificationPreference.setUpdatePref(false);
            }
        }));

    }

    private void checkLanguage() {
        Log.d(TAG, "checkLanguage: called");

        String mCurrentLanguage = Locale.getDefault().getLanguage();

        if (mCurrentLanguage.equals("in"))
            mTextViewCurrentLanguage.setText(R.string.current_language);
        else
            mTextViewCurrentLanguage.setText(R.string.current_language);
    }

    private void checkChecked() {
        Log.d(TAG, "checkChecked: called");

        boolean checkedDaily = notificationPreference.getDailyPref();
        boolean checkedUpdate = notificationPreference.getUpdatePref();

        if (checkedDaily)
            aSwitchDaily.setChecked(true);
        else
            aSwitchDaily.setChecked(false);

        if (checkedUpdate)
            aSwitchUpdate.setChecked(true);
        else
            aSwitchUpdate.setChecked(false);

    }

    @Override
    public void finish() {
        super.finish();
        Log.d(TAG, "finish: called");
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
