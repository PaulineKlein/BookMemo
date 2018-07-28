package com.pklein.bookmemo;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog.Builder;
import android.widget.Toast;

import com.pklein.bookmemo.data.Book;
import com.pklein.bookmemo.data.BookContract;
import com.pklein.bookmemo.tools.BookDbTool;

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
        public final ImageView iv_star;
        public final ImageView iv_mofify;

        public SeeAllAdapterViewHolder(View view) {
            super(view);
            TV_title = view.findViewById(R.id.TV_title);
            TV_number = view.findViewById(R.id.TV_number);
            TV_author = view.findViewById(R.id.TV_author);
            TV_chapter = view.findViewById(R.id.TV_chapter);
            TV_year = view.findViewById(R.id.TV_year);
            TV_comment = view.findViewById(R.id.TV_comment);
            TV_episode = view.findViewById(R.id.TV_episode);
            iv_star = view.findViewById(R.id.star);
            iv_mofify = view.findViewById(R.id.update_bt);
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

        // SET VALUE
        seeAllAdapterViewHolder.TV_title.setText(BookSelected.getTitle());
        seeAllAdapterViewHolder.TV_author.setText(BookSelected.getAuthor());
        seeAllAdapterViewHolder.TV_comment.setText(BookSelected.getDesc());

        if(BookSelected.getTome() != 0){
            seeAllAdapterViewHolder.TV_number.setText(String.valueOf(BookSelected.getTome()));
        }
        if(BookSelected.getChapter() != 0){
            seeAllAdapterViewHolder.TV_chapter.setText(String.valueOf(BookSelected.getChapter()));
        }
        if(BookSelected.getYear() != 0){
            seeAllAdapterViewHolder.TV_year.setText(String.valueOf(BookSelected.getYear()));
        }
        if(BookSelected.getEpisode() != 0){
            seeAllAdapterViewHolder.TV_episode.setText(String.valueOf(BookSelected.getEpisode()));
        }

        //SET COLOR in cased it is finished
        if (BookSelected.getFinish()!=0)
        {
            seeAllAdapterViewHolder.TV_number.setTextColor(seeAllAdapterViewHolder.itemView.getContext().getResources().getColor(R.color.colorGold));
            seeAllAdapterViewHolder.TV_chapter.setTextColor(seeAllAdapterViewHolder.itemView.getContext().getResources().getColor(R.color.colorGold));
            seeAllAdapterViewHolder.TV_episode.setTextColor(seeAllAdapterViewHolder.itemView.getContext().getResources().getColor(R.color.colorGold));
        }

        //IF favorite DISPLAY STAR
        if (BookSelected.getFavorite() != 0)
            seeAllAdapterViewHolder.iv_star.setVisibility(View.VISIBLE);
        else
            seeAllAdapterViewHolder.iv_star.setVisibility(View.INVISIBLE);

        seeAllAdapterViewHolder.iv_mofify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(TAG, "onClickImage : "+BookSelected.getTitle());
                final Context context = v.getContext();

                Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(BookSelected.getTitle());
                builder.setMessage(R.string.modify_book);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                        Intent i = configureIntent(context, BookSelected);
                        context.startActivity(i);
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) { dialog.cancel(); }
                });

                builder.show();
            }
        });


        seeAllAdapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BookDbTool bookDbTool = new BookDbTool();
                final Context context = v.getContext();
                final ContentResolver contentResolver = context.getContentResolver();

                Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(BookSelected.getTitle());
                builder.setMessage(R.string.increment_book);

                builder.setPositiveButton(R.string.increment_episode, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                        try {
                            int value = BookSelected.getEpisode() + 1;
                            bookDbTool.updateOneColumn(contentResolver, BookSelected.getId(), BookContract.BookDb.COLUMN_EPISODE, value);

                            Intent i = configureIntent(context, BookSelected);
                            context.startActivity(i);
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(context, R.string.update_error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNeutralButton (R.string.increment_tome, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                        try {
                            int value = BookSelected.getTome() + 1;
                            bookDbTool.updateOneColumn(contentResolver, BookSelected.getId(), BookContract.BookDb.COLUMN_TOME, value);

                            Intent i = configureIntent(context, BookSelected);
                            context.startActivity(i);
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(context, R.string.update_error, Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder.setNegativeButton(R.string.increment_chapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                        try {
                            int value = BookSelected.getChapter() + 1;
                            bookDbTool.updateOneColumn(contentResolver, BookSelected.getId(), BookContract.BookDb.COLUMN_CHAPTER, value);

                            Intent i = configureIntent(context, BookSelected);
                            context.startActivity(i);
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(context, R.string.update_error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.show();
            }
        });

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

    private Intent configureIntent(Context context, Book BookSelected){
        Book BookToLookFor = new Book(-1,BookSelected.getTitle(), "","",BookSelected.getType(),-1,-1,-1,-1,-1,-1,-1);

        Intent i =new Intent(context,SeeSelectedBooksActivity.class );
     /*   i.putExtra("title_search", BookSelected.getTitle());
        i.putExtra("author_search", "");
        i.putExtra("type_search", BookSelected.getType());
        i.putExtra("year_search", -1);
        i.putExtra("finish_search", -1);
        i.putExtra("bought_search", -1);
        i.putExtra("chapter_search", -1);
        i.putExtra("episode_search", -1);
        i.putExtra("favorite_search", -1);
    */
        i.putExtra("BookToLookFor",BookToLookFor);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }
}
