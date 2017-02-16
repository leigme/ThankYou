package com.yhcloud.thankyou.module.todayrecipes.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesPagerBean;

import java.util.ArrayList;

/**
 * Created by leig on 2017/2/10.
 */

public class TodayRecipesPagerAdapter extends PagerAdapter {

    private ArrayList<TodayRecipesPagerBean> mBeen;

    public TodayRecipesPagerAdapter(ArrayList<TodayRecipesPagerBean> list) {
        this.mBeen = list;
    }

    @Override
    public int getCount() {
        return mBeen.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mBeen.get(position).getTitle();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mBeen.get(position).getView());
        return mBeen.get(position).getView();
    }

    @Override
    public int getItemPosition(Object object) {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mBeen.get(position).getView());
    }

    public void refreshData(ArrayList<TodayRecipesPagerBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }
}
