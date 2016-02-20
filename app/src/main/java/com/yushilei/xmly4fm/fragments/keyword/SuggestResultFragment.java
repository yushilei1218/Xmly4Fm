package com.yushilei.xmly4fm.fragments.keyword;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.SuggestResultAdapter;
import com.yushilei.xmly4fm.entities.AlbumDosEntity;
import com.yushilei.xmly4fm.entities.SuggestAllEntity;
import com.yushilei.xmly4fm.entities.SuggestRelationAlbumEntity;
import com.yushilei.xmly4fm.entities.SuggestRelationTrackEntity;
import com.yushilei.xmly4fm.entities.SuggestRelationUserEntity;
import com.yushilei.xmly4fm.utils.NetWorkUtils;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestResultFragment extends Fragment implements AbsListView.OnScrollListener {


    private ListView suggestResultLv;
    private SuggestResultAdapter adapter;
    private String kw;
    private String searchType;
    private boolean hasMore = true;//是否可以继续请求下一页
    private boolean loading = true;
    private int page = 1;

    public SuggestResultFragment() {
    }

    public static SuggestResultFragment newInstance(String kw, String searchType) {
        SuggestResultFragment fragment = new SuggestResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("kw", kw);
        bundle.putString("type", searchType);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suggest_result, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        suggestResultLv = ((ListView) view.findViewById(R.id.suggest_result_list_view));
        Bundle bundle = getArguments();
        kw = bundle.getString("kw");
        searchType = bundle.getString("type");

        if (kw != null && searchType != null) {
            //只是用来抬高ListView的高度
            LinearLayout linearLayout = new LinearLayout(getContext());
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
            linearLayout.setLayoutParams(lp);
            suggestResultLv.addFooterView(linearLayout);

            adapter = new SuggestResultAdapter(getContext());
            suggestResultLv.setAdapter(adapter);
            suggestResultLv.setOnScrollListener(this);

            if (searchType.equals("album")) {
                Log.d("SuggestResultFragment", "album");
                //网络请求
                queryRelationAlbum(1);
            }
            if (searchType.equals("track")) {
                queryRelationTrack(1);
            }
            if (searchType.equals("user")) {
                queryRelationUser(1);
            }
            if (searchType.equals("all")) {
                //新增回调
                Log.d("SuggestResultFragment", "all");
                adapter.setListener(listener);
                queryAllCategory();
            }
        }
    }

    private void queryAllCategory() {
        NetWorkUtils.getSuggestService().getSuggestAllEntity(kw).enqueue(new Callback<SuggestAllEntity>() {
            @Override
            public void onResponse(Response<SuggestAllEntity> response, Retrofit retrofit) {
                adapter.add(response.body().getAlbum());
                adapter.add(response.body().getUser());
                adapter.add(response.body().getTrack());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void queryRelationAlbum(int page) {
        loading = true;

        NetWorkUtils.getSuggestService().getSuggestRelationAlbumEntity(kw, page).enqueue(new Callback<SuggestRelationAlbumEntity>() {
            @Override
            public void onResponse(Response<SuggestRelationAlbumEntity> response, Retrofit retrofit) {
                adapter.addAll(response.body().getResponse().getDocs());
                AlbumDosEntity entity = response.body().getResponse();
                if (entity != null && entity.getDocs() != null && entity.getDocs().size() == 20) {
                    hasMore = true;
                } else {
                    hasMore = false;
                }
                SuggestResultFragment.this.page++;
                loading = false;
            }

            @Override
            public void onFailure(Throwable t) {
                loading = false;
                t.printStackTrace();
            }
        });

    }

    private void queryRelationTrack(int page) {
        NetWorkUtils.getSuggestService().getSuggestRelationTrackEntity(kw, page).enqueue(new Callback<SuggestRelationTrackEntity>() {
            @Override
            public void onResponse(Response<SuggestRelationTrackEntity> response, Retrofit retrofit) {
                adapter.addAll(response.body().getResponse().getDocs());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void queryRelationUser(int page) {
        NetWorkUtils.getSuggestService().getSuggestRelationUserntity(kw, page).enqueue(new Callback<SuggestRelationUserEntity>() {
            @Override
            public void onResponse(Response<SuggestRelationUserEntity> response, Retrofit retrofit) {
                adapter.addAll(response.body().getResponse().getDocs());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private OnKeyWordGroupSelectedListener listener;

    public void setOnKeyWordGroupSelectedListener(OnKeyWordGroupSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (searchType.equals("album") || searchType.equals("track") || searchType.equals("user")) {
            if (hasMore && !loading) {
                int i = totalItemCount - (firstVisibleItem + visibleItemCount);
                if (i < 4) {
                    if (searchType.equals("album")) {
                        Log.d("SuggestResultFragment", "onScroll");

                        queryRelationAlbum(page);
                    } else if (searchType.equals("track")) {
                        queryRelationTrack(page);
                    }
                }

            }
        }
    }

    //用来回调 KwSearchFragment 切换 radioButton
    public interface OnKeyWordGroupSelectedListener {
        void OnKeyWordGroupSelected(int rId);
    }
}
