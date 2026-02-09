package com.pklein.bookmemo;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pklein.bookmemo.tools.ViewExtension;

public class SeeAllActivity extends AppCompatActivity {
    public static String POSITION = "POSITION";
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new SeeAllFragmentPagerAdapter(getSupportFragmentManager(), SeeAllActivity.this));

        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.colorBluePale), ContextCompat.getColor(this, R.color.colorGold));
        adaptEdgeToEdge();
    }

    private void adaptEdgeToEdge() {
        LinearLayout root = findViewById(R.id.root);
        ViewExtension.addSystemWindowInsetToPadding(root, false, true, false, true);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }
}
