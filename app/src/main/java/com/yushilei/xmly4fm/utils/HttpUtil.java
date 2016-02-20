package com.yushilei.xmly4fm.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yushilei on 2016/2/20.
 */
public class HttpUtil {
    private HttpUtil() {
    }

    public static InputStream getInputStream(String coverPath) {
        InputStream inputStream = null;
        if (coverPath != null) {
            URL url = null;
            try {
                url = new URL(coverPath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                if (conn.getResponseCode() == 200) {
                    inputStream = conn.getInputStream();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream;
    }

    public static byte[] doGetBytes(String coverPath) {
        byte[] data = null;
        InputStream stream = getInputStream(coverPath);
        if (stream != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte arr[] = new byte[1024];
            int len = -1;
            try {
                while ((len = stream.read(arr)) != -1) {
                    bos.write(arr, 0, len);
                }
                data = bos.toByteArray();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

}
