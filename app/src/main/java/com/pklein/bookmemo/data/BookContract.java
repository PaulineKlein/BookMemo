package com.pklein.bookmemo.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class BookContract {

    public static final String TYPE_LITERATURE = "LITERATURE";
    public static final String TYPE_MANGA = "MANGA";
    public static final String TYPE_COMIC = "COMIC";

    public static final String CONTENT_AUTHORITY = "com.pklein.bookmemo.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class BookDb implements BaseColumns {
        public static final String TABLE_NAME = "Book";
        public static final String COLUMN_TYPE = "type"; // manga, book, comic
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_YEAR = "year"; // published Year
        public static final String COLUMN_BOUGHT = "bought"; // bought = 1, not bought = 0
        public static final String COLUMN_FINISH = "finish"; // finished = 1, pending =0
        public static final String COLUMN_TOME = "tome"; // last number of tome read
        public static final String COLUMN_CHAPTER = "chapter"; // last number of chapter read
        public static final String COLUMN_EPISODE = "episode"; // last number of anime's episode seen
        public static final String COLUMN_DESC = "desc"; // description
        public static final String COLUMN_FAVORITE = "favorite"; // favorite = 1, not favorite = 0

        // create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        // for building URIs on insertion
        public static Uri buildBookUri(int id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
