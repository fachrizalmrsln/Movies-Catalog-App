package com.example.zul.moviesmade.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zul.moviesmade.R;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageActivity extends AppCompatActivity {

    private static final String TAG = "LanguageActivity";
    @BindView(R.id.current_language)
    TextView mTextViewCurrentLanguage;
    @BindView(R.id.linear_change_language)
    LinearLayout mLinearChangeLanguage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Log.d(TAG, "onCreate: called");

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_language);

        checkLanguage();

        mLinearChangeLanguage.setOnClickListener(v -> {
            Intent intentLanguage = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intentLanguage);
        });

    }

    private void checkLanguage() {
        Log.d(TAG, "checkLanguage: called");

        String mCurrentLanguage = Locale.getDefault().getLanguage();

        if (mCurrentLanguage.equals("in"))
            mTextViewCurrentLanguage.setText(R.string.current_language);
        else
            mTextViewCurrentLanguage.setText(R.string.current_language);
    }

    @Override
    public void finish() {
        super.finish();
        Log.d(TAG, "finish: called");
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
