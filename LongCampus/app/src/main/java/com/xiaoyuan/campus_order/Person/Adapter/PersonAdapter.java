package com.xiaoyuan.campus_order.Person.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.Person.Bean.PersonBalanceBean;
import com.xiaoyuan.campus_order.Person.Bean.PersonBean;
import com.xiaoyuan.campus_order.Person.Interface.PersonInterface;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by longhengyu on 2017/4/22.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {


    private List<PersonBean> mList;

    private View headerView;
    private View footerView;
    private Context mContext;
    private PersonInterface mInterface;
    private PersonBalanceBean mBalanceBean;
    private String headerImagePath;

    public PersonAdapter(List<PersonBean> list, Context context,PersonInterface anInterface){

        mList = list;
        mContext = context;
        mInterface = anInterface;

    }

    public void reloadItem(List<PersonBean> list){

        mList = list;
        notifyDataSetChanged();
    }

    public void reloadHeader(PersonBalanceBean balanceBean){

        mBalanceBean = balanceBean;
        notifyItemChanged(0);
    }

    public void reloadHeaderImage(String str){
        headerImagePath = str;
        notifyItemChanged(0);
    }


    @Override
    public int getItemViewType(int position) {

        if(position==0){
            return 0;
        }
        if(position==mList.size()-1){
            return 2;
        }
        return 1;
    }

    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==0){
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_person_header,parent,false);
            ViewHolder viewHolder = new ViewHolder(headerView);
            return viewHolder;
        }
        if(viewType==2){

            footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_person_footer,parent,false);
            ViewHolder viewHolder = new ViewHolder(footerView);
            return viewHolder;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PersonAdapter.ViewHolder holder, final int position) {

        if(position==0){

            if(headerImagePath!=null&&headerImagePath.length()>0){

                if(headerImagePath.contains("userhead")){
                    Picasso.with(mContext).load(RequestTools.BaseUrl+headerImagePath).placeholder(R.drawable.touxinag).into(holder.headerImage);
                }else {
                    Picasso.with(mContext).load(new File(headerImagePath)).placeholder(R.drawable.touxinag).into(holder.headerImage);
                }
            }
            for (int i=0;i<holder.mRelativeLayouts.size();i++){
                RelativeLayout relativeLayout = holder.mRelativeLayouts.get(i);
                final int finalI = i;
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mInterface.onClickHeaderView(finalI);
                    }
                });
            }
            holder.titleNameText.setText("昵称:"+LoginManage.getInstance().getLoginBean().getNickname());
            if(mBalanceBean==null){
                holder.titlejifenText.setText("积分:"+LoginManage.getInstance().getLoginBean().getBalance());
            }else {
                holder.titlejifenText.setText("积分:"+mBalanceBean.getIntegral());
            }

            return;
        }
        if(position==mList.size()-1){
            holder.footerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInterface.onClickLogout();
                }
            });
            return;
        }

        holder.nameText.setText(mList.get(position).getTitle());
        holder.mImageView.setImageResource(mList.get(position).getImageId());
        holder.selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickItem(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //item的视图
        private TextView nameText;
        private ImageView mImageView;
        private View selfView;

        //headerView的视图
        private List<RelativeLayout> mRelativeLayouts = new ArrayList<>();
        private TextView titleNameText;
        private TextView titlejifenText;
        private ImageView headerImage;

        //footerView的视图
        private Button footerBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            if(headerView!=null&&headerView==itemView){

                RelativeLayout shouRelat = (RelativeLayout)itemView.findViewById(R.id.relative_person_shouchang);
                RelativeLayout jifenRelat = (RelativeLayout)itemView.findViewById(R.id.relative_person_jifen);
                RelativeLayout dingdanRelat = (RelativeLayout)itemView.findViewById(R.id.relative_person_dingdan);
                mRelativeLayouts.add(shouRelat);
                mRelativeLayouts.add(jifenRelat);
                mRelativeLayouts.add(dingdanRelat);
                titleNameText = (TextView)itemView.findViewById(R.id.text_person_headerName);
                titlejifenText = (TextView)itemView.findViewById(R.id.text_person_titlejifen);
                headerImage = (ImageView)itemView.findViewById(R.id.image_preson_headerIcon);

                return;
            }

            if(footerView!=null&&footerView==itemView){

                footerBtn = (Button)itemView.findViewById(R.id.button_person_logout);

                return;
            }
            selfView = itemView;
            nameText = (TextView)itemView.findViewById(R.id.text_person_itemName);
            mImageView = (ImageView)itemView.findViewById(R.id.image_person_item);
        }
    }
}
