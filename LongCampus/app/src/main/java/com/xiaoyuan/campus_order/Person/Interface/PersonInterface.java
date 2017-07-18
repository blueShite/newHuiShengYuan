package com.xiaoyuan.campus_order.Person.Interface;

import com.xiaoyuan.campus_order.Person.Bean.PersonBalanceBean;

/**
 * Created by longhengyu on 2017/6/20.
 */

public interface PersonInterface {

    void requestPersonData(PersonBalanceBean balanceBean);
    void onClickHeaderView(int headerIndex);
    void onClickItem(int itemIndex);
    void onClickLogout();

}
