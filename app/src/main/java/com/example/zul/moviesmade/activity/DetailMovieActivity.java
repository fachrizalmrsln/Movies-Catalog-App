package com.example.zul.moviesmade.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zul.moviesmade.R;
import com.example.zul.moviesmade.database.DataHelper;
import com.example.zul.moviesmade.database.DatabaseContract;
import com.example.zul.moviesmade.model.Favorite;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_VOTE_AVERAGE = "extra_vote_average";
    public static final String EXTRA_POPULARITY = "extra_popular";
    public static final String EXTRA_VOTE_COUNT = "extra_vote_count";
    public static final String EXTRA_RELEASE_DATE = "extra_release_date";
    public static final String EXTRA_OVERVIEW = "extra_over_view";
    public static final String EXTRA_POSTER_PATH = "extra_poster_path";
    public static final String EXTRA_BACKDROP_PATH = "extra_backdrop_path";
    private static final String TAG = "DetailMovieActivity";
    private static int found;
    @BindView(R.id.text_name_detail)
    TextView textViewTitle;
    @BindView(R.id.text_vote_average_detail)
    TextView textViewVoteAverage;
    @BindView(R.id.text_popularity_detail)
    TextView textViewPopularity;
    @BindView(R.id.text_vote_count_detail)
    TextView textViewVoteCount;
    @BindView(R.id.text_release_detail)
    TextView textViewReleaseDate;
    @BindView(R.id.text_overview_detail)
    TextView textViewOverView;
    @BindView(R.id.image_detail)
    ImageView imageViewBackDrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;
    private ArrayList<Favorite> mArrayList;
    private String mId;
    private String mTitle;
    private String mVote_average;
    private String mPopularity;
    private String mVote_count;
    private String mRelease_date;
    private String mOverview;
    private String mPosterPath;
    private String mBackdrop_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Log.d(TAG, "onCreate: called");

        ButterKnife.bind(this);
        mContext = this;

        mArrayList = new ArrayList<>();

        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mId = getIntent().getStringExtra(EXTRA_ID);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        mVote_average = getIntent().getStringExtra(EXTRA_VOTE_AVERAGE);
        mPopularity = getIntent().getStringExtra(EXTRA_POPULARITY);
        mVote_count = getIntent().getStringExtra(EXTRA_VOTE_COUNT);
        mRelease_date = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        mOverview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        mPosterPath = getIntent().getStringExtra(EXTRA_POSTER_PATH);
        mBackdrop_path = getIntent().getStringExtra(EXTRA_BACKDROP_PATH);

        textViewTitle.setText(mTitle);
        textViewVoteAverage.setText(mVote_average);
        textViewPopularity.setText(mPopularity);
        textViewVoteCount.setText(mVote_count);
        textViewReleaseDate.setText(mRelease_date);
        textViewOverView.setText(mOverview);
        Glide.with(mContext)
                .asBitmap()
                .load(mBackdrop_path)
                .into(imageViewBackDrop);

        getSomeData(mId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: called");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_favorite, menu);
        setFavorite(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: called");

        switch (item.getItemId()) {
            case R.id.favorite_menu:
                if (found == 0) {
                    insertData();
                    item.setIcon(R.drawable.ic_favorite);
                    Toast.makeText(mContext, "Add to favorite",
                            Toast.LENGTH_SHORT).show();
                    found = 1;
                } else {
                    deleteSomeData(mId);
                    item.setIcon(R.drawable.ic_favorite_border);
                    Toast.makeText(mContext, "Remove from favorite",
                            Toast.LENGTH_SHORT).show();
                    found = 0;
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void insertData() {
        Log.d(TAG, "insertData: called");

        mArrayList.clear();

        DataHelper dataHelper = new DataHelper(mContext);
        dataHelper.open();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.COLUMN_ID, mId);
        values.put(DatabaseContract.COLUMN_TITLE, mTitle);
        values.put(DatabaseContract.COLUMN_VOTE_AVERAGE, mVote_average);
        values.put(DatabaseContract.COLUMN_POPULAR, mPopularity);
        values.put(DatabaseContract.COLUMN_VOTE_COUNT, mVote_count);
        values.put(DatabaseContract.COLUMN_RELEASE_DATE, mRelease_date);
        values.put(DatabaseContract.COLUMN_OVERVIEW, mOverview);
        values.put(DatabaseContract.COLUMN_POSTER_PATH, mPosterPath);
        values.put(DatabaseContract.COLUMN_BACKDROP_PATH, mBackdrop_path);

        dataHelper.insertData(values);
        dataHelper.close();
    }

    private void deleteSomeData(String mId) {
        Log.d(TAG, "deleteSomeData: called");

        DataHelper dataHelper = new DataHelper(mContext);
        dataHelper.open();
        dataHelper.deleteSomeData(mId);
        dataHelper.close();
    }

    private void getSomeData(String mId) {
        Log.d(TAG, "getSomeData: called");

        mArrayList.clear();

        DataHelper dataHelper = new DataHelper(mContext);
        dataHelper.open();

        final Cursor cursor = dataHelper.getSomeData(mId);
        if (cursor.getCount() != 0) {
            Log.d(TAG, "getSomeData: id found");
            while (cursor.moveToNext()) {
                String id = cursor.getString(1);
                String title = cursor.getString(2);
                String vote_average = cursor.getString(3);
                String popular = cursor.getString(4);
                String vote_count = cursor.getString(5);
                String release_date = cursor.getString(6);
                String over_view = cursor.getString(7);
                String poster_path = cursor.getString(8);
                String backdrop_path = cursor.getString(9);
                mArrayList.add(new Favorite(id, title, vote_average, popular, vote_count,
                        release_date, over_view, poster_path, backdrop_path));
                found = 1;
            }
        }else{
            Log.d(TAG, "getSomeData: id not found");
            found = 0;
        }
        dataHelper.close();
    }

    private void setFavorite(Menu menu) {
        Log.d(TAG, "setFavorite: called");
        if (found == 0) {
            Log.d(TAG, "setFavorite: add new favorite");
            menu.getItem(0).setIcon(R.drawable.ic_favorite_border);
        }else {
            Log.d(TAG, "setFavorite: remove favorite");
            menu.getItem(0).setIcon(R.drawable.ic_favorite);
        }
    }

}
