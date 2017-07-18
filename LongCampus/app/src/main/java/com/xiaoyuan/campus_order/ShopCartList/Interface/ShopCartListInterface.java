package com.xiaoyuan.campus_order.ShopCartList.Interface;

import android.widget.TextView;

import com.xiaoyuan.campus_order.ShopCartList.Bean.ShopCartItemBean;
import com.xiaoyuan.campus_order.ShopCartList.Bean.ShopCartPriceBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/7/3.
 */

public interface ShopCartListInterface {

    void requestShopListSucess(List<ShopCartItemBean> list);

    void requestSubmitShopCartSucess(ShopCartPriceBean priceBean,String shopId,List<ShopCartItemBean> selectList);

    void onClickGroupItem(int poist);

    void onClickItemAdd(int poist , TextView addText);

    void onClickItemReduce(int poist, TextView jianText);

    void onClickItemDelete(int poist);
}
