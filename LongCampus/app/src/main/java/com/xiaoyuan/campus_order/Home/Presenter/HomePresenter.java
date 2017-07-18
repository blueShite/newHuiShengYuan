package com.xiaoyuan.campus_order.Home.Presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.Home.Bean.CanteenBean;
import com.xiaoyuan.campus_order.Home.Interface.HomeInterface;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/4/20.
 */

public class HomePresenter extends BasePresenter {

    private HomeInterface mHomeInterface;

    public HomePresenter(HomeInterface homeInterface){
        mHomeInterface = homeInterface;
    }

    public void requestHomeData(String page,String schId){

        showDialog();
        Map<String,String> map = new HashMap<>();
        if(LoginManage.getInstance().getLoginBean().getSch_id()==null
                ||LoginManage.getInstance().getLoginBean().getSch_id().isEmpty()){
            map.put("sch_id", "1");
        }else {
            map.put("sch_id", LoginManage.getInstance().getLoginBean().getSch_id());
        }
        map.put("page",page);
        RequestTools.getInstance().postRequest("/api/getResList.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
                mHomeInterface.requestHomeDataError("请求失败");
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                if(response.isRes()){
                    Log.e("首页数据",response.getData());
                    List<CanteenBean> list = JSON.parseArray(response.getData(),CanteenBean.class);
                    mHomeInterface.requestHomeDataSucess(list);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                    mHomeInterface.requestHomeDataError(response.getMes());
                }
                super.onResponse(response, id);
            }
        });
    }
}
