package com.yushilei.xmly4fm.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.CommonFragmentPagerAdapter;
import com.yushilei.xmly4fm.entities.FragmentType;
import com.yushilei.xmly4fm.fragments.keyword.SuggestResultFragment;
import com.yushilei.xmly4fm.fragments.tabCustom.CustomRecommendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomFragment extends Fragment {


    private TabLayout tabBar;

    public CustomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabBar = (TabLayout) view.findViewById(R.id.custom_tab);
        ViewPager pager = (ViewPager) view.findViewById(R.id.custom_pager);
        List<FragmentType> data=new ArrayList<>();
        data.add(new FragmentType("推荐",new CustomRecommendFragment()));
        pager.setAdapter(new CommonFragmentPagerAdapter(getChildFragmentManager(),data));
        tabBar.setupWithViewPager(pager);
    }
}
