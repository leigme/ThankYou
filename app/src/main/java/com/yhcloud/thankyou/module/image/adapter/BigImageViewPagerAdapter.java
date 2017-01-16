package com.yhcloud.thankyou.module.image.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yhcloud.thankyou.module.image.bean.PhotoBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/13.
 */

public class BigImageViewPagerAdapter extends PagerAdapter {

    private ArrayList<View> mBeen;

    public BigImageViewPagerAdapter(ArrayList<View> list) {
        this.mBeen = list;
    }

    @Override
    public int getCount() {
        return mBeen.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mBeen.get(position));
        return mBeen.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mBeen.get(position));
    }
}
