package com.yushilei.xmly4fm.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.entities.rank.RankCategoryEntity;
import com.yushilei.xmly4fm.entities.rank.RankEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yushilei on 2016/2/5.
 */
public class RankListAdapter extends BaseAdapter {
    private List<RankCategoryEntity> list;
    private Context context;

    public RankListAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void addAll(Collection<? extends RankCategoryEntity> data) {
        if (data != null) {
            list.addAll(data);
            notifyDataSetChanged();
        }

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
    public View getView(int position, View convertView, ViewGroup parent) {
        RankCategoryEntity entity = list.get(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.rank_list_item, parent, false);

        View header = convertView.findViewById(R.id.rank_list_item_header);
        TextView headerTitle = (TextView) header.findViewById(R.id.kw_dos_item_header_title);
        TextView headerCount = (TextView) header.findViewById(R.id.kw_dos_item_header_count);
        headerCount.setVisibility(View.GONE);
        headerTitle.setText(entity.getTitle());

        LinearLayout container = (LinearLayout) convertView;
        int size = entity.getList().size();
        for (int i = 0; i < size; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.rank_item, container, false);
            SimpleDraweeView cover = (SimpleDraweeView) view.findViewById(R.id.rank_item_cover);
            TextView title = (TextView) view.findViewById(R.id.rank_item_title);
            TextView subTitle1 = (TextView) view.findViewById(R.id.rank_item_sub_title1);
            TextView subTitle2 = (TextView) view.findViewById(R.id.rank_item_sub_title2);
            title.setText(entity.getTitle());
            String text1 = "1 " + entity.getList().get(i).getFirstKResults().get(0).getTitle();
            String text2 = "2 " + entity.getList().get(i).getFirstKResults().get(1).getTitle();
            subTitle1.setText(text1);
            subTitle2.setText(text2);
            cover.setImageURI(Uri.parse(entity.getList().get(i).getCoverPath()));

            container.addView(view);
        }
        return convertView;
    }
}
