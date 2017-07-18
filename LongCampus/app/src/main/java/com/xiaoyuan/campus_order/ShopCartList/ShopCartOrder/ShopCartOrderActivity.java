package com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.alipay.sdk.app.PayTask;
import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.CustomView.Address_AlertDialog;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.PersonSubs.Address.AddAddressActivity;
import com.xiaoyuan.campus_order.PersonSubs.Address.Bean.AddressBean;
import com.xiaoyuan.campus_order.PersonSubs.Coupon.Bean.CouponBean;
import com.xiaoyuan.campus_order.PersonSubs.Coupon.CouponActivity;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderActivity;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.ShopCartList.Bean.ShopCartItemBean;
import com.xiaoyuan.campus_order.ShopCartList.Bean.ShopCartPriceBean;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Adapter.ShopCartOrderAdapter;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Bean.AuthResult;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Bean.PayResult;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Bean.ShopCartOrderFootBean;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Event.CouponShopCartOrderEvent;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Interface.ShopCartOrderInterface;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Presenter.ShopCartOrderPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ShopCartOrderActivity extends BaseActivity implements ShopCartOrderInterface {

    @BindView(R.id.shop_list_order_recycle)
    RecyclerView mShopListOrderRecycle;

    private List<ShopCartItemBean> mList = new ArrayList<>();
    private ShopCartOrderAdapter mAdapter;
    private ShopCartOrderPresenter mPresenter = new ShopCartOrderPresenter(this);
    private ShopCartOrderFootBean mFootBean;
    private ShopCartPriceBean mPriceBean;
    private List<AddressBean> mAddressList = new ArrayList<>();
    private AddressBean selectAddressBean;
    private String paramShopId;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toasty.success(ShopCartOrderActivity.this,"支付成功").show();
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Intent orderIntent = new Intent(ShopCartOrderActivity.this, OrderActivity.class);
                                orderIntent.putExtra("selectIndex","1");
                                startActivity(orderIntent);
                                finish();
                            }
                        }, 2000);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toasty.error(ShopCartOrderActivity.this,"支付失败").show();
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Intent orderIntent = new Intent(ShopCartOrderActivity.this, OrderActivity.class);
                                orderIntent.putExtra("selectIndex","0");
                                startActivity(orderIntent);
                                finish();
                            }
                        }, 2000);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toasty.success(ShopCartOrderActivity.this,"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode())).show();
                    } else {
                        // 其他状态值则为授权失败
                        Toasty.error(ShopCartOrderActivity.this,"授权失败" + String.format("authCode:%s", authResult.getAuthCode())).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart_order);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        customView();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onMessageEvent(CouponShopCartOrderEvent event) {
        CouponBean bean = event.mCouponBean;
        mFootBean.setCouponSub(bean.getTitle());
        mFootBean.setCouponId(bean.getCpid());
        mAdapter.reloadFootView(mFootBean);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestAddressList(LoginManage.getInstance().getLoginBean().getId());
    }

    private void customView(){

        mPresenter.setContext(ShopCartOrderActivity.this);
        List<ShopCartItemBean> list = (List<ShopCartItemBean>) getIntent().getSerializableExtra("shopList");
        mPriceBean = (ShopCartPriceBean) getIntent().getSerializableExtra("priceBean");
        paramShopId = getIntent().getStringExtra("shopId");
        mList.addAll(mPresenter.handleList(list));
        mFootBean = new ShopCartOrderFootBean();
        mFootBean.setCouponSub("请选择优惠券!");
        mFootBean.setGiveType(0);
        mFootBean.setTime("请选择用餐时间");
        mFootBean.setShopId(paramShopId);
        mFootBean.setTotalPrice(mPriceBean.getTotal()+"");
        mFootBean.setPackPrice(mPriceBean.getPack()+"");
        mFootBean.setPayPrice((mPriceBean.getPack()+mPriceBean.getTotal())+"");

        LinearLayoutManager layoutManager = new LinearLayoutManager(ShopCartOrderActivity.this);
        mShopListOrderRecycle.setLayoutManager(layoutManager);
        mAdapter = new ShopCartOrderAdapter(mList,ShopCartOrderActivity.this,this);
        mShopListOrderRecycle.setAdapter(mAdapter);
        mAdapter.reloadFootView(mFootBean);

    }

    private void hanbleParams(){
        String remark = "";
        ShopCartItemBean itemBean;
        for (int i=1;i<mList.size();i++){
            ShopCartItemBean bean = mList.get(i);
            if(i==1){
                if(bean.getRemark()==null||bean.getRemark().length()<1){
                    remark = "";
                }else {
                    remark = bean.getRemark();
                }
            }else if(i>1&&i!=mList.size()-1){
                if(bean.getItemType().equals("0")){
                    remark = remark+"&";
                }else {
                    itemBean = mList.get(i-1);
                    if(itemBean.getId()==null){
                        remark = remark+bean.getRemark();
                    }else{
                        if(bean.getId().equals(itemBean.getId())){
                            if(bean.getRemark()==null||bean.getRemark().length()<1){
                                remark = remark+",";
                            }else {
                                remark = remark+","+bean.getRemark();
                            }

                        }else {
                            if(bean.getRemark()==null||bean.getRemark().length()<1){
                                remark = remark+"%";
                            }else {
                                remark = remark+"%"+bean.getRemark();
                            }
                        }
                    }
                }
            }else {
                itemBean = mList.get(i-1);
                if(itemBean.getItemType().equals("0")){
                    if(bean.getRemark()!=null&&bean.getRemark().length()>0){
                        remark = remark+bean.getRemark();
                    }else {

                    }
                }else {
                    if(itemBean.getId().equals(bean.getId())){

                        if(bean.getRemark()!=null&&bean.getRemark().length()>0){
                            remark = remark+","+bean.getRemark();
                        }else {
                            remark = remark+",";
                        }
                    }else {
                        if(bean.getRemark()!=null&&bean.getRemark().length()>0){
                            remark = remark+"%"+bean.getRemark();
                        }else {
                            remark = remark+"%";
                        }
                    }
                }
            }
        }
        remark = remark.replace("null","");
        mFootBean.setRemark(remark);
        Log.e("蛋疼的拼接",remark);
    }

    @OnClick(R.id.button_shop_list_order_submit)
    public void onViewClicked() {

        hanbleParams();
        if(mFootBean.getTime()==null||mFootBean.getTime().equals("请选择用餐时间")){
            Toasty.error(ShopCartOrderActivity.this,"请选择用餐时间").show();
            return;
        }
        if(mFootBean.getGiveType()==0){
            Toasty.error(ShopCartOrderActivity.this,"请选择配送方式").show();
            return;
        }
        if(mFootBean.getGiveType()==1){
            if(mFootBean.getAddressId()==null||mFootBean.getAddressId().length()<1){
                Toasty.error(ShopCartOrderActivity.this,"请选择收货地址").show();
                return;
            }
        }
        mPresenter.requestSubmitOrder(mFootBean);
    }


    @Override
    public void requestAddressList(List<AddressBean> list) {
        mAddressList = list;
    }

    @Override
    public void requestSubmitSucess(final String payData) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ShopCartOrderActivity.this);
                Map<String, String> result = alipay.payV2(payData, true);
                Log.e("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void onClickCoupon() {
        Intent couponIntent = new Intent(ShopCartOrderActivity.this, CouponActivity.class);
        couponIntent.putExtra("isSelect","1");
        startActivity(couponIntent);
    }

    @Override
    public void onClickTime() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker picker, int i, int i1) {
                String dateString = i+":"+i1;
                mFootBean.setTime(dateString);
                mAdapter.reloadFootView(mFootBean);
            }
        }, 12, 0, true).show();
    }

    @Override
    public void onClickGiveType(int type) {
        if(type==1){
            if(mAddressList.size()<1){

                AlertDialog.Builder builder = new AlertDialog.Builder(ShopCartOrderActivity.this);
                builder.setTitle("提示");
                builder.setMessage("没有收获地址,是否新增?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface anInterface, int i) {
                        Intent intent = new Intent(ShopCartOrderActivity.this,AddAddressActivity.class);
                        intent.putExtra("isSeting","0");
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
                return;
            }
            new Address_AlertDialog(ShopCartOrderActivity.this,mAddressList,selectAddressBean)
                    .builder()
                    .setGivePrice(mPriceBean.getDelivery()+"")
                    .setOnClickSubmitBtn(new Address_AlertDialog.OnClickAddressAlertInterface() {
                        @Override
                        public void selectAddressItem(AddressBean bean) {
                            selectAddressBean = bean;
                            mFootBean.setAddressId(selectAddressBean.getId());
                            mFootBean.setPayPrice(mPriceBean.getPack()+mPriceBean.getTotal()+mPriceBean.getDelivery()+"");
                            mAdapter.reloadFootView(mFootBean);
                        }
                    })
                    .setCanCelBtn(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setSetingBtn(new Address_AlertDialog.OnClickAddressAlertInterface() {
                        @Override
                        public void selectAddressItem(AddressBean bean) {
                            Intent intent = new Intent(ShopCartOrderActivity.this,AddAddressActivity.class);
                            intent.putExtra("isSeting","1");
                            intent.putExtra("AddressBean",bean);
                            startActivity(intent);
                        }
                    }).show();
            mFootBean.setGiveType(type);
            mAdapter.reloadFootView(mFootBean);
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ShopCartOrderActivity.this);
            builder.setTitle("提示");
            builder.setMessage("现在只支持校内送餐的方式!");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface anInterface, int i) {
                }
            });
            builder.show();
            return;
        }
    }

    @Override
    public void itemEditText(int poist, String editText) {

            ShopCartItemBean bean = mList.get(poist);
            bean.setRemark(editText);
            Log.e("tag2","-------"+poist+"-------"+editText);
    }
}
