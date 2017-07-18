package com.xiaoyuan.campus_order.Circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoyuan.campus_order.Circle.Adapter.CircleAdapter;
import com.xiaoyuan.campus_order.Circle.Bean.CircleHeaderBean;
import com.xiaoyuan.campus_order.Circle.Bean.CircleItemBean;
import com.xiaoyuan.campus_order.Circle.CircleDetail.CircleDetailActivity;
import com.xiaoyuan.campus_order.Circle.Interface.CircleInterface;
import com.xiaoyuan.campus_order.Circle.Presenter.CirclePresenter;
import com.xiaoyuan.campus_order.Circle.ReleaseCircle.ReleaseCircleActivity;
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
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by longhengyu on 2017/4/20.
 */

public class CircleFragment extends SupportFragment implements CircleInterface {


    @BindView(R.id.recyclerview_circle)
    RecyclerView mRecyclerviewCircle;
    @BindView(R.id.refreshLayout_circle)
    TwinklingRefreshLayout mRefreshLayoutCircle;

    private CirclePresenter mPresenter = new CirclePresenter(this);

    private List<CircleHeaderBean> mBannerList = new ArrayList<>();
    private List<CircleItemBean> mItemList = new ArrayList<>();
    private String page;
    private CircleAdapter circleAdapter;


    public static CircleFragment newInstance(String info) {
        Bundle args = new Bundle();
        CircleFragment fragment = new CircleFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_circle, container, false);
        ButterKnife.bind(this, view);
        customView();
        page="1";
        mPresenter.requestBanner();
        return view;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        page="1";
        mPresenter.requestItem(page);
    }

    private void customView() {

        mPresenter.setContext(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerviewCircle.setLayoutManager(manager);
        circleAdapter = new CircleAdapter(mBannerList, mItemList, getContext(), this);
        mRecyclerviewCircle.setAdapter(circleAdapter);

        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mRefreshLayoutCircle.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(getContext());
        mRefreshLayoutCircle.setBottomView(loadingView);
        mRefreshLayoutCircle.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                page="1";
                mPresenter.requestItem(page);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                int indexPage = Integer.parseInt(page)+1;
                page = indexPage+"";
                mPresenter.requestItem(page);
            }
        });
    }

    @OnClick(R.id.image_circle_camare)
    public void onViewClicked() {

        Intent intent = new Intent(getActivity(), ReleaseCircleActivity.class);
        startActivity(intent);

    }

    @Override
    public void requestHeader(List<CircleHeaderBean> bannerList) {
        mBannerList.addAll(bannerList);
        circleAdapter.reloadHeader(mBannerList);
    }

    @Override
    public void requestSucess( List<CircleItemBean> itemList) {

        mRefreshLayoutCircle.finishRefreshing();
        mRefreshLayoutCircle.finishLoadmore();
        if(page.equals("1")){
            mItemList.clear();
        }
        mItemList.addAll(itemList);
        circleAdapter.reloadItem(mItemList);
    }

    @Override
    public void requestError(String error) {
        mRefreshLayoutCircle.finishRefreshing();
        mRefreshLayoutCircle.finishLoadmore();
    }

    @Override
    public void onClickHeader(int poist) {

        Intent intent = new Intent(getActivity(), CircleDetailActivity.class);
        intent.putExtra("circleHeaderBean", mBannerList.get(poist));
        startActivity(intent);

    }

    @Override
    public void onClickItem(int poist) {

        Intent intent = new Intent(getActivity(), CircleDetailActivity.class);
        intent.putExtra("circleItemBean", mItemList.get(poist));
        startActivity(intent);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
