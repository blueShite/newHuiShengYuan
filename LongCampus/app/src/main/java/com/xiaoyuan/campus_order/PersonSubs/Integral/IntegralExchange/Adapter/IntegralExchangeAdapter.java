package com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.Bean.IntergralExchangeBean;
import com.xiaoyuan.campus_order.PersonSubs.Integral.IntegralExchange.Interface.IntegarlExchangeInterface;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longhengyu on 2017/7/3.
 */

public class IntegralExchangeAdapter extends RecyclerView.Adapter<IntegralExchangeAdapter.ViewHolder> {


    private List<IntergralExchangeBean> mList;
    private Context mContext;
    private IntegarlExchangeInterface mInterface;

    public IntegralExchangeAdapter(List<IntergralExchangeBean> list, Context context, IntegarlExchangeInterface anInterface) {
        mList = list;
        mContext = context;
        mInterface = anInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_integral_exchange, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        IntergralExchangeBean bean = mList.get(position);
        Picasso.with(mContext).load(RequestTools.BaseUrl + bean.getLitpic()).resize(120, 120)
                .placeholder(R.drawable.caipu).into(holder.mImageIntegralExchangeItem);
        holder.mTextIntegralExchangeName.setText(bean.getTitle());
        holder.mTextIntegralExchangeInter.setText(bean.getPrice()+"积分");
        holder.mTextIntegralExchangeEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickExchangeSucess(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_integral_exchange_item)
        ImageView mImageIntegralExchangeItem;
        @BindView(R.id.text_integral_exchange_name)
        TextView mTextIntegralExchangeName;
        @BindView(R.id.text_integral_exchange_inter)
        TextView mTextIntegralExchangeInter;
        @BindView(R.id.text_integral_exchange_ex)
        TextView mTextIntegralExchangeEx;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
