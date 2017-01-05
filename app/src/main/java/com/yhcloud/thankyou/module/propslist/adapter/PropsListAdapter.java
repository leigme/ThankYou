package com.yhcloud.thankyou.module.propslist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.module.propslist.bean.PropsListBean;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/5.
 */

public class PropsListAdapter extends RecyclerView.Adapter<PropsListAdapter.PropsListViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<PropsListBean> mBeen;
    private int mListType;

    public PropsListAdapter(Context context, ArrayList<PropsListBean> list, int typeId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
        this.mListType = typeId;
    }

    @Override
    public PropsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_props_list, parent, false);
        PropsListViewHolder viewHolder = new PropsListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PropsListViewHolder holder, int position) {
        PropsListBean plb = mBeen.get(position);
        String content;
        switch (mListType) {
            case 1:
                content = MessageFormat.format("{0} 送给 {1} {2}X{3}", plb.getSendRealName(), plb.getRecvRealName(), plb.getPropName(), plb.getNum());
                break;
            case 2:
                content = MessageFormat.format("{0} 收到 {1} 赠送的 {2}X{3}", plb.getRecvRealName(), plb.getSendRealName(), plb.getPropName(), plb.getNum());
                break;
            default:
                content = "null";
                break;
        }
        holder.tvContent.setText(content);
        holder.tvTime.setText(plb.getCreateTime());
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    public void refreshData(ArrayList<PropsListBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public static class PropsListViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent, tvTime;
        public PropsListViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_propslist_content);
            tvTime = (TextView) itemView.findViewById(R.id.tv_propslist_time);
        }
    }
}
