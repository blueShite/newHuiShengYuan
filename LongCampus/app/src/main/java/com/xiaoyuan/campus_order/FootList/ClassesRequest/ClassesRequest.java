package com.xiaoyuan.campus_order.FootList.ClassesRequest;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.PackpageClassesBean;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/7/4.
 */

public class ClassesRequest {

    public static void requestClassesList(String resId,String flag, final Context context, final ClassesRequestInterface requestInterface){
        Map<String,String> map = new HashMap<>();
        map.put("res_cid",resId);
        map.put("flag",flag);
        RequestTools.getInstance().postRequest("/api/get_window.api.php", false, map, "", new RequestCallBack(context) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    List<PackpageClassesBean> mList = JSON.parseArray(response.getData(),PackpageClassesBean.class);
                    mList.get(0).setSelect(true);
                    requestInterface.requestClassesList(mList);
                }else {
                    Toasty.error(context,response.getMes()).show();
                }
            }
        });
    }

    public interface ClassesRequestInterface{

        void requestClassesList(List<PackpageClassesBean> list);
    }
}
