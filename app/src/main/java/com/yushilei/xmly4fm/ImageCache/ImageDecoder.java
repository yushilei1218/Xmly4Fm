package com.yushilei.xmly4fm.ImageCache;

/**
 * Created by yushilei on 2015/12/19.
 */

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * 图片降低内存占用的加载器,采用2次采用的规则来实现缩小尺寸的Bitmap的加载
 */
public final class ImageDecoder {
    private ImageDecoder() {
    }

    /**
     * 加载资源中的图片,并且指定图片显示ImageView的尺寸
     *
     * @param resId
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap loadScaledDowmBitmap(
            Resources resource, int resId, int targetWidth, int targetHeight) {
        Bitmap ret = null;
        //1.获取原始图片的尺寸 ,但不加载Bitmap
        // 通过设置Options参数来只时获取尺寸
        BitmapFactory.Options options = new BitmapFactory.Options();

        //凡是Options当中in开头的代表可以传递给底层图片解码器的参数
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resource, resId, options);

        int outHeight = options.outHeight;
        int outWidth = options.outWidth;

        Log.d("ImageDecoder", "解码边界" + ret);
        Log.d("ImageDecoder", "outHeight" + outHeight);
        Log.d("ImageDecoder", "outWidth" + outWidth);
        Log.d("ImageDecoder", "outMimeType" + options.outMimeType);
        //2.根据尺寸计算inSampleSize
        int sampleSize = calculateInSampleSize(options, targetWidth, targetHeight);
        //3.图片进行加载
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;//重复利用上面的Options即可 ,设置false代表进行实际Bitmap的加载
        ret = BitmapFactory.decodeResource(resource, resId, options);

        return ret;

    }

    /**
     * 官方计算采样比率的算法.利用宽高的一半与sampleSize进行重复的除2计算
     * 计算出当前采样率代表的图片尺寸与木匾是否匹配,找到最小的
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        //官方代码没有检测为0 的情况,我们自己加if (reqHeight > 0 && reqWidth > 0)
        if (reqHeight > 0 && reqWidth > 0) {
            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                //官方没有检测完全匹配的情况,我们自己加"="
                while ((halfHeight / inSampleSize) >= reqHeight
                        && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;
                }
            }
        }

        return inSampleSize;
    }
}
