package com.xiaoyuan.campus_order.FootList.SubFootList.Presenter;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.FeatureBean;
import com.xiaoyuan.campus_order.FootList.SubFootList.Interface.FeatureInterface;
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
 * Created by longhengyu on 2017/6/27.
 */

public class FeaturePresenter extends BasePresenter {

    private FeatureInterface mInterface;

    public FeaturePresenter(FeatureInterface anInterface){
        mInterface = anInterface;
    }

    public void requestList(String page, final String res_id){

        showDialog();
        Map<String, String> params = new HashMap<>();
        params.put("page", page);
        params.put("res_id", res_id);
        params.put("u_id", LoginManage.getInstance().getLoginBean().getId());
        params.put("flag","3");
        RequestTools.getInstance().postRequest("/api/getMeal.api.php", false, params, "", new RequestCallBack(mContext) {
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
                    List<FeatureBean> list = JSON.parseArray(response.getData(),FeatureBean.class);
                    for (FeatureBean bean:list){
                        bean.setAddNum("0");
                    }
                    mInterface.requestSucess(list);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                    mInterface.requestError(response.getMes());
                }
            }
        });
    }

}
