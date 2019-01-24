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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.zul.moviesmade.R;
import com.example.zul.moviesmade.activity.DetailMovieActivity;
import com.example.zul.moviesmade.model.Favorite;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * copyright zul
 **/

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    private static final String TAG = "FavoriteAdapter";

    private Context mContext;
    private ArrayList<Favorite> mFavorite;

    public FavoriteAdapter(Context mContext, ArrayList<Favorite> mFavorite) {
        this.mContext = mContext;
        this.mFavorite = mFavorite;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        Log.d(TAG, "onCreateViewHolder: called");

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_template, viewGroup, false);

        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder favoriteHolder,
                                 @SuppressLint("RecyclerView") final int position) {
        Log.d(TAG, "onBindViewHolder: at position " + position);

        Glide.with(mContext)
                .asBitmap()
                .load(mFavorite.get(position).getPoster_path())
                .apply(new RequestOptions().fitCenter())
                .into(favoriteHolder.imageView);
        favoriteHolder.textViewTitle.setText(mFavorite.get(position).getTitle());
        favoriteHolder.textViewOverview.setText(mFavorite.get(position).getOver_view());

        favoriteHolder.linearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_ID,
                    mFavorite.get(position).getId());
            intent.putExtra(DetailMovieActivity.EXTRA_TITLE,
                    mFavorite.get(position).getTitle());
            intent.putExtra(DetailMovieActivity.EXTRA_VOTE_AVERAGE,
                    mFavorite.get(position).getVote_average());
            intent.putExtra(DetailMovieActivity.EXTRA_POPULARITY,
                    mFavorite.get(position).getPopular());
            intent.putExtra(DetailMovieActivity.EXTRA_VOTE_COUNT,
                    mFavorite.get(position).getVote_count());
            intent.putExtra(DetailMovieActivity.EXTRA_RELEASE_DATE,
                    mFavorite.get(position).getRelease_date());
            intent.putExtra(DetailMovieActivity.EXTRA_OVERVIEW,
                    mFavorite.get(position).getOver_view());
            intent.putExtra(DetailMovieActivity.EXTRA_BACKDROP_PATH,
                    mFavorite.get(position).getBackdrop_path());
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return mFavorite.size();
    }

    class FavoriteHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "FavoriteHolder";
        @BindView(R.id.linear_template)
        LinearLayout linearLayout;
        @BindView(R.id.image_template)
        ImageView imageView;
        @BindView(R.id.text_title_template)
        TextView textViewTitle;
        @BindView(R.id.text_overview_template)
        TextView textViewOverview;

        private FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "FavoriteHolder: called");
            ButterKnife.bind(this, itemView);
        }

    }

}
