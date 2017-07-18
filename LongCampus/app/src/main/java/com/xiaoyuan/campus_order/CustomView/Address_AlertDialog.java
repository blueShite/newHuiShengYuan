package com.xiaoyuan.campus_order.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoyuan.campus_order.PersonSubs.Address.Bean.AddressBean;
import com.xiaoyuan.campus_order.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longhengyu on 2017/7/6.
 */

public class Address_AlertDialog {

    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private Button cancelBtn;
    private Button setBtn;
    private Button submitBtn;
    private RecyclerView mRecyclerView;
    private List<AddressBean> mAddressList;
    private Display display;
    private AddressAlertAdapter mAdapter;
    private AddressBean  selectBean;
    private TextView givePriceText;

    public Address_AlertDialog(Context context, List<AddressBean> list,AddressBean addressBean) {
        if(addressBean==null){
            for (AddressBean bean:list){
                if(bean.getAcc_state().equals("1")){
                    selectBean = bean;
                }
            }
            if(selectBean==null){
                list.get(0).setAcc_state("1");
                selectBean = list.get(0);
            }
        }else {
            selectBean = addressBean;
            for (AddressBean bean:list){
                if(bean.getId().equals(selectBean.getId())){
                    bean.setAcc_state("1");
                }else {
                    bean.setAcc_state("0");
                }
            }
        }
        this.context = context;
        mAddressList = list;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public Address_AlertDialog builder() {

        View view = LayoutInflater.from(context).inflate(R.layout.view_address_alert, null);
        lLayout_bg = (LinearLayout) view.findViewById(R.id.layout_address_alert);
        cancelBtn = (Button) view.findViewById(R.id.button_address_alert_cancel);
        setBtn = (Button) view.findViewById(R.id.button_address_alert_set);
        submitBtn = (Button) view.findViewById(R.id.button_address_alert_submit);
        givePriceText = (TextView) view.findViewById(R.id.text_address_alert_packPrice);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.address_alert_recycle);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AddressAlertAdapter(mAddressList, context, new AddressAlertInterface() {
            @Override
            public void onClickAddressItem(int poist) {
                for (int i=0;i<mAddressList.size();i++){
                    AddressBean bean = mAddressList.get(i);
                    if(i==poist){
                        bean.setAcc_state("1");
                    }else {
                        bean.setAcc_state("0");
                    }
                }
                mAdapter.notifyDataSetChanged();
                selectBean = mAddressList.get(poist);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.Address_DialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.87), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public Address_AlertDialog setGivePrice(String price){
        givePriceText.setText("配送费"+price+"元");
        return this;
    }

    public Address_AlertDialog setOnClickSubmitBtn(final OnClickAddressAlertInterface anInterface){

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                anInterface.selectAddressItem(selectBean);
                dialog.dismiss();
            }
        });
        return this;
    }

    public Address_AlertDialog setCanCelBtn(final View.OnClickListener listener){

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                dialog.dismiss();
            }
        });
        return this;
    }

    public Address_AlertDialog setSetingBtn(final OnClickAddressAlertInterface anInterface){

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                anInterface.selectAddressItem(selectBean);
                dialog.dismiss();
            }
        });

        return this;
    }


    public void show() {
        dialog.show();
    }

    public void dismiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public class AddressAlertAdapter extends RecyclerView.Adapter<AddressAlertAdapter.ViewHolder> {


        private List<AddressBean> mList;
        private Context mContext;
        private AddressAlertInterface mInterface;

        public AddressAlertAdapter(List<AddressBean> list, Context context, AddressAlertInterface anInterface) {
            mList = list;
            mContext = context;
            mInterface = anInterface;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_alert, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            AddressBean bean = mList.get(position);
            holder.mTextAddressAlertName.setText(bean.getAcc_name());
            holder.mTextAddressAlertPhone.setText(bean.getAcc_phone());
            holder.mTextAddressAlertAddress.setText(bean.getAcc_address());
            if(bean.getAcc_state().equals("1")){
                holder.mButtonAddressAlertSelect.setSelected(true);
            }else {
                holder.mButtonAddressAlertSelect.setSelected(false);
            }
            holder.selfView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInterface.onClickAddressItem(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.text_address_alert_name)
            TextView mTextAddressAlertName;
            @BindView(R.id.button_address_alert_select)
            Button mButtonAddressAlertSelect;
            @BindView(R.id.text_address_alert_phone)
            TextView mTextAddressAlertPhone;
            @BindView(R.id.text_address_alert_address)
            TextView mTextAddressAlertAddress;

            View selfView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                selfView = itemView;
            }
        }
    }

    public interface AddressAlertInterface {

        void onClickAddressItem(int poist);
    }

    public interface OnClickAddressAlertInterface{
        void selectAddressItem(AddressBean bean);
    }

}
