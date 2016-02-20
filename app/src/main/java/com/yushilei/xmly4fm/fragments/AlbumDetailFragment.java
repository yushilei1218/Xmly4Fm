package com.yushilei.xmly4fm.fragments;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.xmly4fm.MainActivity;
import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.TrackListAdapter;
import com.yushilei.xmly4fm.asynctask.NetAsyncTask;
import com.yushilei.xmly4fm.entities.AlbumDetailEntity;
import com.yushilei.xmly4fm.entities.AlbumEntity;
import com.yushilei.xmly4fm.entities.TrackEntity;
import com.yushilei.xmly4fm.entities.share.ShareEntity;
import com.yushilei.xmly4fm.utils.NetWorkUtils;
import com.yushilei.xmly4fm.utils.NumFormat;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumDetailFragment extends Fragment implements Callback<AlbumDetailEntity> {


    private TrackListAdapter adapter;
    private MainActivity activity;
    private SimpleDraweeView headerBg;
    private TextView albumTitle;
    private SimpleDraweeView albumCover;
    private TextView albumPlayTime;
    private SimpleDraweeView albumIcon;
    private TextView nickName;
    private TextView albumIntro;
    private TextView tracksCount;
    private LinearLayout albumTagContainer;
    private Call<AlbumDetailEntity> call;
    private long albumId = -1;

    public AlbumDetailFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = ((MainActivity) context);
    }

    public static AlbumDetailFragment newInstance(Long albumId) {
        Bundle bundle = new Bundle();
        bundle.putLong("albumId", albumId);
        AlbumDetailFragment fragment = new AlbumDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        albumId = bundle.getLong("albumId");
        call = NetWorkUtils.getService().getAlbumDetailEntity(albumId, 1);
        call.enqueue(this);
    }

    @Override
    public void onDestroy() {
        call.cancel();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        headerBg = ((SimpleDraweeView) view.findViewById(R.id.album_detail_header_bg));
        albumTitle = ((TextView) view.findViewById(R.id.album_detail_header_title));
        albumCover = ((SimpleDraweeView) view.findViewById(R.id.album_detail_cover));
        albumIcon = ((SimpleDraweeView) view.findViewById(R.id.album_detail_icon));
        albumPlayTime = ((TextView) view.findViewById(R.id.album_detail_play_time));
        nickName = ((TextView) view.findViewById(R.id.album_detail_nick_name));
        albumIntro = ((TextView) view.findViewById(R.id.album_detail_intro));
        tracksCount = ((TextView) view.findViewById(R.id.album_detail_tracks_count));
        albumTagContainer = ((LinearLayout) view.findViewById(R.id.album_detail_tag_container));

        ImageView shareImg = (ImageView) view.findViewById(R.id.album_detail_share);

        addOnClickToShareImg(shareImg);

        TextView albumSort = ((TextView) view.findViewById(R.id.album_detail_sort));
        TextView albumSelections = ((TextView) view.findViewById(R.id.album_detail_selections));

        //返还键功能实现
        ImageView imgBack = (ImageView) view.findViewById(R.id.album_detail_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().popBackStack();
            }
        });
        //
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.album_detail_recycler);
        adapter = new TrackListAdapter(getContext(), activity);
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
    }

    private void addOnClickToShareImg(ImageView shareImg) {
        shareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (albumId != -1) {
                    if (MainActivity.isForeground) {
                        // MainActivity.progressBar.setVisibility(View.VISIBLE);
                        MainActivity.handler.sendEmptyMessage(MainActivity.OPEN_SHARE);
                    }
                    NetWorkUtils.getService().getShareEntity(albumId, "qq").enqueue(new Callback<ShareEntity>() {
                        @Override
                        public void onResponse(Response<ShareEntity> response, Retrofit retrofit) {
                            ShareEntity body = response.body();
                            ShareSDK.initSDK(getContext());
                            OnekeyShare oks = new OnekeyShare();
                            oks.disableSSOWhenAuthorize();
                            oks.setTitle(body.getTitle());
                            oks.setText(body.getContent());
                            oks.setSite(getString(R.string.app_name));
                            oks.setUrl(body.getUrl());
                            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片

                            NetAsyncTask netAsyncTask = new NetAsyncTask();
                            netAsyncTask.initParams(getContext(), oks);
                            netAsyncTask.execute(body.getWeixinPic());
                            // 启动分享GUI
                            //oks.show(getContext());
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            t.printStackTrace();
                            MainActivity.handler.sendEmptyMessage(MainActivity.CLOSE_SHARE);
                            Toast.makeText(getContext(), "网络异常", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onResponse(Response<AlbumDetailEntity> response, Retrofit retrofit) {
        AlbumEntity album = response.body().getAlbum();
        if (album != null) {
            headerBg.setImageURI(Uri.parse(album.getCoverLarge()));
            albumTitle.setText(album.getTitle());
            albumCover.setImageURI(Uri.parse(album.getCoverLarge()));
            if (album.getPlayTimes() > 0) {
                String playTime = ">" + NumFormat.longToString(album.getPlayTimes());
                albumPlayTime.setText(playTime);
            }
            if (album.getAvatarPath() != null) {
                albumIcon.setImageURI(Uri.parse(album.getAvatarPath()));
            } else {
                albumIcon.setVisibility(View.GONE);
            }
            nickName.setText(album.getNickname());
            if (album.getIntro() != null) {
                albumIntro.setText(album.getIntro());
            }
            if (album.getTracks() > 0) {
                String text = "共" + album.getTracks() + "集";
                tracksCount.setText(text);
            }
            if (album.getTags() != null) {
                String[] tags = album.getTags().split(",");
                int min = Math.min(tags.length, 3);
                for (int i = 0; i < min; i++) {
                    TextView view = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.album_tag_text_view, albumTagContainer, false);
                    view.setText(tags[i]);
                    albumTagContainer.addView(view);
                }
            }

        }
        List<TrackEntity> list = response.body().getTracks().getList();
        adapter.addAll(list);
        if (list != null && list.size() > 0) {
            if (activity.mediaController.isPlaying() && activity.mediaController.getRecorder() == null) {

            } else if (activity.mediaController.getRecorder() != null) {
                //获取当前正在播放的track
                Long trackId = activity.mediaController.getRecorder().getTrackId();
                int size = list.size();
                int i = 0;
                for (; i < size; i++) {
                    if (list.get(i).getTrackId().equals(trackId)) {
                        break;
                    }
                }
                //如果最新加载的track list中包含已经播放的track id则同步;否则播放第一个
                if (i < size) {
                    adapter.syncPlayingTrack(i);
                }
            } else {
                adapter.loadFirstTrack();
            }
        }
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(getContext(), "网络异常", Toast.LENGTH_SHORT).show();
    }
}
