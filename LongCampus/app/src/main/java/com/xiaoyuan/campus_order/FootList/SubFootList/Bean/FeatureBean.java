package com.xiaoyuan.campus_order.FootList.SubFootList.Bean;

import java.io.Serializable;

/**
 * Created by longhengyu on 2017/6/27.
 */

public class FeatureBean implements Serializable {


    /**
     * menu_id : 465
     * dish : 早餐
     * pack : 1.50
     * res_id : 22
     * price : 17.00
     * discount : 2.00
     * num : 100
     * mealinfo : 早餐，营养丰富，美味
     * meal_litpic : /images/menu_img/20170525/20170525084455.jpg
     * res_name : 一窗口
     * ifkeep : 0
     * typename : 特色套餐
     * fen_s : 0.20
     * nums : 0
     */

    private String menu_id;
    private String dish;
    private String pack;
    private String res_id;
    private String price;
    private String discount;
    private String num;
    private String mealinfo;
    private String meal_litpic;
    private String res_name;
    private int ifkeep;
    private String typename;
    private String fen_s;
    private String nums;
    private String addNum;
    private String intro;
    private String litpic;

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAddNum() {
        return addNum;
    }

    public void setAddNum(String addNum) {
        this.addNum = addNum;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMealinfo() {
        return mealinfo;
    }

    public void setMealinfo(String mealinfo) {
        this.mealinfo = mealinfo;
    }

    public String getMeal_litpic() {
        return meal_litpic;
    }

    public void setMeal_litpic(String meal_litpic) {
        this.meal_litpic = meal_litpic;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public int getIfkeep() {
        return ifkeep;
    }

    public void setIfkeep(int ifkeep) {
        this.ifkeep = ifkeep;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getFen_s() {
        return fen_s;
    }

    public void setFen_s(String fen_s) {
        this.fen_s = fen_s;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }
}
