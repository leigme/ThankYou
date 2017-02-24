package com.yhcloud.thankyou.module.homework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterfacea.IOnClickListener;
import com.yhcloud.thankyou.module.homework.bean.QuestionBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/12.
 */

public class StudentHomeworkRadioListAdpater extends RecyclerView.Adapter<StudentHomeworkRadioListAdpater.StudentQuestionViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<QuestionBean> mBeen;
    private IOnClickListener mIOnClickListener;

    public StudentHomeworkRadioListAdpater(Context context, ArrayList<QuestionBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public StudentQuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_homeworkinfo_radio_list, parent, false);
        StudentQuestionViewHolder viewHolder = new StudentQuestionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final StudentQuestionViewHolder holder, int position) {
        if (mBeen.get(position).isStatus()) {
            holder.mImageView.setImageResource(R.mipmap.chose_on);
        } else {
            holder.mImageView.setImageResource(R.mipmap.chose_off);
        }
        holder.mTextView.setText(mBeen.get(position).getQuestionTitle());
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

    public void refreshData(ArrayList<QuestionBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(IOnClickListener iOnClickListener) {
        this.mIOnClickListener = iOnClickListener;
    }

    public static class StudentQuestionViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;
        public StudentQuestionViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_homeworkinfo_radio_title);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_homeworkinfo_list_chose);
        }
    }
}
