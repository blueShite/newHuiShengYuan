package com.xiaoyuan.campus_order.Login.Register;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.Tools.Common.utils.EncryptUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.edit_register_phone)
    EditText mEditRegisterPhone;
    @BindView(R.id.edit_register_passwordTwo)
    EditText mEditRegisterPasswordTwo;
    @BindView(R.id.edit_register_password)
    EditText mEditRegisterPassword;
    @BindView(R.id.edit_register_code)
    EditText mEditRegisterCode;
    @BindView(R.id.button_register_getCode)
    Button mButtonRegisterGetCode;

    Timer mTimer = new Timer();
    int codeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button_register_getCode, R.id.button_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_register_getCode:
                if(mEditRegisterPhone.getText().length()!=11){
                    Toasty.error(RegisterActivity.this,"请输入正确的手机号码!").show();
                    return;
                }
                if(!mButtonRegisterGetCode.getText().equals("获取验证码")){
                    return;
                }
                requestGetCode(mEditRegisterPhone.getText().toString());
                break;
            case R.id.button_register:
                if(mEditRegisterPhone.getText().length()!=11){
                    Toasty.error(RegisterActivity.this,"请输入正确的手机号码!").show();
                    return;
                }
                if(mEditRegisterCode.getText().length()<1){
                    Toasty.error(RegisterActivity.this,"请输入验证码!").show();
                    return;
                }
                if(!mEditRegisterPassword.getText().toString().equals(mEditRegisterPasswordTwo.getText().toString())){
                    Toasty.error(RegisterActivity.this,"两次输入密码不一致!").show();
                    return;
                }
                requestRegister(mEditRegisterPhone.getText().toString(),
                                mEditRegisterPassword.getText().toString(),
                                mEditRegisterCode.getText().toString());
                break;
        }
    }

    private void stopTimer(){
        if(mTimer != null){
            mTimer.cancel();
            // 一定设置为null，否则定时器不会被回收
            mTimer = null;
        }
    }

    private void requestGetCode(String phoneStr){
        Map<String,String> map = new HashMap<>();
        map.put("phnum",phoneStr);
        RequestTools.getInstance().postRequest("/api/getMessageCodePro.api.php", false, map, "", new RequestCallBack(RegisterActivity.this) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    codeTime = 30;
                    mTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(codeTime>1){
                                        codeTime--;
                                        mButtonRegisterGetCode.setText(codeTime+"秒");
                                    }else {
                                        stopTimer();
                                        mButtonRegisterGetCode.setText("获取验证码");
                                    }
                                }
                            });
                        }
                    },1000,1000);
                }else {
                    Toasty.error(RegisterActivity.this,response.getMes()).show();
                }
            }
        });
    }

    private void requestRegister(String phoneStr,String password,String codeStr){
        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("phnum",phoneStr);
        map.put("password", EncryptUtils.encryptMD5ToString(password));
        map.put("smscode",codeStr);
        RequestTools.getInstance().postRequest("/api/registerPro.api.php", false, map, "", new RequestCallBack(RegisterActivity.this) {
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
                    Toasty.success(RegisterActivity.this,"注册成功").show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);
                }else {
                    Toasty.error(RegisterActivity.this,response.getMes()).show();
                }
            }
        });

    }
}
