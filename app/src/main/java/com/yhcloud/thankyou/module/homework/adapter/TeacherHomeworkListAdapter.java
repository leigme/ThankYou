package com.yhcloud.thankyou.module.homework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.module.homework.bean.TeacherHomeworkBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/11.
 */

public class TeacherHomeworkListAdapter extends RecyclerView.Adapter<TeacherHomeworkListAdapter.HomeworkViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TeacherHomeworkBean> mBeen;
    private ItemClinkListener mItemClinkListener;

    public TeacherHomeworkListAdapter(Context context, ArrayList<TeacherHomeworkBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public HomeworkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_homework_list, parent, false);
        HomeworkViewHolder viewHolder = new HomeworkViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeworkViewHolder holder, int position) {
        TeacherHomeworkBean teacherHomeworkBean = mBeen.get(position);
        holder.tvImgSubject.setText(teacherHomeworkBean.getSubject());
        holder.tvTitle.setText(teacherHomeworkBean.getTitle());
        holder.tvSubject.setText(teacherHomeworkBean.getSubject());
        holder.tvTime.setText(teacherHomeworkBean.getCreateTime());
        holder.tvInfo.setText(teacherHomeworkBean.getClassName());
        //2 已发布
        if ("2".equals(teacherHomeworkBean.getStatus())) {
            holder.ivStatus.setImageResource(R.mipmap.icon_published);
            holder.btnSend.setVisibility(View.INVISIBLE);
        } else {
            holder.ivStatus.setImageResource(R.mipmap.icon_unpublished);
            holder.btnSend.setVisibility(View.VISIBLE);
        }
        if (null != mItemClinkListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mItemClinkListener.OnItemClickListener(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    public void refreshData(ArrayList<TeacherHomeworkBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(ItemClinkListener itemClinkListener) {
        this.mItemClinkListener = itemClinkListener;
    }

    public static class HomeworkViewHolder extends RecyclerView.ViewHolder {
        TextView tvImgSubject, tvTitle, tvSubject, tvTime, tvInfoTitle, tvInfo;
        ImageView ivStatus;
        Button btnSend;
        public HomeworkViewHolder(View itemView) {
            super(itemView);
            tvImgSubject = (TextView) itemView.findViewById(R.id.tv_homework_img_subject);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_homework_title);
            tvSubject = (TextView) itemView.findViewById(R.id.tv_homework_subject);
            tvTime = (TextView) itemView.findViewById(R.id.tv_homework_createtime);
            tvInfoTitle = (TextView) itemView.findViewById(R.id.tv_homework_info_title);
            tvInfo = (TextView) itemView.findViewById(R.id.tv_homework_info);
            ivStatus = (ImageView) itemView.findViewById(R.id.iv_status);
            btnSend = (Button) itemView.findViewById(R.id.btn_send);
        }
    }
}
