package com.xiaoyuan.campus_order.PersonSubs.Coupon;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.PersonSubs.Coupon.Adapter.CouponAdapter;
import com.xiaoyuan.campus_order.PersonSubs.Coupon.Bean.CouponBean;
import com.xiaoyuan.campus_order.PersonSubs.Coupon.Interface.CouponInterface;
import com.xiaoyuan.campus_order.PersonSubs.Coupon.Presenter.CouponPresenter;
import com.xiaoyuan.campus_order.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouponOverdueActivity extends BaseActivity implements CouponInterface {

    @BindView(R.id.coupon_overdue_recycle)
    RecyclerView mCouponOverdueRecycle;

    private CouponPresenter mPresenter = new CouponPresenter(this);
    private List<CouponBean> mList = new ArrayList<>();
    private CouponAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_overdue);
        ButterKnife.bind(this);
        customView();
        mPresenter.requestList(LoginManage.getInstance().getLoginBean().getId(),"1","0");
    }

    private void customView(){

        mPresenter.setContext(CouponOverdueActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CouponOverdueActivity.this);
        mCouponOverdueRecycle.setLayoutManager(layoutManager);
        mAdapter = new CouponAdapter(mList,CouponOverdueActivity.this,this);
        mCouponOverdueRecycle.setAdapter(mAdapter);

    }

    @Override
    public void requestCouponList(List<CouponBean> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestCouponError(String error) {

    }

    @Override
    public void onClickCoupon(int poist) {

    }

    @Override
    public void requestReceiveSucess(int poist) {

    }
}
