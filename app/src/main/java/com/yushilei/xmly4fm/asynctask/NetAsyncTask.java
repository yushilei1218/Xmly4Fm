package com.yushilei.xmly4fm.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;

import com.yushilei.xmly4fm.MainActivity;
import com.yushilei.xmly4fm.utils.HttpUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by yushilei on 2016/2/20.
 */
public class NetAsyncTask extends AsyncTask<String, Void, String> {
    private OnekeyShare oks;
    private Context context;

    public void initParams(Context context, OnekeyShare oks) {
        this.context = context;
        this.oks = oks;
    }

    @Override
    protected String doInBackground(String... params) {
        String imgPath = null;
        if (params != null && params[0] != null) {
            byte[] data = HttpUtil.doGetBytes(params[0]);
            File directory = Environment.getExternalStorageDirectory();

            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory, "target.jpg");

            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data, 0, data.length);
                imgPath = file.getAbsolutePath();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgPath;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null && oks != null) {
            oks.setImagePath(s);
            oks.show(context);
            MainActivity.handler.sendEmptyMessage(MainActivity.CLOSE_SHARE);
            //MainActivity.progressBar.setVisibility(View.GONE);
        }
    }
}
