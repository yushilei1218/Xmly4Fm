package com.yushilei.xmly4fm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

public class LoginActivity extends AppCompatActivity implements PlatformActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView weiboLoginTv = (TextView) findViewById(R.id.login_weibo);
        weiboLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.initSDK(v.getContext());
                Platform weibo = ShareSDK.getPlatform(v.getContext(), SinaWeibo.NAME);
                weibo.setPlatformActionListener(LoginActivity.this);
                weibo.showUser(null);//执行登录，登录后在回调里面获取用户资料
            }
        });
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> res) {

            String id,name,description,profile_image_url;
            id=res.get("id").toString();//ID
            name=res.get("name").toString();//用户名
            description=res.get("description").toString();//描述
            profile_image_url=res.get("profile_image_url").toString();//头像链接
            String str="ID: "+id+";\n"+
                    "用户名： "+name+";\n"+
                    "描述："+description+";\n"+
                    "用户头像地址："+profile_image_url;
            System.out.println("用户资料: "+str);

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
