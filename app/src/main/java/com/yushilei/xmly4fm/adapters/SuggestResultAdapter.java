package com.yushilei.xmly4fm.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.TrackPlayingActivity;
import com.yushilei.xmly4fm.entities.AlbumDosEntity;
import com.yushilei.xmly4fm.entities.AlbumEntity;
import com.yushilei.xmly4fm.entities.TrackDosEntity;
import com.yushilei.xmly4fm.entities.TrackEntity;
import com.yushilei.xmly4fm.entities.UserInfo;
import com.yushilei.xmly4fm.entities.UserInfoDosEntity;
import com.yushilei.xmly4fm.fragments.AlbumDetailFragment;
import com.yushilei.xmly4fm.fragments.UserDetailFragment;
import com.yushilei.xmly4fm.fragments.keyword.KeyWordFragment;
import com.yushilei.xmly4fm.fragments.keyword.SuggestResultFragment;
import com.yushilei.xmly4fm.utils.NumFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yushilei on 2016/1/25.
 */
public class SuggestResultAdapter extends BaseAdapter {
    private Context context;
    private List<Object> list;
    private SuggestResultFragment.OnKeyWordGroupSelectedListener listener;

    public SuggestResultAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setListener(SuggestResultFragment.OnKeyWordGroupSelectedListener listener) {
        this.listener = listener;
    }

