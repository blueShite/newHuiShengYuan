package com.xiaoyuan.campus_order.Information.Presenter;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.Information.Bean.InformationBean;
import com.xiaoyuan.campus_order.Information.Interface.InformationInterface;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/4/22.
 */

public class InformationPresenter extends BasePresenter {

    private InformationInterface mInterface;
    private List<InformationBean> bannerList = new ArrayList<>();

    public InformationPresenter(InformationInterface informationInterface){

        mInterface = informationInterface;
    }

    public void requestBanner(){

        RequestTools.getInstance().getRequest("/api/getNewsTop.api.php", false, null, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                if(response.isRes()){
                    bannerList = JSON.parseArray(response.getData(), InformationBean.class);
                    mInterface.requestHeaderSucess(bannerList);
                }else {
                    Toasty.error(mContext,"轮播图请求失败").show();
                }
                super.onResponse(response, id);
            }
        });

    }

    public void requestItem(String page){
        showDialog();
        Map<String ,String> map = new HashMap<>();
        map.put("page",page);
        map.put("rows","10");
        RequestTools.getInstance().postRequest("/api/getHTinfo.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                mInterface.requestError("请求失败");
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                if(response.isRes()){
                    List<InformationBean> list = JSON.parseArray(response.getData(),InformationBean.class);
                    mInterface.requestSuccess(list);
                }else {
                    mInterface.requestError(response.getMes());
                    Toasty.error(mContext,response.getMes()).show();

                }
                super.onResponse(response, id);
            }
        });
    }



}
