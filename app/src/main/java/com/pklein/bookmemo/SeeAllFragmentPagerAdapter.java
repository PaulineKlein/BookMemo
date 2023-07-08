package com.pklein.bookmemo;

import android.content.Context;
import android.content.res.Resources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pklein.bookmemo.data.BookContract;


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
                return SeeAllLiterature.newInstance(BookContract.TYPE_LITERATURE);
            case 1:
                return SeeAllLiterature.newInstance(BookContract.TYPE_MANGA);
            case 2:
                return SeeAllLiterature.newInstance(BookContract.TYPE_COMIC);
            default:
                return SeeAllLiterature.newInstance(BookContract.TYPE_LITERATURE);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position :
        return tabTitles[position];
    }
}