package com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.Adapter.IntegralExchangeAdapter;
import com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.Bean.IntergralExchangeBean;
import com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.Interface.IntegarlExchangeInterface;
import com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.Presenter.IntegralExchangePresenter;
import com.xiaoyuan.campus_order.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntegralExchangeActivity extends BaseActivity implements IntegarlExchangeInterface {

    @BindView(R.id.integral_exchange_recycle)
    RecyclerView mIntegralExchangeRecycle;

    private IntegralExchangePresenter mPresenter = new IntegralExchangePresenter(this);
    private List<IntergralExchangeBean> mList = new ArrayList<>();
    private IntegralExchangeAdapter mAdapter;
    private String userIntegarl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integer_exchange);
        ButterKnife.bind(this);
        customView();
        mPresenter.requestIntegarlList();
    }

    private void customView(){

        mPresenter.setContext(IntegralExchangeActivity.this);
        userIntegarl = getIntent().getStringExtra("integral");
        GridLayoutManager layoutManager = new GridLayoutManager(IntegralExchangeActivity.this, 2);
        mIntegralExchangeRecycle.setLayoutManager(layoutManager);
        mAdapter = new IntegralExchangeAdapter(mList,IntegralExchangeActivity.this,this);
        mIntegralExchangeRecycle.setAdapter(mAdapter);

    }

    @Override
    public void requestListSucess(List<IntergralExchangeBean> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestExChangeSuccess(int poist) {

    }

    @Override
    public void onClickExchangeSucess(int poist) {

        IntergralExchangeBean bean = mList.get(poist);
        mPresenter.requestExchange(LoginManage.getInstance().getLoginBean().getId(),
                bean.getId(),bean.getPrice(),userIntegarl,poist);
    }
}
