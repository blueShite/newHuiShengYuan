package com.xiaoyuan.campus_order.FootList.SubFootList.Presenter;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.FeatureBean;
import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.PackpageClassesBean;
import com.xiaoyuan.campus_order.FootList.SubFootList.Interface.MyPackpageInterface;
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
 * Created by longhengyu on 2017/6/29.
 */

public class MyPackpagePresenter extends BasePresenter {

    private MyPackpageInterface mInterface;
    private List<PackpageClassesBean> mList;

    public MyPackpagePresenter(MyPackpageInterface anInterface){
        mInterface = anInterface;
    }

    public void requestClassesList(final String resId){

        Map<String,String> map = new HashMap<>();
        map.put("res_cid",resId);
        map.put("flag","4");
        RequestTools.getInstance().postRequest("/api/get_window.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    mList = JSON.parseArray(response.getData(),PackpageClassesBean.class);
                    mList.get(0).setSelect(true);
                    requestCommodityList(LoginManage.getInstance().getLoginBean().getHate(),
                            LoginManage.getInstance().getLoginBean().getLike_id(),"1",mList.get(0).getRes_id(),
                            LoginManage.getInstance().getLoginBean().getId(),LoginManage.getInstance().getLoginBean().getTaboos(),true);

                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

    public void requestCommodityList(String hateId,String likeId, String page, final String resId, String uId, String taboos, final boolean isFirst){
        showDialog();
        Map<String,String> map = new HashMap<>();
        if(hateId!=null&&!hateId.isEmpty()){
            //map.put("hate",hateId);
        }
        if(likeId!=null&&!likeId.isEmpty()){
            //map.put("like",likeId);
        }
        map.put("page",page);
        map.put("res_id",resId);
        map.put("u_id",uId);
        map.put("taboos",taboos);
        RequestTools.getInstance().postRequest("/api/getDishList.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
                if(isFirst) {
                    mInterface.requestClassesSuccess(mList, null);
                }else {
                    mInterface.requestCommodityError("请求失败");
                }
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){
                    List<FeatureBean> list = JSON.parseArray(response.getData(),FeatureBean.class);
                    if(isFirst){
                        mInterface.requestClassesSuccess(mList,list);
                    }else {
                        mInterface.requestCommoditySuccess(list);
                    }

                }else {
                    Toasty.error(mContext,response.getMes()).show();
                    if(isFirst){
                        mInterface.requestClassesSuccess(mList,null);
                    }else {
                        mInterface.requestCommodityError(response.getMes());
                    }
                }
            }
        });
    }
}
