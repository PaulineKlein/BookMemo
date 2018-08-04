package com.pklein.bookmemo;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pklein.bookmemo.data.Book;
import com.pklein.bookmemo.data.BookContract;
import com.pklein.bookmemo.tools.BookDbTool;


public class SeeAllAdapter extends RecyclerView.Adapter<SeeAllAdapter.SeeAllAdapterViewHolder> {

    private static final String TAG= SeeAllAdapter.class.getSimpleName();
    private Cursor mBookData ;
    private boolean displayCard = false;

    public SeeAllAdapter() {
    }

    public class SeeAllAdapterViewHolder extends RecyclerView.ViewHolder {

        public final LinearLayout linear_layout;
        public final TextView TV_title;
        public final TextView TV_number;
        public final TextView TV_author;
        public final TextView TV_chapter;
        public final TextView TV_year;
        public final TextView TV_comment;
        public final TextView TV_episode;
        public final TextView TV_bought_or_not;
        public final TextView TV_finish_or_not;
        public final TextView TV_favorite_or_not;
        public final ImageView iv_modify;
        public final Button iv_readMore;
        public final Button iv_addOne;

        public SeeAllAdapterViewHolder(View view) {
            super(view);
            linear_layout = view.findViewById(R.id.linear_layout);
            TV_title = view.findViewById(R.id.TV_title);
            TV_number = view.findViewById(R.id.TV_number);
            TV_author = view.findViewById(R.id.TV_author);
            TV_chapter = view.findViewById(R.id.TV_chapter);
            TV_year = view.findViewById(R.id.TV_year);
            TV_comment = view.findViewById(R.id.TV_comment);
            TV_episode = view.findViewById(R.id.TV_episode);
            TV_bought_or_not = view.findViewById(R.id.TV_bought_or_not);
            TV_finish_or_not = view.findViewById(R.id.TV_finish_or_not);
            TV_favorite_or_not = view.findViewById(R.id.TV_favorite_or_not);
            iv_modify = view.findViewById(R.id.update_bt);
            iv_readMore = view.findViewById(R.id.readMore);
            iv_addOne = view.findViewById(R.id.add_one);
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

        final Book BookSelected = new Book();

        //populate Data :
        mBookData.moveToPosition(position);
        BookSelected.setId(mBookData.getInt(mBookData.getColumnIndex(BookContract.BookDb.COLUMN_ID)));
        BookSelected.setTitle(mBookData.getString(mBookData.getColumnIndex(BookContract.BookDb.COLUMN_TITLE)));
        BookSelected.setAuthor(mBookData.getString(mBookData.getColumnIndex(BookContract.BookDb.COLUMN_AUTHOR)));
        BookSelected.setDesc(mBookData.getString(mBookData.getColumnIndex(BookContract.BookDb.COLUMN_DESC)));
        BookSelected.setType(mBookData.getString(mBookData.getColumnIndex(BookContract.BookDb.COLUMN_TYPE)));
        BookSelected.setYear(mBookData.getInt(mBookData.getColumnIndex(BookContract.BookDb.COLUMN_YEAR)));
        BookSelected.setBought(mBookData.getInt(mBookData.getColumnIndex(BookContract.BookDb.COLUMN_BOUGHT)));
        BookSelected.setFinish(mBookData.getInt(mBookData.getColumnIndex(BookContract.BookDb.COLUMN_FINISH)));
        BookSelected.setTome(mBookData.getInt(mBookData.getColumnIndex(BookContract.BookDb.COLUMN_TOME)));
        BookSelected.setChapter(mBookData.getInt(mBookData.getColumnIndex(BookContract.BookDb.COLUMN_CHAPTER)));
        BookSelected.setEpisode(mBookData.getInt(mBookData.getColumnIndex(BookContract.BookDb.COLUMN_EPISODE)));
        BookSelected.setFavorite(mBookData.getInt(mBookData.getColumnIndex(BookContract.BookDb.COLUMN_FAVORITE)));

        final SeeAllAdapterViewHolder adaptviewholder = seeAllAdapterViewHolder;

        //HIDE BookCard :
        displayBookCard(seeAllAdapterViewHolder, false);

        // SET VALUE
        seeAllAdapterViewHolder.TV_title.setText(BookSelected.getTitle());

        if(!BookSelected.getAuthor().equals("")){
            seeAllAdapterViewHolder.TV_author.setText(BookSelected.getAuthor());
        }else{
            seeAllAdapterViewHolder.TV_author.setText(R.string.str_author_unknown);
        }

        seeAllAdapterViewHolder.TV_comment.setText(BookSelected.getDesc());

        if(BookSelected.getTome() != 0){
            seeAllAdapterViewHolder.TV_number.setText(String.valueOf(BookSelected.getTome()));
        }
        else{
            seeAllAdapterViewHolder.TV_number.setText("-");
        }
        if(BookSelected.getChapter() != 0){
            seeAllAdapterViewHolder.TV_chapter.setText(String.valueOf(BookSelected.getChapter()));
        }
        else{
            seeAllAdapterViewHolder.TV_chapter.setText("-");
        }
        if(BookSelected.getYear() != 0){
            seeAllAdapterViewHolder.TV_year.setText(String.valueOf(BookSelected.getYear()));
        }
        else{
            seeAllAdapterViewHolder.TV_year.setText("");
        }
        if(BookSelected.getEpisode() != 0){
            seeAllAdapterViewHolder.TV_episode.setText(String.valueOf(BookSelected.getEpisode()));
        }else{
            seeAllAdapterViewHolder.TV_episode.setText("-");
        }

        //SET COLOR in cased it is finished
        if (BookSelected.getFinish()!=0)
        {
           seeAllAdapterViewHolder.TV_finish_or_not.setText(R.string.str_collection2);
           seeAllAdapterViewHolder.TV_finish_or_not.setTextColor(seeAllAdapterViewHolder.itemView.getContext().getResources().getColor(R.color.colorGold));
        }
        else{
           seeAllAdapterViewHolder.TV_finish_or_not.setText(R.string.str_collection1);
           seeAllAdapterViewHolder.TV_finish_or_not.setTextColor(seeAllAdapterViewHolder.itemView.getContext().getResources().getColor(R.color.colorBlack));
        }

        seeAllAdapterViewHolder.TV_bought_or_not.setText(R.string.str_bought_display);
        //Verify if it is bought or not :
        if (BookSelected.getBought()!=0)
        {
            seeAllAdapterViewHolder.TV_bought_or_not.getPaint().setStrikeThruText(false);
            seeAllAdapterViewHolder.TV_bought_or_not.setTextColor(seeAllAdapterViewHolder.itemView.getContext().getResources().getColor(R.color.colorGold));
        }
        else{
            seeAllAdapterViewHolder.TV_bought_or_not.getPaint().setStrikeThruText(true);
            seeAllAdapterViewHolder.TV_bought_or_not.setTextColor(seeAllAdapterViewHolder.itemView.getContext().getResources().getColor(R.color.colorBlack));
        }

        //IF favorite DISPLAY STAR
        if (BookSelected.getFavorite() != 0)
            seeAllAdapterViewHolder.TV_favorite_or_not.setVisibility(View.VISIBLE);
        else
            seeAllAdapterViewHolder.TV_favorite_or_not.setVisibility(View.GONE);

        seeAllAdapterViewHolder.iv_modify.setOnClickListener(new View.OnClickListener() {
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
                        Intent i = configureIntent(context, BookSelected,"Update");
                        context.startActivity(i);
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) { dialog.cancel(); }
                });

