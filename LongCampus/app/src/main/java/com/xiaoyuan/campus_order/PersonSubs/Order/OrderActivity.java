package com.xiaoyuan.campus_order.PersonSubs.Order;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.OrderNoPayFragment;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.OrderNoReceiveFragment;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.OrderReceiveFragment;
import com.xiaoyuan.campus_order.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class OrderActivity extends SupportActivity {

    @BindView(R.id.button_order_list_noPay)
    Button mButtonOrderListNoPay;
    @BindView(R.id.button_order_list_noreceive)
    Button mButtonOrderListNoreceive;
    @BindView(R.id.button_order_list_receive)
    Button mButtonOrderListReceive;
    @BindView(R.id.frameLayout_order_list)
    FrameLayout mFrameLayoutOrderList;

    private OrderNoPayFragment mNoPayFragment;
    private OrderNoReceiveFragment mNoReceiveFragment;
    private OrderReceiveFragment mReceiveFragment;
    private String selectIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        customView();
    }

    private void customView(){

        selectIndex = getIntent().getStringExtra("selectIndex");
        if(selectIndex.equals("0")){
            mButtonOrderListNoPay.setSelected(true);
        }
        if(selectIndex.equals("1")){
            mButtonOrderListNoreceive.setSelected(true);
        }
        mNoPayFragment = new OrderNoPayFragment();
        mNoReceiveFragment = new OrderNoReceiveFragment();
        mReceiveFragment = new OrderReceiveFragment();

        loadMultipleRootFragment(R.id.frameLayout_order_list,Integer.parseInt(selectIndex),mNoPayFragment,mNoReceiveFragment,mReceiveFragment);


    }

    @OnClick({R.id.button_order_list_noPay, R.id.button_order_list_noreceive, R.id.button_order_list_receive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_order_list_noPay:
                mButtonOrderListNoPay.setSelected(true);
                mButtonOrderListNoreceive.setSelected(false);
                mButtonOrderListReceive.setSelected(false);
                mNoPayFragment = findFragment(OrderNoPayFragment.class);
                if(mNoPayFragment!=null){
                    showHideFragment(mNoPayFragment);
                }
                break;
            case R.id.button_order_list_noreceive:
                mButtonOrderListNoPay.setSelected(false);
                mButtonOrderListNoreceive.setSelected(true);
                mButtonOrderListReceive.setSelected(false);
                mNoReceiveFragment = findFragment(OrderNoReceiveFragment.class);
                if(mNoReceiveFragment!=null){
                    showHideFragment(mNoReceiveFragment);
                }
                break;
            case R.id.button_order_list_receive:
                mButtonOrderListNoPay.setSelected(false);
                mButtonOrderListNoreceive.setSelected(false);
                mButtonOrderListReceive.setSelected(true);
                mReceiveFragment = findFragment(OrderReceiveFragment.class);
                if(mReceiveFragment!=null){
                    showHideFragment(mReceiveFragment);
                }
                break;
        }
    }
}
