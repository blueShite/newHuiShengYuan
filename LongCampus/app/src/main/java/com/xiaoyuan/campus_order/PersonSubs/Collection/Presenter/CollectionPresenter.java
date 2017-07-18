package com.xiaoyuan.campus_order.PersonSubs.Collection.Presenter;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.Collection.Bean.CollectionBean;
import com.xiaoyuan.campus_order.PersonSubs.Collection.Interface.CollectionInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/6/20.
 */

public class CollectionPresenter extends BasePresenter {

    private CollectionInterface mInterface;

    public CollectionPresenter(CollectionInterface anInterface){
        mInterface = anInterface;
    }

    public void requestCollection(final String page, String uId){
        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("page",page);
        map.put("u_id",uId);
        RequestTools.getInstance().postRequest("/api/getKeepMenu.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
                mInterface.requestCollectionError("请求失败");
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){
                    List<CollectionBean> list = JSON.parseArray(response.getData(),CollectionBean.class);
                    mInterface.requestCollectionSucess(list,page);
                }else {
                    mInterface.requestCollectionError(response.getMes());
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

    public void requestCancelCollection(String uId, String menuId, final int index){

        Map<String,String> map = new HashMap<>();
        map.put("u_id",uId);
        map.put("menu_id",menuId);
        RequestTools.getInstance().postRequest("/api/del_Keeps.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    mInterface.requestCancelCollection(index);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });

    }

}
