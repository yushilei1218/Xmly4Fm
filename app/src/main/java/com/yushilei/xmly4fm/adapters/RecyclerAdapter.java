package com.yushilei.xmly4fm.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by yushilei on 2016/1/22.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private Context context;
    private List<Object> list;
    private Map<Type, BindTool> map;

    public RecyclerAdapter(Context context, Map<Type, BindTool> map) {
        this.context = context;
        this.map = map;
        list = new ArrayList<>();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), viewType, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.binding.setVariable(map.get(list.get(position).getClass()).variableId, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return map.get(list.get(position).getClass()).layoutId;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void add(Object o) {
        int size = list.size();
        list.add(o);
        notifyItemInserted(size);
    }

    public void addAll(Collection<?> collection) {
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public static class BindTool {
        private int layoutId;
        private int variableId;

        public BindTool(int variableId, int layoutId) {
            this.variableId = variableId;
            this.layoutId = layoutId;
        }
    }
}
