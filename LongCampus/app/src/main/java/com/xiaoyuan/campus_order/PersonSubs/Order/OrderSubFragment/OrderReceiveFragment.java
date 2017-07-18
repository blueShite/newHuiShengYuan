package com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Adapter.OrderReceiveAdapter;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Bean.OrderBean;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Interface.OrderReceiveInterface;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Presenter.OrderReceivePresenter;
import com.xiaoyuan.campus_order.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by longhengyu on 2017/7/8.
 */

public class OrderReceiveFragment extends SupportFragment implements OrderReceiveInterface {

    @BindView(R.id.order_receive_recycle)
    RecyclerView mOrderReceiveRecycle;
    @BindView(R.id.order_receive_refresh)
    TwinklingRefreshLayout mOrderReceiveRefresh;

    private View mView;
    private OrderReceiveAdapter mAdapter;
    private String page;
    private OrderReceivePresenter mPresenter = new OrderReceivePresenter(this);
    private List<OrderBean> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_order_receive, container, false);
        ButterKnife.bind(this, mView);
        customView();
        page = "1";
        return mView;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        page = "1";
        mPresenter.requestOrderList(LoginManage.getInstance().getLoginBean().getId(),page,"3");
    }

    private void customView() {

        mPresenter.setContext(getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mOrderReceiveRecycle.setLayoutManager(layoutManager);
        mAdapter = new OrderReceiveAdapter(mList,getContext(),this);
        mOrderReceiveRecycle.setAdapter(mAdapter);
        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mOrderReceiveRefresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(getContext());
        mOrderReceiveRefresh.setBottomView(loadingView);
        mOrderReceiveRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                page="1";
                mPresenter.requestOrderList(LoginManage.getInstance().getLoginBean().getId(),page,"3");
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                int indexPage = Integer.parseInt(page)+1;
                page = indexPage+"";
                mPresenter.requestOrderList(LoginManage.getInstance().getLoginBean().getId(),page,"3");
            }
        });
    }

    @Override
    public void requestOrderList(List<OrderBean> list) {

        mOrderReceiveRefresh.finishRefreshing();
        mOrderReceiveRefresh.finishLoadmore();
        if(page.equals("1")){
            mList.clear();
        }
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestListError(String error) {

        mOrderReceiveRefresh.finishRefreshing();
        mOrderReceiveRefresh.finishLoadmore();
        if(page.equals("1")){
            mList.clear();
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestPingLunSucess(int poist,String remarkStr) {
        mList.get(poist).setOrder_reply(remarkStr);
        mAdapter.notifyItemChanged(poist);
    }

    @Override
    public void onClickOrderItem(int poist) {
    }

    @Override
    public void itemEditText(int poist, String remarkStr) {
        mList.get(poist).setRemark(remarkStr);
    }

    @Override
    public void onClickPinglunBtn(int poist) {
        OrderBean bean = mList.get(poist);
        if(bean.getRemark()==null||bean.getRemark().length()<1){
            Toasty.error(getContext(),"请输入要评论的内容").show();
            return;
        }
        mPresenter.requestRemark(bean.getId(),bean.getRemark(),poist);
    }
}
