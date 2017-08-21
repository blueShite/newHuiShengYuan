package com.xiaoyuan.campus_order.PersonSubs.SetLike.Interface;

import com.xiaoyuan.campus_order.PersonSubs.SetLike.Bean.SetLikeBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/6/22.
 */

public interface SetLikeInterface {

    void requestLikeSubmit(String likeStr);
    void requestHateSubmit(String hateStr);
    void requestSubmitAll(String likeStr,String hateStr);
    void requestFootTypeList(List<SetLikeBean> list);
    void requestFlavorList(List<SetLikeBean> list);

}
