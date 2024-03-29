package com.example.zul.moviesmade.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.zul.moviesmade.R;
import com.example.zul.moviesmade.activity.DetailMovieActivity;
import com.example.zul.moviesmade.model.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * copyright zul
 **/

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private static final String TAG = "MovieAdapter";

    private Context mContext;
    private ArrayList<Result> mResults;

    public MovieAdapter(Context mContext, ArrayList<Result> mResults) {
        this.mContext = mContext;
        this.mResults = mResults;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        Log.d(TAG, "onCreateViewHolder: called");

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_template, viewGroup, false);

        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder,
                                 @SuppressLint("RecyclerView") final int position) {
        Log.d(TAG, "onBindViewHolder: at position " + position);

        Glide.with(mContext)
                .asBitmap()
                .load(mResults.get(position).getPosterPath())
                .apply(new RequestOptions().fitCenter())
                .into(movieHolder.imageView);

        movieHolder.textViewTitle.setText(mResults.get(position).getTitle());

        if (mResults.get(position).getOverview().length() < 4)
            mResults.get(position).setOverview("Tidak tersedia dalam Bahasa Indonesia");

        movieHolder.textViewOverview.setText(mResults.get(position).getOverview());

        String releaseDate = mResults.get(position).getReleaseDate();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(releaseDate);
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            releaseDate = simpleDateFormat.format(date);
            mResults.get(position).setReleaseDate(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        movieHolder.linearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_ID, mResults.get(position).getId());
            intent.putExtra(DetailMovieActivity.EXTRA_TITLE,
                    mResults.get(position).getTitle());
            intent.putExtra(DetailMovieActivity.EXTRA_VOTE_AVERAGE,
                    Double.toString(mResults.get(position).getVoteAverage()));
            intent.putExtra(DetailMovieActivity.EXTRA_POPULARITY,
                    Double.toString(mResults.get(position).getPopularity()));
            intent.putExtra(DetailMovieActivity.EXTRA_VOTE_COUNT,
                    Integer.toString(mResults.get(position).getVoteCount()));
            intent.putExtra(DetailMovieActivity.EXTRA_RELEASE_DATE,
                    mResults.get(position).getReleaseDate());
            intent.putExtra(DetailMovieActivity.EXTRA_OVERVIEW,
                    mResults.get(position).getOverview());
            intent.putExtra(DetailMovieActivity.EXTRA_POSTER_PATH,
                    mResults.get(position).getPosterPath());
            intent.putExtra(DetailMovieActivity.EXTRA_BACKDROP_PATH,
                    mResults.get(position).getBackdropPath());
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return mResults.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "FavoriteHolder";
        @BindView(R.id.linear_template)
        LinearLayout linearLayout;
        @BindView(R.id.image_template)
        ImageView imageView;
        @BindView(R.id.text_title_template)
        TextView textViewTitle;
        @BindView(R.id.text_overview_template)
        TextView textViewOverview;

        private MovieHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "FavoriteHolder: called");
            ButterKnife.bind(this, itemView);
        }

    }

}
