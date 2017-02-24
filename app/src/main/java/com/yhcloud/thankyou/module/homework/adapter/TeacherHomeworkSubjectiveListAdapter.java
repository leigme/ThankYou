package com.yhcloud.thankyou.module.homework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterfacea.IOnClickListener;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/20.
 */

public class TeacherHomeworkSubjectiveListAdapter extends RecyclerView.Adapter<TeacherHomeworkSubjectiveListAdapter.SubjectiveViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mArrayList;
    private IOnClickListener mIOnClickListener;

    public TeacherHomeworkSubjectiveListAdapter(Context context, ArrayList<String> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mArrayList = list;
    }

    @Override
    public SubjectiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_homeworkinfo_question_image, parent, false);
        SubjectiveViewHolder viewHolder = new SubjectiveViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubjectiveViewHolder holder, int position) {
        Tools.GlideImageUrl(mContext, mArrayList.get(position), holder.mImageView);
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
        return mArrayList.size();
    }

    public void refreshData(ArrayList<String> list) {
        this.mArrayList = list;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(IOnClickListener IOnClickListener) {
        this.mIOnClickListener = IOnClickListener;
    }

    public static class SubjectiveViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        public SubjectiveViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_homework_image);
        }
    }
}
