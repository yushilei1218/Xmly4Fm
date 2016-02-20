package com.yushilei.xmly4fm.fragments.discover;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.CategoryAdapter;
import com.yushilei.xmly4fm.entities.CategoryEntity;
import com.yushilei.xmly4fm.entities.CategoryListEntity;
import com.yushilei.xmly4fm.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements Callback<CategoryListEntity> {


    private ListView categoryLv;
    private CategoryAdapter adapter;

    public CategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NetWorkUtils.getService().getCategoryListEntity().enqueue(this);
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryLv = ((ListView) view.findViewById(R.id.category_list_view));
        adapter = new CategoryAdapter(getContext());
        categoryLv.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onResponse(Response<CategoryListEntity> response, Retrofit retrofit) {
        List<CategoryEntity> list = response.body().getList();
        List<CategoryEntity> entities = list.subList(0, 5);
        List<List<CategoryEntity>> data = new ArrayList<>();
        adapter.add(entities);
        for (int i = 5; i < list.size(); i += 6) {
            List<CategoryEntity> entityList = list.subList(i, Math.min(i + 6, list.size()));
            data.add(entityList);
        }
        adapter.addAll(data);
    }

    @Override
    public void onFailure(Throwable t) {

        t.printStackTrace();
    }
}
