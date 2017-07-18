package com.xiaoyuan.campus_order.PersonSubs.Address;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.PersonSubs.Address.Adapter.AddressListAdapter;
import com.xiaoyuan.campus_order.PersonSubs.Address.Bean.AddressBean;
import com.xiaoyuan.campus_order.PersonSubs.Address.Interface.AddressListInterface;
import com.xiaoyuan.campus_order.PersonSubs.Address.Presenter.AddressListPresenter;
import com.xiaoyuan.campus_order.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressListActivity extends BaseActivity implements AddressListInterface{

    @BindView(R.id.address_list_recycle)
    RecyclerView mAddressListRecycle;

    private AddressListPresenter mPresenter = new AddressListPresenter(this);
    private List<AddressBean> mList = new ArrayList<>();
    private AddressListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);
        customView();
    }

    private void customView(){

        mPresenter.setContext(AddressListActivity.this);

        LinearLayoutManager manager = new LinearLayoutManager(AddressListActivity.this);
        mAddressListRecycle.setLayoutManager(manager);
        mAdapter = new AddressListAdapter(mList,AddressListActivity.this,this);
        mAddressListRecycle.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestList(LoginManage.getInstance().getLoginBean().getId());
    }

    @OnClick(R.id.button_add_address_submit)
    public void onViewClicked() {
        Intent intent = new Intent(AddressListActivity.this,AddAddressActivity.class);
        intent.putExtra("isSeting","0");
        startActivity(intent);
    }

    @Override
    public void requestSucess(List<AddressBean> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestError(String error) {

    }

    @Override
    public void requestDeleteSucess(int poist) {
        mList.remove(poist);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestDefaultSucess(int poist) {
        for (int i=0;i<mList.size();i++){
            if(i==poist){
                mList.get(i).setAcc_state("1");
            }else {
                mList.get(i).setAcc_state("0");
            }
        }
        mAdapter.notifyDataSetChanged();
        finish();
    }

    @Override
    public void onClickSelectBtn(int poist) {

        AddressBean bean = mList.get(poist);
        mPresenter.requestSetDefaultAddress(LoginManage.getInstance().getLoginBean().getId(),
                bean.getId(),bean.getAcc_address(),poist);

    }

    @Override
    public void onClickSeting(int poist) {
        Intent intent = new Intent(AddressListActivity.this,AddAddressActivity.class);
        intent.putExtra("isSeting","1");
        intent.putExtra("AddressBean",mList.get(poist));
        startActivity(intent);
    }

    @Override
    public void onClickDelete(final int poist) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddressListActivity.this);
        builder.setTitle("提示");
        builder.setMessage("确定删除此地址吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface anInterface, int i) {
                mPresenter.requestDelect(mList.get(poist).getId(),poist);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }
}
