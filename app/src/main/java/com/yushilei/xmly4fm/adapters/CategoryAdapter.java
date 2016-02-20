package com.yushilei.xmly4fm.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.entities.CategoryEntity;
import com.yushilei.xmly4fm.fragments.category.CategoryDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yushilei on 2016/2/1.
 */
public class CategoryAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<List<CategoryEntity>> list;

    public CategoryAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void add(List<CategoryEntity> data) {
        list.add(data);
        notifyDataSetChanged();
    }

    public void addAll(List<List<CategoryEntity>> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        switch (type) {
            case 0:
                convertView = LayoutInflater.from(context).inflate(R.layout.category_first_item, parent, false);
                convertView.setTag(new CategoryFistViewHolder(convertView));
                break;
            case 1:
                convertView = LayoutInflater.from(context).inflate(R.layout.categories_item, parent, false);
                convertView.setTag(new CategoryViewHolder(convertView));
                break;
        }
        if (type == 0) {
            CategoryFistViewHolder holder = (CategoryFistViewHolder) convertView.getTag();
            List<CategoryEntity> entities = list.get(position);
            holder.item1.setImageURI(Uri.parse(entities.get(0).getCoverPath()));
            holder.item2.setImageURI(Uri.parse(entities.get(1).getCoverPath()));
            holder.item3.setImageURI(Uri.parse(entities.get(2).getCoverPath()));
            holder.item4.setImageURI(Uri.parse(entities.get(3).getCoverPath()));
            holder.item5.setImageURI(Uri.parse(entities.get(4).getCoverPath()));
            holder.item1.setTag(entities.get(0));
            holder.item2.setTag(entities.get(1));
            holder.item3.setTag(entities.get(2));
            holder.item4.setTag(entities.get(3));
            holder.item5.setTag(entities.get(4));
            holder.item1.setOnClickListener(this);
            holder.item2.setOnClickListener(this);
            holder.item3.setOnClickListener(this);
            holder.item4.setOnClickListener(this);
            holder.item5.setOnClickListener(this);

        }
        if (type == 1) {
            CategoryViewHolder holder = (CategoryViewHolder) convertView.getTag();
            List<CategoryEntity> entities = list.get(position);
            for (int i = 0; i < entities.size(); i++) {
                holder.items.get(i).setVisibility(View.VISIBLE);

                holder.titles.get(i).setText(entities.get(i).getTitle());
                holder.icons.get(i).setImageURI(Uri.parse(entities.get(i).getCoverPath()));
                holder.items.get(i).setTag(entities.get(i));
                holder.items.get(i).setOnClickListener(this);
            }
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        CategoryEntity tag = (CategoryEntity) v.getTag();
        FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
        Fragment mainFragment = manager.findFragmentByTag("MainFragment");
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.open_in, R.anim.open_out, R.anim.colse_in, R.anim.colse_out);
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
        transaction.add(R.id.main_container, CategoryDetailFragment.newInstance(tag.getId(), tag.getContentType(), tag.getTitle()), "CategoryDetailFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static class CategoryFistViewHolder {
        private SimpleDraweeView item1;
        private SimpleDraweeView item2;
        private SimpleDraweeView item3;
        private SimpleDraweeView item4;
        private SimpleDraweeView item5;

        public CategoryFistViewHolder(View view) {
            item1 = ((SimpleDraweeView) view.findViewById(R.id.category_first_item_1));
            item2 = ((SimpleDraweeView) view.findViewById(R.id.category_first_item_2));
            item3 = ((SimpleDraweeView) view.findViewById(R.id.category_first_item_3));
            item4 = ((SimpleDraweeView) view.findViewById(R.id.category_first_item_4));
            item5 = ((SimpleDraweeView) view.findViewById(R.id.category_first_item_5));
        }
    }

    public static class CategoryViewHolder {
        private List<View> items;
        private List<SimpleDraweeView> icons;
        private List<TextView> titles;

        public CategoryViewHolder(View view) {
            items = new ArrayList<>();
            icons = new ArrayList<>();
            titles = new ArrayList<>();

            items.add(view.findViewById(R.id.categories_item_1));
            items.add(view.findViewById(R.id.categories_item_2));
            items.add(view.findViewById(R.id.categories_item_3));
            items.add(view.findViewById(R.id.categories_item_4));
            items.add(view.findViewById(R.id.categories_item_5));
            items.add(view.findViewById(R.id.categories_item_6));
            for (int i = 0; i < items.size(); i++) {
                icons.add(((SimpleDraweeView) items.get(i).findViewById(R.id.category_item_icon)));
                titles.add(((TextView) items.get(i).findViewById(R.id.category_item_title)));
            }

        }
    }
}
