package com.xiaoyuan.campus_order.ShopCart.Interface;

import android.widget.TextView;

import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartBean;
import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartHeaderBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/4/25.
 */

public interface ShopCartInterface {

     void requestItemSuccess(List<ShopCartBean> list, ShopCartHeaderBean headerBean, String tag);

     void requestItemError(String error);

     void onClickHeader(int poist);

     void onClickItem(int poist);

     void onClickItemAdd(int poist , TextView addText);

     void onClickItemReduce(int poist, TextView jianText);

}
