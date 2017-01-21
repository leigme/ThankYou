package com.yhcloud.thankyou.module.homework.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yhcloud.thankyou.module.homework.bean.HomeworkInfoViewPagerBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/12.
 */

public class HomeworkInfoViewPagerAdapter extends PagerAdapter {

    private ArrayList<HomeworkInfoViewPagerBean> mBeen;

    public HomeworkInfoViewPagerAdapter(ArrayList<HomeworkInfoViewPagerBean> list) {
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
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mBeen.get(position).getView());
        return mBeen.get(position).getView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mBeen.get(position).getView());
    }

    public void refreshData(ArrayList<HomeworkInfoViewPagerBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }
}
