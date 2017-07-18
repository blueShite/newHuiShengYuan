package com.xiaoyuan.campus_order.PersonSubs.Integral.Interface;

import com.xiaoyuan.campus_order.PersonSubs.Integral.Bean.IntegralBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/6/21.
 */

public interface IntegralInterface  {

    void requestIntegralSucess(List<IntegralBean> list);
    void requestImtegralError(String error);
}
