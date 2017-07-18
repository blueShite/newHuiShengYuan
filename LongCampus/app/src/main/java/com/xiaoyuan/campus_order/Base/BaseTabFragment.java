package com.xiaoyuan.campus_order.Base;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

/**
 * Created by longhengyu on 2017/6/21.
 */

public class BaseTabFragment extends Fragment {

    private ProgressDialog mDialog;

    public void showDialog(){
        if(mDialog==null){
            mDialog = new ProgressDialog(getContext());
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
