package com.xiaoyuan.campus_order.FootDetail.Presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.FootDetail.Bean.FootDetailBean;
import com.xiaoyuan.campus_order.FootDetail.Bean.FootDetailItemBean;
import com.xiaoyuan.campus_order.FootDetail.Interface.FootDetailInterface;
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
 * Created by longhengyu on 2017/4/28.
 */

public class FootDetailPresenter extends BasePresenter {


    private FootDetailInterface mInterface;

    public FootDetailPresenter(FootDetailInterface anInterface){
        mInterface = anInterface;
    }

    public void requestDetail(final String path, String uId, String menuId){
        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("u_id",uId);
        map.put("menu_id",menuId);
        RequestTools.getInstance().postRequest(path, false, map, "", new RequestCallBack(mContext) {
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
                    Log.e("详情数据",response.getData());
                    FootDetailBean bean = JSON.parseArray(response.getData(),FootDetailBean.class).get(0);
                    if(path.equals("/api/getMealCont.api.php")){
                        bean.setMy(true);
                    }
                    mInterface.requestDetailSucess(bean);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

    public List<FootDetailItemBean> hanbleFootDetailData(FootDetailBean bean){

        List<FootDetailItemBean> list = new ArrayList<>();
        if(bean.getFoodma().length()>0){
            list.add(new FootDetailItemBean("主要食材",bean.getFoodma()));
        }
        if(bean.getTact().length()>0){
            list.add(new FootDetailItemBean("做法",bean.getTact()));
        }
        if(bean.getValue().length()>0){
            list.add(new FootDetailItemBean("营养价值",bean.getValue()));
        }
        if(bean.getEfficacy().length()>0){
            list.add(new FootDetailItemBean("独特功效",bean.getEfficacy()));
        }
        if(bean.getLike_id().length()>0){
            list.add(new FootDetailItemBean("口味",bean.getLike_id()));
        }
        if(bean.getBan().length()>0){
            list.add(new FootDetailItemBean("禁忌",bean.getBan()));
        }
        return list;
    }

    public List<FootDetailItemBean> hanbleMyDetailData(FootDetailBean bean){
        List<FootDetailItemBean> list = new ArrayList<>();
        list.add(new FootDetailItemBean("",bean.getMealinfo()));
        return list;
    }
}
