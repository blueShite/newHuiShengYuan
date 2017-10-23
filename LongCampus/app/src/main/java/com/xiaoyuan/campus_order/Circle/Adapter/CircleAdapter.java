package com.xiaoyuan.campus_order.Circle.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Circle.Bean.CircleHeaderBean;
import com.xiaoyuan.campus_order.Circle.Bean.CircleItemBean;
import com.xiaoyuan.campus_order.Circle.Interface.CircleInterface;
import com.xiaoyuan.campus_order.Information.Adapter.PicassoImageLoader;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longhengyu on 2017/4/22.
 */

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.ViewHolder> {

    private View mheaderView;
    private Context mContext;

    private List<CircleHeaderBean> mBannerList;
    private List<CircleItemBean> mItemList;

    private CircleInterface mInterface;

    public CircleAdapter(List<CircleHeaderBean> bannerList,List<CircleItemBean> itemList,Context context,CircleInterface circleInterface){

        mBannerList = bannerList;
        mItemList = itemList;
        mContext = context;
        mInterface = circleInterface;

    }

    public void reloadHeader(List<CircleHeaderBean> bannerList){
        mBannerList = bannerList;
        notifyItemChanged(0);
    }

    public void reloadItem(List<CircleItemBean> itemList){

        mItemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if(position==0){
            return 0;
        }
        return 1;
    }

    @Override
    public CircleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            mheaderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_circle_header,parent,false);
            ViewHolder viewHolder = new ViewHolder(mheaderView);
            return viewHolder;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CircleAdapter.ViewHolder holder, final int position) {

        if(position==0){
            if(mBannerList==null||mBannerList.size()<1){
                return;
            }
            Banner banner = holder.mBanner;
            List<String> list = new ArrayList<>();
            for (int i=0;i<mBannerList.size();i++){
                String imageUrl = RequestTools.BaseUrl+mBannerList.get(i).getGroup_litpic();
                list.add(imageUrl);
            }
            banner.setImages(list);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int posit) {
                    mInterface.onClickHeader(posit);
                }
            });
            banner.start();
            return;
        }

        CircleItemBean bean = mItemList.get(position-1);
        holder.nameText.setText(bean.getGroup_title());
        String[] all=bean.getGroup_time().split("[ ]");
        holder.timeText.setText(all[0]);
        holder.zanText.setText("点赞"+"("+bean.getNum()+")");
        holder.pinglunText.setText("评论"+"("+bean.getReply_num()+")");
        String imageUrl = RequestTools.BaseUrl+bean.getHeadimg();
        Picasso.with(mContext).load(imageUrl).resize(70,70).into(holder.mImageView);
        holder.selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickItem(position-1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemList.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Banner mBanner;

        ImageView mImageView;
        TextView nameText;
        TextView zanText;
        TextView pinglunText;
        TextView timeText;
        View selfView;

        public ViewHolder(View itemView) {
            super(itemView);
            if(mheaderView!=null&&mheaderView==itemView){

                mBanner = (Banner)itemView.findViewById(R.id.banner_circle);
                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                mBanner.setImageLoader(new PicassoImageLoader());

                return;
            }

            mImageView = (ImageView) itemView.findViewById(R.id.circleImage_circle_item);
            nameText = (TextView)itemView.findViewById(R.id.text_circle_name);
            zanText = (TextView)itemView.findViewById(R.id.text_circle_zan);
            pinglunText = (TextView)itemView.findViewById(R.id.text_circle_pinglun);
            timeText = (TextView)itemView.findViewById(R.id.text_circle_time);
            selfView = itemView;
        }
    }
}
