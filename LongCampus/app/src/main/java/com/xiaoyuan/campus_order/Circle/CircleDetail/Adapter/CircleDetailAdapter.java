package com.xiaoyuan.campus_order.Circle.CircleDetail.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Circle.CircleDetail.Bean.CircleDetailHeaderBean;
import com.xiaoyuan.campus_order.Circle.CircleDetail.Bean.CircleDetailItemBean;
import com.xiaoyuan.campus_order.Circle.CircleDetail.Interface.CircleDetailInterface;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by longhengyu on 2017/4/28.
 */

public class CircleDetailAdapter extends RecyclerView.Adapter<CircleDetailAdapter.ViewHolder> {

    private CircleDetailHeaderBean mBean;
    private List<CircleDetailItemBean> mList;
    private Context mContext;
    public CircleDetailInterface mInterface;

    public CircleDetailInterface getmInterface() {
        return mInterface;
    }

    public void setmInterface(CircleDetailInterface mInterface) {
        this.mInterface = mInterface;
    }

    private View headerView;

    public CircleDetailAdapter(List<CircleDetailItemBean> list,CircleDetailHeaderBean bean,Context context){
        mBean = bean;
        mList = list;
        mContext = context;
    }

    public void reloadHeader(CircleDetailHeaderBean bean){
        mBean = bean;
        notifyItemChanged(0);
    }

    public void reoadItem(List<CircleDetailItemBean> list){
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
    public CircleDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==0){
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.veiw_circledetail_header,parent,false);
            ViewHolder viewHolder = new ViewHolder(headerView);
            return viewHolder;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circledetail,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CircleDetailAdapter.ViewHolder holder, int position) {

        if(position==0) {
            if(mBean==null){
                return;
            }
            Picasso.with(mContext).load(RequestTools.BaseUrl+mBean.getHeadimg()).fit().centerCrop().placeholder(R.drawable.touxinag).into(holder.headerImage);
            Picasso.with(mContext).load(RequestTools.BaseUrl+mBean.getGroup_litpic().get(0)).fit().centerCrop().into(holder.headerSubImage);
            holder.headerNameText.setText(mBean.getNickname());
            holder.headerTimeText.setText(mBean.getGroup_time());
            holder.headerPingText.setText("评论("+mBean.getReply_num()+")");
            holder.headerZanText.setText("点赞("+mBean.getNum()+")");
            holder.headerZanText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInterface.onClickZan();
                }
            });
            return;
        }

        CircleDetailItemBean bean = mList.get(position-1);
        Picasso.with(mContext).load(RequestTools.BaseUrl+bean.getHeadimg()).fit().centerCrop().placeholder(R.drawable.touxinag).into(holder.itemImage);
        holder.itemNameText.setText(bean.getNickname());
        holder.itemTimeText.setText(bean.getReply_time());
        holder.itemSubText.setText(bean.getText());
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //header视图
        private ImageView headerImage;
        private TextView headerNameText;
        private TextView headerTimeText;
        private TextView headerPingText;
        private TextView headerZanText;
        private ImageView headerSubImage;

        //item视图
        private ImageView itemImage;
        private TextView itemNameText;
        private TextView itemTimeText;
        private TextView itemSubText;

        public ViewHolder(View itemView) {
            super(itemView);

            if(headerView != null && itemView == headerView){

                headerImage = (ImageView) itemView.findViewById(R.id.image_circleDetail_userHeader);
                headerSubImage = (ImageView)itemView.findViewById(R.id.image_circleDetail_subImage);
                headerNameText = (TextView)itemView.findViewById(R.id.text_circleDetail_userName);
                headerTimeText = (TextView)itemView.findViewById(R.id.text_circleDetail_time);
                headerPingText = (TextView)itemView.findViewById(R.id.text_circleDetail_pinglun);
                headerZanText = (TextView)itemView.findViewById(R.id.text_circleDetail_zan);

                return;
            }

            itemImage = (ImageView)itemView.findViewById(R.id.image_circleDetailItem_userHeader);
            itemNameText = (TextView)itemView.findViewById(R.id.text_circleDetailItem_name);
            itemTimeText = (TextView)itemView.findViewById(R.id.text_circleDetailItem_time);
            itemSubText = (TextView)itemView.findViewById(R.id.text_circleDetailItem_sub);
        }
    }
}
