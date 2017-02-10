package com.yhcloud.thankyou.module.todayrecipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesBean;

import java.util.ArrayList;

/**
 * Created by leig on 2017/2/9.
 */

public class TodayRecipesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TodayRecipesBean> mBeen;
    private IOnClickListener mIOnClickListener;

    public TodayRecipesAdapter(Context context, ArrayList<TodayRecipesBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = mInflater.inflate(R.layout.item_todayrecipes_list, parent, false);
                RecipesViewHolder recipesViewHolder = new RecipesViewHolder(view);
                return recipesViewHolder;
            default:
                view = mInflater.inflate(R.layout.item_todayrecipes_list_title, parent, false);
                TitleViewHolder titleViewHolder = new TitleViewHolder(view);
                return titleViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TodayRecipesBean todayRecipesBean = mBeen.get(position);
        switch (todayRecipesBean.getType()) {
            case 1:
                final RecipesViewHolder recipesViewHolder = (RecipesViewHolder) holder;
                Glide.with(mContext)
                        .load(todayRecipesBean.getImageUrl())
                        .into(recipesViewHolder.ivDish);
                recipesViewHolder.tvTitle.setText(todayRecipesBean.getTitle());
                recipesViewHolder.tvInfo.setText(todayRecipesBean.getInfo());
                if (null != mIOnClickListener) {
                    recipesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int pos = recipesViewHolder.getLayoutPosition();
                            mIOnClickListener.OnItemClickListener(recipesViewHolder.itemView, pos);
                        }
                    });
                }
                break;
            default:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                titleViewHolder.tvTitle.setText(todayRecipesBean.getTitle());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (mBeen.get(position).getType()) {
            case 1:
                return 1;
            default:
                return 0;
        }
    }

    public void setIOnClickListener(IOnClickListener IOnClickListener) {
        mIOnClickListener = IOnClickListener;
    }

    public static class RecipesViewHolder extends RecyclerView.ViewHolder {
        ImageView ivDish;
        TextView tvTitle, tvInfo;
        public RecipesViewHolder(View itemView) {
            super(itemView);
            ivDish = (ImageView) itemView.findViewById(R.id.iv_todayrecipes);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_todayrecipes_title);
            tvInfo = (TextView) itemView.findViewById(R.id.tv_todayrecipes_info);
        }
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        public TitleViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_todayrecipes_list_title);
        }
    }
}
