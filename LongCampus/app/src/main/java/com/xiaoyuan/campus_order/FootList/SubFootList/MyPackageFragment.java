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
import com.xiaoyuan.campus_order.FootList.CollectionRequest.CollectionRequest;
import com.xiaoyuan.campus_order.FootList.CollectionRequest.CollectionRequestInterface;
import com.xiaoyuan.campus_order.FootList.Event.FootListShopEvent;
import com.xiaoyuan.campus_order.FootList.ShopCartRequest.ShopCartChangeInterface;
import com.xiaoyuan.campus_order.FootList.ShopCartRequest.ShopcartRequest;
import com.xiaoyuan.campus_order.FootList.SubFootList.Adapter.PackpageClassesAdapter;
import com.xiaoyuan.campus_order.FootList.SubFootList.Adapter.PackpageCommAdapter;
import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.FeatureBean;
import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.PackpageClassesBean;
import com.xiaoyuan.campus_order.FootList.SubFootList.Interface.MyPackpageInterface;
import com.xiaoyuan.campus_order.FootList.SubFootList.Presenter.MyPackpagePresenter;
import com.xiaoyuan.campus_order.Home.Bean.CanteenBean;
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
public class MyPackageFragment extends SupportFragment implements MyPackpageInterface {

    @BindView(R.id.packpage_classes_recycle)
    RecyclerView mPackpageClassesRecycle;
    @BindView(R.id.packpage_comm_recycle)
    RecyclerView mPackpageCommRecycle;
    @BindView(R.id.packpage_comm_refresh)
    TwinklingRefreshLayout mPackpageCommRefresh;

    private View mView;
    private String page;
    private PackpageClassesAdapter mClassesAdapter;
    private PackpageCommAdapter mCommAdapter;
    private List<PackpageClassesBean> mClassesBeenList = new ArrayList<>();
    private List<FeatureBean> mCommodityBeenList = new ArrayList<>();
    private MyPackpagePresenter mPresenter = new MyPackpagePresenter(this);
    private CanteenBean mCanteenBean;
    private PackpageClassesBean selectClassesBean;

