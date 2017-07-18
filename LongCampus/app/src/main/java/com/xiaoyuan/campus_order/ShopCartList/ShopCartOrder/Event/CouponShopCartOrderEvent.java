package com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Event;

import com.xiaoyuan.campus_order.PersonSubs.Coupon.Bean.CouponBean;

/**
 * Created by longhengyu on 2017/7/6.
 */

public class CouponShopCartOrderEvent {
    public final CouponBean mCouponBean;

    public CouponShopCartOrderEvent(CouponBean couponBean) {
        mCouponBean = couponBean;
    }
}
