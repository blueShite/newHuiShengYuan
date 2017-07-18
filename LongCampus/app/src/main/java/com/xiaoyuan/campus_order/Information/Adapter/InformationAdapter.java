package com.xiaoyuan.campus_order.Information.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Information.Bean.InformationBean;
import com.xiaoyuan.campus_order.Information.Interface.InformationInterface;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longhengyu on 2017/4/21.
 */

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {

    private View mheaderView;

    private List<InformationBean> mbannerList;
    private List<InformationBean> mitemList;
    private Context mContext;

    private InformationInterface mInterface;

    public InformationAdapter(List<InformationBean>bannerList, List<InformationBean>itemList, Context context,InformationInterface informationInterface){

        mbannerList = bannerList;
        mitemList = itemList;
        mInterface = informationInterface;
        mContext = context;

    }

    public void reloadHeader(List<InformationBean> bannerList){
        mbannerList = bannerList;
        notifyItemChanged(0);
    }

    public void reloadItem(List<InformationBean> itemList){

        mitemList = itemList;
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
    public InformationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){

            mheaderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_information_header,parent,false);
            ViewHolder viewHolder = new ViewHolder(mheaderView);
            return viewHolder;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InformationAdapter.ViewHolder holder, final int position) {

        if(position==0){

            if(mbannerList==null||mbannerList.size()<1){
                return;
            }
            Banner banner = holder.mBanner;
            List<String> list = new ArrayList<>();
            for (int i=0;i<mbannerList.size();i++){
                String imageUrl = RequestTools.BaseUrl+mbannerList.get(i).getLitpic();
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

        InformationBean informationBean = mitemList.get(position-1);
        String imageUrl = RequestTools.BaseUrl+informationBean.getLitpic();
        Picasso.with(mContext).load(imageUrl).resize(45,45).into(holder.mImageView);
        holder.nameText.setText(informationBean.getHtinfo());
        holder.subText.setText(informationBean.getTitle());
        holder.selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickitem(position-1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mitemList.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //头视图
        private Banner mBanner;
        //item视图
        private ImageView mImageView;
        private TextView nameText;
        private TextView subText;
        private View selfView;

        public ViewHolder(View itemView) {
            super(itemView);
            if(mheaderView!=null&&mheaderView==itemView){

                mBanner = (Banner)itemView.findViewById(R.id.banner_information);
                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                mBanner.setImageLoader(new PicassoImageLoader());
                return;

            }

            selfView = itemView;
            mImageView = (ImageView)itemView.findViewById(R.id.image_information_item);
            nameText = (TextView)itemView.findViewById(R.id.text_information_name);
            subText = (TextView)itemView.findViewById(R.id.text_information_sub);
        }
    }
}
