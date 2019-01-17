package com.example.zul.moviesmade.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zul.moviesmade.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_VOTE_AVERAGE = "extra_vote_average";
    public static final String EXTRA_POPULARITY = "extra_popular";
    public static final String EXTRA_VOTE_COUNT = "extra_vote_count";
    public static final String EXTRA_RELEASE_DATE = "extra_release_date";
    public static final String EXTRA_OVERVIEW = "extra_over_view";
    public static final String EXTRA_BACKDROP_PATH = "extra_backdrop_path";
    private static final String TAG = "DetailMovieActivity";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Log.d(TAG, "onCreate: called");

        ButterKnife.bind(this);
        Context mContext = this;

        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        String mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        String mVote_average = getIntent().getStringExtra(EXTRA_VOTE_AVERAGE);
        String mPopularity = getIntent().getStringExtra(EXTRA_POPULARITY);
        String mVote_count = getIntent().getStringExtra(EXTRA_VOTE_COUNT);
        String mRelease_date = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        String mOverview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String mBackdrop_path = getIntent().getStringExtra(EXTRA_BACKDROP_PATH);

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
    }

}
