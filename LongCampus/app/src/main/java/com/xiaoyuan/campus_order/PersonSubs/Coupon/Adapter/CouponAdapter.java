package com.xiaoyuan.campus_order.PersonSubs.Coupon.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoyuan.campus_order.PersonSubs.Coupon.Bean.CouponBean;
import com.xiaoyuan.campus_order.PersonSubs.Coupon.Interface.CouponInterface;
import com.xiaoyuan.campus_order.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longhengyu on 2017/6/30.
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {

    private List<CouponBean> mList;
    private Context mContext;
    private CouponInterface mInterface;

    public CouponAdapter(List<CouponBean> list,Context context,CouponInterface anInterface){
        mList = list;
        mContext = context;
        mInterface = anInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        CouponBean bean = mList.get(position);
        holder.mTextCouponItemTime.setText("有效期"+bean.getDate()+"-"+bean.getEtime());
        holder.mTextCouponItemPrice.setText("¥"+bean.getAmount());
        holder.mTextCouponItemSub.setText(bean.getTitle());
        if(bean.getlCouponType().equals("0")){
            holder.mLayoutCouponItemLeft.setBackgroundResource(R.color.ColorCouponLeft);
            holder.mLayoutCouponItemRight.setBackgroundResource(R.drawable.bg_yhq_used);
        }
        if(bean.getlCouponType().equals("2")){
            holder.mLayoutCouponItemRight.setBackgroundResource(R.drawable.bg_yhq_lq);
        }
        holder.selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickCoupon(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_coupon_item_sub)
        TextView mTextCouponItemSub;
        @BindView(R.id.text_coupon_item_price)
        TextView mTextCouponItemPrice;
        @BindView(R.id.layout_coupon_item_left)
        ConstraintLayout mLayoutCouponItemLeft;
        @BindView(R.id.text_coupon_item_time)
        TextView mTextCouponItemTime;
        @BindView(R.id.layout_coupon_item_right)
        ConstraintLayout mLayoutCouponItemRight;

        View selfView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            selfView = itemView;
        }
    }
}
