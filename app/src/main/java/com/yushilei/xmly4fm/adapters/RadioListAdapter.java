package com.yushilei.xmly4fm.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.entities.radio.RadioEntity;
import com.yushilei.xmly4fm.utils.NumFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yushilei on 2016/2/4.
 */
public class RadioListAdapter extends BaseAdapter {
    private List<List<RadioEntity>> list;
    private Context context;

    public RadioListAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void add(List<RadioEntity> data) {
        if (data != null) {
            list.add(data);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<List<RadioEntity>> data) {
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
                convertView = LayoutInflater.from(context).inflate(R.layout.radio_second_recommend_item, parent, false);
                convertView.setTag(new RecommendViewHolder(convertView));
                break;
            case 1:
                convertView = LayoutInflater.from(context).inflate(R.layout.radio_third_rank_item, parent, false);
                convertView.setTag(new RankViewHolder(convertView));
                break;
        }
        if (type == 0) {
            List<RadioEntity> entities = list.get(position);
            RecommendViewHolder holder = (RecommendViewHolder) convertView.getTag();
            holder.headerTitle.setText("推荐电台");
            holder.headerCount.setVisibility(View.GONE);
            for (int i = 0; i < entities.size(); i++) {
                holder.covers.get(i).setImageURI(Uri.parse(entities.get(i).getPicPath()));
                holder.titles.get(i).setText(entities.get(i).getRname());
            }
        }
        if (type == 1) {

            List<RadioEntity> entities = list.get(position);
            RankViewHolder holder = (RankViewHolder) convertView.getTag();
            holder.headerTitle.setText("排行榜");
            for (int i = 0; i < entities.size(); i++) {
                holder.covers.get(i).setImageURI(Uri.parse(entities.get(i).getRadioCoverLarge()));
                holder.titles.get(i).setText(entities.get(i).getRname());
                String text = "正在直播:" + entities.get(i).getProgramName();
                holder.intros.get(i).setText(text);
                if (entities.get(i).getRadioPlayCount() > 0) {
                    holder.palyTimes.get(i).setText(NumFormat.longToString(entities.get(i).getRadioPlayCount()));
                }
            }
        }

        return convertView;
    }

    public static class RecommendViewHolder {
        private TextView headerTitle;
        private TextView headerCount;
        private List<View> items;
        private List<SimpleDraweeView> covers;
        private List<TextView> titles;

        public RecommendViewHolder(View view) {
            items = new ArrayList<>();
            covers = new ArrayList<>();
            titles = new ArrayList<>();
            View header = view.findViewById(R.id.radio_second_recommend_header);
            headerTitle = ((TextView) header.findViewById(R.id.kw_dos_item_header_title));
            headerCount = ((TextView) header.findViewById(R.id.kw_dos_item_header_count));
            View view1 = view.findViewById(R.id.radio_second_recommend_item1);
            View view2 = view.findViewById(R.id.radio_second_recommend_item2);
            View view3 = view.findViewById(R.id.radio_second_recommend_item3);
            items.add(view1);
            items.add(view2);
            items.add(view3);
            for (int i = 0; i < items.size(); i++) {
                covers.add(((SimpleDraweeView) items.get(i).findViewById(R.id.radio_recommend_cover)));
                titles.add(((TextView) items.get(i).findViewById(R.id.radio_recommend_title)));
            }
        }
    }

    public static class RankViewHolder {
        private List<View> items;
        private List<SimpleDraweeView> covers;
        private List<TextView> titles;
        private List<TextView> intros;
        private List<TextView> palyTimes;
        private List<ImageView> btnPlays;

        private TextView headerTitle;
        private TextView headerCount;

        public RankViewHolder(View view) {
            items = new ArrayList<>();

            covers = new ArrayList<>();
            titles = new ArrayList<>();
            intros = new ArrayList<>();
            palyTimes = new ArrayList<>();
            btnPlays = new ArrayList<>();
            View header = view.findViewById(R.id.radio_third_rank_header);
            headerTitle = ((TextView) header.findViewById(R.id.kw_dos_item_header_title));
            headerCount = ((TextView) header.findViewById(R.id.kw_dos_item_header_count));
            View view1 = view.findViewById(R.id.radio_third_rank_item1);
            View view2 = view.findViewById(R.id.radio_third_rank_item2);
            View view3 = view.findViewById(R.id.radio_third_rank_item3);
            items.add(view1);
            items.add(view2);
            items.add(view3);
            for (int i = 0; i < items.size(); i++) {
                covers.add(((SimpleDraweeView) items.get(i).findViewById(R.id.radio_rank_item_cover)));
                titles.add(((TextView) items.get(i).findViewById(R.id.radio_rank_item_title)));
                intros.add(((TextView) items.get(i).findViewById(R.id.radio_rank_item_intro)));
                palyTimes.add(((TextView) items.get(i).findViewById(R.id.radio_rank_item_play_time)));
                btnPlays.add(((ImageView) items.get(i).findViewById(R.id.radio_rank_item_play)));

            }

        }
    }
}
