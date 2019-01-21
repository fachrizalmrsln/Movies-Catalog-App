package com.example.zul.moviesmade.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class UpComingFragment extends Fragment {

    private static final String TAG = "UpComingFragment";
    private static final String RECYCLER_LAYOUT_STATE = "bundle_recycler_state";
    private static String LIST_STATE = "list_state";
    private static boolean state;
    @BindView(R.id.progress_bar_fragment_up_coming)
    ProgressBar mProgressBar;
    @BindView(R.id.swipe_fragment_up_coming)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view_up_coming)
    RecyclerView mRecyclerView;
    private Context mContext;
    private ArrayList<Result> mArrayList;
    private String mLanguage;
    private Parcelable savedRecyclerLayoutState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);

        ButterKnife.bind(this, view);

        mContext = getContext();

        mArrayList = new ArrayList<>();

        mProgressBar.setVisibility(View.GONE);

        if (!state)
            savedInstanceState = null;

        if (savedInstanceState != null) {
            Log.d(TAG, "onCreateView: state not null");

            mArrayList = savedInstanceState.getParcelableArrayList(LIST_STATE);
            savedRecyclerLayoutState = savedInstanceState.getParcelable(RECYCLER_LAYOUT_STATE);
            setRecyclerView();
        } else {
            Log.d(TAG, "onCreateView: state is null");

            inItViews();
        }


        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            inItViews();
            mSwipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: called");

        if (getView() != null && mRecyclerView.getLayoutManager() != null) {
            Log.d(TAG, "onSaveInstanceState: state saved");
            outState.putParcelableArrayList(LIST_STATE, mArrayList);
            outState.putParcelable(RECYCLER_LAYOUT_STATE,
                    mRecyclerView.getLayoutManager().onSaveInstanceState());
            state = true;
        } else {
            Log.d(TAG, "onSaveInstanceState: null state saved");
            state = false;
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: called");

        if (!state)
            savedInstanceState = null;

        if (savedRecyclerLayoutState == null) {
            if (savedInstanceState != null) {
                Log.d(TAG, "onViewStateRestored: state not null");
                mArrayList = savedInstanceState.getParcelableArrayList(LIST_STATE);
                savedRecyclerLayoutState = savedInstanceState.getParcelable(RECYCLER_LAYOUT_STATE);
            } else
                Log.d(TAG, "onViewStateRestored: state is null");
        }
    }

    private void restoreLayoutManagerPosition() {
        Log.d(TAG, "restoreLayoutManagerPosition: called");

        if (savedRecyclerLayoutState != null && mRecyclerView.getLayoutManager() != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }

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

        if (mContext.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        } else
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        restoreLayoutManagerPosition();

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
