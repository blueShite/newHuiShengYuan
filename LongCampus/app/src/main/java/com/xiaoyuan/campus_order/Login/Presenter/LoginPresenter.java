package com.xiaoyuan.campus_order.Login.Presenter;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.Login.Bean.LoginBean;
import com.xiaoyuan.campus_order.Login.Interface.LoginInterface;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.Tools.Common.utils.EncryptUtils;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/4/19.
 */

public class LoginPresenter extends BasePresenter {

    private LoginInterface mInterface;

    public LoginPresenter(LoginInterface anInterface){
        mInterface = anInterface;
    }

    public void requestLogin(String account,String password){

        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("userid",account);
        map.put("password", EncryptUtils.encryptMD5ToString(password));
        RequestTools.getInstance().postRequest("/api/loginPro.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                dismissDialog();
                super.onResponse(response, id);
                if(response.isRes()){

                    mInterface.successLogin();
                    LoginBean bean = JSON.parseArray(response.getData(),LoginBean.class).get(0);
                    LoginManage.getInstance().saveLoginBean(bean);

                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });
    }
}
