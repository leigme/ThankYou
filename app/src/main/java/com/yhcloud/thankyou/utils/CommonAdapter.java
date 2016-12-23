package com.yhcloud.thankyou.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Leig on 2016/7/4.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int mLayoutId;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mLayoutId, position);
        convert(holder, mDatas.get(position));
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t);

    public void refreshData(List<T> datas) {
        this.mDatas = datas;
        this.notifyDataSetChanged();
    }
}
