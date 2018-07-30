package com.pklein.bookmemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
import com.pklein.bookmemo.tools.BookDbTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeeAllLiterature extends Fragment {
    private static final String TAG= SeeAllLiterature.class.getSimpleName();

    @BindView(R.id.recyclerview_seeAll) RecyclerView mRecyclerView;
    @BindView(R.id.tv_error_message_display_seeAll) TextView mErrorMessageDisplay;
    @BindView(R.id.loading_indicator_seeAll) ProgressBar mLoadingIndicator;

    private SeeAllAdapter mSeeAllAdapter;
    private LinearLayoutManager mLayoutManager;
    private String mLiterature_Type;

    private Parcelable mSavedRecyclerViewState;
    private static final String RECYCLER_STATE = "recycler";
    private static final String LIFECYCLE_BOOK_FILTER_KEY = "filter";

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
        Log.i(TAG, "Start onCreateView");
        View view = inflater.inflate(R.layout.see_all_literature, container, false);
        ButterKnife.bind(this, view);

        mLayoutManager= new LinearLayoutManager(getActivity().getApplicationContext());

        if (savedInstanceState != null) { // if a filter is already saved, read it
            if (savedInstanceState.containsKey(LIFECYCLE_BOOK_FILTER_KEY)) {
                mLiterature_Type = savedInstanceState.getString(LIFECYCLE_BOOK_FILTER_KEY);
            }
        }

        Log.i(TAG, "End onCreateView");
        return view;
    }
    @Override
    public void onStart() {
        Log.i(TAG, "Start onStart");
        super.onStart();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mSeeAllAdapter = new SeeAllAdapter();
        mRecyclerView.setAdapter(mSeeAllAdapter);

        BookDbTool bookDbTool = new BookDbTool();
        String subquery = BookContract.BookDb.COLUMN_TYPE + "='" + mLiterature_Type + "'";
        List<Book> listOfBooks = bookDbTool.getSelectedBookfromDatabase(subquery, getActivity().getContentResolver());

        if (listOfBooks != null) {
            showLitteratureListView();
            mSeeAllAdapter.setBookData(listOfBooks);
        } else {
            showErrorMessage();
        }
        Log.i(TAG, "End onStart");
    }

    private void showLitteratureListView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setText(R.string.error_found);
    }

    // with the help of https://inthecheesefactory.com/blog/fragment-state-saving-best-practices/en
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save filter + recyclerview position :
        outState.putString(LIFECYCLE_BOOK_FILTER_KEY, mLiterature_Type);
        outState.putParcelable(RECYCLER_STATE,mLayoutManager.onSaveInstanceState());
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)  {
        super.onActivityCreated(savedInstanceState);
        //with the help of https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll-position-using-recyclerview-state
        if (savedInstanceState != null) {
            mSavedRecyclerViewState = savedInstanceState.getParcelable(RECYCLER_STATE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSavedRecyclerViewState != null) {
            mLayoutManager.onRestoreInstanceState(mSavedRecyclerViewState);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mSavedRecyclerViewState = mLayoutManager.onSaveInstanceState();
    }
}
