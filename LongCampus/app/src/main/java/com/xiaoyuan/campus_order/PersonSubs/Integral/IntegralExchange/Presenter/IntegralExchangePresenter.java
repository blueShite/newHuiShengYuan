package com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.Presenter;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.Bean.IntergralExchangeBean;
import com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.Interface.IntegarlExchangeInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/7/3.
 */

public class IntegralExchangePresenter extends BasePresenter {

    private IntegarlExchangeInterface mInterface;

    public IntegralExchangePresenter(IntegarlExchangeInterface anInterface){
        mInterface = anInterface;
    }

    public void requestIntegarlList(){
        showDialog();
        RequestTools.getInstance().postRequest("/api/getGoodsList.api.php", false, null, "", new RequestCallBack(mContext) {
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
                    List<IntergralExchangeBean> list = JSON.parseArray(response.getData(),IntergralExchangeBean.class);
                    mInterface.requestListSucess(list);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

    public void requestExchange(String uId, String gId, String price, String integral, final int poist){
        Map<String,String> map = new HashMap<>();
        map.put("uid",uId);
        map.put("gid",gId);
        map.put("ginte",price);
        map.put("uinte",integral);
        RequestTools.getInstance().postRequest("/api/addInteTOgoods.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    Toasty.success(mContext,"兑换成功").show();
                    mInterface.requestExChangeSuccess(poist);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }
}
