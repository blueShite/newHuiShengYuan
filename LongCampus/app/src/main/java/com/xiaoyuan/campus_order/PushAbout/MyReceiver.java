package com.xiaoyuan.campus_order.PushAbout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.FootDetail.FootDetailActivity;
import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.FeatureBean;
import com.xiaoyuan.campus_order.Information.Bean.InformationBean;
import com.xiaoyuan.campus_order.Information.InformationDetail.InformationDetailActivity;
import com.xiaoyuan.campus_order.PersonSubs.Collection.CollectionActivity;
import com.xiaoyuan.campus_order.PersonSubs.Coupon.CouponActivity;
import com.xiaoyuan.campus_order.PersonSubs.Coupon.CouponReceiveActivity;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderDetail.OrderDetailActivity;
import com.xiaoyuan.campus_order.Tab.TabActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import es.dmoral.toasty.Toasty;

/**
 * Created by longhengyu on 2017/7/19.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        final String TAG = "极光推送数据";
        try {
            Bundle bundle = intent.getExtras();
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                //发送消息给主界面
                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户点击打开了通知");
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                if(extras!=null&&extras.length()>0){
                    Log.e("收到的json", extras);
                    PushBean bean = JSON.parseObject(extras,PushBean.class);
                    //1:商品详情 2:资讯界面 3:优惠券 4:订单
                    switch (bean.getType()){
                        case 1:
                            Intent detailIntent = new Intent(context, FootDetailActivity.class);
                            FeatureBean featureBean = new FeatureBean();
                            featureBean.setMenu_id(bean.getPushid());
                            detailIntent.putExtra("featureBean",featureBean);
                            detailIntent.putExtra("isCollection","1");
                            detailIntent.putExtra("isMyMenu","0");
                            detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                            context.startActivity(detailIntent);
                            break;
                        case 2:
                            Intent inforIntent = new Intent(context, InformationDetailActivity.class);
                            InformationBean informationBean = new InformationBean();
                            informationBean.setId(bean.getPushid());
                            inforIntent.putExtra("informationBean",informationBean);
                            inforIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                            context.startActivity(inforIntent);
                            break;
                        case 3:
                            Intent coupIntent = new Intent(context,CouponReceiveActivity.class);
                            coupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                            context.startActivity(coupIntent);
                            break;
                        case 4:
                            Intent orderIntent = new Intent(context, OrderDetailActivity.class);
                            orderIntent.putExtra("orderId",bean.getPushid());
                            orderIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                            context.startActivity(orderIntent);
                            break;
                        default:
                            break;
                    }
                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.e(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
            } else {
                Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }

        }catch (Exception e){

        }
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        if (TabActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(TabActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(TabActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(TabActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }
            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }
}
