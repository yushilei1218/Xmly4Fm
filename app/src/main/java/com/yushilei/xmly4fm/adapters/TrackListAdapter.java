package com.yushilei.xmly4fm.adapters;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushilei.xmly4fm.MainActivity;
import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.asynctask.TrackDownAsyncTask;
import com.yushilei.xmly4fm.entities.TrackEntity;
import com.yushilei.xmly4fm.utils.NumFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by yushilei on 2016/1/23.
 */
public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackViewHolder> implements View.OnClickListener {
    private Context context;
    private List<TrackEntity> list;
    private MainActivity activity;
    private int currentPlayingPosition = -1;//用来记录当前正在播放的条目

    public TrackListAdapter(Context context, MainActivity activity) {
        this.context = context;
        list = new ArrayList<>();
        this.activity = activity;
    }

    public void addAll(Collection<? extends TrackEntity> collection) {
        if (collection != null) {
            int size = list.size();
            list.addAll(collection);
            notifyItemRangeInserted(size, collection.size());
        }
    }

    /**
     * 默认进入Album详情页加载第一条数据
     */
    public void loadFirstTrack() {
        list.get(0).setPlaying(true);
        notifyItemChanged(0);
        currentPlayingPosition = 0;
        activity.mediaController.mediaLoad(list.get(0));
    }

    /**
     * @param index
     */
    public void syncPlayingTrack(int index) {
        currentPlayingPosition = index;
        TrackEntity entity = list.get(index);
        entity.setPlaying(!entity.isPlaying());
        notifyItemChanged(index);
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.track_item, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        //Track 条目的点击事件处理
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mPosition = (int) v.getTag();
                TrackEntity entity = list.get(mPosition);
                if (entity.isPlaying()) {
                    currentPlayingPosition = -1;
                    entity.setPlaying(false);
                    activity.mediaController.playOrPause();
                    notifyItemChanged(mPosition);
                } else {
                    if (currentPlayingPosition != -1 && mPosition != currentPlayingPosition) {
                        list.get(currentPlayingPosition).setPlaying(false);
                        notifyItemChanged(currentPlayingPosition);
                    }
                    currentPlayingPosition = mPosition;
                    entity.setPlaying(true);
                    activity.mediaController.mediaLoad(entity);
                    notifyItemChanged(mPosition);
                }
            }
        });
        //设置数据
        TrackEntity entity = list.get(position);
        holder.trackTitle.setText(entity.getTitle());
        if (entity.isPlaying()) {
            holder.animImg.setVisibility(View.VISIBLE);
            AnimationDrawable drawable = (AnimationDrawable) holder.animImg.getDrawable();
            drawable.start();
        } else {
            holder.animImg.setVisibility(View.GONE);
            AnimationDrawable drawable = (AnimationDrawable) holder.animImg.getDrawable();
            drawable.stop();
        }
        if (entity.getComments() > 0) {
            holder.commentTv.setVisibility(View.VISIBLE);
            holder.commentTv.setText(String.valueOf(entity.getComments()));
        } else {
            holder.commentTv.setVisibility(View.GONE);
        }
        if (entity.getPlaytimes() > 0) {
            String count = NumFormat.longToString(entity.getPlaytimes());
            holder.playTimeTv.setVisibility(View.VISIBLE);
            holder.playTimeTv.setText(count);
        } else {
            holder.playTimeTv.setVisibility(View.GONE);
        }
        if (entity.getDuration().longValue() > 0) {
            long duration = entity.getDuration().longValue();
            holder.allTimeTv.setVisibility(View.VISIBLE);
            holder.allTimeTv.setText(NumFormat.longToTime(duration));
        } else {
            holder.allTimeTv.setVisibility(View.GONE);
        }
        if (entity.isDowned()) {
            holder.downImg.setSelected(true);
        } else {
            holder.downImg.setSelected(false);
            holder.downImg.setTag(position);
            holder.downImg.setOnClickListener(this);
        }
        long l = System.currentTimeMillis();
        Date createdAt = entity.getCreatedAt();
        long time = createdAt.getTime();
        long second = (l - time) / 1000;
        if (second > 0) {
            if (3600 > second) {
                long minute = second / 3600;
                String data = minute + "分钟前";
                holder.createTimeTv.setText(data);
                //分
            } else if (second >= 3600 && second < 86400) {
                //小时
                long hour = second / 3600;
                String data = hour + "小时前";
                holder.createTimeTv.setText(data);

            } else if (second >= 86400 && second < 86400 * 30) {
                long day = second / 86400;
                String data = day + "天前";
                holder.createTimeTv.setText(data);
            } else if (second >= 86400 * 30) {
                long mouth = second / (86400 * 30);
                String data = mouth + "月前";
                holder.createTimeTv.setText(data);
            }
        } else {
            holder.createTimeTv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        // TODO: 2016/2/23 下载
        TrackEntity trackEntity = list.get(position);
        trackEntity.setIsDowned(true);
        notifyDataSetChanged();
        new TrackDownAsyncTask(v.getContext()).execute(trackEntity.getDownloadUrl());
    }

    public static class TrackViewHolder extends RecyclerView.ViewHolder {
        private ImageView animImg;
        private ImageView downImg;
        private TextView trackTitle;
        private TextView playTimeTv;
        private TextView allTimeTv;
        private TextView commentTv;
        private TextView createTimeTv;

        public TrackViewHolder(View itemView) {
            super(itemView);
            animImg = ((ImageView) itemView.findViewById(R.id.track_item_anim_list));
            downImg = ((ImageView) itemView.findViewById(R.id.track_item_download));
            trackTitle = ((TextView) itemView.findViewById(R.id.track_item_title));
            playTimeTv = ((TextView) itemView.findViewById(R.id.track_item_play_time));
            allTimeTv = ((TextView) itemView.findViewById(R.id.track_item_alltime));
            commentTv = ((TextView) itemView.findViewById(R.id.track_item_comment));
            createTimeTv = ((TextView) itemView.findViewById(R.id.track_item_create_time));
        }
    }
}
