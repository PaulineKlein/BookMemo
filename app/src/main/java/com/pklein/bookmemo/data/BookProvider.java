package com.pklein.bookmemo.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class BookProvider extends ContentProvider {

    private static final String TAG= BookProvider.class.getSimpleName();
    /* This class has been set up thanks to code from https://github.com/udacity/android-content-provider */
    private static final UriMatcher mUriMatcher = buildUriMatcher();
    private BookDbHelper mBookDbHelper;

    /* Codes for the UriMatcher */
    private static final int ALL_BOOK = 100;
    private static final int BOOK_WITH_TITLE = 101;


    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BookContract.CONTENT_AUTHORITY;

        // TYPE of URI :
        matcher.addURI(authority, BookContract.BookDb.TABLE_NAME, ALL_BOOK);
        matcher.addURI(authority, BookContract.BookDb.TABLE_NAME + "/#", BOOK_WITH_TITLE);

        return matcher;
    }


    @Override
    public boolean onCreate() {

        mBookDbHelper = new BookDbHelper(getContext());
        return true;
    }


    @Override
    public String getType(Uri uri){
        final int match = mUriMatcher.match(uri);

        switch (match){
            case ALL_BOOK:{
                return BookContract.BookDb.CONTENT_DIR_TYPE;
            }
            case BOOK_WITH_TITLE:{
                return BookContract.BookDb.CONTENT_ITEM_TYPE;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        Cursor retCursor;
        switch(mUriMatcher.match(uri)){
            // All Book selected
            case ALL_BOOK:{
                retCursor = mBookDbHelper.getReadableDatabase().query(
                        BookContract.BookDb.TABLE_NAME,                 // The table to query
                        projection,                                     // The columns to return
                        selection,                                      // The columns for the WHERE clause
                        selectionArgs,                                  // The values for the WHERE clause
                        null,                                   // don't group the rows
                        null,                                    // don't filter by row groups
                        sortOrder);                                    // The sort order
                return retCursor;
            }
            // Individual Book based on title selected
            case BOOK_WITH_TITLE:{
                retCursor = mBookDbHelper.getReadableDatabase().query(
                        BookContract.BookDb.TABLE_NAME,                         // The table to query
                        projection,                                                             // The columns to return
                        BookContract.BookDb.COLUMN_TITLE + " = ?",   // The columns for the WHERE clause
                        new String[] {String.valueOf(ContentUris.parseId(uri))},                // The values for the WHERE clause
                        null,                                                           // don't group the rows
                        null,                                                            // don't filter by row groups
                        sortOrder);                                                             // The sort order
                return retCursor;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        Log.i(TAG, "insert ");
        final SQLiteDatabase db = mBookDbHelper.getWritableDatabase();
        Uri returnUri =null;
        switch (mUriMatcher.match(uri)) {
            case ALL_BOOK: {
                long _id = db.insertOrThrow(BookContract.BookDb.TABLE_NAME, null, values);
                //---if added successfully---
                if (_id > 0) {
                    returnUri = BookContract.BookDb.buildBookUri((int)_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        final SQLiteDatabase db = mBookDbHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        int numDeleted;
        switch(match){
            case ALL_BOOK:
                numDeleted = db.delete(BookContract.BookDb.TABLE_NAME, selection, selectionArgs);
                break;
            case BOOK_WITH_TITLE:
                numDeleted = db.delete(BookContract.BookDb.TABLE_NAME,
                        BookContract.BookDb.COLUMN_TITLE + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs){
        final SQLiteDatabase db = mBookDbHelper.getWritableDatabase();
        int numUpdated = 0;

        if (contentValues == null){
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch(mUriMatcher.match(uri)){
            case ALL_BOOK:{
                numUpdated = db.update(BookContract.BookDb.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case BOOK_WITH_TITLE: {
                numUpdated = db.update(BookContract.BookDb.TABLE_NAME,
                        contentValues,
                        BookContract.BookDb.COLUMN_TITLE + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }
}
