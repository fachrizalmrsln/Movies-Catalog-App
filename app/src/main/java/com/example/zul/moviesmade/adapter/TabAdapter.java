package com.example.zul.moviesmade.adapter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * copyright zul
 **/

public class TabAdapter extends FragmentPagerAdapter {

    private static final String TAG = "TabAdapter";
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mStringList = new ArrayList<>();

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addNewFragment(Fragment fragment, String string) {
        Log.d(TAG, "addNewFragment: called");
        mFragmentList.add(fragment);
        mStringList.add(string);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(TAG, "getPageTitle: called");
        return mStringList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: called");
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: called");
        return mStringList.size();
    }

}
