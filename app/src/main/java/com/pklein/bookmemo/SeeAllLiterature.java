package com.pklein.bookmemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SeeAllLiterature extends Fragment {
    private static final String TAG= SeeAllLiterature.class.getSimpleName();

    public static SeeAllLiterature newInstance(/*int page*/) {
        Bundle args = new Bundle();
        //args.putInt(ARG_PAGE, page);
        SeeAllLiterature fragment = new SeeAllLiterature();
        fragment.setArguments(args);
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


        Log.i(TAG, "End MovieInformation");
        return view;
    }
}
