package com.xiaoyuan.campus_order.PersonSubs.Address;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.PersonSubs.Address.Bean.AddressBean;
import com.xiaoyuan.campus_order.PersonSubs.Address.Interface.AddAddressInterface;
import com.xiaoyuan.campus_order.PersonSubs.Address.Presenter.AddAddressPresenter;
import com.xiaoyuan.campus_order.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class AddAddressActivity extends AppCompatActivity implements AddAddressInterface {

    @BindView(R.id.edit_add_address_address)
    EditText mEditAddAddressAddress;
    @BindView(R.id.edit_add_address_phone)
    EditText mEditAddAddressPhone;
    @BindView(R.id.edit_add_address_name)
    EditText mEditAddAddressName;

    private AddAddressPresenter mPresenter = new AddAddressPresenter(this);
    private AddressBean mAddressBean;
    private String isSeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        mPresenter.setContext(AddAddressActivity.this);
        isSeting = getIntent().getStringExtra("isSeting");
        if(isSeting.equals("1")){
            mAddressBean = (AddressBean) getIntent().getSerializableExtra("AddressBean");
            mEditAddAddressName.setText(mAddressBean.getAcc_name());
            mEditAddAddressPhone.setText(mAddressBean.getAcc_phone());
            mEditAddAddressAddress.setText(mAddressBean.getAcc_address());
        }

    }

    @OnClick(R.id.button_add_address_submit)
    public void onViewClicked() {

        if(mEditAddAddressName.getText().length()<2){
            Toasty.error(AddAddressActivity.this,"输入名字要大于两个字!").show();
            return;
        }
        if(mEditAddAddressPhone.getText().length()!=11){
            Toasty.error(AddAddressActivity.this,"请输入正确的手机号码").show();
            return;
        }
        if(mEditAddAddressAddress.getText().length()<1){
            Toasty.error(AddAddressActivity.this,"请输入地址").show();
            return;
        }
        if(isSeting.equals("0")){
            mPresenter.requestSubmitSucess(
                    LoginManage.getInstance().getLoginBean().getId(),
                    mEditAddAddressName.getText().toString(),
                    mEditAddAddressPhone.getText().toString(),
                    mEditAddAddressAddress.getText().toString());
        }else {
            mPresenter.requestSetAddress(
                    mAddressBean.getId(),
                    mEditAddAddressName.getText().toString(),
                    mEditAddAddressPhone.getText().toString(),
                    mEditAddAddressAddress.getText().toString());
        }

    }

    @Override
    public void requestSubmitSucess() {
        Toasty.success(AddAddressActivity.this,"提交成功").show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }
}
