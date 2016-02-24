package com.yushilei.xmly4fm.ImageCache;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yushilei on 2015/12/19.
 */
public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {
    private WeakReference<ImageView> reference;

    public ImageLoadTask(ImageView imageView) {
        reference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        //1.检测内存缓存
        if (params != null && params.length > 0) {
            bitmap = ImageCache.getInstance().get(params[0]);
            if (bitmap == null) {//内存没有
                //2.检测文件缓存
                byte[] load = FileCache.getOutInstance().load(params[0]);
                if (load == null) {//文件缓存没有
                    //3.联网

                } else {//文件缓存有;转换Bitmap 并且存入 内存缓存
                    // TODO: 2015/12/19 加载网络 请求数据
                    try {
                        URL u = new URL(params[0]);
                        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                        //// TODO: 2015/12/19

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
        Bitmap ret = null;
        return ret;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }
}
