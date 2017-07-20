package com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.Address.Bean.AddressBean;
import com.xiaoyuan.campus_order.ShopCartList.Bean.ShopCartItemBean;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Bean.ShopCartOrderFootBean;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Interface.ShopCartOrderInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/7/5.
 */

public class ShopCartOrderPresenter extends BasePresenter {

    private ShopCartOrderInterface mInterface;

    public ShopCartOrderPresenter(ShopCartOrderInterface anInterface){
        mInterface = anInterface;
    }

    public List<ShopCartItemBean> handleList(List<ShopCartItemBean> list){

        List<ShopCartItemBean> itemList = new ArrayList<>();

        for (ShopCartItemBean itemBean:list){
            if(itemBean.getItemType().equals("1")){
                int itemNum = Integer.parseInt(itemBean.getNum());
                for (int i=0;i<itemNum;i++){
                    ShopCartItemBean cartItemBean = new ShopCartItemBean();
                    cartItemBean.setNum("1");
                    cartItemBean.setItemType(itemBean.getItemType());
                    cartItemBean.setDish(itemBean.getDish());
                    cartItemBean.setFlag(itemBean.getFlag());
                    cartItemBean.setMenu_id(itemBean.getMenu_id());
                    cartItemBean.setRes_id(itemBean.getRes_id());
                    cartItemBean.setRes_name(itemBean.getRes_name());
                    cartItemBean.setRes_names(itemBean.getRes_names());
                    cartItemBean.setTotal(itemBean.getTotal());
                    cartItemBean.setId(itemBean.getId());
                    cartItemBean.setOnePrice(itemBean.getOnePrice());
                    itemList.add(cartItemBean);
                }
            }else {
                itemList.add(itemBean);
            }
        }
        return itemList;
    }

    public void requestAddressList(String uId){
        Map<String,String> map = new HashMap<>();
        map.put("id",uId);
        RequestTools.getInstance().postRequest("/api/list_address.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    List<AddressBean> list = JSON.parseArray(response.getData(),AddressBean.class);
                    mInterface.requestAddressList(list);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

    public void requestSubmitOrder(ShopCartOrderFootBean footBean){
        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("text",footBean.getRemark());
        map.put("genre",footBean.getGiveType()+"");
        map.put("id",footBean.getShopId());
        map.put("diner_time",footBean.getTime());
        if(footBean.getGiveType()==1){
            map.put("address",footBean.getAddressId());
        }else {
            map.put("address","");
        }
        if(footBean.getCouponId()!=null&&footBean.getCouponId().length()>0){
            map.put("usrcpid",footBean.getCouponId());
        }else {
            map.put("usrcpid","");
        }
        RequestTools.getInstance().postRequest("/api/add_order.api.php", false, map, "", new RequestCallBack(mContext) {
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
                    Log.e("----------------",response.getData());
                    mInterface.requestSubmitSucess(response.getData());
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });

    }
}
