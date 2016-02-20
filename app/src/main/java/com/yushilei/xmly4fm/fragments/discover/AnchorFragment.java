package com.yushilei.xmly4fm.fragments.discover;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.AnchorItemsAdapter;
import com.yushilei.xmly4fm.entities.Anchor.AnchorHomeEntity;
import com.yushilei.xmly4fm.utils.NetWorkUtils;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnchorFragment extends Fragment implements Callback<AnchorHomeEntity> {


    private RecyclerView recycler;
    private AnchorItemsAdapter adapter;

    public AnchorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anchor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = ((RecyclerView) view.findViewById(R.id.anchor_recycler));
        adapter = new AnchorItemsAdapter(getContext());
        recycler.setAdapter(adapter);
        NetWorkUtils.getService().getAnchorHomeEntity(1).enqueue(this);
    }

    @Override
    public void onResponse(Response<AnchorHomeEntity> response, Retrofit retrofit) {
        adapter.addAll(response.body().getList());
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }
}
