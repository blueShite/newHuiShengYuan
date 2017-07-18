package com.xiaoyuan.campus_order.PersonSubs.Collection.Interface;

import com.xiaoyuan.campus_order.PersonSubs.Collection.Bean.CollectionBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/6/20.
 */

public interface CollectionInterface {

    void requestCollectionSucess(List<CollectionBean> list, String page);
    void requestCollectionError(String error);
    void onClickCollectSelect(int index);
    void requestCancelCollection(int index);
    void onClickItemView(int poist);

}
