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
import com.pklein.bookmemo.tools.BookDbTool;

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

        BookDbTool bookDbTool = new BookDbTool();
        String subquery = BookContract.BookDb.COLUMN_TYPE + "='" + mLiterature_Type + "'";
        List<Book> listOfBooks = bookDbTool.getSelectedBookfromDatabase(subquery, getActivity().getContentResolver());

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
}