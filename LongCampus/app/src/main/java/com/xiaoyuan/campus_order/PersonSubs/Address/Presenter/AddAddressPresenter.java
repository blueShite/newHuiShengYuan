package com.xiaoyuan.campus_order.PersonSubs.Address.Presenter;

import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.Address.Interface.AddAddressInterface;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/6/28.
 */

public class AddAddressPresenter extends BasePresenter {

    private AddAddressInterface mInterface;

    public AddAddressPresenter (AddAddressInterface anInterface){
        mInterface = anInterface;
    }

    public void requestSubmitSucess(String uId,String name,String phone,String address){
        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("acc_name",name);
        map.put("acc_phone",phone);
        map.put("acc_address",address);
        map.put("acc_id",uId);
        RequestTools.getInstance().postRequest("/api/add_address.api.php", false, map, "", new RequestCallBack(mContext) {
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
                    mInterface.requestSubmitSucess();
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });

    }

    public void requestSetAddress(String addressId,String name,String phone,String address){

        Map<String,String> map = new HashMap<>();
        map.put("id",addressId);
        map.put("acc_address",address);
        map.put("acc_name",name);
        map.put("acc_phone",phone);
        RequestTools.getInstance().postRequest("/api/updata_address.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    mInterface.requestSubmitSucess();
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });

    }

}
