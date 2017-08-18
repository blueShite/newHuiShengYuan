package com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Interface;

import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Bean.OrderBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/7/8.
 */

public interface OrderOnPayListInterface {

    void requestOrderList(List<OrderBean> list);

    void requestListError(String error);

    void requestPay(String payData);

    void onClickShowComm(int poist);

    void onClickHideComm(int poist);

    void onClickOrderItem(int poist);

    void onClickLongOrderItem(int poist);

    void onClickPay(int poist);
}
