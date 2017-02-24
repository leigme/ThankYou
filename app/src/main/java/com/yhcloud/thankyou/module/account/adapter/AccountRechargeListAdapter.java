package com.yhcloud.thankyou.module.account.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterfacea.IOnClickListener;
import com.yhcloud.thankyou.module.account.bean.AccountRechargeBean;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountRechargeListAdapter extends RecyclerView.Adapter<AccountRechargeListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<AccountRechargeBean> mBeen;
    private LayoutInflater mInflater;
    private IOnClickListener mIOnClickListener;

    public AccountRechargeListAdapter(Context context, ArrayList<AccountRechargeBean> list) {
        this.mContext = context;
        this.mBeen = list;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recharge_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        AccountRechargeBean bean = mBeen.get(position);
        BigDecimal BigPayMoney = new BigDecimal(Double.parseDouble(bean.getMoney()) * Double.parseDouble(bean.getDiscount()));
        double payMoney = BigPayMoney.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        holder.tvItemRechargeCoin.setText(mBeen.get(position).getCoin());
        holder.tvItemRechargeMoney.setText(String.valueOf(payMoney));
        if (0.0 == Double.parseDouble(mBeen.get(position).getDiscount())) {
            holder.llItemRechargDiscount.setVisibility(View.GONE);
        } else {
            holder.llItemRechargDiscount.setVisibility(View.VISIBLE);
            holder.tvItemRechargeDiscount.setText(mBeen.get(position).getMoney());
            holder.tvItemRechargeDiscount.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        }
        if (!mBeen.get(position).isSelected()) {
            holder.llItemRechargebg.setBackgroundResource(R.mipmap.bg_recharge_item_un);
        } else {
            holder.llItemRechargebg.setBackgroundResource(R.mipmap.bg_recharge_item);
        }
        if (null != mIOnClickListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

    public void setIOnClickListener(IOnClickListener iOnClickListener) {
        this.mIOnClickListener = iOnClickListener;
    }

    public void refreshData(ArrayList<AccountRechargeBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llItemRechargebg, llItemRechargDiscount;
        TextView tvItemRechargeCoin, tvItemRechargeMoney, tvItemRechargeDiscount;
        public MyViewHolder(View itemView) {
            super(itemView);
            llItemRechargebg = (LinearLayout) itemView.findViewById(R.id.ll_item_recharge);
            tvItemRechargeCoin = (TextView) itemView.findViewById(R.id.tv_item_recharge_coin);
            tvItemRechargeMoney = (TextView) itemView.findViewById(R.id.tv_item_recharge_money);
            llItemRechargDiscount = (LinearLayout) itemView.findViewById(R.id.ll_item_recharge_discount);
            tvItemRechargeDiscount = (TextView) itemView.findViewById(R.id.tv_item_recharge_discount);
        }
    }
}
