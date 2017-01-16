package com.yhcloud.thankyou.module.homework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.homework.bean.StudentHomeworkBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/11.
 */

public class StudentHomeworkListAdapter extends RecyclerView.Adapter<StudentHomeworkListAdapter.StudentHomeworkViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<StudentHomeworkBean> mBeen;
    private IOnClickListener mIOnClickListener;

    public StudentHomeworkListAdapter(Context context, ArrayList<StudentHomeworkBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public StudentHomeworkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_homework_list, parent, false);
        StudentHomeworkViewHolder viewHolder = new StudentHomeworkViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final StudentHomeworkViewHolder holder, int position) {
        StudentHomeworkBean studentHomeworkBean = mBeen.get(position);
        holder.tvImgSubject.setText(studentHomeworkBean.getSubject());
        holder.tvTitle.setText(studentHomeworkBean.getTitle());
        holder.tvSubject.setText(studentHomeworkBean.getSubject());
        holder.tvTime.setText(studentHomeworkBean.getCreateTime());
        holder.tvInfoTitle.setText("发送人: ");
        holder.tvInfo.setText(studentHomeworkBean.getSendUser());
        if ("1".equals(studentHomeworkBean.getStatus())) {
            holder.ivStatus.setImageResource(R.mipmap.icon_submitted);
        } else {
            holder.ivStatus.setImageResource(R.mipmap.icon_unsubmitted);
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

    public void refreshData(ArrayList<StudentHomeworkBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(IOnClickListener iOnClickListener) {
        this.mIOnClickListener = iOnClickListener;
    }

    public static class StudentHomeworkViewHolder extends RecyclerView.ViewHolder {
        TextView tvImgSubject, tvTitle, tvSubject, tvTime, tvInfoTitle, tvInfo;
        ImageView ivStatus;
        public StudentHomeworkViewHolder(View itemView) {
            super(itemView);
            tvImgSubject = (TextView) itemView.findViewById(R.id.tv_homework_img_subject);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_homework_title);
            tvSubject = (TextView) itemView.findViewById(R.id.tv_homework_subject);
            tvTime = (TextView) itemView.findViewById(R.id.tv_homework_createtime);
            tvInfoTitle = (TextView) itemView.findViewById(R.id.tv_homework_info_title);
            tvInfo = (TextView) itemView.findViewById(R.id.tv_homework_info);
            ivStatus = (ImageView) itemView.findViewById(R.id.iv_status);
        }
    }
}
