package com.yushilei.xmly4fm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.client.android.Intents;
import com.yushilei.xmly4fm.fragments.MainFragment;
import com.yushilei.xmly4fm.receivers.NetStateReceiver;
import com.yushilei.xmly4fm.services.LocalMediaService;

public class MainActivity extends AppCompatActivity implements ServiceConnection, LocalMediaService.MediaStateCallBack {

    private SimpleDraweeView playCover;
    public LocalMediaService.Controller mediaController;
    private ImageView playImg;
    private boolean isBind;
    private boolean finishService = false;
    private PopupWindow popQuit;
    private static View parent;
    private TextView historyToastTv;
    private boolean checkLastPlay = true;
    //分享相关
    public static boolean isForeground = false;
    public static PopupWindow popShare;
    public static final int CLOSE_SHARE = 5;
    public static final int OPEN_SHARE = 6;
    public static final int LOADING = 7;
    public static final int UNLOADING = 8;

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case OPEN_SHARE:
                    popShare.showAtLocation(parent, Gravity.CENTER, 0, 0);
                    break;
                case CLOSE_SHARE:
                    popShare.dismiss();
                    break;
                case LOADING:
                    popLoading.showAtLocation(parent, Gravity.CENTER, 0, 0);
                    break;
                case UNLOADING:
                    popLoading.dismiss();
                    break;
            }
        }
    };
    //ZXing二维码相关
    public static final int ZXING_REQUEST = 888;
    private static PopupWindow popLoading;
    private static RotateAnimation loadingAnim;
    private NetStateReceiver netStateReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //动态注册网络监听
        netStateReceiver = new NetStateReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netStateReceiver, filter);

        playCover = (SimpleDraweeView) findViewById(R.id.main_play_cover);
        playImg = (ImageView) findViewById(R.id.main_play_btn);
        parent = findViewById(R.id.main_layout);
        historyToastTv = (TextView) findViewById(R.id.main_history_toast);

        FrameLayout playIcon = (FrameLayout) findViewById(R.id.main_play);
        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaController.playOrPause();
            }
        });
        //开启服务
        Intent intent = new Intent(this, LocalMediaService.class);
        startService(intent);
        //添加Container fragment
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, new MainFragment(), "MainFragment").commit();
        initPopQuit();
        //2016年2月19号增加百度推送功能
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "8kOG6HkVwAsGaGh03sNOMmVd");

        popLoadingAnimation();
        initPopShare();
        initPopLoading();
    }

    private void initPopLoading() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_loading, null, false);
        popLoading = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true) {
            @Override
            public void showAtLocation(View parent, int gravity, int x, int y) {
                super.showAtLocation(parent, gravity, x, y);
                View loadingView = this.getContentView().findViewById(R.id.pop_loading_icon);
                if (loadingAnim != null) {
                    loadingAnim.reset();
                    loadingView.startAnimation(loadingAnim);
                }
            }

            @Override
            public void dismiss() {
                View loadingView = this.getContentView().findViewById(R.id.pop_loading_icon);
                loadingView.clearAnimation();
                super.dismiss();
            }
        };
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mediaController = ((LocalMediaService.Controller) service);
        mediaController.setCallBack(this);
        Log.d("MainActivity", "onServiceConnected " + mediaController);
        if (checkLastPlay) {
            checkLastPlay = false;
            mediaController.checkLastPlay();
            SharedPreferences lastPlay = getSharedPreferences("lastPlay", MODE_PRIVATE);
            String title = lastPlay.getString("title", null);
            if (!TextUtils.isEmpty(title)) {
                historyToastTv.setText(title);
                historyToastTv.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.last_play_toast);
                historyToastTv.setAnimation(animation);
                historyToastTv.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        historyToastTv.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mediaController = null;
    }

    /**
     * MediaController 开始加载时回调activity 处理底部播放布局
     *
     * @param coverPic 回调时传回的 cover链接
     */
    @Override
    public void mediaLoad(String coverPic) {
        Log.d("MainActivity", "mediaLoad");
        playImg.setVisibility(View.VISIBLE);
        playCover.setImageURI(Uri.parse(coverPic));
    }

    @Override
    public void mediaStart() {
        playCoverLoadAnimation();
        playImg.setVisibility(View.GONE);
    }

    /**
     * Media播放完毕 回调
     */
    @Override
    public void mediaEnd() {
        playCoverClearAnimation();
        playImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void mediaPause() {
        playImg.setVisibility(View.VISIBLE);
        playCoverStopAnimation();
    }

    /**
     * playCover 开始播放动画 并显示
     */
    private void playCoverLoadAnimation() {
        playCover.setVisibility(View.VISIBLE);
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(4000);
        animation.setRepeatMode(Animation.INFINITE);
        animation.setRepeatCount(-1);
        animation.setInterpolator(new LinearInterpolator());
        playCover.startAnimation(animation);
    }

    private void popLoadingAnimation() {
        loadingAnim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        loadingAnim.setDuration(1000);
        loadingAnim.setRepeatMode(Animation.INFINITE);
        loadingAnim.setRepeatCount(-1);
        loadingAnim.setInterpolator(new LinearInterpolator());
    }

    /**
     * playCover 清除播放动画 并隐藏
     */
    private void playCoverClearAnimation() {
        playCover.clearAnimation();
        playCover.setVisibility(View.GONE);
    }

    /**
     * playCover 清除播放动画  显示
     */
    private void playCoverStopAnimation() {
        playCover.clearAnimation();
    }

    @Override
    protected void onDestroy() {
        //取消注册
        unregisterReceiver(netStateReceiver);

        if (finishService) {
            Intent intent = new Intent(this, LocalMediaService.class);
            stopService(intent);
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开启服务
        isForeground = true;
        Intent intent = new Intent(this, LocalMediaService.class);
        isBind = bindService(intent, this, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        if (isBind) {
            unbindService(this);
            isBind = false;
        }
        super.onStop();
    }

    private boolean quit = false;

    @Override
    public void finish() {
        if (!quit) {
            if (popQuit.isShowing()) {
                popQuit.dismiss();
            } else {
                popQuit.showAtLocation(parent, Gravity.CENTER, 0, 0);
            }
        } else {
            super.finish();
        }
    }

    private void initPopQuit() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_window_for_quit, null, false);
        popQuit = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        TextView quitTw = (TextView) view.findViewById(R.id.pop_window_quit);
        TextView quitMinsize = (TextView) view.findViewById(R.id.pop_window_mini_size);
        TextView quitCancel = (TextView) view.findViewById(R.id.pop_window_cancel);
        RelativeLayout quitBg = (RelativeLayout) view.findViewById(R.id.pop_window_bg);
        quitTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit = true;
                finishService = true;
                popQuit.dismiss();
                finish();
            }
        });
        quitMinsize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit = true;
                finishService = false;
                popQuit.dismiss();
                finish();
            }
        });
        quitCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popQuit.dismiss();
            }
        });
        quitBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popQuit.dismiss();
            }
        });
    }

    private void initPopShare() {
        View view = LayoutInflater.from(this).inflate(R.layout.share_info_loading, null, false);
        popShare = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true) {
            @Override
            public void showAtLocation(View parent, int gravity, int x, int y) {
                super.showAtLocation(parent, gravity, x, y);
                View loadingView = this.getContentView().findViewById(R.id.share_loading_img);
                if (loadingAnim != null) {
                    loadingAnim.reset();
                    loadingView.startAnimation(loadingAnim);
                }
            }

            @Override
            public void dismiss() {
                View loadingView = this.getContentView().findViewById(R.id.share_loading_img);
                loadingView.clearAnimation();
                super.dismiss();
            }
        };

    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MainActivity", "onActivityResult=" + requestCode);

        //处理ZXing二维码
        if (requestCode == ZXING_REQUEST) {
            if (resultCode == RESULT_OK) {
                String text = data.getStringExtra(Intents.Scan.RESULT);
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
