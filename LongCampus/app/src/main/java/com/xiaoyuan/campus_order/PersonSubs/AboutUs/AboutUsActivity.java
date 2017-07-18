package com.xiaoyuan.campus_order.PersonSubs.AboutUs;

import android.os.Bundle;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.R;

import butterknife.ButterKnife;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
    }
}
