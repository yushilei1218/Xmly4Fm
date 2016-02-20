package com.yushilei.xmly4fm.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.entities.Anchor.AnchorCategoryEntity;
import com.yushilei.xmly4fm.entities.UserInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yushilei on 2016/2/4.
 */
public class AnchorItemsAdapter extends RecyclerView.Adapter<AnchorItemsAdapter.AnchorItemsViewHolder> {
    private Context context;
    private List<AnchorCategoryEntity> list;

    public AnchorItemsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void addAll(Collection<? extends AnchorCategoryEntity> data) {
        if (data != null) {
            int size = list.size();
            int count = data.size();
            list.addAll(data);
            notifyItemRangeInserted(size, count);
        }
    }

    @Override
    public AnchorItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.anchor_items, parent, false);
        AnchorItemsViewHolder holder = new AnchorItemsViewHolder(view);
        view.setTag(holder);
        return holder;
    }


    @Override
    public void onBindViewHolder(AnchorItemsViewHolder holder, int position) {
        AnchorCategoryEntity entity = list.get(position);
        holder.headerTitle.setText(entity.getTitle());
        for (int i = 0; i < entity.getList().size(); i++) {
            UserInfo userInfo = entity.getList().get(i);
            holder.covers.get(i).setImageURI(Uri.parse(userInfo.getSmallLogo()));
            holder.nicks.get(i).setText(userInfo.getNickname());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AnchorItemsViewHolder extends RecyclerView.ViewHolder {
        private TextView headerTitle;
        private TextView headerCount;
        private List<View> views;
        private List<SimpleDraweeView> covers;
        private List<TextView> nicks;
        private List<TextView> focus;

        public AnchorItemsViewHolder(View itemView) {
            super(itemView);
            views = new ArrayList<>();
            covers = new ArrayList<>();
            nicks = new ArrayList<>();
            focus = new ArrayList<>();

            View header = itemView.findViewById(R.id.anchor_items_header);
            View view1 = itemView.findViewById(R.id.anchor_items_item1);
            View view2 = itemView.findViewById(R.id.anchor_items_item2);
            View view3 = itemView.findViewById(R.id.anchor_items_item3);
            headerTitle = ((TextView) header.findViewById(R.id.kw_dos_item_header_title));
            headerCount = ((TextView) header.findViewById(R.id.kw_dos_item_header_count));
            views.add(view1);
            views.add(view2);
            views.add(view3);
            for (int i = 0; i < views.size(); i++) {
                covers.add(((SimpleDraweeView) views.get(i).findViewById(R.id.anchor_item_cover)));
                nicks.add(((TextView) views.get(i).findViewById(R.id.anchor_item_nick)));
                focus.add(((TextView) views.get(i).findViewById(R.id.anchor_item_focus)));
            }
        }
    }
}
