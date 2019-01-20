package com.example.zul.moviesmade.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.zul.moviesmade.R;
import com.example.zul.moviesmade.adapter.FavoriteAdapter;
import com.example.zul.moviesmade.database.DataHelper;
import com.example.zul.moviesmade.model.Favorite;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteFragment extends Fragment {

    private static final String TAG = "FavoriteFragment";
    private Context mContext;
    private ArrayList<Favorite> mArrayList;
    private static boolean isEmpty;
    @BindView(R.id.recycler_view_favorite)
    RecyclerView mRecyclerView;
    @BindView(R.id.relative_empty_favorite)
    RelativeLayout mRelativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        ButterKnife.bind(this, view);

        mArrayList = new ArrayList<>();

        mContext = getContext();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
        inItViews();
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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.smoothScrollToPosition(0);

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
        }else {
            Log.d(TAG, "isEmpty: false");
            mRecyclerView.setVisibility(View.VISIBLE);
            mRelativeLayout.setVisibility(View.GONE);
        }
    }

}
