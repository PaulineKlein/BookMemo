package com.pklein.bookmemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pklein.bookmemo.data.Book;
import com.pklein.bookmemo.data.BookContract;

import java.util.ArrayList;
import java.util.List;

public class SeeAllLiterature extends Fragment {
    private static final String TAG= SeeAllLiterature.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private SeeAllAdapter mSeeAllAdapter;
    private LinearLayoutManager mLayoutManager;
    private String mLiterature_Type;


    public static SeeAllLiterature newInstance(String literature_Type) {
        Bundle args = new Bundle();
        SeeAllLiterature fragment = new SeeAllLiterature();
        fragment.setArguments(args);
        fragment.mLiterature_Type = literature_Type;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(TAG, "Start SeeAllLiterature");
        View view = inflater.inflate(R.layout.see_all_literature, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview_seeAll);
        mLoadingIndicator = view.findViewById(R.id.loading_indicator_seeAll);
        mErrorMessageDisplay = view.findViewById(R.id.tv_error_message_display_seeAll);

        mLayoutManager= new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mSeeAllAdapter = new SeeAllAdapter();
        mRecyclerView.setAdapter(mSeeAllAdapter);

        insertBook();
        List<Book> listOfBooks = getBookfromDatabase();

        if (listOfBooks != null) {
            showLitteratureListView();
            mSeeAllAdapter.setBookData(listOfBooks);
        } else {
            showErrorMessage();
        }

        Log.i(TAG, "End MovieInformation");
        return view;
    }

    private void showLitteratureListView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private List<Book> getBookfromDatabase(){
        Log.i(TAG, "getBookfromDatabase ");

        List<Book> ListBook = new ArrayList<>();
        Uri uri = BookContract.BookDb.CONTENT_URI;
        String[] projection = null; // we want all columns return
        String selection =BookContract.BookDb.COLUMN_TYPE + "='" + mLiterature_Type + "'"; // we want to filter by the type of book
        String sortOrder = BookContract.BookDb.COLUMN_TITLE;

        Cursor cursor = getActivity().getContentResolver().query(uri,projection,selection,null,sortOrder );

        if (cursor !=null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                Book book = new Book();
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


        Log.i(TAG, "end getBookfromDatabase ");
        return ListBook;
    }

    private void insertBook(){
        ContentValues values = new ContentValues();
        values.put(BookContract.BookDb.COLUMN_TITLE, "mon livre 2 !!");
        values.put(BookContract.BookDb.COLUMN_AUTHOR, "moi");
        values.put(BookContract.BookDb.COLUMN_DESC, "desc");
        values.put(BookContract.BookDb.COLUMN_TYPE, BookContract.TYPE_LITERATURE);
        values.put(BookContract.BookDb.COLUMN_YEAR, 1990);
        values.put(BookContract.BookDb.COLUMN_BOUGHT, 1);
        values.put(BookContract.BookDb.COLUMN_FINISH, 0);
        values.put(BookContract.BookDb.COLUMN_TOME, 1);
        values.put(BookContract.BookDb.COLUMN_CHAPTER, 0);
        values.put(BookContract.BookDb.COLUMN_EPISODE, 0);
        values.put(BookContract.BookDb.COLUMN_FAVORITE,0);

        Log.i(TAG, values.toString());

        Uri uri =BookContract.BookDb.CONTENT_URI;
        try {
            Uri result = getActivity().getContentResolver().insert(uri, values);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

    }
}
