package com.xiaoyuan.campus_order.Circle.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by longhengyu on 2017/4/22.
 */

public class CircleItemBean implements Serializable {


    /**
     * group_id : 236
     * group_litpic : ["/images/menu_img/20161024/20161024150539.jpg"]
     * group_text :
     * group_time : 2016-10-27 03:32:15
     * group_title : 土豆鸡腿
     * top : 1
     * headimg : /images/admin_head.png
     * nickname : 系统
     * reply_num : 10
     * num : 7
     */

    private String group_id;
    private String group_text;
    private String group_time;
    private String group_title;
    private String top;
    private String headimg;
    private String nickname;
    private String reply_num;
    private String num;
    private List<String> group_litpic;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_text() {
        return group_text;
    }

    public void setGroup_text(String group_text) {
        this.group_text = group_text;
    }

    public String getGroup_time() {
        return group_time;
    }

    public void setGroup_time(String group_time) {
        this.group_time = group_time;
    }

    public String getGroup_title() {
        return group_title;
    }

    public void setGroup_title(String group_title) {
        this.group_title = group_title;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReply_num() {
        return reply_num;
    }

    public void setReply_num(String reply_num) {
        this.reply_num = reply_num;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<String> getGroup_litpic() {
        return group_litpic;
    }

    public void setGroup_litpic(List<String> group_litpic) {
        this.group_litpic = group_litpic;
    }
}
