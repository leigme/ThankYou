package com.yhcloud.thankyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ClassPeopleListAdapter extends RecyclerView.Adapter<ClassPeopleListAdapter.PeoPleViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<UserInfoBean> mBeen;
    private ItemClinkListener mItemClinkListener;

    public ClassPeopleListAdapter(Context context, ArrayList<UserInfoBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public PeoPleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_class_people_list, parent, false);
        PeoPleViewHolder viewHolder = new PeoPleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PeoPleViewHolder holder, int position) {
        UserInfoBean userInfoBean = mBeen.get(position);
        Tools.GlideCircleImageUrl(mContext, userInfoBean.getHeadImageURL(), R.mipmap.default_photo, holder.ivHeader);
        holder.tvName.setText(userInfoBean.getRealName());
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

    public void refreshData(ArrayList<UserInfoBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public static class PeoPleViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHeader;
        TextView tvName;
        public PeoPleViewHolder(View itemView) {
            super(itemView);
            ivHeader = (ImageView) itemView.findViewById(R.id.iv_class_people_image);
            tvName = (TextView) itemView.findViewById(R.id.tv_class_people_title);
        }
    }
}
