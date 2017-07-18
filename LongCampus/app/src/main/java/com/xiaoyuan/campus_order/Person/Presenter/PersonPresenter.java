package com.xiaoyuan.campus_order.Person.Presenter;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BasePresenter;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.Person.Bean.PersonBalanceBean;
import com.xiaoyuan.campus_order.Person.Bean.PersonBean;
import com.xiaoyuan.campus_order.Person.Interface.PersonInterface;
import com.xiaoyuan.campus_order.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * Created by longhengyu on 2017/4/24.
 */

public class PersonPresenter extends BasePresenter {

    private PersonInterface mInterface;

    public PersonPresenter(PersonInterface anInterface){
        mInterface = anInterface;
    }

    public List<PersonBean> returnPersonItemData(){

        List<PersonBean> list = new ArrayList<>();
        list.add(new PersonBean("",0));
        list.add(new PersonBean("个人资料", R.drawable.gerenziliao));
        list.add(new PersonBean("优惠券", R.drawable.youhuiquan));
        list.add(new PersonBean("饮食偏好", R.drawable.gerenxihao));
        list.add(new PersonBean("意见反馈", R.drawable.yijianfankui));
        list.add(new PersonBean("关于我们", R.drawable.guanyuwomen));
        list.add(new PersonBean("清除缓存", R.drawable.qingchuhuancun));
        list.add(new PersonBean("",0));

        return list;
    }

    public void requestBalance(String uId){
        Map<String,String> map = new HashMap<>();
        map.put("uid",uId);
        RequestTools.getInstance().postRequest("/api/getAllPref.api.php", false, map, "", new RequestCallBack(mContext) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    PersonBalanceBean balanceBean = JSON.parseArray(response.getData(),PersonBalanceBean.class).get(0);
                    mInterface.requestPersonData(balanceBean);
                }else {
                    Toasty.error(mContext,response.getMes()).show();
                }
            }
        });

    }


}
