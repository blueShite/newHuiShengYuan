package com.xiaoyuan.campus_order.PersonSubs.Collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.FootDetail.FootDetailActivity;
import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.FeatureBean;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.PersonSubs.Collection.Adapter.CollectionAdapter;
import com.xiaoyuan.campus_order.PersonSubs.Collection.Bean.CollectionBean;
import com.xiaoyuan.campus_order.PersonSubs.Collection.Interface.CollectionInterface;
import com.xiaoyuan.campus_order.PersonSubs.Collection.Presenter.CollectionPresenter;
import com.xiaoyuan.campus_order.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends BaseActivity implements CollectionInterface {

    @BindView(R.id.collection_recycler)
    RecyclerView mCollectionRecycler;
    @BindView(R.id.collection_refresh)
    TwinklingRefreshLayout mCollectionRefresh;

    private CollectionPresenter mPresenter = new CollectionPresenter(this);
    private String page;
    private CollectionAdapter collectAdapter;
    private List<CollectionBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        customView();
        page = "1";
        mPresenter.requestCollection(page, LoginManage.getInstance().getLoginBean().getId());
    }

    private void customView(){

        mPresenter.setContext(CollectionActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(CollectionActivity.this);
        mCollectionRecycler.setLayoutManager(manager);
        collectAdapter = new CollectionAdapter(mList,CollectionActivity.this,this);
        mCollectionRecycler.setAdapter(collectAdapter);
        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(CollectionActivity.this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mCollectionRefresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(CollectionActivity.this);
        mCollectionRefresh.setBottomView(loadingView);
        mCollectionRefresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                page = "1";
                mPresenter.requestCollection(page, LoginManage.getInstance().getLoginBean().getId());
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                int pageIndex = Integer.parseInt(page)+1;
                page = pageIndex+"";
                mPresenter.requestCollection(page, LoginManage.getInstance().getLoginBean().getId());
            }
        });
    }

    @Override
    public void requestCollectionSucess(List<CollectionBean> list,String page) {

        mCollectionRefresh.finishLoadmore();
        mCollectionRefresh.finishRefreshing();
        if(page.equals("1")){
            mList.clear();
        }
        mList.addAll(list);
        collectAdapter.notifyDataSetChanged();

    }

    @Override
    public void requestCollectionError(String error) {
        mCollectionRefresh.finishLoadmore();
        mCollectionRefresh.finishRefreshing();
    }

    @Override
    public void onClickCollectSelect(int index) {

        mPresenter.requestCancelCollection(LoginManage.getInstance().getLoginBean().getId(),mList.get(index).getMenu_id(),index);
    }

    @Override
    public void requestCancelCollection(int index) {
        mList.remove(index);
        collectAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickItemView(int poist) {

        Intent intent = new Intent(CollectionActivity.this, FootDetailActivity.class);
        FeatureBean bean = new FeatureBean();
        bean.setMenu_id(mList.get(poist).getMenu_id());
        intent.putExtra("featureBean",bean);
        intent.putExtra("isCollection","1");
        if(mList.get(poist).getIfmeal()==1){
            intent.putExtra("isMyMenu","1");
        }else {
            intent.putExtra("isMyMenu","0");
        }
        startActivity(intent);
    }
}
