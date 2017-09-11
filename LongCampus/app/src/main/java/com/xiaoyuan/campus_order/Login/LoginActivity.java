package com.xiaoyuan.campus_order.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.Login.Interface.LoginInterface;
import com.xiaoyuan.campus_order.Login.Presenter.LoginPresenter;
import com.xiaoyuan.campus_order.Login.Register.RegisterActivity;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.Tab.TabActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends BaseActivity implements LoginInterface {

    @BindView(R.id.edit_login_account)
    EditText mEditLoginAccount;
    @BindView(R.id.edit_login_password)
    EditText mEditLoginPassword;
    @BindView(R.id.button_login_login)
    Button mButtonLoginLogin;
    @BindView(R.id.text_login_register)
    TextView mTextLoginRegister;
    @BindView(R.id.button_login_reSet)
    Button mButtonLoginReSet;

    private LoginPresenter mLoginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter.setContext(this);
        mTextLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.text_login_register, R.id.button_login_login,R.id.button_login_reSet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_login_register:
                break;
            case R.id.button_login_login:
                if (mEditLoginAccount.getText().length() < 1) {
                    Toasty.error(LoginActivity.this, "请输入账号!").show();
                    return;
                }
                if (mEditLoginPassword.getText().length() < 1) {
                    Toasty.error(LoginActivity.this, "请输入密码!").show();
                    return;
                }
                mLoginPresenter.requestLogin(mEditLoginAccount.getText().toString(), mEditLoginPassword.getText().toString());
                break;
            case R.id.button_login_reSet:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("RegisterType","2");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void successLogin() {

        Toasty.success(LoginActivity.this, "登录成功").show();
        Intent intent = new Intent(LoginActivity.this, TabActivity.class);
        startActivity(intent);
        finish();

    }
}
