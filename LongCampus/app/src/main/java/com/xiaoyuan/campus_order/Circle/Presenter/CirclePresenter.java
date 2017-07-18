package com.xiaoyuan.campus_order.Circle.Presenter;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.Circle.Bean.CircleHeaderBean;
import com.xiaoyuan.campus_order.Circle.Bean.CircleItemBean;
import com.xiaoyuan.campus_order.Circle.Interface.CircleInterface;
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

public class CirclePresenter extends BasePresenter {

    private CircleInterface mInterface;
    private List<CircleHeaderBean> mBannerList = new ArrayList<>();

    public CirclePresenter(CircleInterface circleInterface){

        mInterface = circleInterface;
    }

    public void requestBanner(){

        RequestTools.getInstance().getRequest("/api/getDieCircleTop.api.php", false, null, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    mBannerList = JSON.parseArray(response.getData(),CircleHeaderBean.class);
                    mInterface.requestHeader(mBannerList);
                }else {

                    Toasty.error(mContext,"获取轮播图失败").show();
                }
            }
        });
    }

    public void requestItem(String page){

        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("page",page);
        RequestTools.getInstance().getRequest("/api/getDieCircle.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
                mInterface.requestError("请求失败");
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){

                    List<CircleItemBean> list = JSON.parseArray(response.getData(),CircleItemBean.class);
                    mInterface.requestSucess(list);

                }else {

                    Toasty.error(mContext,"获取列表失败").show();
                    mInterface.requestError(response.getMes());
                }

            }
        });
    }

}
