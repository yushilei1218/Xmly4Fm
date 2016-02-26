package com.yushilei.xmly4fm.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.opengl.Visibility;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.TrackPlayingActivity;
import com.yushilei.xmly4fm.entities.TrackEntity;
import com.yushilei.xmly4fm.receivers.CustomAction;
import com.yushilei.xmly4fm.receivers.NetStateReceiver;
import com.yushilei.xmly4fm.receivers.NotificationReceiver;

import java.io.IOException;

public class LocalMediaService extends Service {
    private MediaPlayer player;
    //自定义receiver 用来监听 RemoteViews 内部控件的点击事件
    private NotificationReceiver receiver;

    private Notification notification;
    private NotificationManager manager;
    private static final int NOTIFICATION_ID = 0;
    //用来记录当前是否正处于播放的状态
    private static boolean isPlaying = false;
    private Controller controller;

    public Controller getController() {
        return controller;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d("LocalMediaService", "onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("LocalMediaService", "onUnbind");
        return false;
        // return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        manager.cancel(NOTIFICATION_ID);
        manager.cancelAll();
        //销毁Service时 取消注册监听
        unregisterReceiver(receiver);
        try {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("LocalMediaService", "service被创建=" + this);

        player = new MediaPlayer();
        //注册一个自定义的BroadReceiver来实现 NotificationLayout 点击的监听
        receiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(CustomAction.ACTION_PLAY);
        registerReceiver(receiver, filter);
        receiver.setService(this);
        //获取通知管理
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //初始化Notification
        initNotification();
    }

    public LocalMediaService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        controller = new Controller();
        Log.d("LocalMediaService", "controller被创建=" + controller);
        return controller;
    }

    public class Controller extends Binder implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
        //用来回调activity
        private MediaStateCallBack callBack;
        //用来记录当前播放的数据
        private TrackEntity recorder;

        public void setCallBack(MediaStateCallBack callBack) {
            this.callBack = callBack;
        }

        public void mediaLoad(TrackEntity entity) {
            recorder = entity;
            //存储数据
            saveLastPlay(entity);
            try {
                String playUrl = null;
                //发送通知


                switch (NetStateReceiver.CURRENT_NETWORK) {
                    case NetStateReceiver.NET_2G:
                    case NetStateReceiver.NET_3G:
                    case NetStateReceiver.NET_4G:
                        Log.d("Controller", "NET_2G or 3G");
                        playUrl = entity.getPlayUrl32();
                        break;
                    case NetStateReceiver.NET_WIFI:
                        Log.d("Controller", "NET_Wifi");
                        playUrl = entity.getPlayUrl64();
                        break;
                    default:
                        playUrl = entity.getPlayUrl32();
                        break;
                }
                isPlaying = true;
                updateRemoteViews(entity);
                player.reset();
                player.setDataSource(playUrl);
                player.prepareAsync();
                player.setOnPreparedListener(this);
                player.setOnCompletionListener(this);
                if (callBack != null) {
                    callBack.mediaLoad(entity.getCoverLarge());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void checkLastPlay() {
            SharedPreferences lastPlay = getSharedPreferences("lastPlay", MODE_PRIVATE);
            String playUrl = lastPlay.getString("playUrl", null);
            String cover = lastPlay.getString("cover", null);
            if (!TextUtils.isEmpty(playUrl) && !TextUtils.isEmpty(cover)) {
                if (player.isPlaying()) {
                    if (callBack != null) {
                        callBack.mediaLoad(cover);
                        callBack.mediaStart();
                    }
                } else {
                    try {
                        player.reset();
                        player.setDataSource(playUrl);
                        player.prepareAsync();
                        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                if (callBack != null) {
                                    callBack.mediaPause();
                                }
                            }
                        });
                        player.setOnCompletionListener(this);
                        if (callBack != null) {
                            callBack.mediaLoad(cover);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void saveLastPlay(TrackEntity entity) {
            SharedPreferences lastPlay = getSharedPreferences("lastPlay", MODE_PRIVATE);
            SharedPreferences.Editor edit = lastPlay.edit();
            edit.putString("playUrl", entity.getPlayUrl32());
            edit.putString("cover", entity.getCoverLarge());
            edit.putString("title", entity.getTitle());
            edit.apply();
        }

        public void playOrPause() {
            if (player.isPlaying()) {
                player.pause();
                isPlaying = false;
                updateRemoteViews(null);
                if (callBack != null) {
                    callBack.mediaPause();
                }
            } else {
                player.start();
                isPlaying = true;
                updateRemoteViews(null);
                if (callBack != null) {
                    callBack.mediaStart();
                }
            }
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
            if (callBack != null) {
                callBack.mediaStart();
            }
        }

        //音乐播放结束
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (callBack != null) {
                callBack.mediaEnd();
            }
        }

        public TrackEntity getRecorder() {
            return recorder;
        }

        public boolean isPlaying() {
            return player.isPlaying();
        }
    }

    public interface MediaStateCallBack {
        void mediaLoad(String coverPic);//音频开始加载

        void mediaStart();//音频加载完成 开始播放

        void mediaEnd();//音频播放完毕

        void mediaPause();//音频播放暂停
    }

    private void initNotification() {
        Intent intent = new Intent(this, TrackPlayingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.notification_layout);
        notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ting)//!!!这个不管在什么情况下都需要设置的，否则出不了Notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setContent(views).build();
        //给通知上的按钮加监听，当点击时发送广播
        views.setOnClickPendingIntent(R.id.not_pause,
                PendingIntent.getBroadcast(
                        this, 0, new Intent(CustomAction.ACTION_PLAY), PendingIntent.FLAG_UPDATE_CURRENT));
        views.setOnClickPendingIntent(R.id.not_play,
                PendingIntent.getBroadcast(
                        this, 0, new Intent(CustomAction.ACTION_PLAY), PendingIntent.FLAG_UPDATE_CURRENT));
        notification.flags = Notification.FLAG_NO_CLEAR;
    }

    private void updateRemoteViews(TrackEntity entity) {
        if (entity != null) {
            notification.contentView.setTextViewText(R.id.not_content, entity.getTitle());
            notification.contentView.setTextViewText(R.id.not_title, entity.getAlbumTitle());
        }
        if (isPlaying) {
            notification.contentView.setViewVisibility(R.id.not_play, View.GONE);
            notification.contentView.setViewVisibility(R.id.not_pause, View.VISIBLE);
        } else {
            notification.contentView.setViewVisibility(R.id.not_play, View.VISIBLE);
            notification.contentView.setViewVisibility(R.id.not_pause, View.GONE);
        }
        manager.notify(null, NOTIFICATION_ID, notification);
    }
}
