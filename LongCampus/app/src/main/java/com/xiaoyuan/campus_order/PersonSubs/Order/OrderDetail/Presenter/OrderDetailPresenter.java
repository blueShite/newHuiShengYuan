package com.xiaoyuan.campus_order.PersonSubs.Order.OrderDetail.Presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderDetail.Bean.OrderDetailBean;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderDetail.Interface.OrderDetailInterface;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/7/11.
 */

public class OrderDetailPresenter extends BasePresenter {

    private OrderDetailInterface mInterface;

    public OrderDetailPresenter(OrderDetailInterface anInterface){
        mInterface = anInterface;
    }

    public void requestOrderDetail(String orderId){
        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("id",orderId);
        RequestTools.getInstance().postRequest("/api/getorder_xq.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){
                    Log.e("订单详情",response.getData());
                    OrderDetailBean bean = JSON.parseObject(response.getData(),OrderDetailBean.class);
                    mInterface.requestOrderDetailSucess(bean);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

}
