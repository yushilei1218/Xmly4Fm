package com.yushilei.xmly4fm.adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.entities.SuggestAlbumEntity;
import com.yushilei.xmly4fm.entities.SuggestQueryEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yushilei on 2016/1/24.
 */
public class AutoSuggestAdapter extends BaseAdapter {
    private Context context;
    private List<Object> list;
    private String kw;

    public AutoSuggestAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void clearAll() {
        list.clear();
    }

    public void addAll(Collection<?> collection, String kw) {
        if (collection != null) {
            list.addAll(collection);
            notifyDataSetChanged();
            this.kw = kw;
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
        if (list.get(position) instanceof SuggestAlbumEntity) {
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
        if (convertView == null) {
            switch (type) {
                case 0:
                    convertView = LayoutInflater.from(context).inflate(R.layout.auto_suggest_album_item, parent, false);
                    convertView.setTag(new AlbumViewHolder(convertView));
                    break;
                case 1:
                    convertView = LayoutInflater.from(context).inflate(R.layout.auto_suggest_normal_item, parent, false);
                    convertView.setTag(new NormalViewHolder(convertView));
                    break;
            }
        }
        if (type == 0) {
            SuggestAlbumEntity entity = (SuggestAlbumEntity) list.get(position);
            AlbumViewHolder holder = (AlbumViewHolder) convertView.getTag();
            holder.itemImg.setImageURI(Uri.parse(entity.getImgPath()));
            holder.categoryTv.setText(entity.getCategory());
            holder.itemTitle.setText(getSpannableStringBuilder(entity.getKeyword(), kw));
        } else {
            SuggestQueryEntity entity = (SuggestQueryEntity) list.get(position);
            NormalViewHolder holder = (NormalViewHolder) convertView.getTag();
            holder.itemTitle.setText(getSpannableStringBuilder(entity.getKeyword(), kw));
        }
        return convertView;
    }

    private SpannableStringBuilder getSpannableStringBuilder(String data, String kw) {
        SpannableStringBuilder builder = null;
        if (!TextUtils.isEmpty(data)) {
            builder = new SpannableStringBuilder(data);
            if (data.contains(kw)) {
                int start = data.indexOf(kw);
                int end = start + kw.length();
                builder.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return builder;
    }

    public static class AlbumViewHolder {
        private SimpleDraweeView itemImg;
        private TextView itemTitle;
        private TextView categoryTv;

        public AlbumViewHolder(View view) {
            itemImg = ((SimpleDraweeView) view.findViewById(R.id.auto_suggest_album_img));
            itemTitle = ((TextView) view.findViewById(R.id.auto_suggest_album_keyword));
            categoryTv = ((TextView) view.findViewById(R.id.auto_suggest_album_category));
        }
    }

    public static class NormalViewHolder {
        private TextView itemTitle;

        public NormalViewHolder(View view) {
            itemTitle = ((TextView) view.findViewById(R.id.auto_suggest_normal_keyword));
        }
    }
}
