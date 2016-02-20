package com.yushilei.xmly4fm.fragments.discover;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.RadioListAdapter;
import com.yushilei.xmly4fm.entities.radio.RadioHomeEntity;
import com.yushilei.xmly4fm.utils.NetWorkUtils;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class BroadCastFragment extends Fragment implements Callback<RadioHomeEntity> {


    private RadioListAdapter adapter;

    public BroadCastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_broad_cast, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView broadLv = (ListView) view.findViewById(R.id.broad_cast_list_view);
        View header = LayoutInflater.from(getContext()).inflate(R.layout.radio_first_item, broadLv, false);
        ListView.LayoutParams lp = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(lp);
        LinearLayout footer = new LinearLayout(getContext());
        footer.setClickable(false);
        ListView.LayoutParams lp2 = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        footer.setLayoutParams(lp2);
        broadLv.addHeaderView(header);
        broadLv.addFooterView(footer);
        adapter = new RadioListAdapter(getContext());
        broadLv.setAdapter(adapter);
        NetWorkUtils.getRadioService().getRadioHomeEntity().enqueue(this);
    }

    @Override
    public void onResponse(Response<RadioHomeEntity> response, Retrofit retrofit) {
        adapter.add(response.body().getResult().getRecommandRadioList());
        adapter.add(response.body().getResult().getTopRadioList());
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }
}
