package com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Interface;

import com.xiaoyuan.campus_order.PersonSubs.Address.Bean.AddressBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/7/5.
 */

public interface ShopCartOrderInterface {

    void requestAddressList(List<AddressBean> list);

    void requestSubmitSucess(String payData);

    void onClickCoupon();

    void onClickTime();

    void onClickGiveType(int type);

    void itemEditText(int poist,String editText);

}
