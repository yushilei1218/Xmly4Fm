package com.yushilei.xmly4fm.widgets;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.entities.FocusImageEntity;

import java.util.List;

/**
 * Created by yushilei on 2016/2/5.
 */
public class BannerView extends FrameLayout implements Runnable {
    private ViewPager pager;
    private RadioGroup indicatorContainer;
    private boolean canRun = true;
    private static final int SLEEP_TIME = 3000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pager.setCurrentItem(msg.what);
        }
    };

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        pager = new ViewPager(context);
        indicatorContainer = new RadioGroup(context);
        LayoutParams pagerLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutParams indicatorLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        pager.setLayoutParams(pagerLp);
        indicatorContainer.setOrientation(LinearLayout.HORIZONTAL);
        indicatorLp.bottomMargin = 15;
        indicatorLp.gravity = (Gravity.CENTER | Gravity.BOTTOM);
        indicatorContainer.setLayoutParams(indicatorLp);
        indicatorContainer.setClickable(false);

        addView(pager);
        addView(indicatorContainer);
    }

    public void initBanner(List<FocusImageEntity> data) {
        if (data != null && data.size() > 0) {
            int size = data.size();
            for (int i = 0; i < size; i++) {
                RadioButton button = (RadioButton) LayoutInflater.from(getContext())
                        .inflate(R.layout.banner_indicator, indicatorContainer, false);
                button.setClickable(false);
                button.setId(i);
                RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.rightMargin = 10;
                button.setLayoutParams(lp);
                indicatorContainer.addView(button);
            }
            pager.setAdapter(new BannerAdapter(data));
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    int count = indicatorContainer.getChildCount();
                    int index = position % count;
                    ((RadioButton) indicatorContainer.findViewById(index)).setChecked(true);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            pager.setCurrentItem(100);
            //循环
            if (size > 1) {
                new Thread(this).start();
            }

        }
    }

    @Override
    public void run() {
        while (canRun) {
            try {
                Thread.sleep(SLEEP_TIME);
                int item = pager.getCurrentItem();
                handler.sendEmptyMessage(item + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onStopBanner() {
        canRun = false;
    }

    public class BannerAdapter extends PagerAdapter {
        private List<FocusImageEntity> list;
        private Pools.Pool<SimpleDraweeView> pool;

        public BannerAdapter(List<FocusImageEntity> list) {
            this.list = list;
            pool = new Pools.SimplePool<>(4);
        }

        @Override

        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
            //return view.getTag().equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            SimpleDraweeView view = pool.acquire();
//            if (view == null) {
            SimpleDraweeView view = ((SimpleDraweeView) LayoutInflater.from(getContext()).inflate(R.layout.banner_image, container, false));
//            }
//            view.setId(position);
//            view.setTag(list.get(position));
            view.setImageURI(Uri.parse(list.get(position % list.size()).getPic()));
            container.addView(view);
//            return list.get(position);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
//            SimpleDraweeView view = (SimpleDraweeView) container.findViewById(position);
//            container.removeView(view);
//            pool.release(view);
        }
    }

}
