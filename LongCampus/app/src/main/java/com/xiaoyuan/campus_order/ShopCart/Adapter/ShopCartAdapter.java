package com.xiaoyuan.campus_order.ShopCart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Information.Adapter.PicassoImageLoader;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartBean;
import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartHeaderBean;
import com.xiaoyuan.campus_order.ShopCart.Interface.ShopCartInterface;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longhengyu on 2017/4/25.
 */

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.ViewHolder> {

    private List<ShopCartBean> mList;
    private ShopCartHeaderBean mBannerBean;

    private Context mContext;
    private View headerView;

    private ShopCartInterface mInterface;

    public ShopCartAdapter(List<ShopCartBean> List,ShopCartHeaderBean bannerBean,ShopCartInterface shopCartInterface,Context context){

        mList = List;
        mBannerBean = bannerBean;
        mInterface = shopCartInterface;
        mContext = context;
    }

    public void reloadItem(List<ShopCartBean> list,ShopCartHeaderBean headerBean){

        mList = list;
        mBannerBean = headerBean;
        notifyDataSetChanged();
    }

    public void reloadHeaderData(int imageId,String headerText){

        mBannerBean.setImageId(imageId);
        mBannerBean.setHeaderText(headerText);
        notifyDataSetChanged();
    }

    public void reloadItemWithPoist(List<ShopCartBean> list,int poist){

        mList = list;
        notifyItemChanged(poist);

    }

    @Override
    public int getItemViewType(int position) {

        if(position==0){
            return 0;
        }
        return 1;
    }


    @Override
    public ShopCartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==0){
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_shopcart_header,parent,false);
            ViewHolder viewHolder = new ViewHolder(headerView);
            return viewHolder;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopcart,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ShopCartAdapter.ViewHolder holder, final int position) {

        if(position==0){

            Banner banner = holder.mBanner;
            List<String> list = new ArrayList<>();
            for (int i=0;i<mBannerBean.getRes_img().size();i++){
                list.add(RequestTools.BaseUrl+mBannerBean.getRes_img().get(i));
            }
            banner.setImages(list);
            banner.start();

            for (int  i=0;i<holder.mRelativeLayouts.size();i++){
                final int j=i;
                RelativeLayout layout = holder.mRelativeLayouts.get(i);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mInterface.onClickHeader(j);
                    }
                });
            }

            holder.tuijianImage.setImageResource(mBannerBean.getImageId());
            holder.headerNameText.setText(mBannerBean.getHeaderText());
            return;
        }
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

        //头视图
        private Banner mBanner;
        private RelativeLayout tuijianRL;
        private RelativeLayout tejianRL;
        private RelativeLayout teseRL;
        private RelativeLayout caidanRL;
        private ImageView tuijianImage;
        private TextView headerNameText;
        private List<RelativeLayout> mRelativeLayouts = new ArrayList<>();

        public ViewHolder(View itemView) {
            super(itemView);
            if(headerView!=null&&headerView==itemView){

                mBanner = (Banner)itemView.findViewById(R.id.banner_shopCart);
                tuijianRL = (RelativeLayout)itemView.findViewById(R.id.relative_shopCart_jinrituijian);
                tejianRL = (RelativeLayout)itemView.findViewById(R.id.relative_shopCart_tejiayouhui);
                teseRL = (RelativeLayout)itemView.findViewById(R.id.relative_shopCart_tesetaocan);
                caidanRL = (RelativeLayout)itemView.findViewById(R.id.relative_shopCart_wodecaidan);
                tuijianImage = (ImageView)itemView.findViewById(R.id.image_shopCart_tuijian);
                headerNameText = (TextView)itemView.findViewById(R.id.text_shopCartHeader_name);
                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                mBanner.setImageLoader(new PicassoImageLoader());
                mRelativeLayouts.add(tuijianRL);
                mRelativeLayouts.add(tejianRL);
                mRelativeLayouts.add(teseRL);
                mRelativeLayouts.add(caidanRL);

                return;
            }

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
