package com.pklein.bookmemo;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pklein.bookmemo.data.Book;
import com.pklein.bookmemo.tools.BookDbTool;

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
    private Book mbookToLookFor;

    private Parcelable mSavedRecyclerViewState;
    private static final String RECYCLER_STATE = "recycler";
    private static final String LIFECYCLE_BOOK_FILTER_KEY = "filter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Start SeeSelectedBooksActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_all_literature);
        ButterKnife.bind(this);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mSeeAllAdapter = new SeeAllAdapter();
        mRecyclerView.setAdapter(mSeeAllAdapter);

        if (savedInstanceState != null) { // if a filter is already saved, read it
            if (savedInstanceState.containsKey(LIFECYCLE_BOOK_FILTER_KEY)) {
                mbookToLookFor = savedInstanceState.getParcelable(LIFECYCLE_BOOK_FILTER_KEY);
                loadBook(mbookToLookFor);
            }
            else { showErrorMessage(); }
        } else {
            if(this.getIntent().hasExtra("BookToLookFor")) {
                mbookToLookFor = this.getIntent().getExtras().getParcelable("BookToLookFor");
                loadBook(mbookToLookFor);
            }
            else { showErrorMessage(); }
        }

        Log.i(TAG, "End SeeSelectedBooksActivity");
    }

    private void loadBook(Book bookToLookFor){
        BookDbTool bookDbTool = new BookDbTool();
        String subquery = bookDbTool.constructSubQuery(bookToLookFor.getTitle(), bookToLookFor.getAuthor(), bookToLookFor.getType(), bookToLookFor.getYear(), bookToLookFor.getFinish(), bookToLookFor.getBought(),  bookToLookFor.getChapter(), bookToLookFor.getEpisode(), bookToLookFor.getFavorite());
        List<Book> listOfBooks = bookDbTool.getSelectedBookfromDatabase(subquery, this.getContentResolver());

        if (listOfBooks != null) {
            showLitteratureListView();
            mSeeAllAdapter.setBookData(listOfBooks);
        } else {
            showErrorMessage();
        }
    }

    private void showLitteratureListView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save filter + recyclerview position :
        outState.putParcelable(LIFECYCLE_BOOK_FILTER_KEY, mbookToLookFor);
        outState.putParcelable(RECYCLER_STATE,mLayoutManager.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //It will restore recycler view at same position
        if (savedInstanceState != null) {
            mSavedRecyclerViewState = savedInstanceState.getParcelable(RECYCLER_STATE);
        }
    }
}
