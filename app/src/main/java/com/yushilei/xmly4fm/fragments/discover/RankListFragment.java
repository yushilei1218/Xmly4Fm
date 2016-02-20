package com.yushilei.xmly4fm.fragments.discover;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.RankListAdapter;
import com.yushilei.xmly4fm.entities.FocusImageEntity;
import com.yushilei.xmly4fm.entities.rank.RankCategoryEntity;
import com.yushilei.xmly4fm.entities.rank.RankHomeEntity;
import com.yushilei.xmly4fm.utils.NetWorkUtils;
import com.yushilei.xmly4fm.widgets.BannerView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankListFragment extends Fragment implements Callback<RankHomeEntity> {


    private RankListAdapter adapter;
    private BannerView bannerView;

    public RankListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rank_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView rankLv = (ListView) view.findViewById(R.id.rank_list_list_view);
        bannerView = new BannerView(getContext());

        ListView.LayoutParams lp = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bannerView.setLayoutParams(lp);
        LinearLayout footer = new LinearLayout(getContext());
        ListView.LayoutParams lp2 = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        footer.setLayoutParams(lp2);
        footer.setClickable(false);
        rankLv.addHeaderView(bannerView);
        rankLv.addFooterView(footer);
        adapter = new RankListAdapter(getContext());
        rankLv.setAdapter(adapter);
        NetWorkUtils.getService().getRankHomeEntity().enqueue(this);
    }

    @Override
    public void onResponse(Response<RankHomeEntity> response, Retrofit retrofit) {
        adapter.addAll(response.body().getDatas());
        int width = bannerView.getWidth();
        ViewGroup.LayoutParams params = bannerView.getLayoutParams();
        params.height = (int) ((double) width / 2.13);

        bannerView.initBanner(response.body().getFocusImages().getList());
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onDestroyView() {
        bannerView.onStopBanner();
        super.onDestroyView();
    }
}
