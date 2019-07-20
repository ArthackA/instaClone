package com.example.instagram.Adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class sharedAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragment= new ArrayList<>();
    private final List<String> mTitles= new ArrayList<>();


    public sharedAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
    public void addFragment(Fragment fragment,String titles){
        mFragment.add(fragment);
        mTitles.add(titles);
    }
}
