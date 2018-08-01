package com.pklein.bookmemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.pklein.bookmemo.data.Book;
import com.pklein.bookmemo.data.BookContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.editTitle) EditText editTitle;
    @BindView(R.id.editauthor) EditText editauthor;
    @BindView(R.id.editYear) EditText editYear;
    @BindView(R.id.validate_button)    Button validate_button;

    private String str_radio_type = "";
    private int int_radio_collection = -1;
    private int int_radio_possession = -1;
    private int int_radio_chapter = -1;
    private int int_radio_episode = -1;
    private int int_radio_favorite = -1;
    private int year_search = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Start SearchActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        // button to validate form :
        validate_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String title_search = editTitle.getText().toString();
                String author_search = editauthor.getText().toString();
                if(!editYear.getText().toString().equals(""))
                    year_search = Integer.parseInt(editYear.getText().toString());

                Intent i = new Intent(getApplicationContext(),SeeSelectedBooksActivity.class );

                Book BookToLookFor = new Book(-1,title_search, author_search,"",str_radio_type,year_search,int_radio_possession,int_radio_collection,-1,int_radio_chapter,int_radio_episode,int_radio_favorite);
                i.putExtra("BookToLookFor",BookToLookFor);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getApplicationContext().startActivity(i);
            }
        });

        Log.i(TAG, "End SearchActivity");
    }

    public void onRadio_typeClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_type1:
                if (checked)
                    str_radio_type= BookContract.TYPE_LITERATURE;
                break;
            case R.id.radio_type2:
                if (checked)
                    str_radio_type=BookContract.TYPE_MANGA;
                break;
            case R.id.radio_type3:
                if (checked)
                    str_radio_type=BookContract.TYPE_COMIC;
                break;
            case R.id.radio_type4:
                if (checked)
                    str_radio_type="";
                break;
        }
    }
    public void onRadio_collectionClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_collection1:
                if (checked)
                    int_radio_collection=0;
                break;
            case R.id.radio_collection2:
                if (checked)
                    int_radio_collection=1;
                break;
            case R.id.radio_collection3:
                if (checked)
                    int_radio_collection=-1;
                break;
        }
    }

    public void onRadio_boughtClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_bought1:
                if (checked)
                    int_radio_possession=1;
                break;
            case R.id.radio_bought2:
                if (checked)
                    int_radio_possession=0;
                break;
            case R.id.radio_bought3:
                if (checked)
                    int_radio_possession=-1;
                break;
        }
    }

    public void onRadio_chapterClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_chapter1:
                if (checked)
                    int_radio_chapter=1;
                break;
            case R.id.radio_chapter2:
                if (checked)
                    int_radio_chapter=0;
                break;
            case R.id.radio_chapter3:
                if (checked)
                    int_radio_chapter=-1;
                break;
        }
    }

    public void onRadio_episodeClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_episode1:
                if (checked)
                    int_radio_episode=1;
                break;
            case R.id.radio_episode2:
                if (checked)
                    int_radio_episode=0;
                break;
            case R.id.radio_episode3:
                if (checked)
                    int_radio_episode=-1;
                break;
        }
    }

    public void onRadio_favoriteClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_favorite1:
                if (checked)
                    int_radio_favorite=1;
                break;
            case R.id.radio_favorite2:
                if (checked)
                    int_radio_favorite=0;
                break;
            case R.id.radio_favorite3:
                if (checked)
                    int_radio_favorite=-1;
                break;
        }
    }
}
