package com.example.zul.moviesmade.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zul.moviesmade.Activity.DetailMovieActivity;
import com.example.zul.moviesmade.Model.Result;
import com.example.zul.moviesmade.R;

import java.util.ArrayList;

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
                .into(movieHolder.imageView);

        movieHolder.textViewTitle.setText(mResults.get(position).getTitle());
        movieHolder.textViewOverview.setText(mResults.get(position).getOverview());

        movieHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailMovieActivity.class);
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
                intent.putExtra(DetailMovieActivity.EXTRA_BACKDROP_PATH,
                        mResults.get(position).getBackdropPath());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayout;
        private ImageView imageView;
        private TextView textViewTitle;
        private TextView textViewOverview;

        private MovieHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linear_template);
            imageView = itemView.findViewById(R.id.image_template);
            textViewTitle = itemView.findViewById(R.id.text_title_template);
            textViewOverview = itemView.findViewById(R.id.text_overview_template);

        }

    }

}
