package com.xiaoyuan.campus_order.FootList.Bean;

import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartHeaderBean;

import java.util.List;

/**
 * Created by longhengyu on 2017/7/17.
 */

public class FootListBannerBean {

    /**
     * res : true
     * total : 1
     * uri : /api/home/res.php
     * data : [{"res_id":"1","sch_id":"1","res_cid":"0","res_names":"一餐厅","res_name":"","user_name":"","pass_word":"","fen_s":"0","res_zao":"0","res_zaoe":"","res_zho":"","res_zhoe":"","res_wan":"","res_wane":"","res_intro":"暂未营业，敬请期待！！！\r\n\r\n\r\n\r\n\r\n\r\n一食堂拥有配套餐桌150套，可供600人同时用餐，每餐服务在校师生1200余人。操作间干净卫生，售饭窗口宽敞明亮。食堂饭菜经济实惠、种类丰富，有不同口味的菜品、套餐、水饺、包子、糕点、面、粥等。其中，水饺、套餐独具特色广受师生好评。食堂严格按照学校的管理体制和《食品安全法》的要求生产运营，切实为广大在校师生提供健康安全的饮食服务。欢迎广大师生来用餐。","res_addr":"西院一食堂","res_tel":"0","res_img":["/images/res_img/20170613/20170613102823-1.jpg","/images/res_img/20170613/20170613102823-1.jpg","/images/res_img/20170613/20170613102823-1.jpg"],"show_type":"1"}]
     */

    private boolean res;
    private int total;
    private String uri;
    private List<ShopCartHeaderBean> data;
    private String mes;

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<ShopCartHeaderBean> getData() {
        return data;
    }

    public void setData(List<ShopCartHeaderBean> data) {
        this.data = data;
    }
}
