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
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.account.bean.AccountPropBean;
import com.yhcloud.thankyou.utils.ServiceAPI;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/5.
 */

public class AccountMyPropsAdapter extends RecyclerView.Adapter<AccountMyPropsAdapter.MyViewHolder> {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<AccountPropBean> mBeen;
    private IOnClickListener mIOnClickListener;
    private boolean mCanOnClcik;

    public AccountMyPropsAdapter(Context context, ArrayList<AccountPropBean> list, boolean canOnClick) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
        this.mCanOnClcik = canOnClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_myprops_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Glide.with(mContext)
                .load(ServiceAPI.SERVICEADDRESS + mBeen.get(position).getPropImg())
                .placeholder(R.mipmap.icon_myprops)
                .error(R.mipmap.icon_myprops)
                .into(holder.propsImage);
        holder.propsName.setText(mBeen.get(position).getPropName());
        holder.propsIntroduction.setText("道具简介: " + mBeen.get(position).getDescription());
        holder.propsNum.setText(mBeen.get(position).getPropNum());
        if (mCanOnClcik) {
            holder.propsSelect.setVisibility(View.VISIBLE);
        } else {
            holder.propsSelect.setVisibility(View.GONE);
        }
        if (mBeen.get(position).isSelected()) {
            holder.propsSelect.setImageResource(R.mipmap.chose_on);
        } else {
            holder.propsSelect.setImageResource(R.mipmap.chose_off);
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

    public void refreshData(ArrayList<AccountPropBean> list, boolean canOnClcik) {
        this.mBeen = list;
        this.mCanOnClcik = canOnClcik;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(IOnClickListener iOnClickListener) {
        this.mIOnClickListener = iOnClickListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView propsImage, propsSelect;
        TextView propsName, propsIntroduction, propsNum;
        public MyViewHolder(View itemView) {
            super(itemView);
            propsImage = (ImageView) itemView.findViewById(R.id.iv_props_image);
            propsName = (TextView) itemView.findViewById(R.id.tv_props_name);
            propsIntroduction = (TextView) itemView.findViewById(R.id.tv_props_introduction);
            propsNum = (TextView) itemView.findViewById(R.id.tv_props_num);
            propsSelect = (ImageView) itemView.findViewById(R.id.iv_myprops_selected);
        }
    }
}
