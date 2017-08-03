package com.xiaoyuan.campus_order.NetWorks;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Map;

/**
 * Created by longhengyu on 2017/3/30.
 */

public class RequestTools {

    //static修饰的静态变量在内存中一旦创建，便永久存在
    private static RequestTools instance = new RequestTools();
    private RequestTools (){}
    public static RequestTools getInstance() {
        return instance;
    }
//    private static final String BaseUrl = "http://116.255.228.199:8010";
    public static final String BaseUrl = "http://www.hsydining-hall.com";
//    public static final String BaseUrl = "http://hsytest.hsydining-hall.com";
    public  void getRequest(String path, boolean isHeader, Map<String,String> map, String tag, RequestCallBack callback){

        String requestUrl = BaseUrl+path;

        if(isHeader){
            OkHttpUtils
                    .get()
                    .url(requestUrl)
                    .params(map)
                    //.addHeader("cookie","webpos="+ LoginManage.getInstance().returnToken())
                    .tag(tag)
                    .build()
                    .execute(callback);

        }else {
            OkHttpUtils
                    .get()
                    .url(requestUrl)
                    .params(map)
                    .tag(tag)
                    .build()
                    .execute(callback);
        }
    }

    public void postRequest(String path, boolean isHeader, Map<String,String> map,String tag,RequestCallBack callback){

        String requestUrl = BaseUrl+path;

        if(isHeader){
            OkHttpUtils
                    .post()
                    .url(requestUrl)
                    .params(map)
                    //.addHeader("cookie","webpos="+ LoginManage.getInstance().returnToken())
                    .tag(tag)
                    .build()
                    .execute(callback);

        }else {
            OkHttpUtils
                    .post()
                    .url(requestUrl)
                    .params(map)
                    .tag(tag)
                    .build()
                    .execute(callback);
        }
    }
}
