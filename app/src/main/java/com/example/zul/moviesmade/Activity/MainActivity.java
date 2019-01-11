package com.example.zul.moviesmade.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zul.moviesmade.Adapter.MovieAdapter;
import com.example.zul.moviesmade.Api.MovieClient;
import com.example.zul.moviesmade.Api.MovieService;
import com.example.zul.moviesmade.BuildConfig;
import com.example.zul.moviesmade.Model.Result;
import com.example.zul.moviesmade.Model.Response;
import com.example.zul.moviesmade.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context mContext;
    private ProgressBar mProgressBar;

    private RecyclerView mRecyclerView;
    private EditText mEditTextSearch;
    private ArrayList<Result> mArrayList = new ArrayList<>();

    private String mLanguage;
    private String mMovieSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: called");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_search);

        mContext = this;

        mRecyclerView = findViewById(R.id.recycler_view_main);
        mEditTextSearch = findViewById(R.id.et_search_main);
        mProgressBar = findViewById(R.id.progress_bar_main);

        mProgressBar.setVisibility(View.GONE);

        mLanguage = "en-US";

        Button mButtonSearch = findViewById(R.id.btn_search_main);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked");

                mMovieSearch = mEditTextSearch.getText().toString().trim();

                if (mMovieSearch.isEmpty())
                    mEditTextSearch.setError("Search something");
                else {

                    setRecyclerView();
                    getSearchedMovie(mMovieSearch);

                }
            }
        });

    }

    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView: called");

        MovieAdapter mAdapter = new MovieAdapter(mContext, mArrayList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.smoothScrollToPosition(0);

        mAdapter.notifyDataSetChanged();

        mProgressBar.setVisibility(View.GONE);

    }

    private void getSearchedMovie(final String search) {
        Log.d(TAG, "getSearchedMovie: called (getting data from api)");

        mProgressBar.setVisibility(View.VISIBLE);

        MovieService mApiMovieService = MovieClient.getRetrofit().create(MovieService.class);
        Call<Response> responseCall =
                mApiMovieService.getSearchedMovie(BuildConfig.ApiKey, mLanguage, search);
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call,
                                   @NonNull retrofit2.Response<Response> response) {
                Log.d(TAG, "onResponse: called");

                if (response.body() != null) {
                    Log.d(TAG, "onResponse.body: not null");

                    List<Result> results = response.body().getResults();

                    mArrayList.clear();
                    mArrayList.addAll(results);
                    setRecyclerView();

                    if (results.isEmpty()) {
                        Log.d(TAG, "onResponse: not found");
                        Toast.makeText(mContext, search + " not found",
                                Toast.LENGTH_SHORT).show();
                    }

                    mProgressBar.setVisibility(View.GONE);

                } else {
                    Log.d(TAG, "onResponse.body: not found");
                    Toast.makeText(mContext, search + " not found",
                            Toast.LENGTH_SHORT).show();

                    mProgressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(@NonNull Call<Response> call,
                                  @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t);

                Toast.makeText(mContext, "Bad Internet Connection",
                        Toast.LENGTH_SHORT).show();

            }

        });

    }

}