package com.pklein.bookmemo;

import android.os.Bundle;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.pklein.bookmemo.tools.ViewExtension;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        adaptEdgeToEdge();
    }

    private void adaptEdgeToEdge() {
        ScrollView root = findViewById(R.id.ScrollView01);
        ViewExtension.addSystemWindowInsetToPadding(root, false, true, false, true);
    }
}
