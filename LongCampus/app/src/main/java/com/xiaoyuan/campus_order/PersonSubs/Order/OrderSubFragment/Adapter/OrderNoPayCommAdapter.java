package com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoyuan.campus_order.PersonSubs.Order.OrderSubFragment.Bean.OrderBean;
import com.xiaoyuan.campus_order.R;

import java.util.List;

/**
 * Created by longhengyu on 2017/7/8.
 */

public class OrderNoPayCommAdapter extends RecyclerView.Adapter<OrderNoPayCommAdapter.ViewHolder> {

    private List<OrderBean.ItmesBean> mList;

    public OrderNoPayCommAdapter(List<OrderBean.ItmesBean> list){
        mList = list;
    }

    @Override
    public OrderNoPayCommAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_nopay_comm,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderNoPayCommAdapter.ViewHolder holder, int position) {
        OrderBean.ItmesBean bean = mList.get(position);
        holder.itemText.setText(bean.getDish()+"x"+bean.getNum());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemText;

        public ViewHolder(View itemView) {
            super(itemView);
            itemText = (TextView) itemView.findViewById(R.id.text_order_nopay_comm_text);
        }
    }
}
