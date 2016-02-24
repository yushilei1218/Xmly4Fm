package com.yushilei.xmly4fm.receivers;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;


public class NetStateReceiver extends BroadcastReceiver {
    public static int CURRENT_NETWORK = -1;
    public static final int NET_WIFI = 0;
    public static final int NET_2G = 2;
    public static final int NET_3G = 3;
    public static final int NET_4G = 4;
    public static final int NET_UNKNOWN = 5;
    public static final int NET_ERROR = 6;

    public NetStateReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("NetStateReceiver", "onReceive");

        String action = intent.getAction();

        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            ConnectivityManager service = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = service.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                int type = networkInfo.getType();

                switch (type) {
                    case ConnectivityManager.TYPE_WIFI:
                        CURRENT_NETWORK = NET_WIFI;
                        break;
                    case ConnectivityManager.TYPE_MOBILE:
                        int subtype = networkInfo.getSubtype();
                        switch (subtype) {
                            case TelephonyManager.NETWORK_TYPE_GPRS: //联通2g
                            case TelephonyManager.NETWORK_TYPE_CDMA: //电信2g
                            case TelephonyManager.NETWORK_TYPE_EDGE: //移动2g
                            case TelephonyManager.NETWORK_TYPE_1xRTT:
                            case TelephonyManager.NETWORK_TYPE_IDEN:
                                CURRENT_NETWORK = NET_2G;
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3g
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                            case TelephonyManager.NETWORK_TYPE_HSUPA:
                            case TelephonyManager.NETWORK_TYPE_HSPA:
                            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                            case TelephonyManager.NETWORK_TYPE_EHRPD:
                            case TelephonyManager.NETWORK_TYPE_HSPAP:
                                CURRENT_NETWORK = NET_3G;
                                break;
                            case TelephonyManager.NETWORK_TYPE_LTE:
                                CURRENT_NETWORK = NET_4G;
                                break;
                            default:
                                CURRENT_NETWORK = NET_UNKNOWN;
                                break;
                        }
                        break;
                    default:
                        CURRENT_NETWORK = NET_UNKNOWN;
                        break;
                }
            } else {
                CURRENT_NETWORK = NET_ERROR;
            }
        }
    }
}
