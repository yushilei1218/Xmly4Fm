package com.yushilei.xmly4fm.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.databinding.FocusImageBinding;
import com.yushilei.xmly4fm.databinding.FocusImageItemBinding;
import com.yushilei.xmly4fm.entities.FocusImageEntity;

import java.util.List;

/**
 * Created by yushilei on 2016/1/22.
 */
public class FocusImageAdapter extends PagerAdapter {
    private List<FocusImageEntity> list;
    private Pools.Pool<View> pool;

    public FocusImageAdapter(List<FocusImageEntity> list) {
        this.list = list;
        pool = new Pools.SimplePool(4);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        FocusImageBinding binding = DataBindingUtil.getBinding(view);
        return binding.getFocusImageEntity().equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = pool.acquire();
        if (view == null) {
            view = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.focus_image, container, false).getRoot();
        }
        FocusImageBinding binding = DataBindingUtil.getBinding(view);
        binding.setFocusImageEntity(list.get(position));
        view.setId(position);
        container.addView(view);
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = container.findViewById(position);
        container.removeView(view);
        pool.release(view);
    }
}
