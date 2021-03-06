package com.xiaoyuan.campus_order.FootList.SubFootList;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoyuan.campus_order.FootDetail.FootDetailActivity;
import com.xiaoyuan.campus_order.FootList.ClassesRequest.ClassesRequest;
import com.xiaoyuan.campus_order.FootList.CollectionRequest.CollectionRequest;
import com.xiaoyuan.campus_order.FootList.CollectionRequest.CollectionRequestInterface;
import com.xiaoyuan.campus_order.FootList.Event.FootListShopEvent;
import com.xiaoyuan.campus_order.FootList.ShopCartRequest.ShopCartChangeInterface;
import com.xiaoyuan.campus_order.FootList.ShopCartRequest.ShopcartRequest;
import com.xiaoyuan.campus_order.FootList.SubFootList.Adapter.PackpageClassesAdapter;
import com.xiaoyuan.campus_order.FootList.SubFootList.Adapter.SaleAdapter;
import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.FeatureBean;
import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.PackpageClassesBean;
import com.xiaoyuan.campus_order.FootList.SubFootList.Interface.SaleInterface;
import com.xiaoyuan.campus_order.FootList.SubFootList.Presenter.SalePresenter;
import com.xiaoyuan.campus_order.Home.Bean.CanteenBean;
import com.xiaoyuan.campus_order.Manage.ClassesManage;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaleFragment extends SupportFragment implements SaleInterface {

    @BindView(R.id.sale_fragment_recycler)
    RecyclerView mSaleFragmentRecycler;
    @BindView(R.id.sale_refresh)
    TwinklingRefreshLayout mSaleRefresh;
    @BindView(R.id.sale_fragment_class_recycle)
    RecyclerView mSaleFragmentClassRecycle;

    private View mView;
    private SaleAdapter mAdapter;
    private List<FeatureBean> mList = new ArrayList<>();
    private SalePresenter mPresenter = new SalePresenter(this);
    private CanteenBean mCanteenBean;
    private String page;

    private PackpageClassesAdapter mClassesAdapter;
    private List<PackpageClassesBean> mClassesList = new ArrayList<>();
    private PackpageClassesBean selectClassesBean;

    public static SaleFragment newInstance(CanteenBean canteenBean) {
        SaleFragment newFragment = new SaleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("canteenBean",canteenBean);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_sale, container, false);
        mCanteenBean =(CanteenBean) getArguments().getSerializable("canteenBean");
        ButterKnife.bind(this, mView);
        customView();
        //if(ClassesManage.getInstance().returnClasses()==null||ClassesManage.getInstance().returnClasses().size()<1){
            ClassesRequest.requestClassesList(mCanteenBean.getRes_id(),"2", getContext(), new ClassesRequest.ClassesRequestInterface() {
                @Override
                public void requestClassesList(List<PackpageClassesBean> list) {
                    ClassesManage.getInstance().saveClasses(list);
                    mClassesList.clear();
                    mClassesList.addAll(list);
                    mClassesList.get(0).setSelect(true);
                    selectClassesBean = mClassesList.get(0);
                    mClassesAdapter.notifyDataSetChanged();
                    page = "1";
                    mPresenter.requestList(selectClassesBean.getRes_id(), page);
                }
            });
        //}else {
            /*mClassesList.clear();
            mClassesList.addAll(ClassesManage.getInstance().returnClasses());
            mClassesList.get(0).setSelect(true);
            selectClassesBean = mClassesList.get(0);
            mClassesAdapter.notifyDataSetChanged();
            page = "1";
            mPresenter.requestList(mCanteenBean.getRes_id(), page);*/
        //}
        return mView;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if(selectClassesBean==null||selectClassesBean.getRes_id()==null){
            return;
        }
        page = "1";
        mPresenter.requestList(selectClassesBean.getRes_id(), page);
    }

    private void customView() {

        mPresenter.setContext(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mSaleFragmentRecycler.setLayoutManager(layoutManager);
        mAdapter = new SaleAdapter(mList, getContext(), this);
        mSaleFragmentRecycler.setAdapter(mAdapter);
        LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
        mSaleFragmentClassRecycle.setLayoutManager(manager1);
        mClassesAdapter = new PackpageClassesAdapter(mClassesList, getContext(), new PackpageClassesAdapter.ClassesInterface() {
            @Override
            public void onClickClassesItem(int poist) {
                for (int i=0;i<mClassesList.size();i++){
                    if(i==poist){
                        mClassesList.get(i).setSelect(true);
                    }else {
                        mClassesList.get(i).setSelect(false);
                    }
                }
                mClassesAdapter.notifyDataSetChanged();
                selectClassesBean = mClassesList.get(poist);
                page = "1";
                mPresenter.requestList(selectClassesBean.getRes_id(), page);
            }
        });
        mSaleFragmentClassRecycle.setAdapter(mClassesAdapter);

        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mSaleRefresh.setHeaderView(headerView);
        LoadingView loadingView = new LoadingView(getContext());
        mSaleRefresh.setBottomView(loadingView);
        mSaleRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                if(selectClassesBean==null||selectClassesBean.getRes_id().length()<1){
                    mSaleRefresh.finishRefreshing();
                    return;
                }
                page = "1";
                mPresenter.requestList(selectClassesBean.getRes_id(), page);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if(selectClassesBean==null||selectClassesBean.getRes_id().length()<1){
                    mSaleRefresh.finishLoadmore();
                    return;
                }
                int pageIndex = Integer.parseInt(page) + 1;
                page = pageIndex + "";
                mPresenter.requestList(selectClassesBean.getRes_id(), page);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void requestSucess(List<FeatureBean> list) {

        mSaleRefresh.finishLoadmore();
        mSaleRefresh.finishRefreshing();
        if(page.equals("1")){
            mList.clear();
        }
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestError(String error) {
        mSaleRefresh.finishLoadmore();
        mSaleRefresh.finishRefreshing();
        if(page.equals("1")){
            mList.clear();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickSelfItem(int poist) {
        Intent intent = new Intent(getActivity(), FootDetailActivity.class);
        intent.putExtra("featureBean", mList.get(poist));
        intent.putExtra("isMyMenu", "0");
        intent.putExtra("resId", mCanteenBean.getRes_id());
        intent.putExtra("flag","2");
        startActivity(intent);
    }

    @Override
    public void onClickCollection(final int poist) {
        CollectionRequest.requestCollection(LoginManage.getInstance().getLoginBean().getId(),
                mList.get(poist).getMenu_id(), getContext(), new CollectionRequestInterface() {
                    @Override
                    public void collectionSucess() {
                        mList.get(poist).setIfkeep(1);
                        mAdapter.notifyItemChanged(poist);
                    }
                });
    }

    @Override
    public void onClickItemAdd(int poist, final TextView addText) {
        final FeatureBean bean = mList.get(poist);
        final String numsStr = (Integer.parseInt(bean.getNums()) + 1) + "";
        ShopcartRequest.requestShopCart(mCanteenBean.getRes_id(), numsStr, bean.getMenu_id(),"2", getContext(), new ShopCartChangeInterface() {
            @Override
            public void changeShopCart() {
                bean.setNums(numsStr);
                addText.setText(numsStr);
                EventBus.getDefault().post(new FootListShopEvent("更新购物车"));
            }
        });
    }

    @Override
    public void onClickItemReduce(int poist, final TextView jianText) {
        final FeatureBean bean = mList.get(poist);
        if (Integer.parseInt(bean.getNums()) < 1) {
            Toasty.error(getContext(), "已经是0了,不能再少了").show();
            return;
        }
        final String numsStr = (Integer.parseInt(bean.getNums()) - 1) + "";
        ShopcartRequest.requestShopCart(mCanteenBean.getRes_id(), numsStr, bean.getMenu_id(),"2", getContext(), new ShopCartChangeInterface() {
            @Override
            public void changeShopCart() {
                bean.setNums(numsStr);
                jianText.setText(numsStr);
                EventBus.getDefault().post(new FootListShopEvent("更新购物车"));
            }
        });
    }
}
