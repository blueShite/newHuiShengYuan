package com.xiaoyuan.campus_order.PersonSubs.Order.OrderDetail.Bean;

import java.util.List;

/**
 * Created by longhengyu on 2017/7/10.
 */

public class OrderDetailBean {


    /**
     * address : {"phone":"13838383883","name":"龙哈鱼","dizhi":"鹿邑县第一看守所"}
     * add_time : 2017.07.11 16:04:15
     * title : 校园外卖
     * total : 46.5
     * nums : 3
     * id : 1707111604150167
     * diner_time : 16:03
     * pack : 2.00
     * dispatching : 1
     * itmes : [[{"price":"15.00","total":"1","text":"睡觉睡觉","num":"1","dish":"农家小炒肉盖饭"}],[{"price":"15.00","total":"5","text":"你说你是","num":"1","dish":"北京烤鸭"},{"price":"15.00","total":"5","text":"你说你是","num":"1","dish":"北京烤鸭"}]]
     * delivery : 2.00
     */

    private AddressBean address;
    private String add_time;
    private String title;
    private double total;
    private int nums;
    private String id;
    private String diner_time;
    private String pack;
    private String dispatching;
    private String delivery;
    private String discount;
    private String res_name;

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    private List<List<ItmesBean>> itmes;

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiner_time() {
        return diner_time;
    }

    public void setDiner_time(String diner_time) {
        this.diner_time = diner_time;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getDispatching() {
        return dispatching;
    }

    public void setDispatching(String dispatching) {
        this.dispatching = dispatching;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public List<List<ItmesBean>> getItmes() {
        return itmes;
    }

    public void setItmes(List<List<ItmesBean>> itmes) {
        this.itmes = itmes;
    }

    public static class AddressBean {
        /**
         * phone : 13838383883
         * name : 龙哈鱼
         * dizhi : 鹿邑县第一看守所
         */

        private String phone;
        private String name;
        private String dizhi;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDizhi() {
            return dizhi;
        }

        public void setDizhi(String dizhi) {
            this.dizhi = dizhi;
        }
    }

    public static class ItmesBean {
        /**
         * price : 15.00
         * total : 1
         * text : 睡觉睡觉
         * num : 1
         * dish : 农家小炒肉盖饭
         */

        private String price;
        private String total;
        private String text;
        private String num;
        private String dish;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
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
