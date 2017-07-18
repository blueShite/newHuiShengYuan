package com.xiaoyuan.campus_order.Home.SearchSchool.Presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.Home.SearchSchool.Bean.SearchSchoolBean;
import com.xiaoyuan.campus_order.Home.SearchSchool.Interface.SearchSchoolInterface;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/7/13.
 */

public class SearchSchoolPresenter extends BasePresenter {

    private SearchSchoolInterface mInterface;

    public SearchSchoolPresenter(SearchSchoolInterface anInterface){
        mInterface = anInterface;
    }

    public void requestHot(){
        RequestTools.getInstance().postRequest("/api/getFlagSchool.api.php", false, null, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    Log.e("热门数据",response.getData());
                    List<SearchSchoolBean> list = JSON.parseArray(response.getData(),SearchSchoolBean.class);
                    mInterface.requestHotSucess(list);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

    public void requestSearch(String searchStr){
        Map<String,String> map = new HashMap<>();
        map.put("sch_name",searchStr);
        RequestTools.getInstance().postRequest("/api/sel_res.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    Log.e("搜索结果",response.getData());
                    List<SearchSchoolBean> list = JSON.parseArray(response.getData(),SearchSchoolBean.class);
                    mInterface.requestSearchSucess(list);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

}
