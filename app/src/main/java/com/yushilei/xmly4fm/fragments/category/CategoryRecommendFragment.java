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
public class CategoryRecommendFragment extends Fragment {


    public CategoryRecommendFragment() {
        // Required empty public constructor
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_recommend, container, false);
    }

}
