package com.yhcloud.thankyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.mInterfacea.IOnClickListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/21.
 */

public class PopupmenuListAdapter extends RecyclerView.Adapter<PopupmenuListAdapter.PopupmenuViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<FunctionBean> mBeen;
    private IOnClickListener mIOnClickListener;

    public PopupmenuListAdapter(Context context, ArrayList<FunctionBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public PopupmenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_popupmenu_list, parent, false);
        PopupmenuViewHolder viewHolder = new PopupmenuViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PopupmenuViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mBeen.get(position).getIcon())
                .into(holder.ivImage);
        holder.tvTitle.setText(mBeen.get(position).getTitle());
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

    public void setIOnClickListener(IOnClickListener iOnClickListener) {
        this.mIOnClickListener = iOnClickListener;
    }

    public static class PopupmenuViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        public PopupmenuViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_popupmenu_list_image);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_popupmenu_list_title);
        }
    }
}
