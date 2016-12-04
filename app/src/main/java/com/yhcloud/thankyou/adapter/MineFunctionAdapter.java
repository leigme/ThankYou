package com.yhcloud.thankyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.MineFunctionBean;
import com.yhcloud.thankyou.mInterface.IOnClickListener;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public class MineFunctionAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<MineFunctionBean> mBeen;
    private IOnClickListener mIOnClickListener;
    private static final int TYPE_DATA = 1;
    private static final int TYPE_LINE = 0;

    public MineFunctionAdapter(Context context, ArrayList<MineFunctionBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (TYPE_DATA == viewType) {
            view = mInflater.inflate(R.layout.item_mine_function, parent, false);
            return new MyViewHolder(view);
        } else if (TYPE_LINE == viewType) {
            view = mInflater.inflate(R.layout.item_dividing_line, parent, false);
            return new LineViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (null != mIOnClickListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mIOnClickListener.OnItemClickListener(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 2:
                return TYPE_LINE;
            case 7:
                return TYPE_LINE;
            case 9:
                return TYPE_LINE;
            default:
                return TYPE_DATA;
        }
    }

    public void setIOnClickListener(IOnClickListener iOnClickListener) {
        this.mIOnClickListener = iOnClickListener;
    }

    public void refreshData(ArrayList<MineFunctionBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class LineViewHolder extends RecyclerView.ViewHolder {
        public LineViewHolder(View itemView) {
            super(itemView);
        }
    }
}
