package com.example.zul.moviesmade.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.zul.moviesmade.database.DataHelper;
import com.example.zul.moviesmade.database.DatabaseContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * copyright zul
 **/

public class Provider extends ContentProvider {

    public static final String AUTHORITY = "com.example.zul.submission_4";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(DatabaseContract.TABLE_FAVORITE)
            .build();
    private static final String TAG = "Provider";
    private static final int DATA = 0;
    private static final int DATA_BY_ID = 1;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVORITE, DATA);
        uriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVORITE + "/#", DATA_BY_ID);
    }

    private DataHelper dataHelper;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: called");
        Context mContext = getContext();
        dataHelper = new DataHelper(mContext);
        dataHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Log.d(TAG, "query: called");
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case DATA:
                cursor = dataHelper.getAllData();
                break;
            case DATA_BY_ID:
                cursor = dataHelper.getSomeData(selection);
                break;
            default:
                cursor = null;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert: called");
        long insert;
        switch (uriMatcher.match(uri)) {
            case DATA:
                insert = dataHelper.insertData(values);
                break;
            default:
                insert = 0;
        }
        return Uri.parse(CONTENT_URI + "/" + insert);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete: called");
        int deleted;
        switch (uriMatcher.match(uri)) {
            case DATA_BY_ID:
                deleted = dataHelper.deleteSomeData(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update: called");
        return dataHelper.updateData(uri.getLastPathSegment(), values);
    }

}
