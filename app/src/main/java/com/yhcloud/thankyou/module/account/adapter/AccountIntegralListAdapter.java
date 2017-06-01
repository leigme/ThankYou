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
import com.yhcloud.thankyou.module.account.bean.AccountIntegralBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountIntegralListAdapter extends RecyclerView.Adapter<AccountIntegralListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<AccountIntegralBean> mBeen;
    private LayoutInflater mInflater;
    private ItemClinkListener mItemClinkListener;

    public AccountIntegralListAdapter(Context context, ArrayList<AccountIntegralBean> list) {
        this.mContext = context;
        this.mBeen = list;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_integral_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvItemIntegralNum.setText(mBeen.get(position).getCoin());
        holder.tvItemIntegralCoin.setText(mBeen.get(position).getMoney());
        if (!mBeen.get(position).isSelected()) {
            holder.llItemIntegralbg.setBackgroundResource(R.mipmap.bg_recharge_item_un);
        } else {
            holder.llItemIntegralbg.setBackgroundResource(R.mipmap.bg_recharge_item);
        }
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

    public void setIOnClickListener(ItemClinkListener itemClinkListener) {
        this.mItemClinkListener = itemClinkListener;
    }

    public void refreshData(ArrayList<AccountIntegralBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llItemIntegralbg;
        TextView tvItemIntegralNum, tvItemIntegralCoin;
        public MyViewHolder(View itemView) {
            super(itemView);
            llItemIntegralbg = (LinearLayout) itemView.findViewById(R.id.ll_item_integral);
            tvItemIntegralNum = (TextView) itemView.findViewById(R.id.tv_item_integral_num);
            tvItemIntegralCoin = (TextView) itemView.findViewById(R.id.tv_item_integral_coin);
        }
    }
}