    public static MyPackageFragment newInstance(CanteenBean canteenBean) {
        MyPackageFragment newFragment = new MyPackageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("canteenBean",canteenBean);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_my_package, container, false);
        mCanteenBean =(CanteenBean) getArguments().getSerializable("canteenBean");
        ButterKnife.bind(this, mView);
        customView();
        page="1";
        mPresenter.requestClassesList(mCanteenBean.getRes_id());
        return mView;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if(selectClassesBean==null||selectClassesBean.getRes_id()==null){
            return;
        }
        page = "1";
        mPresenter.requestCommodityList(LoginManage.getInstance().getLoginBean().getHate(),
                LoginManage.getInstance().getLoginBean().getLike_id(),page,selectClassesBean.getRes_id(),
                LoginManage.getInstance().getLoginBean().getId(),LoginManage.getInstance().getLoginBean().getTaboos(),false);
    }

    private void customView(){

        mPresenter.setContext(getContext());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mPackpageClassesRecycle.setLayoutManager(manager);
        LinearLayoutManager commManager = new LinearLayoutManager(getContext());
        mPackpageCommRecycle.setLayoutManager(commManager);

        mClassesAdapter = new PackpageClassesAdapter(mClassesBeenList, getContext(), new PackpageClassesAdapter.ClassesInterface() {
            @Override
            public void onClickClassesItem(int poist) {
                for (int i=0;i<mClassesBeenList.size();i++){
                    if(i==poist){
                        mClassesBeenList.get(i).setSelect(true);
                    }else {
                        mClassesBeenList.get(i).setSelect(false);
                    }
                }
                mClassesAdapter.notifyDataSetChanged();
                selectClassesBean = mClassesBeenList.get(poist);
                page = "1";
                mPresenter.requestCommodityList(LoginManage.getInstance().getLoginBean().getHate(),
                        LoginManage.getInstance().getLoginBean().getLike_id(),page,selectClassesBean.getRes_id(),
                        LoginManage.getInstance().getLoginBean().getId(),LoginManage.getInstance().getLoginBean().getTaboos(),false);
            }
        });
        mPackpageClassesRecycle.setAdapter(mClassesAdapter);

        mCommAdapter = new PackpageCommAdapter(mCommodityBeenList,getContext(),this);
        mPackpageCommRecycle.setAdapter(mCommAdapter);
        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mPackpageCommRefresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(getContext());
        mPackpageCommRefresh.setBottomView(loadingView);
        mPackpageCommRefresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {

                if(selectClassesBean==null||selectClassesBean.getRes_id().length()<1){
                    mPackpageCommRefresh.finishRefreshing();
                    return;
                }
                page = "1";
                mPresenter.requestCommodityList(LoginManage.getInstance().getLoginBean().getHate(),
                        LoginManage.getInstance().getLoginBean().getLike_id(),page,selectClassesBean.getRes_id(),
                        LoginManage.getInstance().getLoginBean().getId(),LoginManage.getInstance().getLoginBean().getTaboos(),false);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {

                if(selectClassesBean==null||selectClassesBean.getRes_id().length()<1){
                    mPackpageCommRefresh.finishLoadmore();
                    return;
                }
                int pageIndex = Integer.parseInt(page)+1;
                page = pageIndex+"";
                mPresenter.requestCommodityList(LoginManage.getInstance().getLoginBean().getHate(),
                        LoginManage.getInstance().getLoginBean().getLike_id(),page,selectClassesBean.getRes_id(),
                        LoginManage.getInstance().getLoginBean().getId(),LoginManage.getInstance().getLoginBean().getTaboos(),false);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void requestClassesSuccess(List<PackpageClassesBean> list, List<FeatureBean> commodityList) {

        mClassesBeenList.clear();
        mClassesBeenList.addAll(list);
        mClassesAdapter.notifyDataSetChanged();
        selectClassesBean = mClassesBeenList.get(0);
        if(commodityList==null){
            return;
        }
        mCommodityBeenList.clear();
        mCommodityBeenList.addAll(commodityList);
        mCommAdapter.notifyDataSetChanged();

    }

    @Override
    public void requestCommoditySuccess(List<FeatureBean> list) {

        mPackpageCommRefresh.finishLoadmore();
        mPackpageCommRefresh.finishRefreshing();
        if(page.equals("1")){
            mCommodityBeenList.clear();
        }
        mCommodityBeenList.addAll(list);
        mCommAdapter.notifyDataSetChanged();

    }

    @Override
    public void requestCommodityError(String error) {
        mPackpageCommRefresh.finishLoadmore();
        mPackpageCommRefresh.finishRefreshing();
        if(page.equals("1")){
            mCommodityBeenList.clear();
            mCommAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onClickMenu(int poist) {
        Intent intent = new Intent(getActivity(), FootDetailActivity.class);
        intent.putExtra("featureBean",mCommodityBeenList.get(poist));
        intent.putExtra("isMyMenu","0");
        intent.putExtra("resId",mCanteenBean.getRes_id());
        intent.putExtra("flag","4");
        startActivity(intent);
    }

    @Override
    public void onClickCollection(final int poist) {

        CollectionRequest.requestCollection(LoginManage.getInstance().getLoginBean().getId(),
                mCommodityBeenList.get(poist).getMenu_id(), getContext(), new CollectionRequestInterface() {
                    @Override
                    public void collectionSucess() {
                        mCommodityBeenList.get(poist).setIfkeep(1);
                        mCommAdapter.notifyItemChanged(poist);
                    }
                });

    }

    @Override
    public void onClickAddShopCart(final int poist, final TextView numTextView) {

        final FeatureBean bean = mCommodityBeenList.get(poist);
        final String numsStr = (Integer.parseInt(bean.getNums())+1)+"";
        ShopcartRequest.requestShopCart(mCanteenBean.getRes_id(),numsStr, bean.getMenu_id(),"4", getContext(), new ShopCartChangeInterface() {
            @Override
            public void changeShopCart() {
                bean.setNums(numsStr);
                numTextView.setText(numsStr);
                EventBus.getDefault().post(new FootListShopEvent("更新购物车"));
            }
        });

    }

    @Override
    public void onClickReduxShopCart(final int poist, final TextView numTextView) {
        final FeatureBean bean = mCommodityBeenList.get(poist);
        if(Integer.parseInt(bean.getNums())<1){
            Toasty.error(getContext(),"已经是0了,不能再少了").show();
            return;
        }
        final String numsStr = (Integer.parseInt(bean.getNums())-1)+"";
        ShopcartRequest.requestShopCart(mCanteenBean.getRes_id(),numsStr, bean.getMenu_id(),"4", getContext(), new ShopCartChangeInterface() {
            @Override
            public void changeShopCart() {
                bean.setNums(numsStr);
                numTextView.setText(numsStr);
                EventBus.getDefault().post(new FootListShopEvent("更新购物车"));
            }
        });
    }
}
