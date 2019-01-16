package com.example.zul.moviesmade.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zul.moviesmade.BuildConfig;
import com.example.zul.moviesmade.R;
import com.example.zul.moviesmade.adapter.MovieAdapter;
import com.example.zul.moviesmade.api.MovieClient;
import com.example.zul.moviesmade.api.MovieService;
import com.example.zul.moviesmade.model.Response;
import com.example.zul.moviesmade.model.Result;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context mContext;
    private ArrayList<Result> mArrayList = new ArrayList<>();
    private String mLanguage;
    private String mMovieSearch;

    @BindView(R.id.recycler_view_main)
    RecyclerView mRecyclerView;
    @BindView(R.id.et_search_main)
    EditText mEditTextSearch;
    @BindView(R.id.progress_bar_main)
    ProgressBar mProgressBar;
    @BindView(R.id.btn_search_main)
    Button mButtonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: called");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_search);

        ButterKnife.bind(this);
        mContext = this;

        mProgressBar.setVisibility(View.GONE);

        mLanguage = "en-US";

        mButtonSearch.setOnClickListener(v -> {
            Log.d(TAG, "onClick: clicked");

            mMovieSearch = mEditTextSearch.getText().toString().trim();

            if (mMovieSearch.isEmpty())
                mEditTextSearch.setError("Search something");
            else {
                setRecyclerView();
                getSearchedMovie(mMovieSearch);
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

        MovieService mApiMovieService = MovieClient.getRetrofit()
                .create(MovieService.class);

        Call<Response> responseCall = mApiMovieService
                .getSearchedMovie(
                        BuildConfig.API_KEY,
                        mLanguage,
                        search
                );

        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call,
                                   @NonNull retrofit2.Response<Response> response) {
                Log.d(TAG, "onResponse: called");

                if (response.body() != null) {
                    Log.d(TAG, "onResponse.body: data in api not null");

                    List<Result> results = response.body().getResults();

                    mArrayList.clear();
                    mArrayList.addAll(results);
                    setRecyclerView();

                    if (results.isEmpty()) {
                        Log.d(TAG, "onResponse: searched movie not found");
                        Toast.makeText(mContext, search + " not found",
                                Toast.LENGTH_SHORT).show();
                    }

                    mProgressBar.setVisibility(View.GONE);

                } else {
                    Log.d(TAG, "onResponse.body: data in api not found");
                    Toast.makeText(mContext, "Something went wrong with the api",
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