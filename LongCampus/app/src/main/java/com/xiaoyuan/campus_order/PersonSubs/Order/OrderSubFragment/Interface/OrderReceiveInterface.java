package com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Interface;

import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Bean.OrderBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/7/11.
 */

public interface OrderReceiveInterface {

    void requestOrderList(List<OrderBean> list);

    void requestListError(String error);

    void requestPingLunSucess(int poist,String remarkStr);

    void onClickOrderItem(int poist);

    void itemEditText(int poist,String remarkStr);

    void onClickPinglunBtn(int poist);

}
