package com.xiaoyuan.campus_order.PersonSubs.Integral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.Person.Bean.PersonBalanceBean;
import com.xiaoyuan.campus_order.PersonSubs.Integral.Adapter.IntegralAdapter;
import com.xiaoyuan.campus_order.PersonSubs.Integral.Bean.IntegralBean;
import com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.IntegralExchangeActivity;
import com.xiaoyuan.campus_order.PersonSubs.Integral.Interface.IntegralInterface;
import com.xiaoyuan.campus_order.PersonSubs.Integral.Presenter.IntegralPresenter;
import com.xiaoyuan.campus_order.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class IntegralActivity extends BaseActivity implements IntegralInterface {

    @BindView(R.id.text_integral_exchange)
    TextView mTextIntegralExchange;
    @BindView(R.id.integral_recycler)
    RecyclerView mIntegralRecycler;
    @BindView(R.id.integral_refresh)
    TwinklingRefreshLayout mIntegralRefresh;

    private IntegralPresenter mPresenter = new IntegralPresenter(this);
    private String page;
    private IntegralAdapter integralAdapter;
    private List<IntegralBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        ButterKnife.bind(this);
        customView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        page = "1";
        mPresenter.requestIntegralData(LoginManage.getInstance().getLoginBean().getId(),page);
        requestIntergral();
    }

    private void customView(){

        mPresenter.setContext(IntegralActivity.this);
        mTextIntegralExchange.setVisibility(View.GONE);
        mTextIntegralExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntegralActivity.this, IntegralExchangeActivity.class);
                intent.putExtra("integral",getIntent().getStringExtra("integral"));
                startActivity(intent);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(IntegralActivity.this);
        mIntegralRecycler.setLayoutManager(manager);
        integralAdapter = new IntegralAdapter(mList,getIntent().getStringExtra("integral"),IntegralActivity.this);
        mIntegralRecycler.setAdapter(integralAdapter);
        SinaRefreshView headerView = new SinaRefreshView(IntegralActivity.this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mIntegralRefresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(IntegralActivity.this);
        mIntegralRefresh.setBottomView(loadingView);
        mIntegralRefresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                page = "1";
                mPresenter.requestIntegralData(LoginManage.getInstance().getLoginBean().getId(),page);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                int pageIndex = Integer.parseInt(page)+1;
                page = pageIndex+"";
                mPresenter.requestIntegralData(LoginManage.getInstance().getLoginBean().getId(),page);
            }
        });
    }

    private void requestIntergral(){

        Map<String,String> map = new HashMap<>();
        map.put("uid",LoginManage.getInstance().getLoginBean().getId());
        RequestTools.getInstance().postRequest("/api/getAllPref.api.php", false, map, "", new RequestCallBack(IntegralActivity.this) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    PersonBalanceBean balanceBean = JSON.parseArray(response.getData(),PersonBalanceBean.class).get(0);
                    integralAdapter.reloadIntegral(balanceBean.getIntegral());
                }else {
                    Toasty.error(IntegralActivity.this,response.getMes()).show();
                }
            }
        });
    }

    @Override
    public void requestIntegralSucess(List<IntegralBean> list) {
        mIntegralRefresh.finishLoadmore();
        mIntegralRefresh.finishRefreshing();
        if(page.equals("1")){
            mList.clear();
        }
        mList.addAll(list);
        integralAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestImtegralError(String error) {
        mIntegralRefresh.finishLoadmore();
        mIntegralRefresh.finishRefreshing();
    }
}
