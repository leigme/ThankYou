package com.yhcloud.thankyou.module.classcadre.adapter;

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
 * Created by Administrator on 2016/12/23.
 */

public class ClassCadreListAdpater extends RecyclerView.Adapter<ClassCadreListAdpater.ClassCadreViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TeacherBean> mBeen;

    public ClassCadreListAdpater(Context context, ArrayList<TeacherBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public ClassCadreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_class_teacher_list, parent, false);
        ClassCadreViewHolder viewHolder = new ClassCadreViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClassCadreViewHolder holder, int position) {
        Tools.GlideCircleImageUrl(mContext, mBeen.get(position).getHeadImageURL(), holder.ivHeader);
        holder.tvNameTitle.setText("学生名字: ");
        holder.tvName.setText(mBeen.get(position).getRealName());
        holder.tvOffice.setText(mBeen.get(position).getRoles());
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    public void refreshData(ArrayList<TeacherBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public static class ClassCadreViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHeader;
        TextView tvNameTitle, tvName, tvOffice;
        public ClassCadreViewHolder(View itemView) {
            super(itemView);
            ivHeader = (ImageView) itemView.findViewById(R.id.iv_class_teacher_header_image);
            tvNameTitle = (TextView) itemView.findViewById(R.id.tv_class_teacher_name_title);
            tvName = (TextView) itemView.findViewById(R.id.tv_class_teacher_name);
            tvOffice = (TextView) itemView.findViewById(R.id.tv_class_teacher_office);
        }
    }
}
