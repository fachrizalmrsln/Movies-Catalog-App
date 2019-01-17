package com.example.zul.moviesmade.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class UpComingFragment extends Fragment {

    private static final String TAG = "UpComingFragment";
    @BindView(R.id.progress_bar_fragment_up_coming)
    ProgressBar mProgressBar;
    @BindView(R.id.swipe_fragment_up_coming)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view_up_coming)
    RecyclerView mRecyclerView;
    private Context mContext;
    private ArrayList<Result> mArrayList = new ArrayList<>();
    private String mLanguage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);

        ButterKnife.bind(this, view);

        mContext = getContext();

        mProgressBar.setVisibility(View.GONE);

        inItViews();

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            inItViews();
            mSwipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }

    private void inItViews() {
        Log.d(TAG, "inItViews: called");
        checkLanguage();
        getUpComingMovie();
    }

    private void checkLanguage() {
        Log.d(TAG, "checkLanguage: called");

        String mCurrentLanguage = Locale.getDefault().getLanguage();

        if (mCurrentLanguage.equals("in"))
            mLanguage = "id-ID";
        else
            mLanguage = "en-US";
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
    }

    private void getUpComingMovie() {
        Log.d(TAG, "getUpComingMovie: called");

        mProgressBar.setVisibility(View.VISIBLE);

        MovieService mApiMovieService = MovieClient.getRetrofit()
                .create(MovieService.class);

        Call<Response> responseCall = mApiMovieService
                .getUpComingMovie(
                        BuildConfig.API_KEY,
                        mLanguage
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
                    setRecyclerView();
                } else {
                    Log.d(TAG, "onResponse.body: data in api not found");
                    Toast.makeText(mContext, "Something went wrong with the api",
                            Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
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
