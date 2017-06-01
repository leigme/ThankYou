package com.yhcloud.thankyou.module.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.SubmitCallBack;
import com.yhcloud.thankyou.module.account.bean.AccountRechargePayBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/8.
 */

public class AccountRechargePayListAdapter extends RecyclerView.Adapter<AccountRechargePayListAdapter.MyViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<AccountRechargePayBean> mBeen;
    private SubmitCallBack mSubmitCallBack;

    public AccountRechargePayListAdapter(Context context, ArrayList<AccountRechargePayBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_dialog_recharge_pay_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        AccountRechargePayBean bean = mBeen.get(position);

        holder.ivImage.setImageResource(bean.getIconId());
        holder.tvTitle.setText(bean.getPay_name());
        if (bean.isSelected()) {
            holder.ivSelected.setVisibility(View.VISIBLE);
        } else {
            holder.ivSelected.setVisibility(View.INVISIBLE);
        }
        if (null != mSubmitCallBack) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mSubmitCallBack.OnItemClickListener(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    public void refreshData(ArrayList<AccountRechargePayBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(SubmitCallBack submitCallBack) {
        this.mSubmitCallBack = submitCallBack;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage, ivSelected;
        TextView tvTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_item_dialog_recharge_pay_iamge);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_item_dialog_recharge_pay_title);
            ivSelected = (ImageView) itemView.findViewById(R.id.iv_item_dialog_recharge_pay_selected);
        }
    }
}
