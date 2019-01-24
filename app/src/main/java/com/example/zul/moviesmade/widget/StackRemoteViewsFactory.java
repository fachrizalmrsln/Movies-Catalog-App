package com.example.zul.moviesmade.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.example.zul.moviesmade.R;
import com.example.zul.moviesmade.database.DataHelper;
import com.example.zul.moviesmade.model.Favorite;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * copyright zul
 **/

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "StackRemoteViewsFactory";
    private Context mContext;
    private ArrayList<Favorite> mArrayList;

    StackRemoteViewsFactory(Context mContext, Intent mIntent) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: called");

    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged: called");

        getFavorite();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt: current position " + position);

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_item);

        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(mArrayList.get(position).getBackdrop_path())
                    .submit()
                    .get();
            remoteViews.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void getFavorite() {
        Log.d(TAG, "getFavorite: called");

        mArrayList = new ArrayList<>();

        DataHelper dataHelper = new DataHelper(mContext);
        dataHelper.open();

        final Cursor cursor = dataHelper.getAllData();
        if (cursor.getCount() != 0) {
            Log.d(TAG, "getAllData: data found");
            while (cursor.moveToNext()) {
                String backdrop_path = cursor.getString(8);
                mArrayList.add(new Favorite(backdrop_path));
            }
        } else
            Log.d(TAG, "getFavorite: no data found");
        dataHelper.close();
    }

}
