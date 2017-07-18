package com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Bean;

import java.util.List;

/**
 * Created by longhengyu on 2017/7/8.
 */

public class OrderBean {

    boolean isShowComm;
    /**
     * add_time : 2017-07-10 10:18:26
     * id : 1707101018266786
     * res_name : 一窗口
     * res_img : /images/res_img/20170613/20170613102906-1
     * itmes : [{"total":15.5,"num":1,"dish":"农家小炒肉盖饭"},{"total":15.5,"num":1,"dish":"北京烤鸭"}]
     */

    private String add_time;
    private String id;
    private String res_name;
    private String res_img;
    private List<ItmesBean> itmes;
    private double totals;
    private int nums;
    private String remark;
    private String order_reply;

    public String getOrder_reply() {
        return order_reply;
    }

    public void setOrder_reply(String order_reply) {
        this.order_reply = order_reply;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getTotals() {
        return totals;
    }

    public void setTotals(double totals) {
        this.totals = totals;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public boolean isShowComm() {
        return isShowComm;
    }

    public void setShowComm(boolean showComm) {
        isShowComm = showComm;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRes_img() {
        return res_img;
    }

    public void setRes_img(String res_img) {
        this.res_img = res_img;
    }

    public List<ItmesBean> getItmes() {
        return itmes;
    }

    public void setItmes(List<ItmesBean> itmes) {
        this.itmes = itmes;
    }

    public static class ItmesBean {
        /**
         * total : 15.5
         * num : 1
         * dish : 农家小炒肉盖饭
         */

        private double total;
        private int num;
        private String dish;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getDish() {
            return dish;
        }

        public void setDish(String dish) {
            this.dish = dish;
        }
    }
}
