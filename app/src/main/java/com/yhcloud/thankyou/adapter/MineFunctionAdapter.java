package com.yhcloud.thankyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.mInterfacea.IOnClickListener;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public class MineFunctionAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<FunctionBean> mBeen;
    private IOnClickListener mIOnClickListener;
    private static final int TYPE_DATA = 1;
    private static final int TYPE_LINE = 0;

    public MineFunctionAdapter(Context context, ArrayList<FunctionBean> list) {
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
        if (TYPE_DATA == getItemViewType(position)) {
            final MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.ivImage.setImageResource(mBeen.get(position).getIcon());
            viewHolder.tvTitle.setText(mBeen.get(position).getTitle());
            if (null != mIOnClickListener) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = viewHolder.getLayoutPosition();
                        mIOnClickListener.OnItemClickListener(viewHolder.itemView, pos);
                    }
                });
            }
        } else if (TYPE_LINE == getItemViewType(position)) {

        }
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (mBeen.get(position).getId()) {
            case -1:
                return TYPE_LINE;
            default:
                return TYPE_DATA;
        }
    }

    public void setIOnClickListener(IOnClickListener iOnClickListener) {
        this.mIOnClickListener = iOnClickListener;
    }

    public void refreshData(ArrayList<FunctionBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_mine_function_image);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_mine_function_title);
        }
    }

    public class LineViewHolder extends RecyclerView.ViewHolder {
        public LineViewHolder(View itemView) {
            super(itemView);
        }
    }
}
