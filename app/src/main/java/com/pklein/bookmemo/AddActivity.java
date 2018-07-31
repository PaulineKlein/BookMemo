package com.pklein.bookmemo;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.pklein.bookmemo.data.Book;
import com.pklein.bookmemo.data.BookContract;
import com.pklein.bookmemo.tools.BookDbTool;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.editTitle_form)  EditText editTitle;
    @BindView(R.id.editAuthor_form) EditText editAuthor;
    @BindView(R.id.editYear_form) EditText editYear;
    @BindView(R.id.editTome_form) EditText editTome;
    @BindView(R.id.editChapter_form) EditText editChapter;
    @BindView(R.id.editEpisode_form) EditText editEpisode;
    @BindView(R.id.editDesc_form) EditText editDesc;
    @BindView(R.id.validate_button_form) ImageButton validate_button;

    private String str_radio_type = "";
    private int int_radio_collection = 0;
    private int int_radio_possession = 0;
    private int int_radio_favorite = 0;
    private int year = 0;
    private int tome = 0;
    private int chapter = 0;
    private int episode = 0;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w(TAG, "BEGIN AddActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        contentResolver = this.getContentResolver();

        //validate FORM :
        validate_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String title = editTitle.getText().toString();
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

                if(title.equals(""))
                    Toast.makeText(getApplicationContext(), R.string.empty_title, Toast.LENGTH_LONG).show();
                else
                {
                    Log.w(TAG, "infos : "+title+", "+author+", "+desc+", "+year+", "+tome+", "+chapter+", "+str_radio_type+", "+int_radio_collection+", "+int_radio_possession);

                    try
                    {
                        BookDbTool bookDbTool = new BookDbTool();
                        bookDbTool.insertBook(contentResolver,title, author, desc, str_radio_type,year,int_radio_collection, int_radio_possession, chapter, tome, episode, int_radio_favorite);

                        //UPDATE WIDGET :
                        //With the Help of : https://stackoverflow.com/questions/3455123/programmatically-update-widget-from-activity-service-receiver?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
                        Intent intent = new Intent(getApplicationContext(), BookWidget.class);
                        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), BookWidget.class));
                        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
                        sendBroadcast(intent);

                        Intent i = new Intent(getApplicationContext(),SeeSelectedBooksActivity.class );
                        Book BookToLookFor = new Book(-1,title, "","","",-1,-1,-1,-1,-1,-1,-1);
                        i.putExtra("BookToLookFor",BookToLookFor);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getApplicationContext().startActivity(i);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(getApplicationContext(), R.string.add_error, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        Log.w(TAG, "END AddActivity");
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
}
