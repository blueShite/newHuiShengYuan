package com.xiaoyuan.campus_order.ShopCartList.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.ShopCartList.Bean.ShopCartItemBean;
import com.xiaoyuan.campus_order.ShopCartList.Interface.ShopCartListInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longhengyu on 2017/7/3.
 */

public class ShopCartListAdapter extends RecyclerView.Adapter<ShopCartListAdapter.ViewHolder> {



    private List<ShopCartItemBean> mList;
    private Context mContext;
    private ShopCartListInterface mInterface;
    private View groupView;

    public ShopCartListAdapter(List<ShopCartItemBean> list, Context context, ShopCartListInterface anInterface) {
        mList = list;
        mContext = context;
        mInterface = anInterface;
    }

    @Override
    public int getItemViewType(int position) {

        ShopCartItemBean bean = mList.get(position);
        if (bean.getItemType().equals("0")) {
            return 0;
        }
        return 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            groupView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopcart_list_group, parent, false);
            ViewHolder viewHolder = new ViewHolder(groupView);
            return viewHolder;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopcart_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        ShopCartItemBean itemBean = mList.get(position);
        if (itemBean.getItemType().equals("0")) {

            holder.groupTextView.setText(itemBean.getRes_name());
            if (itemBean.getSelectType().equals("1")) {
                holder.selectBtn.setSelected(true);
            }else {
                holder.selectBtn.setSelected(false);
            }
            holder.selectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInterface.onClickGroupItem(position);
                }
            });
            return;
        }
        holder.mTextShopcartListName.setText(itemBean.getDish());
        holder.mTextShopcartListPrice.setText("¥" + itemBean.getTotal());
        holder.mTextShopcartListNum.setText(itemBean.getNum());
        Picasso.with(mContext).load(RequestTools.BaseUrl+itemBean.getLitpic()).fit().centerCrop()
                .into(holder.mImageShopcartListCommImage);
        holder.mImageShopcartListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickItemAdd(position, holder.mTextShopcartListNum);
            }
        });
        holder.mImageShopcartListRedux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickItemReduce(position, holder.mTextShopcartListNum);
            }
        });
        holder.selfView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mInterface.onClickItemDelete(position);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView groupTextView;
        Button selectBtn;

        @BindView(R.id.image_shopcart_list_commImage)
        ImageView mImageShopcartListCommImage;
        @BindView(R.id.text_shopcart_list_name)
        TextView mTextShopcartListName;
        @BindView(R.id.text_shopcart_list_price)
        TextView mTextShopcartListPrice;
        @BindView(R.id.image_shopcart_list_add)
        ImageView mImageShopcartListAdd;
        @BindView(R.id.text_shopcart_list_num)
        TextView mTextShopcartListNum;
        @BindView(R.id.image_shopcart_list_redux)
        ImageView mImageShopcartListRedux;

        View selfView;

        public ViewHolder(View itemView) {
            super(itemView);
            if (groupView != null && groupView == itemView) {
                groupTextView = (TextView) itemView.findViewById(R.id.text_shopcart_list_group);
                selectBtn = (Button) itemView.findViewById(R.id.button_shopcart_list_group_select);
                return;
            }
            ButterKnife.bind(this, itemView);
            selfView = itemView;

        }
    }
}
