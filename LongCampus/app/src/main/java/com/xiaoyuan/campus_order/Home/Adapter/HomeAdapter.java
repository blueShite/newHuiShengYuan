package com.xiaoyuan.campus_order.Home.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Home.Bean.CanteenBean;
import com.xiaoyuan.campus_order.Home.Interface.HomeAdapterInterface;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by longhengyu on 2017/4/20.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private View mheaderView;

    private List<CanteenBean> mList;
    private Context mContext;

    public HomeAdapterInterface getAdapterInterface() {
        return mAdapterInterface;
    }

    public void setAdapterInterface(HomeAdapterInterface adapterInterface) {
        mAdapterInterface = adapterInterface;
    }

    private HomeAdapterInterface mAdapterInterface;

    public HomeAdapter(List<CanteenBean> list, Context context){
        mList = list;
        mContext = context;
    }

    public void reloadData(List<CanteenBean> list){
        mList = list;
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
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==0){
            mheaderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home,parent,false);
            ViewHolder viewHolder = new ViewHolder(mheaderView);
            return viewHolder;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_recycler,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, final int position) {

        if(position==0){
            return;
        }
        CanteenBean bean = mList.get(position);
        Picasso.with(mContext).load(RequestTools.BaseUrl+bean.getRes_img()).resize(120,120).into(holder.mImageView);
        holder.subText.setText(bean.getRes_intro());
        holder.nameText.setText(bean.getRes_names());
        holder.selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterInterface.onClickAdapter(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView nameText;
        private TextView subText;
        private View selfView;

        public ViewHolder(View itemView) {
            super(itemView);
            if(mheaderView!=null&&itemView==mheaderView){

                return;
            }
            mImageView = (ImageView) itemView.findViewById(R.id.image_homeItem_canteen);
            nameText = (TextView)itemView.findViewById(R.id.text_homeItem_name);
            subText = (TextView)itemView.findViewById(R.id.text_homeItem_sub);
            selfView = itemView;
        }
    }
}
