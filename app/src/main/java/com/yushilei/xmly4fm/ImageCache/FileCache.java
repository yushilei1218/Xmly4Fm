package com.yushilei.xmly4fm.ImageCache;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 单例的形式 完成文件缓存的使用
 */
public class FileCache {
    private static FileCache outInstance;
    private File cacheDir;

    private FileCache() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            //获取根目录
            File directory = Environment.getExternalStorageDirectory();
            //路径自己定义
            cacheDir = new File(directory, ".MyApp/cache");
            if (!cacheDir.exists()) {
                cacheDir.mkdir();// TODO: 2015/12/19  写存储卡权限
            }
        }
    }

    public static FileCache getOutInstance() {
        if (outInstance == null) {
            outInstance = new FileCache();
        }
        return outInstance;
    }

    private String md5String(String url) {
        String ret = null;
        if (url != null) {
            try {
                //创建MD5的消息摘要算法的实例 进行调用
                MessageDigest digest = MessageDigest.getInstance("MD5");
                //计算出一个唯一识别的信息
                byte[] data = digest.digest(url.getBytes());
                StringBuilder sb = new StringBuilder();
                //将数组转成16进制 String
                for (byte b : data) {
                    int ib = b & 0x0FF;
                    String s = Integer.toHexString(ib);
                    if (ib < 16) {
                        sb.append(0);
                    }
                    sb.append(s);
                }
                ret = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public void save(String url, byte[] data) {
        // TODO: 2015/12/19 保存数据
        if (url != null && data != null) {
            if (data.length > 0) {
                //创建文件前 url 映射到MD5
                String fileName = md5String(url);
                FileOutputStream fout = null;

                File targetFile = new File(cacheDir, fileName);
                try {
                    fout = new FileOutputStream(targetFile);
                    fout.write(data);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public byte[] load(String url) {

        byte[] ret = null;
        if (url != null) {
            String fileName = md5String(url);
            File targetFile = new File(cacheDir, fileName);
            if (targetFile.exists()) {
                FileInputStream fin = null;
                ByteArrayOutputStream bos = null;
                try {
                    fin = new FileInputStream(targetFile);
                    bos = new ByteArrayOutputStream();
                    byte[] arr = new byte[1024];
                    int len = 0;
                    while ((len = fin.read(arr)) != -1) {
                        bos.write(arr, 0, len);
                    }
                    ret = bos.toByteArray();
                }  catch (IOException e) {
                    e.printStackTrace();
                }
                if (fin != null) {
                    try {
                        fin.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return ret;
    }

}
