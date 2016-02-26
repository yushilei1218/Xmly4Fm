package com.yushilei.xmly4fm.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yushilei.xmly4fm.services.LocalMediaService;

/**
 * Created by yushilei on 2016/2/26.
 */
public class NotificationReceiver extends BroadcastReceiver {

    private LocalMediaService service;

    public void setService(LocalMediaService service) {
        this.service = service;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        LocalMediaService.Controller controller = service.getController();
        if (controller != null) {

            String action = intent.getAction();
            switch (action) {
                case CustomAction.ACTION_PLAY:
                    controller.playOrPause();
                    break;
                case CustomAction.ACTION_NEXT:
                    break;
            }
        }
    }
}
