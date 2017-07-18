package com.xiaoyuan.campus_order.FootDetail.Bean;

/**
 * Created by longhengyu on 2017/4/28.
 */

public class FootDetailItemBean {

    String name;
    String sub;

    public FootDetailItemBean(String itemName,String itemSub){
        name = itemName;
        sub = itemSub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
