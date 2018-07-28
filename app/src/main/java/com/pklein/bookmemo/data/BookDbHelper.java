package com.pklein.bookmemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookDbHelper extends SQLiteOpenHelper {

    private static final String TAG= BookDbHelper.class.getSimpleName();

    // The Database name
    private static final String DATABASE_NAME = "Book.db";

    // If we change the Database schema, we must increment the Database version
    private static final int DATABASE_VERSION = 1;

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create the table
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + BookContract.BookDb.TABLE_NAME + " (" +
                BookContract.BookDb.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BookContract.BookDb.COLUMN_TITLE + " TEXT NOT NULL UNIQUE, " +
                BookContract.BookDb.COLUMN_TYPE + " TEXT NOT NULL, " +
                BookContract.BookDb.COLUMN_AUTHOR + " TEXT NULL, " +
                BookContract.BookDb.COLUMN_YEAR + " INTEGER DEFAULT 0, " +
                BookContract.BookDb.COLUMN_BOUGHT + " INTEGER DEFAULT 0, " +
                BookContract.BookDb.COLUMN_FINISH + " INTEGER DEFAULT 0, " +
                BookContract.BookDb.COLUMN_TOME + " INTEGER DEFAULT 0, " +
                BookContract.BookDb.COLUMN_CHAPTER + " INTEGER DEFAULT 0, " +
                BookContract.BookDb.COLUMN_EPISODE + " INTEGER DEFAULT 0, " +
                BookContract.BookDb.COLUMN_DESC + " TEXT NULL, " +
                BookContract.BookDb.COLUMN_FAVORITE + " INTEGER DEFAULT 0 " +
                ");";

        Log.i(TAG,SQL_CREATE_WAITLIST_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w(TAG, "Upgrading database from version " + i + " to " + i1 + ". OLD DATA WILL BE DESTROYED");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BookContract.BookDb.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
