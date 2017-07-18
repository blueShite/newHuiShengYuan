package com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alipay.sdk.app.PayTask;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Adapter.OrderNoPayAdapter;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Bean.OrderBean;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Interface.OrderOnPayListInterface;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Presenter.OrderNoPayPresenter;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Bean.AuthResult;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Bean.PayResult;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by longhengyu on 2017/7/8.
 */

public class OrderNoPayFragment extends SupportFragment implements OrderOnPayListInterface {

    @BindView(R.id.order_nopay_recycler)
    RecyclerView mOrderNopayRecycler;
    @BindView(R.id.order_nopay_refresh)
    TwinklingRefreshLayout mOrderNopayRefresh;

    private View mView;
    private String page;
    private OrderNoPayAdapter mAdapter;
    private List<OrderBean> mList = new ArrayList<>();
    private OrderNoPayPresenter mPresenter = new OrderNoPayPresenter(this);

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toasty.success(getContext(),"支付成功").show();
                        page = "1";
                        mPresenter.requestOrderList(LoginManage.getInstance().getLoginBean().getId(),page,"1");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toasty.error(getContext(),"支付失败").show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toasty.success(getContext(),"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode())).show();
                    } else {
                        // 其他状态值则为授权失败
                        Toasty.error(getContext(),"授权失败" + String.format("authCode:%s", authResult.getAuthCode())).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_order_nopay, container, false);
        ButterKnife.bind(this, mView);
        customView();
        page = "1";
        return mView;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        page = "1";
        mPresenter.requestOrderList(LoginManage.getInstance().getLoginBean().getId(),page,"1");
    }

    private void customView(){

        mPresenter.setContext(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mOrderNopayRecycler.setLayoutManager(layoutManager);
        mAdapter = new OrderNoPayAdapter(mList,this,getContext(),0);
        mOrderNopayRecycler.setAdapter(mAdapter);

        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mOrderNopayRefresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(getContext());
        mOrderNopayRefresh.setBottomView(loadingView);
        mOrderNopayRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                page="1";
                mPresenter.requestOrderList(LoginManage.getInstance().getLoginBean().getId(),page,"1");
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                int indexPage = Integer.parseInt(page)+1;
                page = indexPage+"";
                mPresenter.requestOrderList(LoginManage.getInstance().getLoginBean().getId(),page,"1");
            }
        });
    }

    @Override
    public void requestOrderList(List<OrderBean> list) {
        mOrderNopayRefresh.finishRefreshing();
        mOrderNopayRefresh.finishLoadmore();
        if(page.equals("1")){
            mList.clear();
        }
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestListError(String error) {
        mOrderNopayRefresh.finishRefreshing();
        mOrderNopayRefresh.finishLoadmore();
        if(page.equals("1")){
            mList.clear();
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestPay(final String payData) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(payData, true);
                Log.e("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void onClickShowComm(int poist) {
        mList.get(poist).setShowComm(true);
        mAdapter.notifyItemChanged(poist);
    }

    @Override
    public void onClickHideComm(int poist) {
        mList.get(poist).setShowComm(false);
        mAdapter.notifyItemChanged(poist);
    }

    @Override
    public void onClickOrderItem(int poist) {

    }

    @Override
    public void onClickPay(int poist) {
        OrderBean bean = mList.get(poist);
        mPresenter.requestPay(bean.getId());
    }
}
