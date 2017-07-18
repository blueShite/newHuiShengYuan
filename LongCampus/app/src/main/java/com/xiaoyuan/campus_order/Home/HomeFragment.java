package com.xiaoyuan.campus_order.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.xiaoyuan.campus_order.FootList.FootListActivity;
import com.xiaoyuan.campus_order.Home.Adapter.HomeAdapter;
import com.xiaoyuan.campus_order.Home.Bean.CanteenBean;
import com.xiaoyuan.campus_order.Home.Interface.HomeAdapterInterface;
import com.xiaoyuan.campus_order.Home.Interface.HomeInterface;
import com.xiaoyuan.campus_order.Home.Presenter.HomePresenter;
import com.xiaoyuan.campus_order.Home.SearchSchool.SearchSchoolActivity;
import com.xiaoyuan.campus_order.LocationAbout.LongLocation;
import com.xiaoyuan.campus_order.LocationAbout.LongLocationListener;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by longhengyu on 2017/4/20.
 */

public class HomeFragment extends SupportFragment implements HomeInterface,HomeAdapterInterface,LongLocationListener {

    @BindView(R.id.text_home_dingwei)
    TextView mTextHomeDingwei;
    @BindView(R.id.recyclerview_home)
    RecyclerView mRecyclerviewHome;
    @BindView(R.id.refreshLayout_home)
    TwinklingRefreshLayout mRefreshLayoutHome;

    private List<CanteenBean> mList = new ArrayList<>();;
    private HomePresenter mPresenter = new HomePresenter(this);
    private String page;
    private String locationStr = "正在定位中...";
    private String schId;

    private LongLocation mLongLocation;


    public static HomeFragment newInstance(String info) {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        page = "1";
        if(LoginManage.getInstance().getLoginBean().getStuid().isEmpty()){
            schId = "1";
        }else {
            schId= LoginManage.getInstance().getLoginBean().getStuid();
        }
        mPresenter.requestHomeData(page,schId);
        mLongLocation.startLocation();
        return view;
    }

    /*@Override
    public void onSupportVisible() {
        super.onSupportVisible();
        page = "1";
        if(LoginManage.getInstance().getLoginBean().getStuid().isEmpty()){
            schId = "1";
        }else {
            schId= LoginManage.getInstance().getLoginBean().getStuid();
        }
        mPresenter.requestHomeData(page,schId);
    }*/

    public void reloadHomeData(String schId){
        this.schId = schId;
        page = "1";
        mPresenter.requestHomeData(page,schId);
    }

    protected void initView() {

        customView();
        page = "1";
        mLongLocation = new LongLocation(getContext(),this);
        mTextHomeDingwei.setText(locationStr);

    }

    private void customView(){

        mPresenter.setContext(getContext());
        HomeAdapter adapter = new HomeAdapter(mList,getContext());
        adapter.setAdapterInterface(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerviewHome.setLayoutManager(manager);
        mRecyclerviewHome.setAdapter(adapter);

        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mRefreshLayoutHome.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(getContext());
        mRefreshLayoutHome.setBottomView(loadingView);
        mRefreshLayoutHome.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                page = "1";
                mPresenter.requestHomeData(page,schId);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                int pageIndex = Integer.parseInt(page)+1;
                page = pageIndex+"";
                mPresenter.requestHomeData(page,schId);
            }
        });

        mTextHomeDingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchSchoolActivity.class);
                intent.putExtra("dingwei",mTextHomeDingwei.getText().toString());
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.relative_home_dingwei)
    public void onViewClicked() {

    }

    //成功时回调的监听
    @Override
    public void requestHomeDataSucess(List<CanteenBean> list) {

        mRefreshLayoutHome.finishRefreshing();
        mRefreshLayoutHome.finishLoadmore();
        if(page.equals("1")){

            mList = list;
            mList.add(0,new CanteenBean());
            HomeAdapter adapter = (HomeAdapter) mRecyclerviewHome.getAdapter();
            adapter.reloadData(mList);
            mRefreshLayoutHome.finishRefreshing();

        }
    }

    @Override
    public void requestHomeDataError(String error) {
        mRefreshLayoutHome.finishRefreshing();
        mRefreshLayoutHome.finishLoadmore();
    }

    //点击item的监听
    @Override
    public void onClickAdapter(int posit) {

        /*Intent intent = new Intent(getActivity(), ShopCartActivity.class);
        intent.putExtra("canteenBean",mList.get(posit));
        startActivity(intent);*/
        Intent intent = new Intent(getActivity(), FootListActivity.class);
        intent.putExtra("canteenBean",mList.get(posit));
        startActivity(intent);

    }

    //定位成功时候的回调
    @Override
    public void LocationSucess(final BDLocation location) {

        if(location.getLocType()==61||location.getLocType()==161){

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    locationStr = location.getStreet();
                    mTextHomeDingwei.setText(locationStr);
                }
            });

            Toasty.success(getContext(),"定位成功"+location.getStreet()).show();
            mLongLocation.stopLocation();
        }else {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    locationStr = "定位失败了";
                    mTextHomeDingwei.setText(locationStr);
                }
            });
            Toasty.error(getContext(),"定位失败了"+location.getLocType()).show();
        }
    }
}
