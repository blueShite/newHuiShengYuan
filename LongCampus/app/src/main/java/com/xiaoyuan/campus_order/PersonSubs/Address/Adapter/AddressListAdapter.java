package com.xiaoyuan.campus_order.PersonSubs.Address.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xiaoyuan.campus_order.PersonSubs.Address.Bean.AddressBean;
import com.xiaoyuan.campus_order.PersonSubs.Address.Interface.AddressListInterface;
import com.xiaoyuan.campus_order.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longhengyu on 2017/6/28.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {


    private List<AddressBean> mList;
    private Context mContext;
    private AddressListInterface mInterface;

    public AddressListAdapter(List<AddressBean> list, Context context, AddressListInterface anInterface) {
        mList = list;
        mContext = context;
        mInterface = anInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        AddressBean bean = mList.get(position);

        holder.mTextAddressListItemName.setText(bean.getAcc_name());
        holder.mTextAddressListItemAddress.setText(bean.getAcc_address());
        holder.mTextAddressListPhone.setText(bean.getAcc_phone());
        if(bean.getAcc_state().equals("0")){
            holder.mButtonAddressListItemSelect.setSelected(false);
        }else {
            holder.mButtonAddressListItemSelect.setSelected(true);
        }
        holder.mTextAddressListSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickSelectBtn(position);
            }
        });
        holder.mTextAddressListItemSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickSeting(position);
            }
        });

        holder.mTextAddressListItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickDelete(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_address_list_item_set)
        TextView mTextAddressListItemSet;
        @BindView(R.id.button_address_list_item_select)
        Button mButtonAddressListItemSelect;
        @BindView(R.id.text_address_list_item_name)
        TextView mTextAddressListItemName;
        @BindView(R.id.text_address_list_phone)
        TextView mTextAddressListPhone;
        @BindView(R.id.text_address_list_item_address)
        TextView mTextAddressListItemAddress;
        @BindView(R.id.text_address_list_item_delete)
        TextView mTextAddressListItemDelete;
        @BindView(R.id.text_address_list_select)
        TextView mTextAddressListSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
