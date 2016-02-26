package com.yushilei.xmly4fm.utils;

/**
 * Created by yushilei on 2016/1/23.
 */
public class NumFormat {
    private static long KB = 1024;
    private static long MB = 1024 * 1024;
    private static long GB = 1024 * 1024 * 1024;

    public static String longToTime(long time) {
        String timeStr = null;
        long hour = 0;
        long minute = 0;
        long second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static String unitFormat(long i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Long.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String longToString(long data) {
        String format = null;
        if (data >= 100000000) {
            double v = data / 100000000.0;
            format = String.format("%.1f", v) + "亿";
        } else if (data >= 10000) {
            double v = data / 10000.0;
            format = String.format("%.1f", v) + "万";
        } else if (data >= 1000) {
            double v = data / 1000.0;
            format = String.format("%.1f", v) + "千";
        } else {
            format = String.valueOf(data);
        }
        return format;
    }

    public static String longToKbMbGb(long data) {
        String ret = null;
        if (data >= GB) {
            long gb = data / GB;
            ret = gb + "G";
        } else if (data >= MB) {
            long mb = data / MB;
            ret = mb + "M";
        } else if (data > KB) {
            long kb = data / KB;
            ret = kb + "K";
        } else {
            ret = "0K";
        }

        return ret;
    }
}
