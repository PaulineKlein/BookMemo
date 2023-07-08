package com.pklein.bookmemo;

import android.content.Context;
import android.content.res.Resources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class StatsFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[];
    private Context context;

    public StatsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        Resources resources = context.getResources();
        this.tabTitles = new String[]{resources.getString(R.string.TAB_global), resources.getString(R.string.TAB_manga)};
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return StatsGlobal.newInstance();
            case 1:
                return StatsManga.newInstance();
            default:
                return StatsGlobal.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position :
        return tabTitles[position];
    }
}