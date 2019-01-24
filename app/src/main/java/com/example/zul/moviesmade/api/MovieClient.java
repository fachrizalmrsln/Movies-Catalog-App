package com.example.zul.moviesmade.api;

import android.util.Log;

import com.example.zul.moviesmade.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * copyright zul
 **/

public class MovieClient {

    private static final String TAG = "MovieClient";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        Log.d(TAG, "getRetrofit: called");

        if (retrofit == null) {
            Log.d(TAG, "getRetrofit: created new retrofit");
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else
            Log.d(TAG, "getRetrofit: reused previous retrofit");

        return retrofit;

    }

}
