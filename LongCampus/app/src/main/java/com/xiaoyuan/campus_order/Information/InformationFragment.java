package com.xiaoyuan.campus_order.Information;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoyuan.campus_order.Information.Adapter.InformationAdapter;
import com.xiaoyuan.campus_order.Information.Bean.InformationBean;
import com.xiaoyuan.campus_order.Information.InformationDetail.InformationDetailActivity;
import com.xiaoyuan.campus_order.Information.Interface.InformationInterface;
import com.xiaoyuan.campus_order.Information.Presenter.InformationPresenter;
import com.xiaoyuan.campus_order.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by longhengyu on 2017/4/20.
 */

public class InformationFragment extends SupportFragment implements InformationInterface {

    @BindView(R.id.recyclerview_information)
    RecyclerView mRecyclerviewInformation;
    @BindView(R.id.refreshLayout_information)
    TwinklingRefreshLayout mRefreshLayoutInformation;

    private List<InformationBean> mbannerList = new ArrayList<>();
    private List<InformationBean> mitemList = new ArrayList<>();

    private InformationPresenter mInformationPresenter = new InformationPresenter(this);
    private String page;
    public static InformationFragment newInstance(String info) {
        Bundle args = new Bundle();
        InformationFragment fragment = new InformationFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, view);
        customView();
        page = "1";
        mInformationPresenter.requestBanner();
        mInformationPresenter.requestItem(page);
        return view;
    }

    private void customView(){


        mInformationPresenter.setContext(getContext());

        //定制recycle
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerviewInformation.setLayoutManager(manager);
        InformationAdapter adapter = new InformationAdapter(mbannerList,mitemList,getContext(),this);
        mRecyclerviewInformation.setAdapter(adapter);


        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mRefreshLayoutInformation.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(getContext());
        mRefreshLayoutInformation.setBottomView(loadingView);
        mRefreshLayoutInformation.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                mInformationPresenter.requestBanner();
                page = "1";
                mInformationPresenter.requestItem(page);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {

                int pageIndex = Integer.parseInt(page)+1;
                page = pageIndex+"";
                mInformationPresenter.requestItem(page);
            }
        });
    }


    @Override
    public void requestHeaderSucess(List<InformationBean> bannerList) {
        mbannerList.clear();
        mbannerList.addAll(bannerList);
        InformationAdapter adapter = (InformationAdapter) mRecyclerviewInformation.getAdapter();
        adapter.reloadHeader(bannerList);
    }

    @Override
    public void requestSuccess(List<InformationBean> itemList) {
        mRefreshLayoutInformation.finishRefreshing();
        mRefreshLayoutInformation.finishLoadmore();
        if(page.equals("1")){
            mitemList.clear();
        }
        mitemList.addAll(itemList);
        InformationAdapter adapter = (InformationAdapter) mRecyclerviewInformation.getAdapter();
        adapter.reloadItem(mitemList);
    }

    @Override
    public void requestError(String error) {
        mRefreshLayoutInformation.finishRefreshing();
        mRefreshLayoutInformation.finishLoadmore();
    }

    @Override
    public void onClickHeader(int poist) {

        Intent intent = new Intent(getActivity(), InformationDetailActivity.class);
        intent.putExtra("informationBean",mbannerList.get(poist));
        startActivity(intent);
    }

    @Override
    public void onClickitem(int poist) {

        Intent intent = new Intent(getActivity(), InformationDetailActivity.class);
        intent.putExtra("informationBean",mitemList.get(poist));
        startActivity(intent);
    }
}
