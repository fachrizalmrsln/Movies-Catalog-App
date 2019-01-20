package com.example.app2.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app2.R;
import com.example.app2.activity.DetailMovieActivity;
import com.example.app2.model.Favorite;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    private static final String TAG = "FavoriteAdapter";

    private Context mContext;
    private Cursor mCursor;

    public FavoriteAdapter(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    private Favorite getItem(int position) {
        Log.d(TAG, "getItem: called");
        if (!mCursor.moveToPosition(position))
            throw new IllegalArgumentException("Position " + position + " not found");
        else
            return new Favorite(mCursor);
    }

    public void newData(Cursor mCursor) {
        Log.d(TAG, "newData: called");
        this.mCursor = mCursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        Log.d(TAG, "onCreateViewHolder: called");

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_template, viewGroup, false);

        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder favoriteHolder,
                                 int position) {
        Log.d(TAG, "onBindViewHolder: at position " + position);
        favoriteHolder.bindView(getItem(position));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        if (mCursor.getCount() == 0)
            return 0;
        else
            return mCursor.getCount();
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

        void bindView(final Favorite mFavorite) {
            Log.d(TAG, "bindView: called");

            Glide.with(mContext)
                    .asBitmap()
                    .load(mFavorite.getPoster_path())
                    .into(imageView);
            textViewTitle.setText(mFavorite.getTitle());
            textViewOverview.setText(mFavorite.getOver_view());

            linearLayout.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_TITLE,
                        mFavorite.getTitle());
                intent.putExtra(DetailMovieActivity.EXTRA_VOTE_AVERAGE,
                        mFavorite.getVote_average());
                intent.putExtra(DetailMovieActivity.EXTRA_POPULARITY,
                        mFavorite.getPopular());
                intent.putExtra(DetailMovieActivity.EXTRA_VOTE_COUNT,
                        mFavorite.getVote_count());
                intent.putExtra(DetailMovieActivity.EXTRA_RELEASE_DATE,
                        mFavorite.getRelease_date());
                intent.putExtra(DetailMovieActivity.EXTRA_OVERVIEW,
                        mFavorite.getOver_view());
                intent.putExtra(DetailMovieActivity.EXTRA_BACKDROP_PATH,
                        mFavorite.getBackdrop_path());
                mContext.startActivity(intent);
            });

        }

    }

}
