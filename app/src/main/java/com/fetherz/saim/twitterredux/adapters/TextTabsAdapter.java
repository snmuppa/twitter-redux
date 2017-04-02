package com.fetherz.saim.twitterredux.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by sm032858 on 4/2/17.
 */

public class TextTabsAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titleList;

    public TextTabsAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override // Returns the number of items to be displayed in ViewPager
    public int getCount() {
        return fragmentList.size();
    }

    @Override // Returns the Item at a particular position
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override // Returns the Title of the Tab
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}