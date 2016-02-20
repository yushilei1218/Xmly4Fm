package com.yushilei.xmly4fm.fragments.keyword;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yushilei.xmly4fm.MainActivity;
import com.yushilei.xmly4fm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KwSearchFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, SuggestResultFragment.OnKeyWordGroupSelectedListener {


    private String kw;
    private RadioGroup tabBar;

    public KwSearchFragment() {
        // Required empty public constructor
    }

    public static KwSearchFragment newInstance(String kw) {
        Bundle bundle = new Bundle();
        bundle.putString("kw", kw);
        KwSearchFragment fragment = new KwSearchFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kw_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabBar = (RadioGroup) view.findViewById(R.id.kw_search_tab_bar);
        Bundle bundle = getArguments();
        kw = bundle.getString("kw");
        if (kw != null) {
            tabBar.setOnCheckedChangeListener(this);
            //默认进来加载全部
            RadioButton radioAll = (RadioButton) view.findViewById(R.id.kw_search_tab1_all);
            radioAll.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (checkedId) {
            case R.id.kw_search_tab1_all:
                SuggestResultFragment fragment = SuggestResultFragment.newInstance(kw, "all");
                fragment.setOnKeyWordGroupSelectedListener(this);
                transaction.replace(R.id.kw_search_result_container, fragment);
                break;
            case R.id.kw_search_tab2_album:
                transaction.replace(R.id.kw_search_result_container, SuggestResultFragment.newInstance(kw, "album"));
                break;
            case R.id.kw_search_tab3_user:
                transaction.replace(R.id.kw_search_result_container, SuggestResultFragment.newInstance(kw, "user"));
                break;
            case R.id.kw_search_tab4_track:
                transaction.replace(R.id.kw_search_result_container, SuggestResultFragment.newInstance(kw, "track"));
                break;
        }
        transaction.commit();
    }

    @Override
    public void OnKeyWordGroupSelected(int rId) {
        tabBar.check(rId);
    }
}
