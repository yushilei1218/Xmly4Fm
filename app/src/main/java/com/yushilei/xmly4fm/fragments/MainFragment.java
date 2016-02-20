package com.yushilei.xmly4fm.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.yushilei.xmly4fm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {


    private DiscoverFragment discoverFragment;
    private CustomFragment customFragment;
    private DownLoadFragment downLoadFragment;
    private SelfFragment selfFragment;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioGroup tabBar = (RadioGroup) view.findViewById(R.id.main_tab_bar);
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        discoverFragment = new DiscoverFragment();
        customFragment = new CustomFragment();
        downLoadFragment = new DownLoadFragment();
        selfFragment = new SelfFragment();

        transaction.add(R.id.main_fragment_container, discoverFragment);
        transaction.add(R.id.main_fragment_container, customFragment);
        transaction.add(R.id.main_fragment_container, downLoadFragment);
        transaction.add(R.id.main_fragment_container, selfFragment);
        transaction.show(discoverFragment);
        transaction.hide(customFragment);
        transaction.hide(downLoadFragment);
        transaction.hide(selfFragment);
        transaction.commit();
        tabBar.setOnCheckedChangeListener(this);
        tabBar.check(R.id.main_fragment_tab1_recommend);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (checkedId) {
            case R.id.main_fragment_tab1_recommend:
                transaction.show(discoverFragment);
                transaction.hide(customFragment);
                transaction.hide(downLoadFragment);
                transaction.hide(selfFragment);
                break;
            case R.id.main_fragment_tab2_custom:
                transaction.hide(discoverFragment);
                transaction.show(customFragment);
                transaction.hide(downLoadFragment);
                transaction.hide(selfFragment);
                break;
            case R.id.main_fragment_tab3_download:
                transaction.hide(discoverFragment);
                transaction.hide(customFragment);
                transaction.show(downLoadFragment);
                transaction.hide(selfFragment);
                break;
            case R.id.main_fragment_tab4_my:
                transaction.hide(discoverFragment);
                transaction.hide(customFragment);
                transaction.hide(downLoadFragment);
                transaction.show(selfFragment);
                break;
        }
        transaction.commit();
    }
}
