package com.example.app2.provider;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.app2.database.DatabaseContract;

public class ContractProvider {

    private static final String TAG = "ContractProvider";
    private static final String AUTHORITY = "com.example.zul.submission_4";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(DatabaseContract.TABLE_FAVORITE)
            .build();

    public static String getColumnString(Cursor cursor, String column) {
        Log.d(TAG, "getColumnString: called");
        return cursor.getString(cursor.getColumnIndex(column));
    }

}
