package com.yushilei.xmly4fm.fragments.category;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yushilei.xmly4fm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryOtherFragment extends Fragment {

    private static final String CATEGORY_ID = "categoryId";
    private static final String TAG_NAME = "tagName";

    public CategoryOtherFragment() {
        // Required empty public constructor
    }

    public static CategoryOtherFragment newInstance(int categoryId, String tagName) {
        CategoryOtherFragment fragment = new CategoryOtherFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_ID, categoryId);
        bundle.putString(TAG_NAME, tagName);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_other, container, false);
    }

}
