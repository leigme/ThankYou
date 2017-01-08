package com.yhcloud.thankyou.module.classnotification.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.classnotification.bean.ClassNotificationBean;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/6.
 */

public class ClassNotificationListAdapter extends RecyclerView.Adapter<ClassNotificationListAdapter.ClassNotificationViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ClassNotificationBean> mBeen;
    private IOnClickListener mIOnClickListener;

    public ClassNotificationListAdapter(Context context, ArrayList<ClassNotificationBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public ClassNotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_classnotification_list, parent, false);
        ClassNotificationViewHolder viewHolder = new ClassNotificationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ClassNotificationViewHolder holder, int position) {
        ClassNotificationBean cnb = mBeen.get(position);
        holder.tvTitle.setText(cnb.getTitle());
        holder.tvContent.setText(cnb.getContent());
        String time = "";
        if (null != cnb.getCreateTime() && cnb.getCreateTime().length() > 10) {
            time = cnb.getCreateTime().substring(0, 10);
        }
        String info = MessageFormat.format("发布人: {0} 时间: {1}", cnb.getPublishUserName(), time);
        holder.tvInfo.setText(info);
        if ("0".equals(cnb.getIsRead())) {
            holder.ivNew.setVisibility(View.VISIBLE);
        } else {
            holder.ivNew.setVisibility(View.GONE);
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

    public void refreshData(ArrayList<ClassNotificationBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(IOnClickListener iOnClickListener) {
        this.mIOnClickListener = iOnClickListener;
    }

    public static class ClassNotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvInfo;
        ImageView ivNew;
        public ClassNotificationViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_classnotification_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_classnotification_content);
            tvInfo = (TextView) itemView.findViewById(R.id.tv_classnotification_info);
            ivNew = (ImageView) itemView.findViewById(R.id.iv_classnotification_new);
        }
    }
}
