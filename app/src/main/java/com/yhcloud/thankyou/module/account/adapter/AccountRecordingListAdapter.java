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
import com.yhcloud.thankyou.module.account.bean.AccountRecordingBean;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountRecordingListAdapter extends RecyclerView.Adapter<AccountRecordingListAdapter.MyViewHolder> {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<AccountRecordingBean> mBeen;

    public AccountRecordingListAdapter(Context context, ArrayList<AccountRecordingBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recording_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        switch (mBeen.get(position).getType()) {
            case "2":
                Glide.with(mContext)
                        .load(Constant.SERVICEADDRESS + mBeen.get(position).getPropImg())
                        .error(R.mipmap.icon_account_404)
                        .into(holder.ivRecordingImage);
                holder.tvRecordingType.setText("您送出了");
                break;
            case "3":
                Glide.with(mContext)
                        .load(Constant.SERVICEADDRESS + mBeen.get(position).getPropImg())
                        .error(R.mipmap.icon_account_404)
                        .into(holder.ivRecordingImage);
                holder.tvRecordingType.setText("您收到了");
                break;
            case "4":
                Glide.with(mContext)
                        .load(R.mipmap.icon_large_you)
                        .error(R.mipmap.icon_account_404)
                        .into(holder.ivRecordingImage);
                holder.tvRecordingType.setText("您充值了");
                holder.tvRecordingBusinessType.setText("优点交易");
                break;
            case "5":
                Glide.with(mContext)
                        .load(R.mipmap.icon_coin)
                        .error(R.mipmap.icon_account_404)
                        .into(holder.ivRecordingImage);
//                holder.tvRecordingNum.setText(mBeen.get(position).getBuyNum());
//                holder.tvRecordingSpend.setText(mBeen.get(position).getCoin());
                break;
            case "6":
                Glide.with(mContext)
                        .load(Tools.getFileTypeImage(mBeen.get(position).getPropType()))
                        .error(R.mipmap.icon_account_404)
                        .into(holder.ivRecordingImage);
                holder.tvRecordingBusinessType.setText("优点交易");
                break;
            case "7":
                Glide.with(mContext)
                        .load(Constant.SERVICEADDRESS + mBeen.get(position).getPropImg())
                        .error(R.mipmap.icon_account_404)
                        .into(holder.ivRecordingImage);
                holder.tvRecordingType.setText("您卖出资源获得了");
                holder.tvRecordingBusinessType.setText("优点交易");
                break;
            default:
                Glide.with(mContext)
                        .load(Constant.SERVICEADDRESS + mBeen.get(position).getPropImg())
                        .error(R.mipmap.icon_account_404)
                        .into(holder.ivRecordingImage);
                holder.tvRecordingType.setText("您兑换了");
                holder.tvRecordingBusinessType.setText("积分交易");
                break;
        }
        holder.tvRecordingName.setText(mBeen.get(position).getPropName());
        holder.tvRecordingNum.setText(mBeen.get(position).getBuyNum());
        holder.tvRecordingSpend.setText(mBeen.get(position).getCoin());
        holder.tvRecordingTime.setText(mBeen.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    public void refreshData(ArrayList<AccountRecordingBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRecordingImage;
        TextView tvRecordingType, tvRecordingBusinessType, tvRecordingName, tvRecordingNum, tvRecordingSymbol, tvRecordingSpend, tvRecordingTime;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivRecordingImage = (ImageView) itemView.findViewById(R.id.iv_recording_image);
            tvRecordingType = (TextView) itemView.findViewById(R.id.tv_recording_type);
            tvRecordingBusinessType = (TextView) itemView.findViewById(R.id.tv_recording_business_type);
            tvRecordingName = (TextView) itemView.findViewById(R.id.tv_recording_name);
            tvRecordingNum = (TextView) itemView.findViewById(R.id.tv_recording_num);
            tvRecordingSymbol = (TextView) itemView.findViewById(R.id.tv_recording_symbol);
            tvRecordingSpend = (TextView) itemView.findViewById(R.id.tv_recording_spend);
            tvRecordingTime = (TextView) itemView.findViewById(R.id.tv_recording_time);
        }
    }
}
