package com.xiaoyuan.campus_order.ShopCart.Presenter;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartBean;
import com.xiaoyuan.campus_order.ShopCart.Interface.TodayFeatureInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/6/19.
 */

public class TodayFeaturePresenter extends BasePresenter {

    private TodayFeatureInterface mInterface;

    public TodayFeaturePresenter(TodayFeatureInterface anInterface){
        mInterface = anInterface;
    }

    public void requestItem(String path, final String page, final String resId, final String flag){

        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("page",page);
        map.put("res_id",resId);
        map.put("u_id", LoginManage.getInstance().getLoginBean().getId());
        if(flag.equals("1")||flag.equals("2")){
            map.put("flag",flag);
        }
        RequestTools.getInstance().postRequest(path, false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
                mInterface.requestItemError("请求失败");
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){
                   List<ShopCartBean> mList = JSON.parseArray(response.getData(),ShopCartBean.class);
                    for (int i=0;i<mList.size();i++){
                        ShopCartBean bean = mList.get(i);
                        bean.setAddNum("0");
                    }
                    mInterface.requestItemSuccess(mList,flag,page);
                }else {
                    mInterface.requestItemError(response.getMes());
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });

    }

}
