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
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouponReceiveActivity extends BaseActivity implements CouponInterface {

    @BindView(R.id.coupon_receive_recycle)
    RecyclerView mCouponReceiveRecycle;
    @BindView(R.id.coupon_receive_refresh)
    TwinklingRefreshLayout mCouponReceiveRefresh;

    private CouponPresenter mPresenter = new CouponPresenter(this);
    private List<CouponBean> mList = new ArrayList<>();
    private CouponAdapter mAdapter;
    private String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_receive);
        ButterKnife.bind(this);
        customView();
        page = "1";
        mPresenter.requestReceiveList(LoginManage.getInstance().getLoginBean().getId(),page);
    }

    private void customView(){

        mPresenter.setContext(CouponReceiveActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CouponReceiveActivity.this);
        mCouponReceiveRecycle.setLayoutManager(layoutManager);
        mAdapter = new CouponAdapter(mList,CouponReceiveActivity.this,this);
        mCouponReceiveRecycle.setAdapter(mAdapter);

        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(CouponReceiveActivity.this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mCouponReceiveRefresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(CouponReceiveActivity.this);
        mCouponReceiveRefresh.setBottomView(loadingView);
        mCouponReceiveRefresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                page = "1";
                mPresenter.requestReceiveList(LoginManage.getInstance().getLoginBean().getId(),page);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                int pageIndex = Integer.parseInt(page)+1;
                page = pageIndex+"";
                mPresenter.requestReceiveList(LoginManage.getInstance().getLoginBean().getId(),page);
            }
        });
    }

    @Override
    public void requestCouponList(List<CouponBean> list) {

        mCouponReceiveRefresh.finishLoadmore();
        mCouponReceiveRefresh.finishRefreshing();
        if(page.equals("1")){
            mList.clear();
        }
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestCouponError(String error) {
        mCouponReceiveRefresh.finishLoadmore();
        mCouponReceiveRefresh.finishRefreshing();
    }

    @Override
    public void onClickCoupon(int poist) {
        mPresenter.requestReceiveCoupon(mList.get(poist).getId(),LoginManage.getInstance().getLoginBean().getId(),poist);
    }

    @Override
    public void requestReceiveSucess(int poist) {
        mList.remove(poist);
        mAdapter.notifyDataSetChanged();
    }
}
