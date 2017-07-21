package com.xiaoyuan.campus_order.Tab.Bean;

/**
 * Created by longhengyu on 2017/7/21.
 */

public class UpdateAppBean {


    /**
     * res : true
     * mes : 您有新版本更新
     * data : {"version":"1.1","details":"惠生园","size":"10608.64","sign":"1adcc08a6ad5b349d20682b1d6386e17","url":"http://hsytest.hsydining-hall.com/app/app1.1.apk"}
     */

    private boolean res;
    private String mes;
    private DataBean data;

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * version : 1.1
         * details : 惠生园
         * size : 10608.64
         * sign : 1adcc08a6ad5b349d20682b1d6386e17
         * url : http://hsytest.hsydining-hall.com/app/app1.1.apk
         */

        private String version;
        private String details;
        private String size;
        private String sign;
        private String url;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
