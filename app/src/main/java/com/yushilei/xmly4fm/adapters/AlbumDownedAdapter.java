package com.yushilei.xmly4fm.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yushilei.xmly4fm.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yushilei on 2016/2/24.
 */
public class AlbumDownedAdapter extends RecyclerView.Adapter<AlbumDownedAdapter.DownedViewHolder> implements View.OnClickListener {

    private List<File> files;

    public AlbumDownedAdapter() {
        files = new ArrayList<>();
    }

    public void addAll(List<File> list) {
        if (list != null && list.size() > 0) {
            int size = files.size();
            int size1 = list.size();
            files.addAll(list);
            notifyItemRangeInserted(size, size1);
        }
    }

    @Override
    public DownedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_downed_item, parent, false);
        return new DownedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DownedViewHolder holder, int position) {
        holder.deleteImg.setTag(position);
        holder.deleteImg.setOnClickListener(this);
        File file = files.get(position);
        holder.trackTitle.setText(file.getName());
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        if (files.get(tag).exists()) {
            boolean delete = files.get(tag).delete();
            if (delete) {
                Toast.makeText(v.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        }
        files.remove(tag);
        notifyItemRemoved(tag);
    }

    public class DownedViewHolder extends RecyclerView.ViewHolder {
        private ImageView deleteImg;
        private TextView trackTitle;

        public DownedViewHolder(View itemView) {
            super(itemView);
            deleteImg = ((ImageView) itemView.findViewById(R.id.album_downed_item_delete));
            trackTitle = ((TextView) itemView.findViewById(R.id.album_downed_item_title));
        }
    }
}
