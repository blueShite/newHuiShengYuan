package com.xiaoyuan.campus_order.PersonSubs.SetPerson.Bean;

/**
 * Created by longhengyu on 2017/6/23.
 */

public class SetPersonBean {

    private String name;
    private String sub;

    public SetPersonBean(String aName,String aSub){
        name = aName;
        sub = aSub;
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
