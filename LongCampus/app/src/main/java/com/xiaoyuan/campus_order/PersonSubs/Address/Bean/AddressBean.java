package com.xiaoyuan.campus_order.PersonSubs.Address.Bean;

import java.io.Serializable;

/**
 * Created by longhengyu on 2017/6/28.
 */

public class AddressBean implements Serializable {

    /**
     * acc_id : 1044
     * id : 5
     * acc_phone : 13676917233
     * acc_name : 龙恒宇
     * acc_address : 呵呵呵呵呵呵
     */

    private String acc_id;
    private String id;
    private String acc_phone;
    private String acc_name;
    private String acc_address;
    private String acc_state;

    public String getAcc_state() {
        return acc_state;
    }
    public void setAcc_state(String acc_state) {
        this.acc_state = acc_state;
    }

    public String getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(String acc_id) {
        this.acc_id = acc_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcc_phone() {
        return acc_phone;
    }

    public void setAcc_phone(String acc_phone) {
        this.acc_phone = acc_phone;
    }

    public String getAcc_name() {
        return acc_name;
    }

    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }

    public String getAcc_address() {
        return acc_address;
    }

    public void setAcc_address(String acc_address) {
        this.acc_address = acc_address;
    }
}
