package com.xiaoyuan.campus_order.Tab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.xiaoyuan.campus_order.Circle.CircleFragment;
import com.xiaoyuan.campus_order.Home.HomeFragment;
import com.xiaoyuan.campus_order.Home.SearchSchool.Event.SearchSchoolEvent;
import com.xiaoyuan.campus_order.Information.InformationFragment;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.Person.PersonFragment;
import com.xiaoyuan.campus_order.PushAbout.ExampleUtil;
import com.xiaoyuan.campus_order.PushAbout.LocalBroadcastManager;
import com.xiaoyuan.campus_order.PushAbout.TagAliasOperatorHelper;

import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.Tools.ActivityCollector;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import me.yokeyword.fragmentation.SupportActivity;

import static  com.xiaoyuan.campus_order.PushAbout.TagAliasOperatorHelper.sequence;

public class TabActivity extends SupportActivity {


    @BindView(R.id.contentView)
    FrameLayout mContentView;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;


    private long m_exitTime = 1;
    private HomeFragment mHomeFragment;
    private CircleFragment mCircleFragment;
    private InformationFragment mInformationFragment;
    private PersonFragment mPersonFragment;

    public static boolean isForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ActivityCollector.addActivity(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        injectPages();
        initBottomBar();
        JPushInterface.init(getApplicationContext());
        setAlias();
        Log.e("push注册Id",JPushInterface.getRegistrationID(getApplicationContext()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Subscribe
    public void onMessageEvent(SearchSchoolEvent event) {

        mHomeFragment.reloadHomeData(event.schId);
    }

    private void injectPages() {


        mHomeFragment = HomeFragment.newInstance("首页");
        mInformationFragment = InformationFragment.newInstance("资讯");
        mCircleFragment = CircleFragment.newInstance("圈子");
        mPersonFragment = PersonFragment.newInstance("我的");
        loadMultipleRootFragment(R.id.contentView,0,mHomeFragment,mInformationFragment,mCircleFragment,mPersonFragment);

    }

    public BottomBar getBottomBar() {
        return mBottomBar;
    }

    private void initBottomBar() {

        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.home:
                        mHomeFragment = findFragment(HomeFragment.class);
                        if(mHomeFragment!=null){
                            showHideFragment(mHomeFragment);
                        }
                        break;
                    case R.id.Information:
                        mInformationFragment = findFragment(InformationFragment.class);
                        if(mInformationFragment!=null){
                            showHideFragment(mInformationFragment);
                        }
                        break;
                    case R.id.Circle:
                        mCircleFragment = findFragment(CircleFragment.class);
                        if(mCircleFragment!=null){
                            showHideFragment(mCircleFragment);
                        }
                        break;
                    case R.id.Person:
                        mPersonFragment = findFragment(PersonFragment.class);
                        if(mPersonFragment!=null){
                            showHideFragment(mPersonFragment);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setAlias(){
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.setAction(2);
        tagAliasBean.setAlias(LoginManage.getInstance().getLoginBean().getId());
        tagAliasBean.setAliasAction(true);
        sequence++;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e){
            }
        }
    }

    private void setCostomMsg(String msg){
        Log.e("tabActivity",msg);
    }

    /**
     * 再按一次退出程序
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - m_exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                m_exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
