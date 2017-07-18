package com.xiaoyuan.campus_order.FootList.Interface;

import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartHeaderBean;

/**
 * Created by longhengyu on 2017/6/26.
 */

public interface FootListInterface {

    void requestSucess(ShopCartHeaderBean headerBean, String uri);

    void requestShopNum(String shopNum);
}
