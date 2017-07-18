package com.xiaoyuan.campus_order.FootList.SubFootList.Interface;

import android.widget.TextView;

import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.FeatureBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/6/27.
 */

public interface FeatureInterface {

    void requestSucess(List<FeatureBean> list);

    void requestError(String error);

    void onClickItem(int poist);

    void onClickCollection(int poist);

    void onClickAddShopCart(int poist, TextView numTextView);

    void onClickReduxShopCart(int poist, TextView numTextView);


}
