package com.yushilei.xmly4fm.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.yushilei.xmly4fm.entities.TrackEntity;
import com.yushilei.xmly4fm.receivers.NetStateReceiver;

import java.io.IOException;

public class LocalMediaService extends Service {
    private MediaPlayer player;

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
    }

    public LocalMediaService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Controller controller = new Controller();
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
                if (callBack != null) {
                    callBack.mediaPause();
                }
            } else {
                player.start();
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
}
