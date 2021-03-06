package com.yhcloud.thankyou.module.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.module.account.bean.AccountFunctionBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/25.
 */

public class AccountFunctionAdapter extends RecyclerView.Adapter<AccountFunctionAdapter.MyViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<AccountFunctionBean> mBeen;
    private ItemClinkListener mItemClinkListener;

    public AccountFunctionAdapter(Context context, ArrayList<AccountFunctionBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_account_function, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mLinearLayout.setBackgroundResource(mBeen.get(position).getBgResId());
        holder.mTextView.setText(mBeen.get(position).getTitle());
        if (null != mItemClinkListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mItemClinkListener.OnItemClickListener(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.ll_background);
            mTextView = (TextView) itemView.findViewById(R.id.tv_account_function_title);
        }
    }

    public void setIOnClickListener(ItemClinkListener onClickListener) {
        this.mItemClinkListener = onClickListener;
    }

    public void refreshData(ArrayList<AccountFunctionBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }
}
