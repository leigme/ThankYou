package com.yhcloud.thankyou.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.utils.myview.drag.DragHolderCallBack;
import com.yhcloud.thankyou.utils.myview.drag.RecycleCallBack;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/20.
 */

public class HomeFunctionListAdapter extends RecyclerView.Adapter<HomeFunctionListAdapter.FunctionViewHolder> {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<FunctionBean> mBeen;
    private RecycleCallBack mCallBack;
    private ItemClinkListener mItemClinkListener;
    public SparseArray<Integer> show = new SparseArray<>();

    public HomeFunctionListAdapter(Context context, ArrayList<FunctionBean> list, RecycleCallBack callBack) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
        this.mCallBack = callBack;
    }

    @Override
    public FunctionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_home_function, parent, false);
        FunctionViewHolder viewHolder = new FunctionViewHolder(view, mCallBack);
        return viewHolder;
    }

    public void setData(ArrayList<FunctionBean> list) {
        this.mBeen = list;
    }

    @Override
    public void onBindViewHolder(final FunctionViewHolder holder, int position) {
        holder.image.setImageResource(mBeen.get(position).getImage());
        holder.title.setText(mBeen.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    class FunctionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, DragHolderCallBack {
        ImageView image;
        TextView title;
        RecycleCallBack mCallBack;
        public FunctionViewHolder(View itemView, RecycleCallBack callBack) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_home_function_image);
            title = (TextView) itemView.findViewById(R.id.tv_home_function_title);
            itemView.setOnClickListener(this);
            this.mCallBack = callBack;
        }

        @Override
        public void onClick(View v) {
            if (null != mCallBack) {
                show.clear();
                mCallBack.itemOnClick(getAdapterPosition(), v);
            }
            if (null != mItemClinkListener) {
                mItemClinkListener.OnItemClickListener(v, getAdapterPosition());
            }
        }

        @Override
        public void onSelect() {
            show.clear();
            show.put(getAdapterPosition(), getAdapterPosition());
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onClear() {
            itemView.setBackgroundColor(Color.WHITE);
            mCallBack.saveFunctionList();
        }
    }

    public void setIOnClickListener(ItemClinkListener itemClinkListener) {
        this.mItemClinkListener = itemClinkListener;
    }

    public void refreshData(ArrayList<FunctionBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }
}
