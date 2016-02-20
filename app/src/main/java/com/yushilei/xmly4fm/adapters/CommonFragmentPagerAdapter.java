package com.yushilei.xmly4fm.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yushilei.xmly4fm.entities.FragmentType;

import java.util.List;

/**
 * Created by yushilei on 2016/1/22.
 */
public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<FragmentType> list;

    public CommonFragmentPagerAdapter(FragmentManager fm, List<FragmentType> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }
}
