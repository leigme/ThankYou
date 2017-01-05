package com.yhcloud.thankyou.module.propslist.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yhcloud.thankyou.module.propslist.bean.PropsListViewBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/5.
 */

public class PropsListViewAdapter extends PagerAdapter {

    private ArrayList<PropsListViewBean> mBeen;

    public PropsListViewAdapter(ArrayList<PropsListViewBean> list) {
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
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mBeen.get(position).getView());
        return mBeen.get(position).getView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mBeen.get(position).getView());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mBeen.get(position).getTitle();
    }
}
