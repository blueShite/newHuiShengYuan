package com.xiaoyuan.campus_order.ShopCartList.Bean;

import java.io.Serializable;

/**
 * Created by longhengyu on 2017/7/5.
 */

public class ShopCartPriceBean implements Serializable {

    /**
     * total : 144.5
     * pack : 5.5
     */

    private double total;
    private double pack;
    private double delivery;

    public double getDelivery() {
        return delivery;
    }

    public void setDelivery(double delivery) {
        this.delivery = delivery;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPack() {
        return pack;
    }

    public void setPack(double pack) {
        this.pack = pack;
    }
}
