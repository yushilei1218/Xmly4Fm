package com.yushilei.xmly4fm.fragments.tabdown;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.AlbumDownedAdapter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumDownedFragment extends Fragment {


    private AlbumDownedAdapter adapter;

    public AlbumDownedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_downed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.album_downed_recyler);
        adapter = new AlbumDownedAdapter();
        recycler.setAdapter(adapter);
        File directory = Environment.getExternalStorageDirectory();
        File dir = new File(directory, "xmly4fm/tracks");
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".m4a");
            }
        });
        List<File> data=new ArrayList<>(Arrays.asList(files));
        adapter.addAll(data);
    }
}
