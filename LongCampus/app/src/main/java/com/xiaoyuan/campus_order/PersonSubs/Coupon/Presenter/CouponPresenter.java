package com.xiaoyuan.campus_order.PersonSubs.Coupon.Presenter;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.Coupon.Bean.CouponBean;
import com.xiaoyuan.campus_order.PersonSubs.Coupon.Interface.CouponInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/6/30.
 */

public class CouponPresenter extends BasePresenter {

    private CouponInterface mInterface;

    public CouponPresenter(CouponInterface anInterface){
        mInterface = anInterface;
    }

    public void requestList(String uId, String page, final String flag){
        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("uid",uId);
        map.put("rows","10");
        map.put("page",page);
        map.put("flag",flag);
        RequestTools.getInstance().postRequest("/api/getUsercoupon.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
                mInterface.requestCouponError("请求失败");
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){
                    List<CouponBean> list = JSON.parseArray(response.getData(),CouponBean.class);
                    for (CouponBean bean:list){
                        bean.setlCouponType(flag);
                    }
                    mInterface.requestCouponList(list);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                    mInterface.requestCouponError(response.getMes());
                }
            }
        });

    }

    public void requestReceiveList(String uId, String page){

        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("uid",uId);
        map.put("rows","10");
        map.put("page",page);
        RequestTools.getInstance().postRequest("/api/getSyscoupon.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
                mInterface.requestCouponError("请求失败");
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){
                    List<CouponBean> list = JSON.parseArray(response.getData(),CouponBean.class);
                    for (CouponBean bean:list){
                        bean.setlCouponType("2");
                    }
                    mInterface.requestCouponList(list);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                    mInterface.requestCouponError(response.getMes());
                }
            }
        });
    }

    public void requestReceiveCoupon(final String couponId , String uId, final int poist){

        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("cpid",couponId);
        map.put("uid",uId);
        RequestTools.getInstance().postRequest("/api/addusercoupon.api.php", false, map, "", new RequestCallBack(mContext) {
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
                    Toasty.success(mContext,"领取成功").show();
                    mInterface.requestReceiveSucess(poist);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }
}
