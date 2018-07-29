package com.pklein.bookmemo;

import android.content.ContentResolver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pklein.bookmemo.data.Book;
import com.pklein.bookmemo.data.BookContract;
import com.pklein.bookmemo.tools.BookDbTool;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateActivity extends AppCompatActivity {

    private static final String TAG= UpdateActivity.class.getSimpleName();

    @BindView(R.id.editTitle_form)  EditText editTitle;
    @BindView(R.id.editAuthor_form) EditText editAuthor;
    @BindView(R.id.editYear_form) EditText editYear;
    @BindView(R.id.editTome_form) EditText editTome;
    @BindView(R.id.editChapter_form) EditText editChapter;
    @BindView(R.id.editEpisode_form) EditText editEpisode;
    @BindView(R.id.editDesc_form) EditText editDesc;
    @BindView(R.id.radio_type_form) RadioGroup radiogroupType;
    @BindView(R.id.radio_collection_form) RadioGroup radiogroupCollection;
    @BindView(R.id.radio_bought_form) RadioGroup radiogroupBought;
    @BindView(R.id.radio_favorite_form) RadioGroup radiogroupFavorite;

    @BindView(R.id.validate_button_update)  ImageButton validate_button;
    @BindView(R.id.delete_button)  ImageButton delete_button;

    private String str_radio_type = "";
    private int int_radio_collection = 0;
    private int int_radio_possession = 0;
    private int int_radio_favorite = 0;
    private int year = 0;
    private int tome = 0;
    private int chapter = 0;
    private int episode = 0;
    private Book mbookToUpdate;
    private ContentResolver contentResolver;

    private static final String LIFECYCLE_BOOK_FILTER_KEY = "filter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w(TAG, "BEGIN UpdateActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);

        contentResolver = this.getContentResolver();
        mbookToUpdate = new Book();
        if (savedInstanceState != null) { // if a filter is already saved, read it
            if (savedInstanceState.containsKey(LIFECYCLE_BOOK_FILTER_KEY)) {
                mbookToUpdate = savedInstanceState.getParcelable(LIFECYCLE_BOOK_FILTER_KEY);
            }
        } else {
            if(this.getIntent().hasExtra("BookToLookFor")) {
                mbookToUpdate = this.getIntent().getExtras().getParcelable("BookToLookFor");
            }
        }

        editTitle.setText(mbookToUpdate.getTitle());
        editAuthor.setText(mbookToUpdate.getAuthor());
        editDesc.setText(mbookToUpdate.getDesc());
        editYear.setText(""+mbookToUpdate.getYear());
        editTome.setText(""+mbookToUpdate.getTome());
        editChapter.setText(""+mbookToUpdate.getChapter());
        editEpisode.setText(""+mbookToUpdate.getEpisode());
        str_radio_type = mbookToUpdate.getType();
        int_radio_collection = mbookToUpdate.getFinish();
        int_radio_possession = mbookToUpdate.getBought();
        int_radio_favorite = mbookToUpdate.getFavorite();

        // Radio Buttons :
        if(mbookToUpdate.getType().equals(BookContract.TYPE_LITERATURE))
            radiogroupType.check(R.id.radio_type1_form);
        else if(mbookToUpdate.getType().equals(BookContract.TYPE_MANGA))
            radiogroupType.check(R.id.radio_type2_form);
        else
            radiogroupType.check(R.id.radio_type3_form);

        if(mbookToUpdate.getFinish() == 1)
            radiogroupCollection.check(R.id.radio_collection2_form);
        else
            radiogroupCollection.check(R.id.radio_collection1_form);

        if(mbookToUpdate.getBought() == 1)
            radiogroupBought.check(R.id.radio_bought1_form);
        else
            radiogroupBought.check(R.id.radio_bought2_form);

        if(mbookToUpdate.getFavorite() == 1)
            radiogroupFavorite.check(R.id.radio_favorite1_form);
        else
            radiogroupFavorite.check(R.id.radio_favorite2_form);

        validate_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String newtitle = editTitle.getText().toString();
                String author = editAuthor.getText().toString();
                String desc = editDesc.getText().toString();

                if(!editYear.getText().toString().equals(""))
                    year = Integer.parseInt(editYear.getText().toString());
                if(!editTome.getText().toString().equals(""))
                    tome = Integer.parseInt(editTome.getText().toString());
                if(!editChapter.getText().toString().equals(""))
                    chapter = Integer.parseInt(editChapter.getText().toString());
                if(!editEpisode.getText().toString().equals(""))
                    episode = Integer.parseInt(editEpisode.getText().toString());

                if(newtitle.equals(""))
                    Toast.makeText(getApplicationContext(), R.string.empty_title, Toast.LENGTH_LONG).show();
                else
                {
                    Log.w(TAG, "infos : "+newtitle+", "+author+", "+desc+", "+year+", "+tome+", "+chapter+", "+str_radio_type+", "+int_radio_collection+", "+int_radio_possession);

                    try
                    {
                        BookDbTool bookDbTool = new BookDbTool();
                        bookDbTool.updateAllColumn(contentResolver,mbookToUpdate.getId(),newtitle, author, desc, str_radio_type,year,int_radio_collection, int_radio_possession, chapter, tome, episode, int_radio_favorite);

                        Intent i = new Intent(getApplicationContext(),SeeSelectedBooksActivity.class );
                        Book BookToLookFor = new Book(-1,newtitle, "","","",-1,-1,-1,-1,-1,-1,-1);
                        i.putExtra("BookToLookFor",BookToLookFor);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getApplicationContext().startActivity(i);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(getApplicationContext(), R.string.update_error, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        Log.w(TAG, "END UpdateActivity");
    }

    public void onRadio_typeClicked_form(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_type1_form:
                if (checked)
                    str_radio_type= BookContract.TYPE_LITERATURE;
                break;
            case R.id.radio_type2_form:
                if (checked)
                    str_radio_type=BookContract.TYPE_MANGA;
                break;
            case R.id.radio_type3_form:
                if (checked)
                    str_radio_type=BookContract.TYPE_COMIC;
                break;
        }
    }
    public void onRadio_collectionClicked_form(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_collection1_form:
                if (checked)
                    int_radio_collection=0;
                break;
            case R.id.radio_collection2_form:
                if (checked)
                    int_radio_collection=1;
                break;
        }
    }

    public void onRadio_boughtClicked_form(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_bought1_form:
                if (checked)
                    int_radio_possession=1;
                break;
            case R.id.radio_bought2_form:
                if (checked)
                    int_radio_possession=0;
                break;
        }
    }

    public void onRadio_favoriteClicked_form(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_favorite1_form:
                if (checked)
                    int_radio_favorite=1;
                break;
            case R.id.radio_favorite2_form:
                if (checked)
                    int_radio_favorite=0;
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIFECYCLE_BOOK_FILTER_KEY, mbookToUpdate);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
