package com.yhcloud.thankyou.module.schoolannouncement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.minterface.IOnClickListener;
import com.yhcloud.thankyou.module.schoolannouncement.bean.SchoolAnnouncementBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/8.
 */

public class SchoolAnnouncementListAdapter extends RecyclerView.Adapter<SchoolAnnouncementListAdapter.SchoolAnnouncementViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<SchoolAnnouncementBean> mBeen;
    private IOnClickListener mIOnClickListener;

    public SchoolAnnouncementListAdapter(Context context, ArrayList<SchoolAnnouncementBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public SchoolAnnouncementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_schoolannouncement_list, parent, false);
        SchoolAnnouncementViewHolder viewHolder = new SchoolAnnouncementViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SchoolAnnouncementViewHolder holder, int position) {
        holder.tvTitle.setText(mBeen.get(position).getTitle());
        String strTime = mBeen.get(position).getCreateTime();
        if (null != strTime && 10 <= strTime.length()) {
            strTime = strTime.substring(0, 10);
        }
        holder.tvTime.setText(strTime);
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

    public void refreshData(ArrayList<SchoolAnnouncementBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(IOnClickListener iOnClickListener) {
        this.mIOnClickListener = iOnClickListener;
    }

    public static class SchoolAnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTime;
        ImageView ivNew;
        public SchoolAnnouncementViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_schoolannouncement_title);
            tvTime = (TextView) itemView.findViewById(R.id.tv_schoolannouncement_time);
            ivNew = (ImageView) itemView.findViewById(R.id.iv_schoolannouncement_new);
        }
    }
}
