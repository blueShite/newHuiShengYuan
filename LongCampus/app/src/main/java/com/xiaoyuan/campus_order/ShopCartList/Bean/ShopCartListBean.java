package com.xiaoyuan.campus_order.ShopCartList.Bean;

import java.util.List;

/**
 * Created by longhengyu on 2017/7/4.
 */

public class ShopCartListBean {

    /**
     * items : [{"menu_id":"496","res_names":"一餐厅","res_id":"22","total":"1.10","num":"1","res_name":"一窗口","dish":"宫保鸡丁"}]
     * name : 一窗口
     */

    private String name;
    private List<ShopCartItemBean> itmes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShopCartItemBean> getItmes() {
        return itmes;
    }

    public void setItmes(List<ShopCartItemBean> itmes) {
        this.itmes = itmes;
    }
}
