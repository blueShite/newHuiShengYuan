package com.xiaoyuan.campus_order.FootList.SubFootList.Bean;

/**
 * Created by longhengyu on 2017/6/29.
 */

public class PackpageClassesBean {

    private String res_id;
    private String res_name;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }
}
