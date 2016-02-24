package com.yushilei.xmly4fm.ImageCache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by yushilei on 2015/12/19.
 */
public class ImageCache {


    private LruCache<String, Bitmap> memoryCache;
    //为了充分利用内存空间,使用HashMap保存软引用对象,在内存不足的时候会被回收,提高内存利用率
    private HashMap<String, SoftReference<Bitmap>> softReferenceHashMap;

    private static ImageCache ourInstance = new ImageCache();

    public static ImageCache getInstance() {
        return ourInstance;
    }

    private ImageCache() {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        Log.d("MainActivity", "totalMemory=" + totalMemory / 1024 / 1024 +
                " maxMemory=" + maxMemory / 1024 / 1024 + " freeMemory=" + freeMemory / 1024 / 1024);

        softReferenceHashMap = new HashMap<String, SoftReference<Bitmap>>();

        //使用内存占用总数.可以采用maxMemory
        memoryCache = new LruCache<String, Bitmap>((int) maxMemory / 8) {
            //重写sizeOf来测量Bitmap占用的字节数
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //value.getRowBytes()获取图片一行所占用的字节数
                //value.getHeight() 获取图片有多少行
                return value.getRowBytes() * value.getHeight();
            }

            /**
             * 当一个缓存项被移出或者删除的时候 ,来进行回调这个方法
             */

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                //如果当前
                if (evicted) {
                    //代表被移出,释放空间的时候
                    //将oldValue存储在软引用的缓存中
                    SoftReference<Bitmap> ref = new SoftReference<Bitmap>(oldValue);
                    softReferenceHashMap.put(key, ref);
                }
            }

            /**
             * 当LruCache内部存储没有找到对应key的对象,就会尝试调用Create方法,如果这个方法返回为null
             * 代表内存中不存在对象;
             * 如果不为null 将会直接使用这个对象;
             * 作用:给开发人员一个从softReference 对象中查找和创建 对象 的一个机会
             * @param key
             * @return
             */
            @Override
            protected Bitmap create(String key) {
                Bitmap bitmap = null;
                // TODO: 获取
                SoftReference<Bitmap> reference = softReferenceHashMap.get(key);
                if (reference != null) {
                    bitmap = reference.get();
                    //既然已经返回了Bitmap 不管bitmap 是否有值 ,softReferenceHashMap对于的key都没有用了
                    softReferenceHashMap.remove(key);
                }
                return bitmap;
            }
        };
    }

    public Bitmap get(String url) {
        return memoryCache.get(url);
    }

    public void put(String url, Bitmap bitmap) {

    }

}
