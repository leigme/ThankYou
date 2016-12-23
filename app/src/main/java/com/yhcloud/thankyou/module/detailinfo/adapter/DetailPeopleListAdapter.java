package com.yhcloud.thankyou.module.detailinfo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.RelativeInfoBean;
import com.yhcloud.thankyou.mInterface.IOnClickListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/22.
 */

public class DetailPeopleListAdapter extends RecyclerView.Adapter<DetailPeopleListAdapter.PeopleViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<RelativeInfoBean> mBeen;
    private IOnClickListener mIOnClickListener;

    public DetailPeopleListAdapter(Context context, ArrayList<RelativeInfoBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_detail_people_list, parent, false);
        PeopleViewHolder viewHolder = new PeopleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PeopleViewHolder holder, int position) {
        if (null != mBeen.get(position).getRelativesRealName() && !"".equals(mBeen.get(position).getRelativesRealName())) {
            holder.llDetailItem.setVisibility(View.VISIBLE);
            holder.tvName.setText(mBeen.get(position).getRelativesRealName());
        } else {
            holder.llDetailItem.setVisibility(View.GONE);
        }
        if (null != mIOnClickListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mIOnClickListener.OnItemClickListener(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    public void setIOnClickListener(IOnClickListener iOnClickListener) {
        this.mIOnClickListener = iOnClickListener;
    }

    public void refreshData(ArrayList<RelativeInfoBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public static class PeopleViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llDetailItem;
        TextView tvName;
        public PeopleViewHolder(View itemView) {
            super(itemView);
            llDetailItem = (LinearLayout) itemView.findViewById(R.id.ll_detail_item);
            tvName = (TextView) itemView.findViewById(R.id.tv_detail_item_name);
        }
    }
}
