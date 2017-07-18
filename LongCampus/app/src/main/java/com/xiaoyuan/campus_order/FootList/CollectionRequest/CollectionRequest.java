package com.xiaoyuan.campus_order.FootList.CollectionRequest;

import android.content.Context;

import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/6/30.
 */

public class CollectionRequest {

    public static void requestCollection(String uId, String commId, final Context context, final CollectionRequestInterface requestInterface){
        Map<String,String> map = new HashMap<>();
        map.put("u_id",uId);
        map.put("menu_id",commId);
        RequestTools.getInstance().postRequest("/api/addKeep.api.php", false, map, "", new RequestCallBack(context) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    Toasty.success(context,"收藏成功").show();
                    requestInterface.collectionSucess();
                }else {
                    Toasty.error(context,response.getMes()).show();
                }
            }
        });
    }
}
