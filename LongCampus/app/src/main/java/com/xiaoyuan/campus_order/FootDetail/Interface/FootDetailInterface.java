package com.xiaoyuan.campus_order.FootDetail.Interface;

import android.widget.TextView;

import com.xiaoyuan.campus_order.FootDetail.Bean.FootDetailBean;

/**
 * Created by longhengyu on 2017/7/1.
 */

public interface FootDetailInterface {

    void requestDetailSucess(FootDetailBean detailBean);

    void onClickAdd(TextView numTextView);

    void onClickRedux(TextView numTextView);

}
