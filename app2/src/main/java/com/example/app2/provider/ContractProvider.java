package com.example.app2.provider;

import android.database.Cursor;
import android.net.Uri;

import com.example.app2.database.DatabaseContract;

public class ContractProvider {

    public static final String AUTHORITY = "com.example.zul.submission_4";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(DatabaseContract.TABLE_FAVORITE)
            .build();

    public static String getColumnString(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

}
