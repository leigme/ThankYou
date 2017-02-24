package com.yhcloud.thankyou.module.todayrecipes.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterfacea.IOnClickListener;
import com.yhcloud.thankyou.module.todayrecipes.bean.RecipesBean;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesBean;
import com.yhcloud.thankyou.module.todayrecipes.view.TodayRecipesInfoActivity;

import java.util.ArrayList;

/**
 * Created by leig on 2017/2/9.
 */

public class TodayRecipesAdapter extends RecyclerView.Adapter<TodayRecipesAdapter.RecipesViewHolder> {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TodayRecipesBean> mBeen;

    public TodayRecipesAdapter(Context context, ArrayList<TodayRecipesBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_todayrecipesday_list, parent, false);
        RecipesViewHolder recipesViewHolder = new RecipesViewHolder(view);
        return recipesViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        final ArrayList<RecipesBean> list = mBeen.get(position).getBeen();
        holder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        switch (mBeen.get(position).getTag()) {
            case "早餐":
                holder.mRecyclerView.setBackgroundColor(0xFFFFEFFA);
                break;
            case "中餐":
                holder.mRecyclerView.setBackgroundColor(0xFFFFFBE4);
                break;
            case "晚餐":
                holder.mRecyclerView.setBackgroundColor(0xFFF5F9FF);
                break;
            default:
                break;
        }
        RecipesListAdapter rla = new RecipesListAdapter(mContext, list);
        rla.setIOnClickListener(new IOnClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                Intent intent = new Intent(mContext, TodayRecipesInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Recipes", list.get(position));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });
        holder.mRecyclerView.setAdapter(rla);
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    public static class RecipesViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;
        public RecipesViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_todayrecipesday_list);
        }
    }
}
