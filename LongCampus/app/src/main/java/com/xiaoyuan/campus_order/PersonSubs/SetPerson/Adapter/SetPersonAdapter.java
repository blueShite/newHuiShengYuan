package com.xiaoyuan.campus_order.PersonSubs.SetPerson.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.SetPerson.Bean.SetPersonBean;
import com.xiaoyuan.campus_order.PersonSubs.SetPerson.Interface.SetPersonInterface;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by longhengyu on 2017/6/23.
 */

public class SetPersonAdapter extends RecyclerView.Adapter<SetPersonAdapter.ViewHolder> {

    private View headerView;
    private List<SetPersonBean> mList;
    private Context mContext;
    private SetPersonInterface mInterface;
    private String headerStr;

    public SetPersonAdapter(List<SetPersonBean> list,Context context,SetPersonInterface anInterface){
        mList = list;
        mContext = context;
        mInterface = anInterface;
    }

    public void reloadPersonHeader(String str){
        headerStr = str;
        notifyItemChanged(0);
    }

    @Override
    public int getItemViewType(int position) {

        if(position==0){
            return 0;
        }
        return 1;
    }

    @Override
    public SetPersonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_setperson_header,parent,false);
            ViewHolder viewHolder = new ViewHolder(headerView);
            return viewHolder;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set_person,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SetPersonAdapter.ViewHolder holder, final int position) {
        if(position==0){
            holder.backImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInterface.onClickHeaderView();
                }
            });

            if(headerStr!=null&&headerStr.length()>0){

                if(headerStr.contains("userhead")){
                    Picasso.with(mContext).load(RequestTools.BaseUrl+headerStr).placeholder(R.drawable.touxinag).into(holder.headerImage);
                }else {
                    Picasso.with(mContext).load(new File(headerStr)).placeholder(R.drawable.touxinag).into(holder.headerImage);
                }
            }
            return;
        }
        SetPersonBean bean = mList.get(position-1);
        holder.nameText.setText(bean.getName());
        holder.subText.setText(bean.getSub());
        holder.selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickPersonItem(position-1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //itemView
        private TextView nameText;
        private TextView subText;
        private View selfView;

        //headerView
        private ImageView backImage;
        private ImageView headerImage;

        public ViewHolder(View itemView) {
            super(itemView);
            if(headerView!=null&&itemView==headerView){
                backImage = (ImageView) itemView.findViewById(R.id.image_setPreson_header_back);
                headerImage = (ImageView) itemView.findViewById(R.id.image_setPreson_header_icon);
                return;
            }
            selfView = itemView;
            nameText = (TextView) itemView.findViewById(R.id.text_setPerson_name);
            subText = (TextView) itemView.findViewById(R.id.text_setPerson_sub);
        }
    }
}
