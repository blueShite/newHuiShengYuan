package com.xiaoyuan.campus_order.Tools;

import android.app.Application;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by longhengyu on 2017/3/8.
 */

public class MyApplication extends Application {

    //方便调用Context
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
    }

    public static Context getContext(){

        return context;
    }

}
