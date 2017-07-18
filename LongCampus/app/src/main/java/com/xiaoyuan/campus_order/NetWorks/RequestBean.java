package com.xiaoyuan.campus_order.NetWorks;

/**
 * Created by longhengyu on 2017/3/31.
 */

public class RequestBean<T> {

    private boolean res;
    private String mes;
    private String data;

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
