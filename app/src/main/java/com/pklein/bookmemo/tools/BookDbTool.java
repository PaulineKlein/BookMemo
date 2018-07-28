package com.pklein.bookmemo.tools;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.pklein.bookmemo.SeeAllAdapter;
import com.pklein.bookmemo.data.Book;
import com.pklein.bookmemo.data.BookContract;

import java.util.ArrayList;
import java.util.List;


public class BookDbTool {

    private static final String TAG= SeeAllAdapter.class.getSimpleName();

    public BookDbTool() {
    }

    public String constructSubQuery(String title, String author, String type,int year,int finish, int bought, int chapter, int episode, int favorite){
        String res = "";
        String str1 = "";
        String str2 = "";
        String str3 = "";
        String str4 = "";
        String str5 = "";
        String str6 = "";
        String str7 = "";
        String str8 = "";
        String str9 = "";

        if((title != null && !title.equals("")) || (author != null && !author.equals("")) || (type != null && !type.equals("")) || year !=0 || finish !=-1 || bought !=-1 || chapter !=-1 || episode !=-1 || favorite !=1)
        {
            res = "BEGIN";

            if(title != null && !title.equals("")) {
                title = title.replace("'","%");
                str1 += "AND " + BookContract.BookDb.COLUMN_TITLE + " LIKE '%" + title + "%' ";
            }
            if(author != null && !author.equals("")) {
                author = author.replace("'","%");
                str2 += "AND " + BookContract.BookDb.COLUMN_AUTHOR + " LIKE '%" + author + "%' ";
            }
            if(type != null && !type.equals(""))
                str3+= "AND "+BookContract.BookDb.COLUMN_TYPE+" = '"+ type +"' ";
            if(year !=-1)
                str4+= "AND "+BookContract.BookDb.COLUMN_YEAR+" = "+ year +" ";
            if(finish !=-1)
                str5+= "AND "+BookContract.BookDb.COLUMN_FINISH+" = "+ finish +" ";
            if(bought !=-1)
                str6+= "AND "+BookContract.BookDb.COLUMN_BOUGHT+" = "+ bought +" ";
            if(chapter !=-1)
            {
                if(chapter ==0) // don't display chapters
                    str7+= "AND "+BookContract.BookDb.COLUMN_CHAPTER+" = 0 ";
                else if(chapter ==1) // display chapter
                    str7+= "AND "+BookContract.BookDb.COLUMN_CHAPTER+" != 0 ";
            }
            if(episode !=-1)
            {
                if(episode ==0) // don't display anime
                    str8+= "AND "+BookContract.BookDb.COLUMN_EPISODE+" = 0 ";
                else if(episode ==1) // display anime
                    str8+= "AND "+BookContract.BookDb.COLUMN_EPISODE+" != 0 ";
            }
            if(favorite !=-1)
            {
                if(favorite ==0) // don't display favorite
                    str9+= "AND "+BookContract.BookDb.COLUMN_FAVORITE+" = 0 ";
                else if(favorite ==1) // display favorite
                    str9+= "AND "+BookContract.BookDb.COLUMN_FAVORITE+" != 0 ";
            }

            res+=str1+str2+str3+str4+str5+str6+str7+str8+str9;
            res = res.replace("BEGINAND", "");
            res = res.replace("BEGIN", "");
        }
        Log.w(TAG, "res = "+res);

        return res;
    }

    public List<Book> getSelectedBookfromDatabase(String subQuery, ContentResolver contentResolver){
        Log.i(TAG, "getSelectedBookfromDatabase ");

        List<Book> ListBook = new ArrayList<>();
        Uri uri = BookContract.BookDb.CONTENT_URI;
        String[] projection = null; // we want all columns return
        String selection =subQuery;
        String sortOrder = BookContract.BookDb.COLUMN_TITLE;

        Cursor cursor = contentResolver.query(uri,projection,selection,null,sortOrder );

        if (cursor !=null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                Book book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndex(BookContract.BookDb.COLUMN_ID)));
                book.setTitle(cursor.getString(cursor.getColumnIndex(BookContract.BookDb.COLUMN_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndex(BookContract.BookDb.COLUMN_AUTHOR)));
                book.setDesc(cursor.getString(cursor.getColumnIndex(BookContract.BookDb.COLUMN_DESC)));
                book.setType(cursor.getString(cursor.getColumnIndex(BookContract.BookDb.COLUMN_TYPE)));
                book.setYear(cursor.getInt(cursor.getColumnIndex(BookContract.BookDb.COLUMN_YEAR)));
                book.setBought(cursor.getInt(cursor.getColumnIndex(BookContract.BookDb.COLUMN_BOUGHT)));
                book.setFinish(cursor.getInt(cursor.getColumnIndex(BookContract.BookDb.COLUMN_FINISH)));
                book.setTome(cursor.getInt(cursor.getColumnIndex(BookContract.BookDb.COLUMN_TOME)));
                book.setChapter(cursor.getInt(cursor.getColumnIndex(BookContract.BookDb.COLUMN_CHAPTER)));
                book.setEpisode(cursor.getInt(cursor.getColumnIndex(BookContract.BookDb.COLUMN_EPISODE)));
                book.setFavorite(cursor.getInt(cursor.getColumnIndex(BookContract.BookDb.COLUMN_FAVORITE)));

                Log.i(TAG, book.getTitle());

                ListBook.add(book);
                cursor.moveToNext();
            }
            cursor.close();
        }

        Log.i(TAG, "end getSelectedBookfromDatabase ");
        return ListBook;
    }

    public void insertBook(ContentResolver contentResolver,String title, String author, String desc, String type,int year,int finish, int bought, int chapter, int tome, int episode, int favorite) throws Exception {
        ContentValues values = new ContentValues();
        values.put(BookContract.BookDb.COLUMN_TITLE, title);
        values.put(BookContract.BookDb.COLUMN_AUTHOR, author);
        values.put(BookContract.BookDb.COLUMN_DESC, desc);
        values.put(BookContract.BookDb.COLUMN_TYPE, type);
        values.put(BookContract.BookDb.COLUMN_YEAR, year);
        values.put(BookContract.BookDb.COLUMN_BOUGHT, bought);
        values.put(BookContract.BookDb.COLUMN_FINISH, finish);
        values.put(BookContract.BookDb.COLUMN_TOME, tome);
        values.put(BookContract.BookDb.COLUMN_CHAPTER, chapter);
        values.put(BookContract.BookDb.COLUMN_EPISODE, episode);
        values.put(BookContract.BookDb.COLUMN_FAVORITE, favorite);

        Log.i(TAG, values.toString());

        Uri uri =BookContract.BookDb.CONTENT_URI;
        try {
            Uri result = contentResolver.insert(uri, values);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
            throw new Exception("insert Error");
        }
    }

    public void updateOneColumn(ContentResolver contentResolver,int id, String Column, int ColumnValue) throws Exception{
       // Uri uri =BookContract.BookDb.CONTENT_URI;
        Uri uri = BookContract.BookDb.buildBookUri(id);
        try {
            ContentValues values = new ContentValues();
            values.put(Column, ColumnValue);
            //use the id of the book to build the URI, and then to update it
            int result = contentResolver.update(uri, values,null,null);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
            throw new Exception("update Error");
        }
    }
}
