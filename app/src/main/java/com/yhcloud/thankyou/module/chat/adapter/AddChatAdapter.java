package com.yhcloud.thankyou.module.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.chat.bean.ChatContact;

import java.util.ArrayList;

/**
 * Created by leig on 2017/2/9.
 */

public class AddChatAdapter extends RecyclerView.Adapter<AddChatAdapter.AddChatViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ChatContact> mBeen;
    private IOnClickListener mIOnClickListener;

    public AddChatAdapter(Context context, ArrayList<ChatContact> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public AddChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_addchat_list, parent, false);
        AddChatViewHolder viewHolder = new AddChatViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AddChatViewHolder holder, int position) {
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

    public void refreshData(ArrayList<ChatContact> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(IOnClickListener IOnClickListener) {
        mIOnClickListener = IOnClickListener;
    }

    public static class AddChatViewHolder extends RecyclerView.ViewHolder {

        public AddChatViewHolder(View itemView) {
            super(itemView);
        }
    }
}
