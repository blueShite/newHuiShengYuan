package com.xiaoyuan.campus_order.Tools;

import android.app.Application;
import android.content.Context;

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
    }

    public static Context getContext(){

        return context;
    }

}
