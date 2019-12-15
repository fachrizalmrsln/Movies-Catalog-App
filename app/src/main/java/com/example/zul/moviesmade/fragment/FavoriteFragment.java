package com.example.zul.moviesmade.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zul.moviesmade.R;
import com.example.zul.moviesmade.adapter.FavoriteAdapter;
import com.example.zul.moviesmade.database.DataHelper;
import com.example.zul.moviesmade.model.Favorite;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * copyright zul
 **/

public class FavoriteFragment extends Fragment {

    private static final String TAG = "FavoriteFragment";
    private static final String RECYCLER_LAYOUT_STATE = "bundle_recycler_state";
    private static boolean isEmpty;
    private static boolean state;
    @BindView(R.id.recycler_view_favorite)
    RecyclerView mRecyclerView;
    @BindView(R.id.relative_empty_favorite)
    RelativeLayout mRelativeLayout;
    private Context mContext;
    private ArrayList<Favorite> mArrayList;
    private Parcelable savedRecyclerLayoutState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        ButterKnife.bind(this, view);

        mArrayList = new ArrayList<>();

        mContext = getContext();

        if (!state)
            savedInstanceState = null;

        if (savedInstanceState != null) {
            Log.d(TAG, "onCreateView: state not null");

            savedRecyclerLayoutState = savedInstanceState.getParcelable(RECYCLER_LAYOUT_STATE);
            setRecyclerView();
        } else {
            Log.d(TAG, "onCreateView: state is null");

            inItViews();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
        inItViews();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: called");

        if (getView() != null && mRecyclerView.getLayoutManager() != null) {
            Log.d(TAG, "onSaveInstanceState: state saved");
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
        setRecyclerView();
        getAllData();
        isEmpty();
    }

    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView: called");

        FavoriteAdapter mAdapter = new FavoriteAdapter(mContext, mArrayList);

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

    private void getAllData() {
        Log.d(TAG, "getAllData: called");

        mArrayList.clear();

        DataHelper dataHelper = new DataHelper(mContext);
        dataHelper.open();

        final Cursor cursor = dataHelper.getAllData();
        if (cursor.getCount() != 0) {
            Log.d(TAG, "getAllData: data found");
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String title = cursor.getString(1);
                String vote_average = cursor.getString(3);
                String popular = cursor.getString(3);
                String vote_count = cursor.getString(4);
                String release_date = cursor.getString(5);
                String over_view = cursor.getString(6);
                String poster_path = cursor.getString(7);
                String backdrop_path = cursor.getString(8);
                mArrayList.add(new Favorite(id, title, vote_average, popular, vote_count,
                        release_date, over_view, poster_path, backdrop_path));
            }
            isEmpty = false;
        } else {
            Log.d(TAG, "getAllData: data empty");
            isEmpty = true;
        }
    }

    private void isEmpty() {
        Log.d(TAG, "isEmpty: called");
        if (isEmpty) {
            Log.d(TAG, "isEmpty: true");
            mRecyclerView.setVisibility(View.GONE);
            mRelativeLayout.setVisibility(View.VISIBLE);
        } else {
            Log.d(TAG, "isEmpty: false");
            mRecyclerView.setVisibility(View.VISIBLE);
            mRelativeLayout.setVisibility(View.GONE);
        }
    }

}
