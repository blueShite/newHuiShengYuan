package com.xiaoyuan.campus_order.FootList.ShopCartRequest;

import android.content.Context;

import com.xiaoyuan.campus_order.Manage.LoginManage;
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

public class ShopcartRequest {


    public static void requestShopCart(String res_cid,String num, String commId,String flag,final Context context, final ShopCartChangeInterface requestInterface){

        Map<String,String> map = new HashMap<>();
        map.put("num",num);
        map.put("menu_id",commId);
        map.put("res_cid",res_cid);
        map.put("flag",flag);
        map.put("u_id", LoginManage.getInstance().getLoginBean().getId());
        RequestTools.getInstance().postRequest("/api/update_shopping.api.php", false, map, "", new RequestCallBack(context) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    requestInterface.changeShopCart();
                }else {
                    Toasty.error(context,response.getMes()).show();
                }
            }
        });
    }

}
