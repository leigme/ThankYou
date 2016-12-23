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
import com.yhcloud.thankyou.module.curriculum.bean.CurriculumBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/14.
 */

public class CurriculumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<CurriculumBean> mBeen;
    private int TYPE_ITEM = 1, TYPE_LINE = 0;

    public CurriculumAdapter(Context context, ArrayList<CurriculumBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = mInflater.inflate(R.layout.item_curriculum_line_list, parent, false);
                LineViewHolder lineViewHolder = new LineViewHolder(view);
                return lineViewHolder;
            case 1:
                view = mInflater.inflate(R.layout.item_curriculum_list, parent, false);
                ItemViewHolder itemViewHolder = new ItemViewHolder(view);
                return itemViewHolder;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CurriculumBean curriculumBean = mBeen.get(position);
        String startTime = curriculumBean.getStartTime();
        String endTime = curriculumBean.getEndTime();
        switch (getItemViewType(position)) {
            case 1:
                ItemViewHolder viewHolder = (ItemViewHolder) holder;
                TextPaint tp = viewHolder.title.getPaint();
                if (!curriculumBean.isHeader()) {
                    tp.setFakeBoldText(false);
                    if ("".equals(curriculumBean.getStartTime()) && "".equals(curriculumBean.getEndTime())) {
                        if (curriculumBean.isDay()) {
                            viewHolder.title.setTextColor(Color.parseColor("#f56814"));
                        } else {
                            viewHolder.title.setTextColor(Color.parseColor("#666666"));
                        }
                    } else {
                        viewHolder.title.setTextColor(Color.parseColor("#666666"));
                    }
                    viewHolder.title.setText(curriculumBean.getTitle());
                    if (null != startTime && 5 < startTime.length() && null != endTime && 5 < endTime.length()) {
                        viewHolder.startTime.setVisibility(View.VISIBLE);
                        viewHolder.startTime.setText(startTime.substring(0, 5));
                        viewHolder.endTime.setVisibility(View.VISIBLE);
                        viewHolder.endTime.setText(endTime.substring(0, 5));
                    } else {
                        viewHolder.startTime.setVisibility(View.INVISIBLE);
                        viewHolder.startTime.setText("");
                        viewHolder.endTime.setVisibility(View.GONE);
                        viewHolder.endTime.setText("");
                    }
                } else {
                    tp.setFakeBoldText(true);
                    viewHolder.title.setTextColor(Color.parseColor("#f56814"));
                    viewHolder.title.setText(curriculumBean.getDayTitle());
                    viewHolder.startTime.setVisibility(View.GONE);
                    viewHolder.startTime.setText("");
                    viewHolder.endTime.setVisibility(View.GONE);
                    viewHolder.endTime.setText("");
                }
                break;
            case 0:
                LineViewHolder lineViewHolder = (LineViewHolder) holder;
                lineViewHolder.title.setText(curriculumBean.getTitle());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 36:
                return TYPE_LINE;
            case 37:
                return TYPE_LINE;
            case 38:
                return TYPE_LINE;
            case 39:
                return TYPE_LINE;
            case 40:
                return TYPE_LINE;
            case 41:
                return TYPE_LINE;
            default:
                return TYPE_ITEM;
        }
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

    public static class LineViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public LineViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_curriculum_title);
        }
    }
}
