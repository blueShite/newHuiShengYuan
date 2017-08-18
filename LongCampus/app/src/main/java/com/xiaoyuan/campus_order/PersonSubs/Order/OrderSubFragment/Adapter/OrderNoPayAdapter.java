package com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Bean.OrderBean;
import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Interface.OrderOnPayListInterface;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longhengyu on 2017/7/8.
 */

public class OrderNoPayAdapter extends RecyclerView.Adapter<OrderNoPayAdapter.ViewHolder> {

    private List<OrderBean> mList;
    private OrderOnPayListInterface mInterface;
    private Context mContext;
    private int adapterType;

    public OrderNoPayAdapter(List<OrderBean> list,OrderOnPayListInterface anInterface,Context context,int type){
        mList = list;
        mInterface = anInterface;
        mContext = context;
        adapterType = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_nopay, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        OrderBean bean = mList.get(position);

        holder.mTextOrderNopayNum.setText("订单号"+bean.getId());
        holder.mTextOrderNopayTime.setText(bean.getAdd_time());
        Picasso.with(mContext).load(RequestTools.BaseUrl+bean.getRes_img()).resize(150, 150).into(holder.mCircleImageView);
        holder.mTextOrderNopayWindow.setText(bean.getRes_name());
        holder.mTextOrderNopayCommNum.setText("数量"+bean.getNums()+"份");
        holder.mTextOrderNopayPrice.setText("总价"+bean.getTotals()+"元");
        if(adapterType==1){
            holder.mButtonOrderNopayPay.setVisibility(View.INVISIBLE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        holder.mOrderNopayCommRecycle.setLayoutManager(layoutManager);
        OrderNoPayCommAdapter adapter = new OrderNoPayCommAdapter(bean.getItmes());
        holder.mOrderNopayCommRecycle.setAdapter(adapter);

        if(bean.isShowComm()){
            holder.mLayoutOrderNopayComm.setVisibility(View.VISIBLE);
            holder.mButtonOrderNopayShang.setVisibility(View.VISIBLE);
            holder.mButtonOrderNopayXia.setVisibility(View.GONE);
        }else {
            holder.mLayoutOrderNopayComm.setVisibility(View.GONE);
            holder.mButtonOrderNopayShang.setVisibility(View.GONE);
            holder.mButtonOrderNopayXia.setVisibility(View.VISIBLE);
        }

        holder.mButtonOrderNopayXia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickShowComm(position);
            }
        });

        holder.mButtonOrderNopayShang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickHideComm(position);
            }
        });
        holder.selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickOrderItem(position);
            }
        });
        holder.mButtonOrderNopayPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickPay(position);
            }
        });

        holder.selfView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mInterface.onClickLongOrderItem(position);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.button_order_nopay_pay)
        Button mButtonOrderNopayPay;
        @BindView(R.id.text_order_nopay_num)
        TextView mTextOrderNopayNum;
        @BindView(R.id.circleImageView)
        ImageView mCircleImageView;
        @BindView(R.id.text_order_nopay_time)
        TextView mTextOrderNopayTime;
        @BindView(R.id.text_order_nopay_window)
        TextView mTextOrderNopayWindow;
        @BindView(R.id.text_order_nopay_price)
        TextView mTextOrderNopayPrice;
        @BindView(R.id.text_order_nopay_commNum)
        TextView mTextOrderNopayCommNum;
        @BindView(R.id.button_order_nopay_xia)
        Button mButtonOrderNopayXia;
        @BindView(R.id.order_nopay_commRecycle)
        RecyclerView mOrderNopayCommRecycle;
        @BindView(R.id.button_order_nopay_shang)
        Button mButtonOrderNopayShang;
        @BindView(R.id.layout_order_nopay_comm)
        ConstraintLayout mLayoutOrderNopayComm;

        View selfView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            selfView = itemView;
        }
    }
}
