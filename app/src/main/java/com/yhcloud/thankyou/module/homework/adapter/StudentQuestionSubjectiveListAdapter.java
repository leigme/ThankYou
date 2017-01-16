package com.yhcloud.thankyou.module.homework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/12.
 */

public class StudentQuestionSubjectiveListAdapter extends RecyclerView.Adapter<StudentQuestionSubjectiveListAdapter.SubjectiveViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> urls;
    private IOnClickListener mIOnClickListener;

    public StudentQuestionSubjectiveListAdapter(Context context, ArrayList<String> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.urls = list;
    }

    @Override
    public SubjectiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_homeworkinfo_question_image, parent, false);
        SubjectiveViewHolder viewHolder = new SubjectiveViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubjectiveViewHolder holder, int position) {
//        Tools.GlideImageUrl(mContext, urls.get(position), holder.ivImage);
        Glide.with(mContext)
                .load(urls.get(position))
                .into(holder.ivImage);
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
        return urls.size();
    }

    public void refreshData(ArrayList<String> list) {
        this.urls = list;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(IOnClickListener iOnClickListener) {
        this.mIOnClickListener = iOnClickListener;
    }

    public static class SubjectiveViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        public SubjectiveViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_homeworkinfo_question_image);
        }
    }
}
