package com.xiaoyuan.campus_order.PersonSubs.Integral.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoyuan.campus_order.PersonSubs.Integral.Bean.IntegralBean;
import com.xiaoyuan.campus_order.R;

import java.util.List;

/**
 * Created by longhengyu on 2017/6/21.
 */

public class IntegralAdapter extends RecyclerView.Adapter<IntegralAdapter.ViewHolder> {

    private List<IntegralBean> mList;
    private View headerView;
    private String integralString;
    private Context mContext;

    public IntegralAdapter(List<IntegralBean> list,String string,Context context){
        mList = list;
        integralString = string;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {

        if(position==0){
            return 0;
        }
        return 1;
    }

    public void reloadIntegral(String integralString){
        this.integralString = integralString;
        notifyItemChanged(0);
    }

    @Override
    public IntegralAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==0){
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_integral_header,parent,false);
            ViewHolder viewHolder = new ViewHolder(headerView);
            return viewHolder;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_integral,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IntegralAdapter.ViewHolder holder, int position) {

        if(position==0){
            holder.headerText.setText(integralString);
            return;
        }

        IntegralBean bean = mList.get(position-1);
        holder.timeText.setText(bean.getDate());
        holder.integralText.setText(bean.getIntegral());
        holder.typeText.setText(bean.getDish());

    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //headerView
        private TextView headerText;

        //itemView
        private TextView timeText;
        private TextView integralText;
        private TextView typeText;

        public ViewHolder(View itemView) {
            super(itemView);
            if(headerView!=null&&itemView==headerView){
                headerText = (TextView) itemView.findViewById(R.id.text_header_integral);
                return;
            }
            timeText = (TextView) itemView.findViewById(R.id.text_item_integral_time);
            integralText = (TextView) itemView.findViewById(R.id.text_item_integral);
            typeText = (TextView) itemView.findViewById(R.id.text_item_integral_type);
        }
    }
}
