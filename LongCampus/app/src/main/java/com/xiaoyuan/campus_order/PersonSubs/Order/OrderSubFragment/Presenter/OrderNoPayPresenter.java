package com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Bean.OrderBean;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Interface.OrderOnPayListInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/7/10.
 */

public class OrderNoPayPresenter extends BasePresenter {

    private OrderOnPayListInterface mInterface;

    public OrderNoPayPresenter (OrderOnPayListInterface anInterface){
        mInterface = anInterface;
    }

    public void requestOrderList(String uId,String page,String orderType){
        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("uid",uId);
        map.put("page",page);
        map.put("state",orderType);
        map.put("rows","10");
        RequestTools.getInstance().postRequest("/api/getOrderPro.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
                mInterface.requestListError("请求失败");
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){
                    Log.e("订单列表",response.getData());
                    List<OrderBean> list = JSON.parseArray(response.getData(),OrderBean.class);
                    mInterface.requestOrderList(list);
                }else {
                    mInterface.requestListError(response.getMes());
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

    public void requestPay(String orderId){
        Map<String,String> map = new HashMap<>();
        map.put("id",orderId);
        RequestTools.getInstance().postRequest("/api/pay.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    mInterface.requestPay(response.getData());
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

}
