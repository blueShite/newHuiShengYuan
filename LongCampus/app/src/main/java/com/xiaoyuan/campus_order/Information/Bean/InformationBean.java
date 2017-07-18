package com.xiaoyuan.campus_order.Information.Bean;

import java.io.Serializable;

/**
 * Created by longhengyu on 2017/4/21.
 */

public class InformationBean implements Serializable {

    /**
     * id : 3
     * htinfo : 养生哲学
     * litpic : /images/news_img/20160817/20160817153940.jpg
     * title : 夏日养生
     * form : 原创
     * author : 惠生园
     * body : 夏季养生，夏季是阳气最盛的季节，气候炎热而生机旺盛。此时是新陈代谢的时期，阳气外发，伏阴在内，气血运行亦相应地旺盛起来，活跃于机体表面。夏天的特点是燥热，“热”以“凉”克之，“燥”以“清”驱之。因此，清燥解热是夏季养生的关键。盛夏酷暑蒸灼，人易感到困倦烦躁和闷热不安，因此首先要使自己的思想平静下来，神清气静，做到神清气和，切忌暴怒，以防心火内生。注意养心，夏季是心脏病的高发期，中医认为“心与夏气相通应”心的阳气在夏季最为旺盛，所以夏季更要注意心脏的养生保健。夏季养生重在精神调摄，保持愉快而稳定的情绪，切忌大悲大喜，以免以热助热，火上加油。心静人自凉，可达到养生的目的。
     * date : 2016-06-26
     */

    private String id;
    private String htinfo;
    private String litpic;
    private String title;
    private String form;
    private String author;
    private String body;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHtinfo() {
        return htinfo;
    }

    public void setHtinfo(String htinfo) {
        this.htinfo = htinfo;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
