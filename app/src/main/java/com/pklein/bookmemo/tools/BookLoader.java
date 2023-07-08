package com.pklein.bookmemo.tools;

import android.content.Context;
import androidx.loader.content.CursorLoader;
import android.net.Uri;
import android.util.Log;

import com.pklein.bookmemo.data.BookContract;

public class BookLoader extends CursorLoader {

    private static final String TAG= BookLoader.class.getSimpleName();

    public static BookLoader getSelectedBookfromDatabase(Context context,String subQuery) {
        Log.i(TAG, "getSelectedBookfromDatabase ");
        return new BookLoader(
                context,
                BookContract.BookDb.CONTENT_URI, //uri
                null, //All columns
                subQuery, // WHERE clause
                null, // WHERE clause's arguments
                BookContract.BookDb.COLUMN_TITLE); // sort order
    }

    private BookLoader(Context context, Uri uri, String[] projection,String selection,String[] selectionArg,String sortOrder ) {
        super(context, uri,projection , selection, selectionArg, sortOrder);
    }


}
