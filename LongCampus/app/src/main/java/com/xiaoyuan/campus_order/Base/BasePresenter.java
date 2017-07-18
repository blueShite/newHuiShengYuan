package com.xiaoyuan.campus_order.Base;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by longhengyu on 2017/4/19.
 */

public class BasePresenter {

    public Context mContext;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    private ProgressDialog mDialog;


    public void showDialog(){
        if(mDialog==null){
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("正在加载...");
        }
        dismissDialog();
        mDialog.show();
    }

    public void dismissDialog(){
        if(mDialog==null){
            return;
        }
        mDialog.dismiss();
    }
}