                builder.show();
            }
        });

        seeAllAdapterViewHolder.iv_readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(TAG, "onClickReadMore : "+BookSelected.getTitle());

                Intent i = new Intent(v.getContext(), ReadMoreActivity.class);
                i.putExtra("BookToLookFor",BookSelected);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(i);
            }
        });



        seeAllAdapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.w(TAG, "onClickDisplayCard : ");
                displayBookCard(adaptviewholder, displayCard);
            }
        });

        seeAllAdapterViewHolder.iv_addOne.setOnClickListener(new View.OnClickListener() {
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

                            //UPDATE WIDGET :
                            Intent intent = new Intent(context, BookWidget.class);
                            intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                            int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, BookWidget.class));
                            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
                            context.sendBroadcast(intent);

                            Intent i = configureIntent(context, BookSelected,"See");
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

                            Intent i = configureIntent(context, BookSelected ,"See");
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

                            Intent i = configureIntent(context, BookSelected, "See");
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
        return mBookData.getCount();
    }

    public void setBookData(Cursor newCursor) {
        mBookData = newCursor;
        notifyDataSetChanged();
    }

    private Intent configureIntent(Context context, Book BookSelected, String ActivityChosen){

        Intent i;
        Book BookToLookFor;

        if(ActivityChosen.equals("Update")) {
            i = new Intent(context, UpdateActivity.class);
            BookToLookFor = BookSelected;
        }
        else{
            i = new Intent(context, SeeSelectedBooksActivity.class);
            BookToLookFor = new Book(-1,BookSelected.getTitle(), "","","",-1,-1,-1,-1,-1,-1,-1);
        }

        i.putExtra("BookToLookFor",BookToLookFor);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    private void displayBookCard(SeeAllAdapterViewHolder seeAllAdapterViewHolder, boolean seen){
        if(seen) {
            seeAllAdapterViewHolder.linear_layout.setVisibility(View.VISIBLE);
            seeAllAdapterViewHolder.iv_modify.setVisibility(View.VISIBLE);
            seeAllAdapterViewHolder.iv_readMore.setVisibility(View.VISIBLE);
            seeAllAdapterViewHolder.iv_addOne.setVisibility(View.VISIBLE);
            displayCard = false;
        }
        else{
            seeAllAdapterViewHolder.linear_layout.setVisibility(View.GONE);
            seeAllAdapterViewHolder.iv_modify.setVisibility(View.GONE);
            seeAllAdapterViewHolder.iv_readMore.setVisibility(View.GONE);
            seeAllAdapterViewHolder.iv_addOne.setVisibility(View.GONE);
            displayCard = true;
        }
    }
}
