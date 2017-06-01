package com.yhcloud.thankyou.module.todayrecipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.module.todayrecipes.bean.RecipesBean;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

/**
 * Created by leig on 2017/2/14.
 */

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipesListViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<RecipesBean> mBeen;
    private ItemClinkListener mItemClinkListener;

    public RecipesListAdapter(Context context, ArrayList<RecipesBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public RecipesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_todayrecipes_list, parent, false);
        RecipesListViewHolder viewHolder = new RecipesListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecipesListViewHolder holder, int position) {
        Tools.GlideImageUrl(mContext, mBeen.get(position).getImageUrl(), holder.ivImage);
        holder.tvTitle.setText(mBeen.get(position).getTitle());
        if (null != mItemClinkListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mItemClinkListener.OnItemClickListener(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    public void setIOnClickListener(ItemClinkListener itemClinkListener) {
        this.mItemClinkListener = itemClinkListener;
    }

    public static class RecipesListViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivImage;
        public RecipesListViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_todayrecipes_title);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_todayrecipes);
        }
    }
}
