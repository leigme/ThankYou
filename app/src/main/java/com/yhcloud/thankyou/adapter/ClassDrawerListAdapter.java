package com.yhcloud.thankyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.comm.SubmitCallBack;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/19.
 */

public class ClassDrawerListAdapter extends RecyclerView.Adapter<ClassDrawerListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ClassInfoBean> mDatas;
    private LayoutInflater mInflater;
    private SubmitCallBack mListener;

    public ClassDrawerListAdapter(Context context, ArrayList<ClassInfoBean> list) {
        this.mContext = context;
        this.mDatas = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_drawerlist, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mTextView.setText(mDatas.get(position).getClassName());
        if (mDatas.get(position).isSelected()) {
            holder.mLayout.setBackgroundResource(R.drawable.item_drawer_bg);
        } else {
            holder.mLayout.setBackgroundResource(R.drawable.item_drawer_bg_un);
        }
        if (null != mListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mListener.OnItemClickListener(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mListener.OnItemLongClickListener(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setMyOnClickListener(SubmitCallBack submitCallBack) {
        this.mListener = submitCallBack;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout mLayout;
        TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.rl_drawer_btn);
            mTextView = (TextView) itemView.findViewById(R.id.tv_itemdrawer_title);
        }
    }

    public void reflreshList(ArrayList<ClassInfoBean> list) {
        this.mDatas = list;
        this.notifyDataSetChanged();
    }
}
