package com.xiaoyuan.campus_order.Circle.CircleDetail.Bean;

import java.util.List;

/**
 * Created by longhengyu on 2017/4/28.
 */

public class CircleDetailItemBean {


    /**
     * id : 293
     * group_id : 236
     * text : 贾老板，什么时候上线
     * p_id : 0
     * nickname :
     * reply_time : 2016-11-01 12:54:45
     * headimg :
     * reply : [{"id":"294","group_id":"0","text":"为什么","p_id":"293","nickname":"","reply_time":"2016-11-01 22:04:30"},{"id":"306","group_id":"0","text":"25585","p_id":"293","nickname":"风君子卢某","reply_time":"2017-02-20 11:13:47"}]
     */

    private String id;
    private String group_id;
    private String text;
    private String p_id;
    private String nickname;
    private String reply_time;
    private String headimg;
    private List<ReplyBean> reply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public List<ReplyBean> getReply() {
        return reply;
    }

    public void setReply(List<ReplyBean> reply) {
        this.reply = reply;
    }

    public static class ReplyBean {
        /**
         * id : 294
         * group_id : 0
         * text : 为什么
         * p_id : 293
         * nickname :
         * reply_time : 2016-11-01 22:04:30
         */

        private String id;
        private String group_id;
        private String text;
        private String p_id;
        private String nickname;
        private String reply_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getP_id() {
            return p_id;
        }

        public void setP_id(String p_id) {
            this.p_id = p_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getReply_time() {
            return reply_time;
        }

        public void setReply_time(String reply_time) {
            this.reply_time = reply_time;
        }
    }
}
