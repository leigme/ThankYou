package com.yhcloud.thankyou.module.curriculum.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.module.curriculum.bean.CurriculumItemBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/14.
 */

public class CurriculumAdapter extends RecyclerView.Adapter<CurriculumAdapter.ItemViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<CurriculumItemBean> mBeen;

    public CurriculumAdapter(Context context, ArrayList<CurriculumItemBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_curriculum_list, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        CurriculumItemBean curriculumItemBean = mBeen.get(position);
        //画笔工具
        TextPaint tp = holder.title.getPaint();
        switch (curriculumItemBean.getType()) {
            case 1:
                holder.startTime.setVisibility(View.GONE);
                holder.endTime.setVisibility(View.GONE);
                holder.title.setTextColor(Color.parseColor("#f56814"));
                //设置为粗体
                tp.setFakeBoldText(true);
                holder.itemView.setBackgroundColor(Color.parseColor("#00000000"));
                break;
            case 2:
                holder.startTime.setVisibility(View.GONE);
                holder.endTime.setVisibility(View.GONE);
                //设置为粗体
                tp.setFakeBoldText(true);
                holder.itemView.setBackgroundColor(Color.parseColor("#f3f3f3"));
                break;
            default:
                String startTime = curriculumItemBean.getStartTime();
                String endTime = curriculumItemBean.getEndTime();
                if (null != startTime && 5 < startTime.length()) {
                    holder.startTime.setVisibility(View.VISIBLE);
                    holder.startTime.setText(startTime.substring(0, 5));
                } else {
                    holder.startTime.setVisibility(View.INVISIBLE);
                }
                if (null != endTime && 5 < endTime.length()) {
                    holder.endTime.setVisibility(View.VISIBLE);
                    holder.endTime.setText(endTime.substring(0, 5));
                } else {
                    holder.endTime.setVisibility(View.GONE);
                }
                holder.title.setTextColor(Color.parseColor("#666666"));
                //设置为正常
                tp.setFakeBoldText(false);
                holder.itemView.setBackgroundColor(Color.parseColor("#00000000"));
                break;
        }
        holder.title.setText(curriculumItemBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView startTime, title, endTime;
        public ItemViewHolder(View itemView) {
            super(itemView);
            startTime = (TextView) itemView.findViewById(R.id.tv_curriculum_starttime);
            title = (TextView) itemView.findViewById(R.id.tv_curriculum_title);
            endTime = (TextView) itemView.findViewById(R.id.tv_curriculum_endtime);
        }
    }
}
