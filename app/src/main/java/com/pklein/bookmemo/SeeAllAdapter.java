package com.pklein.bookmemo;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pklein.bookmemo.data.Book;

import java.util.ArrayList;
import java.util.List;


public class SeeAllAdapter extends RecyclerView.Adapter<SeeAllAdapter.SeeAllAdapterViewHolder> {

    private static final String TAG= SeeAllAdapter.class.getSimpleName();
    private List<Book> mBookData = new ArrayList<>();

    public SeeAllAdapter() {
    }

    public class SeeAllAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView TV_title;
        public final TextView TV_number;
        public final TextView TV_author;
        public final TextView TV_chapter;
        public final TextView TV_year;
        public final TextView TV_comment;
        public final TextView TV_episode;

        public SeeAllAdapterViewHolder(View view) {
            super(view);
            TV_title = view.findViewById(R.id.TV_title);
            TV_number = view.findViewById(R.id.TV_number);
            TV_author = view.findViewById(R.id.TV_author);
            TV_chapter = view.findViewById(R.id.TV_chapter);
            TV_year = view.findViewById(R.id.TV_year);
            TV_comment = view.findViewById(R.id.TV_comment);
            TV_episode = view.findViewById(R.id.TV_episode);
        }
    }

    @Override
    public SeeAllAdapter.SeeAllAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.book;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new SeeAllAdapter.SeeAllAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder( SeeAllAdapter.SeeAllAdapterViewHolder seeAllAdapterViewHolder, int position) {

        final Book BookSelected = mBookData.get(position);

        Log.i(TAG,"titre : "+BookSelected.getTitle() );
        Log.i(TAG,"position : "+position );

        seeAllAdapterViewHolder.TV_title.setText(BookSelected.getTitle());
        seeAllAdapterViewHolder.TV_number.setText(String.valueOf(BookSelected.getTome()));
        seeAllAdapterViewHolder.TV_author.setText(BookSelected.getAuthor());
        seeAllAdapterViewHolder.TV_chapter.setText(String.valueOf(BookSelected.getChapter()));
        seeAllAdapterViewHolder.TV_year.setText(String.valueOf(BookSelected.getYear()));
        seeAllAdapterViewHolder.TV_comment.setText(BookSelected.getDesc());
        seeAllAdapterViewHolder.TV_episode.setText(String.valueOf(BookSelected.getEpisode()));

    }

    @Override
    public int getItemCount() {
        if (null == mBookData) return 0;
        return mBookData.size();
    }

    public void setBookData(List<Book> bookData) {
        mBookData = bookData;
        notifyDataSetChanged();
    }
}
