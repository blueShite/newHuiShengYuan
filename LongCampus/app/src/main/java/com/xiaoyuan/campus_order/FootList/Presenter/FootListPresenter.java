package com.xiaoyuan.campus_order.FootList.Presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.FootList.Bean.FootListBannerBean;
import com.xiaoyuan.campus_order.FootList.Interface.FootListInterface;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/6/26.
 */

public class FootListPresenter extends BasePresenter {

    private FootListInterface mInterface;

    public FootListPresenter(FootListInterface anInterface){
        mInterface = anInterface;
    }

    public void requestFootListHeader(final String resId){
        Map<String,String> map = new HashMap<>();
        map.put("res_id",resId);
        OkHttpUtils
                .post()
                .url(RequestTools.BaseUrl+"/api/getResTop.api.php")
                .params(map)
                .tag("")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toasty.error(mContext,"请求失败").show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("轮播图的数据",response);
                        FootListBannerBean bean = JSON.parseObject(response,FootListBannerBean.class);
                        if(bean.isRes()){
                            mInterface.requestSucess(bean.getData().get(0),bean.getUri());
                        }else {
                            Toasty.error(mContext,bean.getMes()).show();
                        }
                    }
                });
    }

    public void requestShopCartNum(String uId,String restId){

        Map<String,String> map = new HashMap<>();
        map.put("u_id",uId);
        map.put("res_cid",restId);
        RequestTools.getInstance().postRequest("/api/shoping_state.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    mInterface.requestShopNum(response.getData()+"");
                }
            }
        });

    }

}
