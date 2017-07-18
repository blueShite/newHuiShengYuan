package com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.Interface;

import com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.Bean.IntergralExchangeBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/7/3.
 */

public interface IntegarlExchangeInterface {

    void requestListSucess(List<IntergralExchangeBean> list);

    void requestExChangeSuccess(int poist);

    void onClickExchangeSucess(int poist);

}
