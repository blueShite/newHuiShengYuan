package com.xiaoyuan.campus_order.FootList.SubFootList.Interface;

import android.widget.TextView;

import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.FeatureBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/6/27.
 */

public interface SaleInterface  {

    void requestSucess(List<FeatureBean> list);

    void requestError(String error);

    void onClickSelfItem(int poist);

    void onClickCollection(int poist);

    void onClickItemAdd(int poist , TextView addText);

    void onClickItemReduce(int poist, TextView jianText);
}
