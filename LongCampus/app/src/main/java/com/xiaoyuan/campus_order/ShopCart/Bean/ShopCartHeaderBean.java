package com.xiaoyuan.campus_order.ShopCart.Bean;

import java.util.List;

/**
 * Created by longhengyu on 2017/4/27.
 */

public class ShopCartHeaderBean {


    /**
     * res_id : 1
     * sch_id : 1
     * res_name : 一食堂
     * res_zao :
     * res_zaoe :
     * res_zho :
     * res_zhoe :
     * res_wan :
     * res_wane :
     * res_intro : 暂未营业，敬请期待！！！





     一食堂拥有配套餐桌150套，可供600人同时用餐，每餐服务在校师生1200余人。操作间干净卫生，售饭窗口宽敞明亮。食堂饭菜经济实惠、种类丰富，有不同口味的菜品、套餐、水饺、包子、糕点、面、粥等。其中，水饺、套餐独具特色广受师生好评。食堂严格按照学校的管理体制和《食品安全法》的要求生产运营，切实为广大在校师生提供健康安全的饮食服务。欢迎广大师生来用餐。
     * res_addr : 西院一食堂
     * res_tel : 0
     * res_img : ["/images/res_img/20170406/20170406113444-1.jpg","/images/res_img/20170406/20170406113444-2.jpg","/images/res_img/20170406/20170406113444-3.jpg"]
     */

    private String res_id;
    private String sch_id;
    private String res_names;
    private String res_zao;
    private String res_zaoe;
    private String res_zho;
    private String res_zhoe;
    private String res_wan;
    private String res_wane;
    private String res_intro;
    private String res_addr;
    private String res_tel;
    private List<String> res_img;
    private int imageId;
    private String headerText;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getSch_id() {
        return sch_id;
    }

    public void setSch_id(String sch_id) {
        this.sch_id = sch_id;
    }

    public String getRes_names() {
        return res_names;
    }

    public void setRes_names(String res_names) {
        this.res_names = res_names;
    }

    public String getRes_zao() {
        return res_zao;
    }

    public void setRes_zao(String res_zao) {
        this.res_zao = res_zao;
    }

    public String getRes_zaoe() {
        return res_zaoe;
    }

    public void setRes_zaoe(String res_zaoe) {
        this.res_zaoe = res_zaoe;
    }

    public String getRes_zho() {
        return res_zho;
    }

    public void setRes_zho(String res_zho) {
        this.res_zho = res_zho;
    }

    public String getRes_zhoe() {
        return res_zhoe;
    }

    public void setRes_zhoe(String res_zhoe) {
        this.res_zhoe = res_zhoe;
    }

    public String getRes_wan() {
        return res_wan;
    }

    public void setRes_wan(String res_wan) {
        this.res_wan = res_wan;
    }

    public String getRes_wane() {
        return res_wane;
    }

    public void setRes_wane(String res_wane) {
        this.res_wane = res_wane;
    }

    public String getRes_intro() {
        return res_intro;
    }

    public void setRes_intro(String res_intro) {
        this.res_intro = res_intro;
    }

    public String getRes_addr() {
        return res_addr;
    }

    public void setRes_addr(String res_addr) {
        this.res_addr = res_addr;
    }

    public String getRes_tel() {
        return res_tel;
    }

    public void setRes_tel(String res_tel) {
        this.res_tel = res_tel;
    }

    public List<String> getRes_img() {
        return res_img;
    }

    public void setRes_img(List<String> res_img) {
        this.res_img = res_img;
    }
}
