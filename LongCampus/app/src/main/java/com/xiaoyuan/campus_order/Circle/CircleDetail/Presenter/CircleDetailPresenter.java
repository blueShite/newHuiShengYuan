package com.xiaoyuan.campus_order.Circle.CircleDetail.Presenter;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.Circle.CircleDetail.Bean.CircleDetailHeaderBean;
import com.xiaoyuan.campus_order.Circle.CircleDetail.Bean.CircleDetailItemBean;
import com.xiaoyuan.campus_order.Circle.CircleDetail.Interface.CircleDetailInterface;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/4/28.
 */

public class CircleDetailPresenter extends BasePresenter {

    private CircleDetailInterface mInterface;
    private List<CircleDetailItemBean> mList = new ArrayList<>();

    public CircleDetailPresenter(CircleDetailInterface circleDetailInterface){
        mInterface = circleDetailInterface;
    }

    public void requestData(final String groupId, String page){

        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("group_id",groupId);
        map.put("com_page",page);
        RequestTools.getInstance().postRequest("/api/groupCommentList.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
                mInterface.requestError("请求失败");
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){
                    mList = JSON.parseArray(response.getData(),CircleDetailItemBean.class);
                    mInterface.requestSucess(mList);
                }else {
                    mInterface.requestError(response.getMes());
                }
            }
        });

    }

    public void requestHeaderData(String groupId){

        final Map<String,String> map = new HashMap<>();
        map.put("gid",groupId);
        RequestTools.getInstance().postRequest("/api/getDieCircleCon.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {

                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){

                    CircleDetailHeaderBean bean = JSON.parseArray(response.getData(),CircleDetailHeaderBean.class).get(0);
                    mInterface.requestHeaderData(bean);
                }else {
                    Toasty.error(mContext,"获取信息失败").show();
                }
            }
        });
    }

    public void requestComment(String groupId , final String comment, String uId){

        Map<String,String> map = new HashMap<>();
        map.put("group_id",groupId);
        map.put("text",comment);
        map.put("u_id",uId);
        RequestTools.getInstance().postRequest("/api/group_add.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    Toasty.success(mContext,"评论成功!").show();
                    mInterface.requestCommentSucess(comment);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }

}
