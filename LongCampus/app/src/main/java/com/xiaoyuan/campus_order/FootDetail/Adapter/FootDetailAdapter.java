package com.xiaoyuan.campus_order.FootDetail.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.FootDetail.Bean.FootDetailBean;
import com.xiaoyuan.campus_order.FootDetail.Bean.FootDetailItemBean;
import com.xiaoyuan.campus_order.FootDetail.Interface.FootDetailInterface;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by longhengyu on 2017/4/27.
 */

public class FootDetailAdapter extends RecyclerView.Adapter<FootDetailAdapter.ViewHolder> {

    private View headerView;
    private Context mContext;

    private List<FootDetailItemBean> mList;
    private FootDetailBean mShopCartBean;
    private FootDetailInterface mInterface;
    private String mIsCollection;

    public FootDetailAdapter(List<FootDetailItemBean> list, FootDetailBean shopCartBean, Context context,FootDetailInterface anInterface){

        mList = list;
        mShopCartBean = shopCartBean;
        mContext = context;
        mInterface = anInterface;
    }

    public void reloadHeader(String isCollection){
        mIsCollection = isCollection;
    }

    @Override
    public int getItemViewType(int position) {

        if(position==0){
            return 0;
        }
        return 1;
    }

    @Override
    public FootDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_footdetail_header,parent,false);
            ViewHolder viewHolder = new ViewHolder(headerView);
            return viewHolder;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footdetail,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FootDetailAdapter.ViewHolder holder, int position) {

        if(position==0){

            if(mShopCartBean.isMy()){
                holder.nameText.setText(mShopCartBean.getMealname());
                Picasso.with(mContext).load(RequestTools.BaseUrl+mShopCartBean.getMeal_litpic()).fit().centerCrop().into(holder.headerImage);
            }else {
                holder.nameText.setText(mShopCartBean.getDish());
                Picasso.with(mContext).load(RequestTools.BaseUrl+mShopCartBean.getLitpic()).fit().centerCrop().into(holder.headerImage);
            }
            holder.numText.setText("月售"+mShopCartBean.getSalnum()+"份");
            holder.priceText.setText("¥"+mShopCartBean.getPrice());
            holder.oldPriceText.setText("原价"+mShopCartBean.getPrice()+"元");
            holder.addNumText.setText(mShopCartBean.getNums());
            holder.addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInterface.onClickAdd(holder.addNumText);
                }
            });
            holder.jianImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInterface.onClickRedux(holder.addNumText);
                }
            });
            if(mIsCollection.equals("1")){
                holder.addImage.setVisibility(View.INVISIBLE);
                holder.jianImage.setVisibility(View.INVISIBLE);
                holder.addNumText.setVisibility(View.INVISIBLE);
            }else {
                holder.addImage.setVisibility(View.VISIBLE);
                holder.jianImage.setVisibility(View.VISIBLE);
                holder.addNumText.setVisibility(View.VISIBLE);
            }
            return;
        }

        FootDetailItemBean bean = mList.get(position);
        holder.itemNameText.setText(bean.getName());
        holder.itemSubText.setText(bean.getSub());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //item视图
        private TextView itemNameText;
        private TextView itemSubText;

        //header视图
        private ImageView headerImage;
        private ImageView addImage;
        private ImageView jianImage;
        private TextView nameText;
        private TextView numText;
        private TextView priceText;
        private TextView oldPriceText;
        private TextView addNumText;


        public ViewHolder(View itemView) {
            super(itemView);

            if(headerView!=null&&headerView==itemView){

                headerImage = (ImageView)itemView.findViewById(R.id.image_footDetail_header);
                addImage = (ImageView)itemView.findViewById(R.id.image_footDetail_add);
                jianImage = (ImageView)itemView.findViewById(R.id.image_footDetail_jian);
                nameText = (TextView)itemView.findViewById(R.id.text_footDetail_name);
                numText = (TextView)itemView.findViewById(R.id.text_footDetail_num);
                priceText = (TextView)itemView.findViewById(R.id.text_footDetail_price);
                oldPriceText = (TextView)itemView.findViewById(R.id.text_footDetail_oldPrice);
                addNumText = (TextView)itemView.findViewById(R.id.text_footDetail_addNum);

                return;
            }

            itemNameText = (TextView)itemView.findViewById(R.id.text_footDetailItem_name);
            itemSubText = (TextView)itemView.findViewById(R.id.text_footDetailItem_sub);
        }
    }
}
