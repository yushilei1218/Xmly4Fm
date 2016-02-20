package com.yushilei.xmly4fm.fragments.discover;


import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yushilei.xmly4fm.BR;
import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.FocusImageAdapter;
import com.yushilei.xmly4fm.adapters.RecyclerAdapter;
import com.yushilei.xmly4fm.databinding.FocusImageItemBinding;
import com.yushilei.xmly4fm.databinding.FragmentRecommendBinding;
import com.yushilei.xmly4fm.entities.DiscoveryColumnsEntity;
import com.yushilei.xmly4fm.entities.FocusImageEntity;
import com.yushilei.xmly4fm.entities.FocusImagesEntity;
import com.yushilei.xmly4fm.entities.HomeEntity;
import com.yushilei.xmly4fm.entities.RecommendEntity;
import com.yushilei.xmly4fm.entities.SpecialColumnEntity;
import com.yushilei.xmly4fm.utils.NetWorkUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment implements Callback<HomeEntity> {


    private RecyclerAdapter adapter;

    public RecommendFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NetWorkUtils.getService().getHomeEntity().enqueue(this);
        FragmentRecommendBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommend, container, false);

        Map<Type, RecyclerAdapter.BindTool> map = new HashMap<>();

        map.put(RecommendEntity.class, new RecyclerAdapter.BindTool(BR.recommendEntity, R.layout.album_row_item));
        map.put(DiscoveryColumnsEntity.class, new RecyclerAdapter.BindTool(BR.discoveryColumnsEntity, R.layout.discover_column_item));
        map.put(SpecialColumnEntity.class, new RecyclerAdapter.BindTool(BR.specialColumnEntity, R.layout.special_column_item));
        map.put(FocusImagesEntity.class, new RecyclerAdapter.BindTool(BR.focusImagesEntity, R.layout.focus_image_item));

        adapter = new RecyclerAdapter(getContext(), map);
        binding.recommendRecycler.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onResponse(Response<HomeEntity> response, Retrofit retrofit) {
        adapter.add(response.body().getFocusImages());//热点图
        adapter.add(response.body().getEditorRecommendAlbums());//小编推荐
        adapter.add(response.body().getSpecialColumn());//精品听单
        adapter.add(response.body().getDiscoveryColumns());//发现新奇
        adapter.addAll(response.body().getHotRecommends().getList());//热门推荐
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(getContext(), "网络异常", Toast.LENGTH_SHORT).show();
    }

    @BindingAdapter("bind:init")
    public static void initFocusImages(View view, List<FocusImageEntity> list) {

        final FocusImageItemBinding binding = DataBindingUtil.getBinding(view);
        if (binding.focusViewPager.getAdapter() == null) {
            binding.focusViewPager.setAdapter(new FocusImageAdapter(list));
            final int size = list.size();
            for (int i = 0; i < size; i++) {
                View indicator = LayoutInflater.from(view.getContext()).inflate(R.layout.indicator, binding.focusIndicatorContainer, false);
                indicator.setId(i);
                binding.focusIndicatorContainer.addView(indicator);
            }
            binding.focusViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    binding.focusIndicatorContainer.check(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    int currentItem = binding.focusViewPager.getCurrentItem();
                    if (currentItem + 1 < size) {
                        binding.focusViewPager.setCurrentItem(currentItem + 1, true);
                    } else {
                        binding.focusViewPager.setCurrentItem(0, true);
                    }
                    sendEmptyMessageDelayed(1, 3000);
                }
            };
            handler.sendEmptyMessageDelayed(1, 3000);
        }

    }
}
