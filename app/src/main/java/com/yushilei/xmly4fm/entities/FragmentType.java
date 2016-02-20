package com.yushilei.xmly4fm.entities;

import android.support.v4.app.Fragment;

/**
 * Created by yushilei on 2016/1/22.
 */
public class FragmentType {
    private Fragment fragment;
    private String title;

    public FragmentType(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
