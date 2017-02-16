package com.yhcloud.thankyou.module.todayrecipes.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.module.todayrecipes.adapter.TodayRecipesAdapter;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesBean;
import com.yhcloud.thankyou.utils.PinnedDivider;

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

    public View TodayRecipesFragment(int layoutId, int recyclerId, ArrayList<TodayRecipesBean> list) {
        View view = mInflater.inflate(layoutId, null);
        TextView mTextView = (TextView) view.findViewById(R.id.tv_todayrecipes_default);
        mTextView.setVisibility(View.GONE);
        final RecyclerView mRecyclerView = (RecyclerView) view.findViewById(recyclerId);
        mRecyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        PinnedDivider pinnedDivider = new PinnedDivider.Builder(mContext, list)
                .tagHeight(R.dimen.my_height)
                .tagBgColor(R.color.colorWhite)
                .build();
        mRecyclerView.addItemDecoration(pinnedDivider);
        TodayRecipesAdapter adapter = new TodayRecipesAdapter(mContext, list);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    public View TodayRecipesFragment(int layoutId, int recyclerId) {
        View view = mInflater.inflate(layoutId, null);
        TextView mTextView = (TextView) view.findViewById(R.id.tv_todayrecipes_default);
        mTextView.setVisibility(View.VISIBLE);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(recyclerId);
        mRecyclerView.setVisibility(View.GONE);
        return view;
    }
}
