package com.yushilei.xmly4fm.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.yushilei.xmly4fm.utils.HttpUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yushilei on 2016/2/23.
 */
public class TrackDownAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;

    public TrackDownAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String ret = "no";
        String state = Environment.getExternalStorageState();
        if (params[0] != null && state.equals(Environment.MEDIA_MOUNTED)) {
            File directory = Environment.getExternalStorageDirectory();
            File dir = new File(directory, "xmly4fm");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            byte[] data = HttpUtil.doGetBytes(params[0]);
            try {
                FileOutputStream fos = new FileOutputStream(new File(dir, "aa.m4a"));
                fos.write(data);
                fos.close();
                ret = "yes";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s.equals("yes")) {
            Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
        }
    }
}
