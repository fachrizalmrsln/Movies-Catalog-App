package com.example.zul.moviesmade.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "favorite_movie.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = DatabaseContract.TABLE_FAVORITE;
    private static final String PRIMARY_KEY = DatabaseContract.COLUMN_PRIMARY_KEY;
    private static final String ID = DatabaseContract.COLUMN_ID;
    private static final String TITLE = DatabaseContract.COLUMN_TITLE;
    private static final String VOTE_AVERAGE = DatabaseContract.COLUMN_VOTE_AVERAGE;
    private static final String RELEASE_DATE = DatabaseContract.COLUMN_RELEASE_DATE;
    private static final String OVERVIEW = DatabaseContract.COLUMN_OVERVIEW;
    private static final String POPULAR = DatabaseContract.COLUMN_POPULAR;
    private static final String VOTE_COUNT = DatabaseContract.COLUMN_VOTE_COUNT;
    private static final String POSTER_PATH = DatabaseContract.COLUMN_POSTER_PATH;
    private static final String BACKDROP_PATH = DatabaseContract.COLUMN_BACKDROP_PATH;

    DatabaseHelper(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: called");
        db.execSQL("CREATE TABLE " + TABLE_NAME
                +
                "("
                + PRIMARY_KEY + " INTEGER PRIMARY KEY NOT NULL, "
                + ID + " TEXT NOT NULL, "
                + TITLE + " TEXT NOT NULL, "
                + VOTE_AVERAGE + " TEXT NOT NULL, "
                + RELEASE_DATE + " TEXT NOT NULL, "
                + OVERVIEW + " TEXT NOT NULL, "
                + POPULAR + " TEXT NOT NULL, "
                + VOTE_COUNT + " TEXT NOT NULL, "
                + POSTER_PATH + " TEXT NOT NULL, "
                + BACKDROP_PATH + " TEXT NOT NULL"
                +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: called");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

}