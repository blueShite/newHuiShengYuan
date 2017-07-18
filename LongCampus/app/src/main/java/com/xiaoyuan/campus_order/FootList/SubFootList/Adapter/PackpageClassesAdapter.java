package com.xiaoyuan.campus_order.FootList.SubFootList.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.PackpageClassesBean;
import com.xiaoyuan.campus_order.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longhengyu on 2017/6/29.
 */

public class PackpageClassesAdapter extends RecyclerView.Adapter<PackpageClassesAdapter.ViewHolder> {

    private List<PackpageClassesBean> mList;
    private Context mContext;
    private ClassesInterface mInterface;

    public PackpageClassesAdapter(List<PackpageClassesBean> list,Context context,ClassesInterface anInterface){
        mList = list;
        mContext = context;
        mInterface = anInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_packpage_classes, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        PackpageClassesBean bean = mList.get(position);
        holder.mTextPackpageClassesName.setText(bean.getRes_name());
        if(bean.isSelect()){
            holder.mLayoutPakeBack.setBackgroundResource(R.color.colorAccent);
        }else {
            holder.mLayoutPakeBack.setBackgroundResource(R.color.ColorTransparent);
        }
        holder.mLayoutPakeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClickClassesItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_packpage_classes_name)
        TextView mTextPackpageClassesName;
        @BindView(R.id.layout_pake_back)
        LinearLayout mLayoutPakeBack;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ClassesInterface{
        void onClickClassesItem(int poist);
    }
}
