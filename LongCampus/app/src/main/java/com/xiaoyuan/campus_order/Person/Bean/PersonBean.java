package com.xiaoyuan.campus_order.Person.Bean;

/**
 * Created by longhengyu on 2017/4/24.
 */

public class PersonBean {

    String title;
    int    imageId;

    public PersonBean(String name,int image){
        this.title = name;
        this.imageId = image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
