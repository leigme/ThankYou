package com.yhcloud.thankyou.module.homework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.yhcloud.thankyou.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/20.
 */

public class TeacherHomeworkObjectiveListAdapter extends RecyclerView.Adapter<TeacherHomeworkObjectiveListAdapter.ObjcetiveViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mArrayList;

    public TeacherHomeworkObjectiveListAdapter(Context context, ArrayList<String> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mArrayList = list;
    }

    @Override
    public ObjcetiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_homeworkinfo_radio_list, parent, false);
        ObjcetiveViewHolder viewHolder = new ObjcetiveViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ObjcetiveViewHolder holder, int position) {
        holder.mImageView.setVisibility(View.GONE);
        holder.mTextView.setText(mArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class ObjcetiveViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        public ObjcetiveViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_homeworkinfo_list_chose);
            mTextView = (TextView) itemView.findViewById(R.id.tv_homeworkinfo_radio_title);
        }
    }
}
