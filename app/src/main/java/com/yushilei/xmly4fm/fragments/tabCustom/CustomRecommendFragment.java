package com.yushilei.xmly4fm.fragments.tabCustom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.SuggestResultAdapter;
import com.yushilei.xmly4fm.entities.CustomRecommendEntity;
import com.yushilei.xmly4fm.utils.NetWorkUtils;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomRecommendFragment extends Fragment implements Callback<CustomRecommendEntity> {


    private SuggestResultAdapter adapter;

    public CustomRecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_recommend, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView) view.findViewById(R.id.custom_recommend);
        adapter = new SuggestResultAdapter(getContext());
        listView.setAdapter(adapter);
        Call<CustomRecommendEntity> call = NetWorkUtils.getService().getCustomRecommendEntity();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<CustomRecommendEntity> response, Retrofit retrofit) {
        adapter.addAll(response.body().getData().getList());
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(getContext(), "网络异常", Toast.LENGTH_SHORT).show();
    }
}
