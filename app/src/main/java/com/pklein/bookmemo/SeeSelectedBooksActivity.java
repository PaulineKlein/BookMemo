package com.pklein.bookmemo;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pklein.bookmemo.data.Book;
import com.pklein.bookmemo.data.BookContract;
import com.pklein.bookmemo.tools.BookDbTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeeSelectedBooksActivity extends AppCompatActivity {

    private static final String TAG= SeeSelectedBooksActivity.class.getSimpleName();
    @BindView(R.id.recyclerview_seeAll) RecyclerView mRecyclerView;
    @BindView(R.id.tv_error_message_display_seeAll) TextView mErrorMessageDisplay;
    @BindView(R.id.loading_indicator_seeAll) ProgressBar mLoadingIndicator;
    private SeeAllAdapter mSeeAllAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Start SeeSelectedBooksActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_all_literature);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        String title_search = b.getString("title_search");
        String author_search = b.getString("author_search");
        String type_search = b.getString("type_search");
        int year_search = b.getInt("year_search");
        int finish_search = b.getInt("finish_search");
        int bought_search = b.getInt("bought_search");
        int chapter_search = b.getInt("chapter_search");
        int episode_search = b.getInt("episode_search");
        int favorite_search = b.getInt("favorite_search");

        mLayoutManager= new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mSeeAllAdapter = new SeeAllAdapter();
        mRecyclerView.setAdapter(mSeeAllAdapter);

        BookDbTool bookDbTool = new BookDbTool();
        String subquery = bookDbTool.constructSubQuery(title_search, author_search, type_search,year_search,finish_search, bought_search, chapter_search, episode_search, favorite_search);
        List<Book> listOfBooks = bookDbTool.getSelectedBookfromDatabase(subquery, this.getContentResolver());

        if (listOfBooks != null) {
            showLitteratureListView();
            mSeeAllAdapter.setBookData(listOfBooks);
        } else {
            showErrorMessage();
        }

        Log.i(TAG, "End SeeSelectedBooksActivity");
    }

    private void showLitteratureListView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
