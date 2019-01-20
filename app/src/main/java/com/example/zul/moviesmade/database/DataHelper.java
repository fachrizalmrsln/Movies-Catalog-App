package com.example.zul.moviesmade.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataHelper {

    private static final String TAG = "DataHelper";

    private static final String TABLE_NAME = DatabaseContract.TABLE_FAVORITE;
    private static final String ID = DatabaseContract.COLUMN_ID;

    private Context mContext;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DataHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void open() throws SQLException {
        Log.d(TAG, "open: called");
        databaseHelper = new DatabaseHelper(mContext);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close() {
        Log.d(TAG, "close: called");
        databaseHelper.close();
    }

    public long insertData(ContentValues values) {
        Log.d(TAG, "insertData: called");
        return sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    public Cursor getAllData() {
        Log.d(TAG, "getAllData: called");
        return sqLiteDatabase.rawQuery(
                    "SELECT * FROM "
                        + TABLE_NAME + " ORDER BY "
                        + ID + " DESC",
                null
        );
    }

    public Cursor getSomeData(String query) {
        Log.d(TAG, "getSomeData: called");
        return sqLiteDatabase.rawQuery(
                "SELECT * FROM "
                        + TABLE_NAME + " WHERE "
                        + ID + " = "
                        + query,
                null
        );
    }

    public int deleteSomeData(String query) {
        Log.d(TAG, "deleteSomeData: called");
        return sqLiteDatabase.delete(
                TABLE_NAME,
                ID + " = " + query,
                null
        );
    }

    public int updateData(String id, ContentValues contentValues) {
        return sqLiteDatabase.update(TABLE_NAME, contentValues,
                ID + " = ?", new String[]{id});
    }

}
