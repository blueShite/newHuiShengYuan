package com.xiaoyuan.campus_order.NetWorks;

import android.content.Context;


import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.callback.Callback;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by longhengyu on 2017/3/31.
 */

public abstract class RequestCallBack extends Callback<RequestBean>{

    Context mContext;
    public RequestCallBack(Context context){
        mContext = context;
    }

    @Override
    public RequestBean parseNetworkResponse(Response response, int id) throws Exception {

        String string = response.body().string();
        RequestBean requestBean = JSON.parseObject(string,RequestBean.class);
        return requestBean;
    }

    @Override
    public  void onError(Call call, Exception e, int id){

        Toasty.error(mContext,"请求失败！").show();
    }

    @Override
    public void onResponse(RequestBean response, int id) {


    }
}