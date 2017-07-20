package com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Bean.OrderBean;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Interface.OrderReceiveInterface;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longhengyu on 2017/7/8.
 */

public class OrderReceiveAdapter extends RecyclerView.Adapter<OrderReceiveAdapter.ViewHolder> {


    private List<OrderBean> mList;
    private OrderReceiveInterface mInterface;
    private Context mContext;

    public OrderReceiveAdapter(List<OrderBean> list, Context context, OrderReceiveInterface anInterface) {
        mList = list;
        mInterface = anInterface;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_receive, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        OrderBean bean = mList.get(position);

        holder.mTextOrderReceiveNum.setText("数量:"+bean.getNums()+"");
        holder.mTextOrderReceiveOrderNum.setText(bean.getId());
        holder.mTextOrderReceivePrice.setText("总价:"+bean.getTotals()+"");
        holder.mTextOrderReceiveTime.setText(bean.getAdd_time());
        holder.mTextOrderReceiveWindow.setText(bean.getRes_name());
        Picasso.with(mContext).load(RequestTools.BaseUrl+bean.getRes_img()).resize(150, 150).into(holder.mImageOrderReceive);
        if(bean.getOrder_reply()==null||bean.getOrder_reply().length()<1){
            holder.mLauoytOrderReceivePing.setVisibility(View.VISIBLE);
            holder.mTextOrderReceivePingsub.setVisibility(View.INVISIBLE);
        }else {
            holder.mLauoytOrderReceivePing.setVisibility(View.GONE);
            holder.mTextOrderReceivePingsub.setVisibility(View.VISIBLE);
        }
        if (holder.mEditOrderReceivePing.getTag() instanceof TextWatcher) {
            holder.mEditOrderReceivePing.removeTextChangedListener((TextWatcher) holder.mEditOrderReceivePing.getTag());
        }
        holder.mEditOrderReceivePing.setText(bean.getRemark());
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("tag1","-------"+position+"-------"+holder.mEditOrderReceivePing.getText().toString());
                mInterface.itemEditText(position, holder.mEditOrderReceivePing.getText().toString());
            }
        };
        holder.mEditOrderReceivePing.addTextChangedListener(watcher);


        holder.mEditOrderReceivePing.setTag(watcher);

        holder.mButtonOrderReceivePing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickPinglunBtn(position);
            }
        });
        holder.selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickOrderItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_order_receive_orderNum)
        TextView mTextOrderReceiveOrderNum;
        @BindView(R.id.Image_order_receive)
        ImageView mImageOrderReceive;
        @BindView(R.id.text_order_receive_time)
        TextView mTextOrderReceiveTime;
        @BindView(R.id.text_order_receive_window)
        TextView mTextOrderReceiveWindow;
        @BindView(R.id.text_order_receive_price)
        TextView mTextOrderReceivePrice;
        @BindView(R.id.text_order_receive_num)
        TextView mTextOrderReceiveNum;
        @BindView(R.id.text_order_receive_pingsub)
        TextView mTextOrderReceivePingsub;
        @BindView(R.id.button_order_receive_ping)
        Button mButtonOrderReceivePing;
        @BindView(R.id.edit_order_receive_ping)
        EditText mEditOrderReceivePing;
        @BindView(R.id.lauoyt_order_receive_ping)
        ConstraintLayout mLauoytOrderReceivePing;
        View selfView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            selfView = itemView;
        }
    }
}
