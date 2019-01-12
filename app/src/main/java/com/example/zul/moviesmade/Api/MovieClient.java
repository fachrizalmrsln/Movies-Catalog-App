package com.example.zul.moviesmade.Api;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieClient {

    private static final String TAG = "MovieClient";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        Log.d(TAG, "getRetrofit: called");

        if (retrofit == null) {
            Log.d(TAG, "getRetrofit: created new retrofit");
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else
            Log.d(TAG, "getRetrofit: reused previous retrofit");

        return retrofit;

    }

}
