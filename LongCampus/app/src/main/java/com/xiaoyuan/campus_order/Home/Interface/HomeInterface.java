package com.xiaoyuan.campus_order.Home.Interface;

import com.xiaoyuan.campus_order.Home.Bean.CanteenBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/4/20.
 */

public interface HomeInterface  {

    void requestHomeDataSucess(List<CanteenBean> list);

    void requestHomeDataError(String error);

}
