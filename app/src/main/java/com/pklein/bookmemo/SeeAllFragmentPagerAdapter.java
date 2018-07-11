package com.pklein.bookmemo;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class SeeAllFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] ;
    private Context context;

    public SeeAllFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        Resources resources = context.getResources();
        this.tabTitles = new String[] { resources.getString(R.string.TAB_literature), resources.getString(R.string.TAB_manga), resources.getString(R.string.TAB_comic) };
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return SeeAllLiterature.newInstance();
            case 1:
                return SeeAllLiterature.newInstance();
            case 2:
                return SeeAllLiterature.newInstance();
            default:
                return SeeAllLiterature.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position :
        return tabTitles[position];
    }
}