    public void addAll(Collection<?> collection) {
        if (collection != null && collection.size() > 0) {
            list.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void add(Object o) {
        if (o != null) {
            list.add(o);
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
        Object obj = list.get(position);
        if (obj instanceof AlbumDosEntity) {
            return 0;
        } else if (obj instanceof UserInfoDosEntity) {
            return 1;
        } else if (obj instanceof TrackDosEntity) {
            return 2;
        } else if (obj instanceof AlbumEntity) {
            return 3;
        } else if (obj instanceof UserInfo) {
            return 4;
        } else if (obj instanceof TrackEntity) {
            return 5;
        } else {
            return 0;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 6;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case 0:
                    convertView = LayoutInflater.from(context).inflate(R.layout.kw_album_dos_item, parent, false);
                    convertView.setTag(new AlbumDosViewHolder(convertView, position));
                    break;
                case 1:
                    convertView = LayoutInflater.from(context).inflate(R.layout.kw_user_dos_item, parent, false);
                    convertView.setTag(new UserDosViewHolder(convertView, position));
                    break;
                case 2:
                    convertView = LayoutInflater.from(context).inflate(R.layout.kw_track_dos_item, parent, false);
                    convertView.setTag(new TrackDosViewHolder(convertView, position));
                    break;
                case 3:
                    //分类请求
                    convertView = LayoutInflater.from(context).inflate(R.layout.kw_album_item, parent, false);
                    convertView.setTag(new AlbumViewHolder(convertView, position, 0));
                    break;
                case 4:
                    convertView = LayoutInflater.from(context).inflate(R.layout.kw_user_item, parent, false);
                    convertView.setTag(new UserViewHolder(convertView, position, 0));
                    break;
                case 5:
                    convertView = LayoutInflater.from(context).inflate(R.layout.kw_track_item, parent, false);
                    convertView.setTag(new TrackViewHolder(convertView, position, 0));
                    break;
            }
        }
        if (type == 0) {
            AlbumDosEntity entity = (AlbumDosEntity) list.get(position);
            AlbumDosViewHolder holder = (AlbumDosViewHolder) convertView.getTag();
            //处理header
            holder.headerTitle.setText("专辑");
            if (entity.getNumFound() > 3) {
                holder.headerCount.setVisibility(View.VISIBLE);
                holder.headerCount.setText(getSpannableStringBuilder(String.valueOf(entity.getNumFound())));
                holder.headerCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.OnKeyWordGroupSelected(R.id.kw_search_tab2_album);
                        }
                    }
                });
            } else {
                holder.headerCount.setVisibility(View.GONE);
            }
            //处理item
            int size = entity.getDocs().size();
            for (int i = 0; i < size; i++) {
                AlbumEntity albumEntity = entity.getDocs().get(i);
                AlbumViewHolder albumViewHolder = holder.viewHolders.get(i);
                initAlbumItem(albumEntity, albumViewHolder);
                holder.albumItems.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlbumViewHolder tag = (AlbumViewHolder) v.getTag();
                        int index = tag.getPosition();
                        AlbumDosEntity dosEntity = (AlbumDosEntity) list.get(index);
                        int mPosition = tag.getmPosition();
                        AlbumEntity en = dosEntity.getDocs().get(mPosition);
                        jumpToAlbumDetailFragment(en, ((FragmentActivity) v.getContext()));

                    }
                });
            }
            for (int i = 0; i < 3 - size; i++) {
                holder.albumItems.get(2 - i).setVisibility(View.GONE);
            }
        }
        if (type == 1) {
            UserInfoDosEntity entity = (UserInfoDosEntity) list.get(position);
            UserDosViewHolder holder = (UserDosViewHolder) convertView.getTag();
            holder.headerTitle.setText("用户");
            if (entity.getNumFound() > 3) {
                holder.headerCount.setVisibility(View.VISIBLE);
                holder.headerCount.setText(getSpannableStringBuilder(String.valueOf(entity.getNumFound())));
                holder.headerCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.OnKeyWordGroupSelected(R.id.kw_search_tab3_user);
                        }
                    }
                });
            } else {
                holder.headerCount.setVisibility(View.GONE);
            }
            int size = entity.getDocs().size();
            for (int i = 0; i < size; i++) {
                UserInfo userInfo = entity.getDocs().get(i);
                UserViewHolder userViewHolder = holder.viewHolders.get(i);
                initUserItem(userInfo, userViewHolder);
                holder.userItems.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserViewHolder tag = (UserViewHolder) v.getTag();
                        UserInfo info = (UserInfo) ((UserInfoDosEntity) list.get(tag.getPosition())).getDocs().get(tag.getmPosition());
                        FragmentActivity context = (FragmentActivity) v.getContext();
                        jumpToUserDetailFragment(context, info);
                    }
                });
            }
            for (int i = 0; i < 3 - size; i++) {
                holder.userItems.get(2 - i).setVisibility(View.GONE);
            }
        }
        if (type == 2) {
            TrackDosViewHolder holder = (TrackDosViewHolder) convertView.getTag();
            TrackDosEntity entity = (TrackDosEntity) list.get(position);
            holder.headerTitle.setText("声音");
            if (entity.getNumFound() > 3) {
                holder.headerCount.setVisibility(View.VISIBLE);
                holder.headerCount.setText(getSpannableStringBuilder(String.valueOf(entity.getNumFound())));
                holder.headerCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.OnKeyWordGroupSelected(R.id.kw_search_tab4_track);
                        }
                    }
                });
            } else {
                holder.headerCount.setVisibility(View.GONE);
            }
            int size = entity.getDocs().size();
            for (int i = 0; i < size; i++) {
                TrackEntity trackEntity = entity.getDocs().get(i);
                TrackViewHolder trackViewHolder = holder.viewHolders.get(i);
                initTrackItem(trackEntity, trackViewHolder);
                //跳转
                holder.trackItems.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TrackViewHolder tag = (TrackViewHolder) v.getTag();
                        TrackEntity track = ((TrackDosEntity) list.get(tag.getPosition())).getDocs().get(tag.getmPosition());
                        Intent intent = new Intent(v.getContext(), TrackPlayingActivity.class);
                        intent.putExtra("trackId", track.getId());
                        v.getContext().startActivity(intent);
                    }
                });

            }
            for (int i = 0; i < 3 - size; i++) {
                holder.trackItems.get(2 - i).setVisibility(View.GONE);
            }
        }
        if (type == 3) {
            AlbumEntity albumEntity = (AlbumEntity) list.get(position);
            AlbumViewHolder holder = (AlbumViewHolder) convertView.getTag();
            initAlbumItem(albumEntity, holder);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlbumViewHolder tag = (AlbumViewHolder) v.getTag();
                    int index = tag.getPosition();
                    AlbumEntity entity = (AlbumEntity) list.get(index);
                    //点击跳转AlbumDetailFragment
                    Context context = v.getContext();
                    jumpToAlbumDetailFragment(entity, (FragmentActivity) context);
                }
            });
        }
        if (type == 4) {
            final UserInfo userInfo = (UserInfo) list.get(position);
            UserViewHolder holder = (UserViewHolder) convertView.getTag();
            initUserItem(userInfo, holder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserViewHolder tag = (UserViewHolder) v.getTag();
                    FragmentActivity context = (FragmentActivity) v.getContext();
                    UserInfo info = (UserInfo) list.get(tag.getPosition());
                    //设置跳转
                    jumpToUserDetailFragment(context, info);
                }
            });
        }
        if (type == 5) {
            TrackEntity trackEntity = (TrackEntity) list.get(position);
            TrackViewHolder holder = (TrackViewHolder) convertView.getTag();
            initTrackItem(trackEntity, holder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TrackViewHolder tag = (TrackViewHolder) v.getTag();
                    TrackEntity entity = (TrackEntity) list.get(tag.getPosition());

                    Intent intent = new Intent(v.getContext(), TrackPlayingActivity.class);
                    intent.putExtra("trackId", entity.getId());
                    v.getContext().startActivity(intent);
                }
            });
        }
        return convertView;
    }

    private void jumpToUserDetailFragment(FragmentActivity context, UserInfo userInfo) {
        FragmentManager manager = context.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        KeyWordFragment keyWordFragment = ((KeyWordFragment) manager.findFragmentByTag("KeyWordFragment"));
        transaction.setCustomAnimations(R.anim.open_in, R.anim.open_out, R.anim.colse_in, R.anim.colse_out);
        transaction.add(R.id.main_container, UserDetailFragment.newInstance(userInfo.getUid()));
        if (keyWordFragment != null) {
            transaction.hide(keyWordFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void jumpToAlbumDetailFragment(AlbumEntity entity, FragmentActivity context) {
        FragmentManager manager = context.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        KeyWordFragment keyWordFragment = ((KeyWordFragment) manager.findFragmentByTag("KeyWordFragment"));
        transaction.setCustomAnimations(R.anim.open_in, R.anim.open_out, R.anim.colse_in, R.anim.colse_out);
        long albumId=entity.getId()==0?entity.getAlbumId().longValue():entity.getId();
        transaction.add(R.id.main_container, AlbumDetailFragment.newInstance(albumId));
        if (keyWordFragment != null) {
            transaction.hide(keyWordFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //初始化 单一一个Track条目
    private void initTrackItem(TrackEntity trackEntity, TrackViewHolder holder) {
        if (trackEntity.getCover_path() != null) {
            holder.trackCover.setImageURI(Uri.parse(trackEntity.getCover_path()));
        }
        holder.trackTitle.setText(trackEntity.getTitle());
        String text = "by " + trackEntity.getNickname();
        holder.trackIntro.setText(text);
        if (trackEntity.getCount_play() > 0) {
            holder.trackPlayTime.setVisibility(View.VISIBLE);
            holder.trackPlayTime.setText(NumFormat.longToString(trackEntity.getCount_play()));
        } else {
            holder.trackPlayTime.setVisibility(View.GONE);
        }
        if (trackEntity.getCount_like() > 0) {
            holder.trackLike.setVisibility(View.VISIBLE);
            holder.trackLike.setText(NumFormat.longToString(trackEntity.getCount_like()));
        } else {
            holder.trackLike.setVisibility(View.GONE);
        }
        long value = trackEntity.getDuration().longValue();
        if (value > 0) {
            holder.trackAllTime.setVisibility(View.VISIBLE);
            holder.trackAllTime.setText(NumFormat.longToTime(value));
        } else {
            holder.trackAllTime.setVisibility(View.GONE);
        }
        if (trackEntity.getCount_comment() > 0) {
            holder.trackComment.setVisibility(View.VISIBLE);
            holder.trackComment.setText(NumFormat.longToString(trackEntity.getCount_comment()));
        } else {
            holder.trackComment.setVisibility(View.GONE);
        }
    }

    //初始化 单一一个User条目
    private void initUserItem(UserInfo userInfo, UserViewHolder holder) {
        if (userInfo.getSmallPic() != null) {
            holder.userCover.setImageURI(Uri.parse(userInfo.getSmallPic()));
        }
        holder.userTitle.setText(userInfo.getNickname());
        if (userInfo.getTracks_counts() > 0) {
            holder.userTrackCount.setVisibility(View.VISIBLE);
            String text = "声音 " + userInfo.getTracks_counts();
            holder.userTrackCount.setText(text);
        } else {
            holder.userTrackCount.setVisibility(View.GONE);

        }
        if (userInfo.getFollowers_counts() > 0) {
            holder.userFunCount.setVisibility(View.VISIBLE);
            String text = "粉丝 " + NumFormat.longToString(userInfo.getFollowers_counts());
            holder.userFunCount.setText(text);
        } else {
            holder.userFunCount.setVisibility(View.GONE);
        }
        if (userInfo.getIntro() != null) {
            holder.userIntro.setVisibility(View.VISIBLE);
            holder.userIntro.setText(userInfo.getIntro());
        } else {
            holder.userIntro.setVisibility(View.GONE);
        }
        if (userInfo.getVerifyType() == 1) {
            holder.userImgV.setVisibility(View.VISIBLE);
        } else {
            holder.userImgV.setVisibility(View.GONE);
        }
    }

    //初始化 单一一个Album条目
    private void initAlbumItem(AlbumEntity albumEntity, AlbumViewHolder holder) {
        if (albumEntity.getCover_path() != null) {
            holder.albumCover.setImageURI(Uri.parse(albumEntity.getCover_path()));
        } else if (albumEntity.getCoverMiddle() != null) {
            holder.albumCover.setImageURI(Uri.parse(albumEntity.getCoverMiddle()));
        }
        holder.albumTitle.setText(albumEntity.getTitle());
        if (albumEntity.getPlay() > 2000000) {
            holder.albumHotIcon.setVisibility(View.VISIBLE);
        } else {
            holder.albumHotIcon.setVisibility(View.GONE);
        }
        holder.albumIntro.setText(albumEntity.getIntro());
        if (albumEntity.getPlay() > 0) {
            holder.albumPlayTime.setVisibility(View.VISIBLE);
            holder.albumPlayTime.setText(NumFormat.longToString(albumEntity.getPlay()));
        } else if (albumEntity.getPlaysCounts()>0) {
            holder.albumPlayTime.setVisibility(View.VISIBLE);
            holder.albumPlayTime.setText(NumFormat.longToString(albumEntity.getPlaysCounts()));
        } else {
            holder.albumPlayTime.setVisibility(View.GONE);
        }
        if (albumEntity.getTracks() > 0) {
            holder.albumTrackCount.setVisibility(View.VISIBLE);
            holder.albumTrackCount.setText(String.valueOf(albumEntity.getTracks()));
        } else {
            holder.albumTrackCount.setVisibility(View.GONE);
        }
    }

    public static class AlbumDosViewHolder {
        private int position;
        private TextView headerTitle;
        private TextView headerCount;
        private List<View> albumItems;
        private List<AlbumViewHolder> viewHolders;

        public AlbumDosViewHolder(View view, int position) {
            this.position = position;
            viewHolders = new ArrayList<>();
            albumItems = new ArrayList<>();

            View header = view.findViewById(R.id.kw_album_dos_item_header);
            headerTitle = ((TextView) header.findViewById(R.id.kw_dos_item_header_title));
            headerCount = ((TextView) header.findViewById(R.id.kw_dos_item_header_count));

            View album1 = view.findViewById(R.id.kw_album_dos_item_1);
            View album2 = view.findViewById(R.id.kw_album_dos_item_2);
            View album3 = view.findViewById(R.id.kw_album_dos_item_3);

            viewHolders.add(new AlbumViewHolder(album1, position, 0));
            viewHolders.add(new AlbumViewHolder(album2, position, 1));
            viewHolders.add(new AlbumViewHolder(album3, position, 2));
            album1.setTag(viewHolders.get(0));
            album2.setTag(viewHolders.get(1));
            album3.setTag(viewHolders.get(2));

            albumItems.add(album1);
            albumItems.add(album2);
            albumItems.add(album3);
        }
    }

    public static class UserDosViewHolder {
        private TextView headerTitle;
        private TextView headerCount;
        private List<View> userItems;
        private List<UserViewHolder> viewHolders;


        public UserDosViewHolder(View view, int position) {
            viewHolders = new ArrayList<>();

            userItems = new ArrayList<>();
            View header = view.findViewById(R.id.kw_user_dos_item_header);
            headerTitle = ((TextView) header.findViewById(R.id.kw_dos_item_header_title));
            headerCount = ((TextView) header.findViewById(R.id.kw_dos_item_header_count));

            View user1 = view.findViewById(R.id.kw_user_dos_item_1);
            View user2 = view.findViewById(R.id.kw_user_dos_item_2);
            View user3 = view.findViewById(R.id.kw_user_dos_item_3);

            viewHolders.add(new UserViewHolder(user1, position, 0));
            viewHolders.add(new UserViewHolder(user2, position, 1));
            viewHolders.add(new UserViewHolder(user3, position, 2));
            user1.setTag(viewHolders.get(0));
            user2.setTag(viewHolders.get(1));
            user3.setTag(viewHolders.get(2));
            userItems.add(user1);
            userItems.add(user2);
            userItems.add(user3);
        }
    }

    public static class TrackDosViewHolder {
        private TextView headerTitle;
        private TextView headerCount;
        private List<View> trackItems;
        private List<TrackViewHolder> viewHolders;

        public TrackDosViewHolder(View view, int position) {
            viewHolders = new ArrayList<>();
            trackItems = new ArrayList<>();

            View header = view.findViewById(R.id.kw_track_dos_item_header);
            headerTitle = ((TextView) header.findViewById(R.id.kw_dos_item_header_title));
            headerCount = ((TextView) header.findViewById(R.id.kw_dos_item_header_count));

            View track1 = view.findViewById(R.id.kw_track_dos_item_1);
            View track2 = view.findViewById(R.id.kw_track_dos_item_2);
            View track3 = view.findViewById(R.id.kw_track_dos_item_3);

            viewHolders.add(new TrackViewHolder(track1, position, 0));
            viewHolders.add(new TrackViewHolder(track2, position, 1));
            viewHolders.add(new TrackViewHolder(track3, position, 2));
            track1.setTag(viewHolders.get(0));
            track2.setTag(viewHolders.get(1));
            track3.setTag(viewHolders.get(2));

            trackItems.add(track1);
            trackItems.add(track2);
            trackItems.add(track3);
        }
    }

    public static class AlbumViewHolder {
        private int position; //v在list中的位子
        private int mPosition;//v在item中的位置
        private SimpleDraweeView albumCover;
        private ImageView albumHotIcon;
        private TextView albumTitle;
        private TextView albumIntro;
        private TextView albumPlayTime;
        private TextView albumTrackCount;

        public int getPosition() {
            return position;
        }

        public int getmPosition() {
            return mPosition;
        }

        public AlbumViewHolder(View view, int position, int mPosition) {
            this.position = position;
            this.mPosition = mPosition;
            albumCover = ((SimpleDraweeView) view.findViewById(R.id.kw_album_item_cover));
            albumHotIcon = ((ImageView) view.findViewById(R.id.kw_album_item_hot_icon));
            albumTitle = ((TextView) view.findViewById(R.id.kw_album_item_title));
            albumIntro = ((TextView) view.findViewById(R.id.kw_album_item_intro));
            albumPlayTime = ((TextView) view.findViewById(R.id.kw_album_item_play_time));
            albumTrackCount = ((TextView) view.findViewById(R.id.kw_album_item_track_count));
        }
    }

    public static class UserViewHolder {
        private int position; //v在list中的位子
        private int mPosition;//v在item中的位置
        private SimpleDraweeView userCover;
        private TextView userTitle;
        private TextView userTrackCount;
        private TextView userFunCount;
        private TextView userIntro;
        private ImageView userImgV;

        public int getPosition() {
            return position;
        }

        public int getmPosition() {
            return mPosition;
        }

        public UserViewHolder(View view, int position, int mPosition) {
            this.position = position;
            this.mPosition = mPosition;
            userCover = ((SimpleDraweeView) view.findViewById(R.id.kw_user_item_cover));
            userTitle = ((TextView) view.findViewById(R.id.kw_user_item_title));
            userTrackCount = ((TextView) view.findViewById(R.id.kw_user_item_track_count));
            userFunCount = ((TextView) view.findViewById(R.id.kw_user_item_fun_count));
            userIntro = ((TextView) view.findViewById(R.id.kw_user_item_intro));
            userImgV = ((ImageView) view.findViewById(R.id.kw_user_item_is_v));

        }
    }

    public static class TrackViewHolder {
        private int position;
        private int mPosition;
        private SimpleDraweeView trackCover;
        private TextView trackTitle;
        private TextView trackIntro;
        private TextView trackPlayTime;
        private TextView trackAllTime;
        private TextView trackLike;
        private TextView trackComment;

        public int getPosition() {
            return position;
        }

        public int getmPosition() {
            return mPosition;
        }

        public TrackViewHolder(View view, int position, int mPosition) {
            this.position = position;
            this.mPosition = mPosition;
            trackCover = ((SimpleDraweeView) view.findViewById(R.id.kw_track_item_cover));
            trackTitle = ((TextView) view.findViewById(R.id.kw_track_item_title));
            trackIntro = ((TextView) view.findViewById(R.id.kw_track_item_intro));
            trackPlayTime = ((TextView) view.findViewById(R.id.kw_track_item_play_time));
            trackAllTime = ((TextView) view.findViewById(R.id.kw_track_item_all_time));
            trackLike = ((TextView) view.findViewById(R.id.kw_track_item_play_like));
            trackComment = ((TextView) view.findViewById(R.id.kw_track_item_comments));
        }
    }

    private SpannableStringBuilder getSpannableStringBuilder(String data) {
        SpannableStringBuilder builder = null;
        if (!TextUtils.isEmpty(data)) {
            String text = "全部" + data + "条结果";
            builder = new SpannableStringBuilder(text);
            builder.setSpan(new ForegroundColorSpan(Color.RED), 2, text.length() - 3, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return builder;
    }
}
