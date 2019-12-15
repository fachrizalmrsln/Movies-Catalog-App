package com.example.app2.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Binder;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app2.R;
import com.example.app2.adapter.FavoriteAdapter;
import com.example.app2.provider.ContractProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * copyright zul
 **/

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String RECYCLER_LAYOUT_STATE = "bundle_recycler_state";
    private static boolean isEmpty;
    private static boolean state;
    @BindView(R.id.recycler_view_favorite)
    RecyclerView mRecyclerView;
    @BindView(R.id.relative_empty_favorite)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;
    private FavoriteAdapter mAdapter;
    private Cursor mCursorList;
    private Parcelable savedRecyclerLayoutState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: called");

        ButterKnife.bind(this);
        mContext = this;

        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_main);

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        inItViews();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: called");

        if (getResources() != null && mRecyclerView.getLayoutManager() != null) {
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
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: called");

        if (!state)
            savedInstanceState = null;

        if (savedRecyclerLayoutState == null) {
            if (savedInstanceState != null) {
                Log.d(TAG, "onRestoreInstanceState: state not null");
                savedRecyclerLayoutState = savedInstanceState.getParcelable(RECYCLER_LAYOUT_STATE);
            } else
                Log.d(TAG, "onRestoreInstanceState: state is null");
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

        mAdapter = new FavoriteAdapter(mContext, mCursorList);

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

        final long identifyToken = Binder.clearCallingIdentity();

        mCursorList = null;

        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(
                ContractProvider.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        mCursorList = cursor;
        mAdapter.addData(cursor);

        if (mCursorList != null) {
            if (mCursorList.getCount() != 0) {
                Log.d(TAG, "getAllData: data found");
                isEmpty = false;
            } else {
                Log.d(TAG, "getAllData: data not found");
                isEmpty = true;
            }
        } else
            isEmpty = true;

        Binder.restoreCallingIdentity(identifyToken);
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
