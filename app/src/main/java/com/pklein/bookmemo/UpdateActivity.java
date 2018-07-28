package com.pklein.bookmemo;

import android.content.ContentResolver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

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
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w(TAG, "BEGIN UpdateActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);


        Log.w(TAG, "END UpdateActivity");
    }
}
