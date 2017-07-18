package com.xiaoyuan.campus_order.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.xiaoyuan.campus_order.Login.Bean.LoginBean;
import com.xiaoyuan.campus_order.Login.LoginActivity;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.Tab.TabActivity;
import com.squareup.picasso.Picasso;

import qiu.niorgai.StatusBarCompat;

public class SplashActivity extends Activity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //SDK >= 21时, 取消状态栏的阴影
        StatusBarCompat.translucentStatusBar(SplashActivity.this, true);
        mImageView = (ImageView)findViewById(R.id.image_splash);
        Picasso.with(SplashActivity.this).load(R.drawable.splash).fit().centerCrop().into(mImageView);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 2000);
    }

    private void enterHomeActivity(){

        LoginBean loginBean = LoginManage.getInstance().getLoginBean();
        if(loginBean==null||loginBean.getPhone().isEmpty()){
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        Intent intent = new Intent(SplashActivity.this,TabActivity.class);
        startActivity(intent);
        finish();
    }


}
