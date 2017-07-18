package com.xiaoyuan.campus_order.PersonSubs.Order.OrderDetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderDetail.Adapter.OrderDetailAdapter;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderDetail.Bean.OrderDetailBean;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderDetail.Interface.OrderDetailInterface;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderDetail.Presenter.OrderDetailPresenter;
import com.xiaoyuan.campus_order.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity implements OrderDetailInterface {

    @BindView(R.id.order_detail_recycle)
    RecyclerView mOrderDetailRecycle;

    private OrderDetailAdapter mAdapter;
    private String mOrderId;
    private OrderDetailPresenter mPresenter = new OrderDetailPresenter(this);
    private OrderDetailBean mDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        customView();
        mPresenter.requestOrderDetail(mOrderId);
    }

    private void customView(){

        mPresenter.setContext(OrderDetailActivity.this);
        mOrderId = getIntent().getStringExtra("orderId");
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderDetailActivity.this);
        mOrderDetailRecycle.setLayoutManager(layoutManager);
    }

    @Override
    public void requestOrderDetailSucess(OrderDetailBean detailBean) {
        mDetailBean = detailBean;
        mAdapter = new OrderDetailAdapter(detailBean,OrderDetailActivity.this);
        mOrderDetailRecycle.setAdapter(mAdapter);
    }
}
