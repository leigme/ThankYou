package com.yhcloud.thankyou.module.homework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/12.
 */

public class StudentHomeworkSubjectiveListAdapter extends RecyclerView.Adapter<StudentHomeworkSubjectiveListAdapter.SubjectiveViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> urls;
    private ItemClinkListener mItemClinkListener;

    public StudentHomeworkSubjectiveListAdapter(Context context, ArrayList<String> list) {
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
        Tools.GlideImageUrl(mContext, urls.get(position), holder.ivImage);
//        Glide.with(mContext)
//                .load(urls.get(position))
//                .placeholder(R.mipmap.loading)
//                .error(R.mipmap.icon_big_404)
//                .into(holder.ivImage);
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
        return urls.size();
    }

    public void refreshData(ArrayList<String> list) {
        this.urls = list;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(ItemClinkListener itemClinkListener) {
        this.mItemClinkListener = itemClinkListener;
    }

    public static class SubjectiveViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        public SubjectiveViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_homeworkinfo_question_image);
        }
    }
}
