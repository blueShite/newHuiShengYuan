package com.xiaoyuan.campus_order.Information.Interface;

import com.xiaoyuan.campus_order.Information.Bean.InformationBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/4/22.
 */

public interface InformationInterface {

    void requestHeaderSucess(List<InformationBean> bannerList);

    void requestSuccess(List<InformationBean> itemList);

    void requestError(String error);

    void onClickHeader(int poist);

    void onClickitem(int poist);

}
