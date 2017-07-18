package com.xiaoyuan.campus_order.PersonSubs.Order.OrderDetail.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xiaoyuan.campus_order.PersonSubs.Order.OrderDetail.Bean.OrderDetailBean;
import com.xiaoyuan.campus_order.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longhengyu on 2017/7/10.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {


    private OrderDetailBean mDetailBean;
    private List<OrderDetailBean.ItmesBean> mList = new ArrayList<>();
    private Context mContext;
    private View headerView;
    private View footView;

    public OrderDetailAdapter(OrderDetailBean detailBean, Context context) {
        mDetailBean = detailBean;
        for (List<OrderDetailBean.ItmesBean> beanList:detailBean.getItmes()){
            for (OrderDetailBean.ItmesBean bean:beanList){
                mList.add(bean);
            }
        }
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return 0;
        }
        if (position == mList.size()+1) {
            return 2;
        }

        return 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order_detail_header, parent, false);
            ViewHolder viewHolder = new ViewHolder(headerView);
            return viewHolder;
        }

        if (viewType == 2) {
            footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order_detail_foot, parent, false);
            ViewHolder viewHolder = new ViewHolder(footView);
            return viewHolder;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {

            switch (Integer.parseInt(mDetailBean.getDispatching())){
                case 3:
                    holder.dian4.setSelected(true);
                case 2:
                    holder.dian3.setSelected(true);
                    holder.xian5.setSelected(true);
                    holder.xian6.setSelected(true);
                case 1:
                    holder.dian2.setSelected(true);
                    holder.xian3.setSelected(true);
                    holder.xian4.setSelected(true);
                case 0:
                    holder.dian1.setSelected(true);
                    holder.xian1.setSelected(true);
                    holder.xian2.setSelected(true);
                    break;
            }
            switch (Integer.parseInt(mDetailBean.getDispatching())){
                case 3:
                    holder.headerOrderType.setText("订单已完成");
                    break;
                case 2:
                    holder.headerOrderType.setText("订单正在配送");
                    break;
                case 1:
                    holder.headerOrderType.setText("制作完成");
                    break;
                case 0:
                    holder.headerOrderType.setText("已接单");
                    break;
            }
            holder.headerWindowName.setText(mDetailBean.getRes_name());
            return ;
        }
        if (position == mList.size()+1) {

            holder.mTextOrderDetailFootTotalPrice.setText(mDetailBean.getTotal()+"");
            holder.mTextOrderDetailFootPackPrice.setText(mDetailBean.getPack());
            holder.mTextOrderDetailFootGivePrice.setText(mDetailBean.getDelivery());
            holder.mTextOrderDetailFootSalePrice.setText(mDetailBean.getDiscount());
            holder.mTextOrderDetailFootUserName.setText(mDetailBean.getAddress().getName());
            holder.mTextOrderDetailFootUserPhone.setText(mDetailBean.getAddress().getPhone());
            holder.mTextOrderDetailFootUserAddress.setText(mDetailBean.getAddress().getDizhi());
            holder.mTextOrderDetailFootGiveType.setText("校内送餐");
            holder.mTextOrderDetailFootTime.setText(mDetailBean.getDiner_time());
            holder.mTextOrderDetailFootOrderNum.setText(mDetailBean.getId());
            holder.mTextOrderDetailFootOrderTime.setText(mDetailBean.getAdd_time());
            holder.mTextOrderDetailFootPayType.setText("在线支付");

            return ;
        }

        OrderDetailBean.ItmesBean bean =  mList.get(position-1);
        holder.itemName.setText(bean.getDish());
        holder.itemNum.setText("x"+bean.getNum());
        holder.itemPrice.setText(bean.getPrice()+"元");
        if(bean.getText()==null){
            bean.setText("");
        }
        holder.itemRemark.setText("备注:"+bean.getText());
    }

    @Override
    public int getItemCount() {
        return mList.size() + 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //headerView
        TextView headerOrderType;
        TextView headerWindowName;
        Button dian1;
        Button xian1;
        Button xian2;
        Button dian2;
        Button xian3;
        Button xian4;
        Button dian3;
        Button xian5;
        Button xian6;
        Button dian4;

        //footView
        @BindView(R.id.text_order_detail_foot_userAddress)
        TextView mTextOrderDetailFootUserAddress;
        @BindView(R.id.text_order_detail_foot_userPhone)
        TextView mTextOrderDetailFootUserPhone;
        @BindView(R.id.text_order_detail_foot_packPrice)
        TextView mTextOrderDetailFootPackPrice;
        @BindView(R.id.text_order_detail_foot_givePrice)
        TextView mTextOrderDetailFootGivePrice;
        @BindView(R.id.text_order_detail_foot_salePrice)
        TextView mTextOrderDetailFootSalePrice;
        @BindView(R.id.text_order_detail_foot_totalPrice)
        TextView mTextOrderDetailFootTotalPrice;
        @BindView(R.id.text_order_detail_foot_time)
        TextView mTextOrderDetailFootTime;
        @BindView(R.id.text_order_detail_foot_userName)
        TextView mTextOrderDetailFootUserName;
        @BindView(R.id.text_order_detail_foot_giveType)
        TextView mTextOrderDetailFootGiveType;
        @BindView(R.id.text_order_detail_foot_orderNum)
        TextView mTextOrderDetailFootOrderNum;
        @BindView(R.id.text_order_detail_foot_payType)
        TextView mTextOrderDetailFootPayType;
        @BindView(R.id.text_order_detail_foot_orderTime)
        TextView mTextOrderDetailFootOrderTime;

        //itemView
        TextView itemName;
        TextView itemNum;
        TextView itemPrice;
        TextView itemRemark;

        public ViewHolder(View itemView) {
            super(itemView);
            if (headerView != null && headerView == itemView) {
                headerOrderType = (TextView) itemView.findViewById(R.id.text_order_detail_header_orderSub);
                headerWindowName = (TextView) itemView.findViewById(R.id.text_order_detail_header_windowName);
                dian1 = (Button) itemView.findViewById(R.id.button_order_detail_dian1);
                dian2 = (Button) itemView.findViewById(R.id.button_order_detail_dian2);
                dian3 = (Button) itemView.findViewById(R.id.button_order_detail_dian3);
                dian4 = (Button) itemView.findViewById(R.id.button_order_detail_dian4);
                xian1 = (Button) itemView.findViewById(R.id.button_order_detail_xian1);
                xian2 = (Button) itemView.findViewById(R.id.button_order_detail_xian2);
                xian3 = (Button) itemView.findViewById(R.id.button_order_detail_xian3);
                xian4 = (Button) itemView.findViewById(R.id.button_order_detail_xian4);
                xian5 = (Button) itemView.findViewById(R.id.button_order_detail_xian5);
                xian6 = (Button) itemView.findViewById(R.id.button_order_detail_xian6);
                return;
            }
            if (footView != null && footView == itemView) {
                ButterKnife.bind(this, footView);
                return;
            }

            itemName = (TextView) itemView.findViewById(R.id.text_order_detail_itemName);
            itemNum = (TextView) itemView.findViewById(R.id.text_order_detail_itemNum);
            itemPrice = (TextView) itemView.findViewById(R.id.text_order_detail_itemPrice);
            itemRemark = (TextView) itemView.findViewById(R.id.text_order_detail_itemRemark);
        }
    }
}
