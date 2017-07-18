package com.xiaoyuan.campus_order.ShopCart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.FootDetail.FootDetailActivity;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.ShopCart.Adapter.TodayFeatureAdapter;
import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartBean;
import com.xiaoyuan.campus_order.ShopCart.Interface.TodayFeatureInterface;
import com.xiaoyuan.campus_order.ShopCart.Presenter.TodayFeaturePresenter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayFeatureActivity extends BaseActivity implements TodayFeatureInterface {

    @BindView(R.id.todayFeature_recycle)
    RecyclerView mTodayFeatureRecycle;
    @BindView(R.id.todayFeature_fefresh)
    TwinklingRefreshLayout mTodayFeatureFefresh;
    @BindView(R.id.text_todayFeature_title)
    TextView mTextTodayFeatureTitle;

    private TodayFeaturePresenter mPresenter = new TodayFeaturePresenter(this);
    private List<ShopCartBean> mList = new ArrayList<>();
    private String pathStr;
    private String page;
    private String index;
    private String resId;
    private TodayFeatureAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_feature);
        ButterKnife.bind(this);
        customView();
        page = "1";
        pathStr = getIntent().getStringExtra("path");
        index = getIntent().getStringExtra("index");
        resId = getIntent().getStringExtra("res_id");
        mPresenter.requestItem(pathStr,page,resId,index);
    }

    private void customView(){

        mPresenter.setContext(TodayFeatureActivity.this);
        mTextTodayFeatureTitle.setText(getIntent().getStringExtra("TitleName"));
        LinearLayoutManager manager = new LinearLayoutManager(TodayFeatureActivity.this);
        mTodayFeatureRecycle.setLayoutManager(manager);
        mAdapter = new TodayFeatureAdapter(mList,this,TodayFeatureActivity.this);
        mTodayFeatureRecycle.setAdapter(mAdapter);

        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(TodayFeatureActivity.this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mTodayFeatureFefresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(TodayFeatureActivity.this);
        mTodayFeatureFefresh.setBottomView(loadingView);
        mTodayFeatureFefresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                page = "1";
                mPresenter.requestItem(pathStr,page,resId,index);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                int pageIndex = Integer.parseInt(page)+1;
                page = pageIndex+"";
                mPresenter.requestItem(pathStr,page,resId,index);
            }
        });
    }


    @Override
    public void requestItemSuccess(List<ShopCartBean> list, String tag, String page) {
        mTodayFeatureFefresh.finishRefreshing();
        mTodayFeatureFefresh.finishLoadmore();
        if(page.equals("1")){
            mList.clear();
        }
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestItemError(String error) {
        mTodayFeatureFefresh.finishRefreshing();
        mTodayFeatureFefresh.finishLoadmore();
    }

    @Override
    public void onClickItem(int poist) {
        Intent intent = new Intent(TodayFeatureActivity.this, FootDetailActivity.class);
        intent.putExtra("shopCartBean",mList.get(poist));
        startActivity(intent);
    }

    @Override
    public void onClickItemAdd(int poist, TextView addText) {

    }

    @Override
    public void onClickItemReduce(int poist, TextView jianText) {

    }
}
