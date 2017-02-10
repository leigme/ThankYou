package com.yhcloud.thankyou.module.todayrecipes.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.todayrecipes.adapter.TodayRecipesAdapter;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesBean;

import java.util.ArrayList;

/**
 * Created by leig on 2017/2/9.
 */

public class TodayRecipesFragmentViews {

    private Context mContext;
    private LayoutInflater mInflater;

    public TodayRecipesFragmentViews(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public View TodayRecipesFragment(int layoutId, int recyclerId, ArrayList<TodayRecipesBean> list, IOnClickListener iOnClickListener) {
        View view = mInflater.inflate(layoutId, null);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(recyclerId);
        TodayRecipesAdapter adapter = new TodayRecipesAdapter(mContext, list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter.setIOnClickListener(iOnClickListener);
        mRecyclerView.setAdapter(adapter);
        return view;
    }
}
