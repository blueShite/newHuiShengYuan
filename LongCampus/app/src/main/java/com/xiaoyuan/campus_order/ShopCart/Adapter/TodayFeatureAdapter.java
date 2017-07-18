package com.xiaoyuan.campus_order.ShopCart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartBean;
import com.xiaoyuan.campus_order.ShopCart.Interface.TodayFeatureInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by longhengyu on 2017/6/19.
 */

public class TodayFeatureAdapter extends RecyclerView.Adapter<TodayFeatureAdapter.ViewHolder> {

    private List<ShopCartBean> mList;
    private TodayFeatureInterface mInterface;
    private Context mContext;

    public TodayFeatureAdapter(List<ShopCartBean> list,TodayFeatureInterface anInterface,Context context){
        mList = list;
        mInterface = anInterface;
        mContext = context;
    }

    @Override
    public TodayFeatureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopcart,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TodayFeatureAdapter.ViewHolder holder, final int position) {

        ShopCartBean bean = mList.get(position);
        String imageStr = RequestTools.BaseUrl+bean.getLitpic();
        Picasso.with(mContext).load(imageStr).resize(50,50).into(holder.shopImage);
        holder.nameText.setText(bean.getDish());
        holder.subText.setText(bean.getIntro());
        holder.priceText.setText("¥"+bean.getPrice()+"元");
        holder.oldPriceText.setText("原价:"+bean.getPack()+"元");
        holder.oldNumText.setText("已售"+bean.getSalnum()+"份");
        holder.numText.setText(bean.getAddNum());
        holder.selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickItem(position);
            }
        });
        holder.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickItemAdd(position,holder.numText);
            }
        });
        holder.jianImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickItemReduce(position,holder.numText);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //item视图
        private ImageView shopImage;
        private TextView nameText;
        private TextView priceText;
        private TextView oldPriceText;
        private TextView oldNumText;
        private TextView numText;
        private TextView subText;
        private ImageView addImage;
        private ImageView jianImage;
        private View selfView;

        public ViewHolder(View itemView) {
            super(itemView);
            selfView = itemView;
            shopImage = (ImageView)itemView.findViewById(R.id.image_shopCartItem);
            addImage = (ImageView)itemView.findViewById(R.id.image_shopCartItem_jia);
            jianImage = (ImageView)itemView.findViewById(R.id.image_shopCartItem_jian);
            nameText = (TextView)itemView.findViewById(R.id.text_shopCartItem_name);
            priceText = (TextView)itemView.findViewById(R.id.text_shopCartItem_price);
            numText = (TextView)itemView.findViewById(R.id.text_shopCartItem_num);
            oldPriceText = (TextView)itemView.findViewById(R.id.text_shopCart_oldPrice);
            oldNumText = (TextView)itemView.findViewById(R.id.text_shopCart_oldNum);
            subText = (TextView)itemView.findViewById(R.id.text_shopCartItem_sub);

        }
    }
}
