package com.yushilei.xmly4fm.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.CommonFragmentPagerAdapter;
import com.yushilei.xmly4fm.entities.FragmentType;
import com.yushilei.xmly4fm.fragments.discover.AnchorFragment;
import com.yushilei.xmly4fm.fragments.discover.BroadCastFragment;
import com.yushilei.xmly4fm.fragments.discover.CategoryFragment;
import com.yushilei.xmly4fm.fragments.discover.RankListFragment;
import com.yushilei.xmly4fm.fragments.discover.RecommendFragment;
import com.yushilei.xmly4fm.fragments.keyword.KeyWordFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {


    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imgSearch = (ImageView) view.findViewById(R.id.discover_icon_search);
        TabLayout tab = (TabLayout) view.findViewById(R.id.discover_tab_layout);
        ViewPager pager = (ViewPager) view.findViewById(R.id.discover_view_pager);
        //keyWord fragment页面跳转
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment mainFragment = manager.findFragmentByTag("MainFragment");
                KeyWordFragment keyWordFragment = new KeyWordFragment();
                transaction.setCustomAnimations(R.anim.open_in, R.anim.open_out, R.anim.colse_in, R.anim.colse_out);
                transaction.add(R.id.main_container, keyWordFragment,"KeyWordFragment");
                transaction.hide(mainFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        List<FragmentType> list = new ArrayList<>();
        list.add(new FragmentType("推荐", new RecommendFragment()));
        list.add(new FragmentType("分类", new CategoryFragment()));
        list.add(new FragmentType("广播", new BroadCastFragment()));
        list.add(new FragmentType("榜单", new RankListFragment()));
        list.add(new FragmentType("主播", new AnchorFragment()));
        pager.setAdapter(new CommonFragmentPagerAdapter(getChildFragmentManager(), list));
        tab.setupWithViewPager(pager);
    }
}
