package com.xiaoyuan.campus_order.ShopCart.Bean;

import java.io.Serializable;

/**
 * Created by longhengyu on 2017/4/25.
 */

public class ShopCartBean implements Serializable {


    /**
     * menu_id : 66
     * pack : 0.00
     * res_name : 一食堂
     * dish : 红烧肉
     * dis_id : 49
     * foodma : 五花肉
     * dosing : 酱油、精盐、白糖、葱片、姜片、八角、香叶
     * tact : 1．将带皮五花肉切成方形块，葱、姜切成大片。
     2．锅内放油烧热，放入白糖翻炒，待变成糖色时放入肉块，加水适量，用酱油、精盐、白糖、葱片、姜片、八角、香叶调味，改小火焖1--1.5小时即成。0
     * litpic : /images/menu_img/20160817/20160817154229.jpg
     * intro : 红烧肉是热菜菜谱之一。以五花肉为制作主料，最好选用肥瘦相间的三层肉（五花肉）来做。红烧肉的烹饪技巧以砂锅为主，肥瘦相间，香甜松软，入口即化。红烧肉在我国各地流传甚广，是一道著名的大众菜肴。
     * like_id : 香甜
     * price : 0.00
     * discount : 1.00
     * num : 0
     * salnum : 2
     * flag : 1
     * ban : 猪肉中胆固醇含量偏高，故糖尿病、肥胖人群及血脂较高者不宜多食。
     * value : 猪肉含有丰富的优质蛋白质和必需的脂肪酸，并提供血红素（有机铁）和促进铁吸收的半胱氨酸，能改善缺铁性贫血；具有补肾养血，滋阴润燥的功效
     * efficacy : 润肠胃、生津液、补肾气、解热毒，主治热病伤津、消渴羸瘦、肾虚体弱、产后血虚、燥咳、便秘、补虚、滋阴、润燥、滋肝阴、润肌肤、利小便和止消渴。
     * typename : 热菜
     * once : 0.33
     * ifkeep : 1
     */

    private String menu_id;
    private String pack;
    private String res_name;
    private String dish;
    private String dis_id;
    private String foodma;
    private String dosing;
    private String tact;
    private String litpic;
    private String intro;
    private String like_id;
    private String price;
    private String discount;
    private String num;
    private String salnum;
    private String flag;
    private String ban;
    private String value;
    private String efficacy;
    private String typename;
    private String once;
    private String addNum;

    public String getAddNum() {
        return addNum;
    }

    public void setAddNum(String addNum) {
        this.addNum = addNum;
    }

    private int ifkeep;

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getDis_id() {
        return dis_id;
    }

    public void setDis_id(String dis_id) {
        this.dis_id = dis_id;
    }

    public String getFoodma() {
        return foodma;
    }

    public void setFoodma(String foodma) {
        this.foodma = foodma;
    }

    public String getDosing() {
        return dosing;
    }

    public void setDosing(String dosing) {
        this.dosing = dosing;
    }

    public String getTact() {
        return tact;
    }

    public void setTact(String tact) {
        this.tact = tact;
    }

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

    public String getLike_id() {
        return like_id;
    }

    public void setLike_id(String like_id) {
        this.like_id = like_id;
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

    public String getSalnum() {
        return salnum;
    }

    public void setSalnum(String salnum) {
        this.salnum = salnum;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getBan() {
        return ban;
    }

    public void setBan(String ban) {
        this.ban = ban;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEfficacy() {
        return efficacy;
    }

    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getOnce() {
        return once;
    }

    public void setOnce(String once) {
        this.once = once;
    }

    public int getIfkeep() {
        return ifkeep;
    }

    public void setIfkeep(int ifkeep) {
        this.ifkeep = ifkeep;
    }
}
