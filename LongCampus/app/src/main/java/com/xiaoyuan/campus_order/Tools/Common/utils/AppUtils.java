package com.xiaoyuan.campus_order.Tools.Common.utils;

/**
 * Created by a on 2014/5/18.
 *
 * 和App相关的辅助类
 *
 * getAppName(Context context) 获取应用程序名称
 * getVersionName(Context context) 获取应用程序版本名称信息
 *
 *@author zhengjiao
 *
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.xiaoyuan.campus_order.Tools.MyApplication;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;


public class AppUtils {

    private AppUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }
    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo( context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return -1;
    }

    //获取当前时间格式如下
    public static String getNowTime(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String date=sdf.format(new java.util.Date());
        return date;
    }

    public interface AlertInterface{
        void onClickItem(int i);
    }

    public static void showSelectAlertDialog(String title,String[] array, final Context context, final AlertInterface alertInterface){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertInterface.onClickItem(which);
                        dialog.dismiss();
                    }

                })
                .show();
    }

    //浮点型转成字符串并保留小数点后两位
    public static String doubleZhuanMa(double posit){
        BigDecimal bg = new BigDecimal(posit);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return ""+f1;
    }
    //字符串保留两位小数
    public static String doubleZhuanMa(String posit){

        BigDecimal bg = new BigDecimal(Double.parseDouble(posit));
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return ""+f1;
    }

    public static int getWidth(){

        WindowManager wm = (WindowManager) MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

}
