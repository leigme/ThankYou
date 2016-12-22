package com.yhcloud.thankyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.TeacherBean;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/22.
 */

public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListAdapter.TeacherViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TeacherBean> mBeen;

    public TeacherListAdapter(Context context, ArrayList<TeacherBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_class_teacher_list, parent, false);
        TeacherViewHolder viewHolder = new TeacherViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, int position) {
        Tools.GlideImageUrl(mContext, mBeen.get(position).getHeadImageURL(), holder.ivHeader);
        holder.tvName.setText(mBeen.get(position).getRealName());
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHeader;
        TextView tvName;
        public TeacherViewHolder(View itemView) {
            super(itemView);
            ivHeader = (ImageView) itemView.findViewById(R.id.iv_class_teacher_header_image);
            tvName = (TextView) itemView.findViewById(R.id.tv_class_teacher_name);
        }
    }
}
