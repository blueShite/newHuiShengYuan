package com.xiaoyuan.campus_order.ShopCart.Interface;

import android.widget.TextView;

import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/6/19.
 */

public interface TodayFeatureInterface  {

    void requestItemSuccess(List<ShopCartBean> list, String tag, String page);

    void requestItemError(String error);

    void onClickItem(int poist);

    void onClickItemAdd(int poist , TextView addText);

    void onClickItemReduce(int poist, TextView jianText);
}
