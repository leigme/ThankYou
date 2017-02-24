package com.yhcloud.thankyou.module.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.minterface.IOnClickListener;
import com.yhcloud.thankyou.module.account.bean.AccountPropBean;
import com.yhcloud.thankyou.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/25.
 */

public class AccountPropAdapter extends RecyclerView.Adapter<AccountPropAdapter.MyViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<AccountPropBean> mBeen;
    private IOnClickListener mIOnClickListener;

    public AccountPropAdapter(Context context, ArrayList<AccountPropBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_account_prop, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Glide.with(mContext)
                .load(Constant.SERVICEADDRESS + mBeen.get(position).getPropImg())
                .error(R.mipmap.bg_props)
                .into(holder.propsImage);
        holder.propsName.setText(mBeen.get(position).getPropName());
        holder.propsIntroduction.setText("道具简介: " + mBeen.get(position).getDescription());
        holder.propsCoin.setText(mBeen.get(position).getPropPrice());
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView propsImage;
        TextView propsName, propsIntroduction, propsCoin;
        public MyViewHolder(View itemView) {
            super(itemView);
            propsImage = (ImageView) itemView.findViewById(R.id.iv_props_image);
            propsName = (TextView) itemView.findViewById(R.id.tv_props_name);
            propsIntroduction = (TextView) itemView.findViewById(R.id.tv_props_introduction);
            propsCoin = (TextView) itemView.findViewById(R.id.tv_props_coin);
        }
    }

    public void refreshData(ArrayList<AccountPropBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(IOnClickListener onClickListener) {
        this.mIOnClickListener = onClickListener;
    }
}
