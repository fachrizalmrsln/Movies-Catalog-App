package com.example.app2.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.app2.R;
import com.example.app2.adapter.FavoriteAdapter;
import com.example.app2.provider.ContractProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static boolean isEmpty;
    @BindView(R.id.recycler_view_favorite)
    RecyclerView mRecyclerView;
    @BindView(R.id.relative_empty_favorite)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;
    private FavoriteAdapter mAdapter;
    private Cursor mCursorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: called");

        ButterKnife.bind(this);
        mContext = this;

        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_main);

        inItViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

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

        mAdapter = new FavoriteAdapter(mContext, mCursorList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.smoothScrollToPosition(0);

        mAdapter.notifyDataSetChanged();
    }

    private void getAllData() {
        Log.d(TAG, "getAllData: called");

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
        }else
            isEmpty = true;
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
