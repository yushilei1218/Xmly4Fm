package com.yushilei.xmly4fm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.xmly4fm.entities.BulletCommentsEntity;
import com.yushilei.xmly4fm.entities.Comment;
import com.yushilei.xmly4fm.entities.TrackEntity;
import com.yushilei.xmly4fm.services.MediaService;
import com.yushilei.xmly4fm.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TrackPlayingActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection, Runnable, MediaService.MediaStateCallBack {

    private MediaService.Controller mediaController;

    private SimpleDraweeView coverBg;
    private RelativeLayout bulletContainer;
    private SeekBar seekBar;
    private TextView trackTitle;
    private TextView albumListTv;

    //存储弹幕的Binding(View 以及被绑定的动画)
    private static List<Binding> bindings = new ArrayList<>();
    //处理弹幕Handler
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Binding binding = bindings.get(msg.what);
            if (commentList.size() > 0) {
                //每次执行动画 就删除掉数据源的第一项数据
                binding.initContent(commentList.remove(0));
                binding.startAnimation();
            }
        }
    };
    //弹幕数据源
    private static List<Comment> commentList = new ArrayList<>();
    private int second = 0;
    private Random random = new Random();
    //指定是否可以播放弹幕
    private boolean openBulletState = true;

    private long trackId;
    private ImageView loadingImg;
    private ImageView pauseImg;
    private ImageView startImg;
    private boolean isBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_playing);
        isBind = bindService(new Intent(this, MediaService.class), this, BIND_AUTO_CREATE);

        ImageView backImg = (ImageView) findViewById(R.id.track_playing_back);
        backImg.setOnClickListener(this);

        coverBg = (SimpleDraweeView) findViewById(R.id.track_playing_cover_bg);
        trackTitle = (TextView) findViewById(R.id.track_playing_title);
        bulletContainer = (RelativeLayout) findViewById(R.id.track_playing_container);
        SwitchCompat bulletSwitch = (SwitchCompat) findViewById(R.id.track_playing_bullet_switch);
        //音频控制部分
        loadingImg = (ImageView) findViewById(R.id.track_playing_loading);
        pauseImg = (ImageView) findViewById(R.id.track_playing_pause);
        startImg = (ImageView) findViewById(R.id.track_playing_start);
        seekBar = (SeekBar) findViewById(R.id.track_playing_seek_bar);
        albumListTv = (TextView) findViewById(R.id.track_playing_album_list);

        //初始化弹幕
        for (int i = 0; i < 6; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.bullet_item, bulletContainer, false);
            bulletContainer.addView(view);
            bindings.add(new Binding(view, bulletContainer, i * 55 + 20));
        }
        Intent intent = getIntent();
        trackId = intent.getLongExtra("trackId", 0);
        if (trackId != 0) {
            //请求本Track 的基本信息 并播放
            NetWorkUtils.getService().getTrackEntity(trackId).enqueue(new Callback<TrackEntity>() {
                @Override
                public void onResponse(Response<TrackEntity> response, Retrofit retrofit) {
                    coverBg.setImageURI(Uri.parse(response.body().getCoverLarge()));
                    trackTitle.setText(response.body().getTitle());
                    mediaController.mediaLoad(response.body());
                    mediaLoading();
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(TrackPlayingActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                }
            });
            //设置 Switch 功能，开启和关闭弹幕
            bulletSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        //开启弹幕
                        openBulletState = true;
                        new Thread(TrackPlayingActivity.this).start();

                    } else {
                        openBulletState = false;
                    }
                }
            });
        }

        pauseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaController.playOrPause();
            }
        });
        startImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaController.playOrPause();
            }
        });
    }

    private void mediaLoading() {
        loadingImg = (ImageView) findViewById(R.id.track_playing_loading);
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(4000);
        animation.setRepeatMode(Animation.INFINITE);
        animation.setRepeatCount(-1);
        loadingImg.startAnimation(animation);
        pauseImg.setVisibility(View.GONE);
        startImg.setVisibility(View.GONE);
    }

    private void mediaLoadingFinished() {
        loadingImg.clearAnimation();
        loadingImg.setVisibility(View.GONE);
        pauseImg.setVisibility(View.VISIBLE);
        startImg.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        if (isBind) {
            unbindService(this);
            isBind = false;
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        openBulletState = false;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mediaController = ((MediaService.Controller) service);
        mediaController.setCallBack(this);
        Log.d("TrackPlayingActivity", "onServiceConnected " + mediaController);

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mediaController = null;
    }

    /**
     * 弹幕执行代码
     */
    @Override
    public void run() {
        if (openBulletState) {
            while (commentList.size() > 0 && openBulletState) {
                Log.d("TrackPlayingActivity", "run");

                try {
                    int index = random.nextInt(bindings.size());
                    int ranSleep = 1000+ random.nextInt(2000);
                    Thread.sleep(ranSleep);
                    Binding binding = bindings.get(index);
                    //如果当前的View不再运行动画状态
                    if (!binding.running) {
                        binding.resetAnimation();
                        handler.sendEmptyMessage(index);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (trackId != 0) {
                NetWorkUtils.getService().getBulletCommentsEntity(second, trackId).enqueue(new Callback<BulletCommentsEntity>() {
                    @Override
                    public void onResponse(Response<BulletCommentsEntity> response, Retrofit retrofit) {
                        commentList = response.body().getData().getComments();
                        second = response.body().getData().getSecond();
                        if (commentList == null || commentList.size() == 0) {
                            openBulletState = false;
                        }
                        new Thread(TrackPlayingActivity.this).start();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }
    }

    @Override
    public void mediaLoad(String coverPic) {

    }

    @Override
    public void mediaStart() {
        mediaLoadingFinished();
    }

    @Override
    public void mediaEnd() {

    }

    @Override
    public void mediaPause() {
        loadingImg.setVisibility(View.GONE);
        pauseImg.setVisibility(View.GONE);
        startImg.setVisibility(View.VISIBLE);
    }

    /**
     * View  动画 绑定类，提供动画的初始化 ，以及View 内容的初始化方法;
     */
    public class Binding {
        private View view;
        //判定当前Binding绑定的View 是否处于运行动画状态
        private boolean running = false;
        //绑定在当前View上的动画
        private TranslateAnimation animation;
        private TextView contentTv;
        private SimpleDraweeView cover;
        private TextView nickNameTv;
        //需要给定父容器 用来获取 容器的宽度
        private View parent;
        //该binding 绑定的view将出现在指定的y上
        private int y;

        public Binding(View view, View parent, int y) {
            this.y = y;
            this.view = view;
            this.parent = parent;
            contentTv = ((TextView) view.findViewById(R.id.bullet_item_content));
            nickNameTv = ((TextView) view.findViewById(R.id.bullet_item_nick_name));
            cover = ((SimpleDraweeView) view.findViewById(R.id.bullet_item_cover));
        }

        public void resetAnimation() {
            animation = new TranslateAnimation(parent.getWidth(), -view.getWidth(), y, y);
            Random random = new Random();
            int ran = 3000 + random.nextInt(3000);
            animation.setDuration(ran);
            animation.setInterpolator(new LinearInterpolator());
        }

        /**
         * 该方法 需要更改View内容 必须主线程来调用
         */
        public void initContent(Comment comment) {
            if (comment != null) {
                contentTv.setText(comment.getContent());
                nickNameTv.setText(comment.getNickname());
                if (comment.getSmallHeader() != null) {
                    cover.setImageURI(Uri.parse(comment.getSmallHeader()));
                }
            }
        }

        public void startAnimation() {
            if (animation != null) {
                view.setVisibility(View.VISIBLE);
                view.startAnimation(animation);
                running = true;
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        running = false;
                        view.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }
    }
}
