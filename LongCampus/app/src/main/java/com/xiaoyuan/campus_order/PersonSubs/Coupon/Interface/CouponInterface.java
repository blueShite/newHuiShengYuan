package com.xiaoyuan.campus_order.PersonSubs.Coupon.Interface;

import com.xiaoyuan.campus_order.PersonSubs.Coupon.Bean.CouponBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/6/30.
 */

public interface CouponInterface {


    void requestCouponList(List<CouponBean> list);

    void requestCouponError(String error);

    void onClickCoupon(int poist);

    void  requestReceiveSucess(int poist);
}
