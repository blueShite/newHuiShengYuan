package com.xiaoyuan.campus_order.FootList.SubFootList.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.FeatureBean;
import com.xiaoyuan.campus_order.FootList.SubFootList.Interface.MyPackpageInterface;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;
import com.xiaoyuan.campus_order.Tools.Common.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by longhengyu on 2017/6/29.
 */

public class PackpageCommAdapter extends RecyclerView.Adapter<PackpageCommAdapter.ViewHolder> {


    private List<FeatureBean> mList;
    private Context mContext;
    private MyPackpageInterface mInterface;

    public PackpageCommAdapter(List<FeatureBean> list, Context context, MyPackpageInterface anInterface) {
        mList = list;
        mContext = context;
        mInterface = anInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_packpage_commodity, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        FeatureBean commodityBean = mList.get(position);
        holder.mTextPackCommName.setText(commodityBean.getDish());
        double price = Double.parseDouble(commodityBean.getPrice())*Double.parseDouble(commodityBean.getDiscount());
        holder.mTextPackCommSub.setText("¥" + AppUtils.doubleZhuanMa(price) + "元");
        holder.mTextPackCommOldPrice.setText("原价"+commodityBean.getPrice());
        holder.mTextPackCommNum.setText(commodityBean.getNums());
        Picasso.with(mContext).load(RequestTools.BaseUrl + commodityBean.getLitpic()).resize(120, 120).into(holder.mImagePackCommHeader);
        holder.mButtonPackCommCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickCollection(position);
            }
        });
        if (commodityBean.getIfkeep() == 0) {
            holder.mButtonPackCommCollection.setSelected(false);
        } else {
            holder.mButtonPackCommCollection.setSelected(true);
        }
        holder.mImagePackCommAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickAddShopCart(position, holder.mTextPackCommNum);
            }
        });
        holder.mImagePackCommRedux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickReduxShopCart(position, holder.mTextPackCommNum);
            }
        });
        holder.selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickMenu(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_pack_comm_header)
        CircleImageView mImagePackCommHeader;
        @BindView(R.id.text_pack_comm_name)
        TextView mTextPackCommName;
        @BindView(R.id.button_pack_comm_collection)
        Button mButtonPackCommCollection;
        @BindView(R.id.text_pack_comm_sub)
        TextView mTextPackCommSub;
        @BindView(R.id.image_pack_comm_add)
        ImageView mImagePackCommAdd;
        @BindView(R.id.text_pack_comm_num)
        TextView mTextPackCommNum;
        @BindView(R.id.image_pack_comm_redux)
        ImageView mImagePackCommRedux;
        @BindView(R.id.text_pack_comm_oldPrice)
        TextView mTextPackCommOldPrice;

        View selfView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            selfView = itemView;
        }
    }
}
