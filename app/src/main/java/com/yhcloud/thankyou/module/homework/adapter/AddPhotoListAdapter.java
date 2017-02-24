package com.yhcloud.thankyou.module.homework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterfacea.IOnClickListener;
import com.yhcloud.thankyou.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/13.
 */

public class AddPhotoListAdapter extends RecyclerView.Adapter<AddPhotoListAdapter.AddPhotoViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mBeen;
    private IOnClickListener mIOnClickListener;
    private IDelOnClickListerner mIDelOnClickListerner;

    public AddPhotoListAdapter(Context context, ArrayList<String> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public AddPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_photo_list, parent, false);
        AddPhotoViewHolder viewHolder = new AddPhotoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AddPhotoViewHolder holder, int position) {
        if (Constant.ADDIMAGE.equals(mBeen.get(position))) {
            Glide.with(mContext).load(R.mipmap.add_photo_image).into(holder.ivPhoto);
            holder.ivDel.setVisibility(View.GONE);
        } else {
            Glide.with(mContext).load(mBeen.get(position)).into(holder.ivPhoto);
            holder.ivDel.setVisibility(View.VISIBLE);
            if (null != mIDelOnClickListerner) {
                holder.ivDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mIDelOnClickListerner.delOnClickListerner(holder.itemView, pos);
                    }
                });
            }
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

    public void refreshData(ArrayList<String> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public void setIOnClickListener(IOnClickListener iOnClickListener, IDelOnClickListerner iDelOnClickListerner) {
        this.mIOnClickListener = iOnClickListener;
        this.mIDelOnClickListerner = iDelOnClickListerner;
    }

    public interface IDelOnClickListerner {
        void delOnClickListerner(View view, int position);
    }

    public static class AddPhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto, ivDel;
        public AddPhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo_image);
            ivDel = (ImageView) itemView.findViewById(R.id.iv_photo_del_image);
        }
    }
}
