package com.yushilei.xmly4fm.fragments;


import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.CommonFragmentPagerAdapter;
import com.yushilei.xmly4fm.entities.FragmentType;
import com.yushilei.xmly4fm.fragments.tabdown.AlbumDownedFragment;
import com.yushilei.xmly4fm.utils.NumFormat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownLoadFragment extends Fragment {


    public DownLoadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_down_load, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabBar = (TabLayout) view.findViewById(R.id.down_load_tab);
        ViewPager pager = (ViewPager) view.findViewById(R.id.down_load_pager);
        TextView spaceTv = (TextView) view.findViewById(R.id.down_load_app_space);

        File directory = Environment.getExternalStorageDirectory();
        File dir = new File(directory, "xmly4fm/tracks");
        long usedSpace = 0;

        long freeSpace = directory.getFreeSpace();

        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                usedSpace += f.length();
            }
        }

        String free = NumFormat.longToKbMbGb(freeSpace);
        String used = NumFormat.longToKbMbGb(usedSpace);
        String spaceUsed = "已占用" + used + ",可用空间" + free;
        spaceTv.setText(spaceUsed);
        List<FragmentType> data = new ArrayList<>();
        data.add(new FragmentType("专辑", new AlbumDownedFragment()));
        data.add(new FragmentType("音频", new AlbumDownedFragment()));
        data.add(new FragmentType("下载中", new AlbumDownedFragment()));
        pager.setAdapter(new CommonFragmentPagerAdapter(getChildFragmentManager(), data));
        tabBar.setupWithViewPager(pager);
    }
}
