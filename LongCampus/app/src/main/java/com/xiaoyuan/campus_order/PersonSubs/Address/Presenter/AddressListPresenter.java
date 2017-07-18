package com.xiaoyuan.campus_order.PersonSubs.Address.Presenter;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.Login.Bean.LoginBean;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.Address.Bean.AddressBean;
import com.xiaoyuan.campus_order.PersonSubs.Address.Interface.AddressListInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/6/28.
 */

public class AddressListPresenter extends BasePresenter {

    private AddressListInterface mInterface;
    public AddressListPresenter(AddressListInterface anInterface){
        mInterface = anInterface;
    }

    public void requestList(String uId){
        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("id",uId);
        RequestTools.getInstance().postRequest("/api/list_address.api.php", false, map, "", new RequestCallBack(mContext) {
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
                    List<AddressBean> list = JSON.parseArray(response.getData(),AddressBean.class);
                    for (AddressBean bean:list){

                        if(bean.getAcc_state().equals("1")){
                            LoginBean loginBean = LoginManage.getInstance().getLoginBean();
                            loginBean.setLaddressId(bean.getId());
                            loginBean.setAddress(bean.getAcc_address());
                            LoginManage.getInstance().saveLoginBean(loginBean);
                            break;
                        }
                    }
                    mInterface.requestSucess(list);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }
    public void requestDelect(String addressId, final int poist){

        Map<String,String> map = new HashMap<>();
        map.put("id",addressId);
        RequestTools.getInstance().postRequest("/api/del_address.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    Toasty.success(mContext,"删除成功!").show();
                    mInterface.requestDeleteSucess(poist);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

    public void requestSetDefaultAddress(String uId, final String addressId, final String address, final int poist){

        Map<String,String> map = new HashMap<>();
        map.put("u_id",uId);
        map.put("id",addressId);
        RequestTools.getInstance().postRequest("/api/default.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    LoginBean loginBean = LoginManage.getInstance().getLoginBean();
                    loginBean.setAddress(address);
                    loginBean.setLaddressId(addressId);
                    LoginManage.getInstance().saveLoginBean(loginBean);
                    mInterface.requestDefaultSucess(poist);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }
}
