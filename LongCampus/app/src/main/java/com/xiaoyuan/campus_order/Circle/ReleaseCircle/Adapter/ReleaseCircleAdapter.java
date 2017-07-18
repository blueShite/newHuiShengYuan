package com.xiaoyuan.campus_order.Circle.ReleaseCircle.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xiaoyuan.campus_order.Circle.ReleaseCircle.Interface.ReleaseCircleInterface;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by longhengyu on 2017/6/28.
 */

public class ReleaseCircleAdapter extends RecyclerView.Adapter<ReleaseCircleAdapter.ViewHolder> {

    private List<String> mList;
    private Context mContext;
    private ReleaseCircleInterface mInterface;

    public ReleaseCircleAdapter(List<String> list,Context context,ReleaseCircleInterface anInterface){

        mList = list;
        mContext = context;
        mInterface = anInterface;

    }

    @Override
    public ReleaseCircleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_release_circle,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReleaseCircleAdapter.ViewHolder holder, final int position) {

        if(mList.size()<9&&position==mList.size()){

            Picasso.with(mContext).load(R.drawable.icon_addpic_focused).into(holder.itemImage);
            holder.itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInterface.onClickAddImage();
                }
            });
            return;
        }

        String path = mList.get(position);
        Picasso.with(mContext).load(new File(path)).into(holder.itemImage);
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickDeleteImage(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mList.size()>8){
            return mList.size();
        }
        return mList.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.image_release_circle_item);

        }
    }
}
