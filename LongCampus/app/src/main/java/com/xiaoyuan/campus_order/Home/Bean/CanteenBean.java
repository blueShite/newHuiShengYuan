package com.xiaoyuan.campus_order.Home.Bean;

import java.io.Serializable;

/**
 * Created by longhengyu on 2017/4/20.
 */

public class CanteenBean implements Serializable {


    /**
     * res_id : 1
     * sch_id : 1
     * res_name : 一食堂
     * res_intro : 暂未营业，敬请期待！！！





     一食堂拥有配套餐桌150套，可供600人同时用餐，每餐服务在校师生1200余人。操作间干净卫生，售饭窗口宽敞明亮。食堂饭菜经济实惠、种类丰富，有不同口味的菜品、套餐、水饺、包子、糕点、面、粥等。其中，水饺、套餐独具特色广受师生好评。食堂严格按照学校的管理体制和《食品安全法》的要求生产运营，切实为广大在校师生提供健康安全的饮食服务。欢迎广大师生来用餐。
     * res_addr : 西院一食堂
     * res_tel : 0
     * res_img : /api/editImg.php?img=http://www.hsydining-hall.com/images/res_img/20170406/20170406113444-1.jpg
     * sch_name : XX大学中心校区
     */

    private String res_id;
    private String sch_id;
    private String res_names;
    private String res_intro;
    private String res_addr;
    private String res_tel;
    private String res_img;
    private String sch_name;

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

    public String getRes_img() {
        return res_img;
    }

    public void setRes_img(String res_img) {
        this.res_img = res_img;
    }

    public String getSch_name() {
        return sch_name;
    }

    public void setSch_name(String sch_name) {
        this.sch_name = sch_name;
    }
}
