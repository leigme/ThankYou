package com.yhcloud.thankyou.module.allfuncation.adapter;

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
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.utils.myview.drag.DragHolderCallBack;
import com.yhcloud.thankyou.utils.myview.drag.RecycleCallBack;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/11.
 */

public class AllFuncationListAdapter extends RecyclerView.Adapter {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<FunctionBean> mBeen;
    private RecycleCallBack mCallBack;
    private IOnClickListener mIOnClickListener;
    private ISelectItem mISelectItem;
    public SparseArray<Integer> show = new SparseArray<>();

    public AllFuncationListAdapter(Context context, ArrayList<FunctionBean> list, RecycleCallBack callBack) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
        this.mCallBack = callBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = mInflater.inflate(R.layout.item_null_line_list, parent, false);
                NullViewHolder nullViewHolder = new NullViewHolder(view);
                return nullViewHolder;
            case 1:
                view = mInflater.inflate(R.layout.item_home_function, parent, false);
                ContentViewHolder contentViewHolder = new ContentViewHolder(view, mCallBack);
                return contentViewHolder;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (!"".equals(mBeen.get(position).getTitle())){
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            contentViewHolder.title.setText(mBeen.get(position).getTitle());
            contentViewHolder.image.setImageResource(mBeen.get(position).getImage());
        }
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    @Override
    public int getItemViewType(int position) {
        if ("".equals(mBeen.get(position).getTitle())) {
            return 0;
        }
        return 1;
    }

    public void setData(ArrayList<FunctionBean> list) {
        this.mBeen = list;
    }

    class ContentViewHolder extends RecyclerView.ViewHolder implements DragHolderCallBack, View.OnClickListener {
        ImageView image;
        TextView title;
        RecycleCallBack mCallBack;
        public ContentViewHolder(View itemView, RecycleCallBack callBack) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_home_function_image);
            title = (TextView) itemView.findViewById(R.id.tv_home_function_title);
            itemView.setOnClickListener(this);
            this.mCallBack = callBack;
        }

        @Override
        public void onSelect() {
            if (null != mISelectItem) {
                mISelectItem.setSelectItem(show, itemView, getAdapterPosition());
            }
        }

        @Override
        public void onClear() {
            itemView.setBackgroundColor(Color.WHITE);
            mCallBack.saveFunctionList();
        }

        @Override
        public void onClick(View v) {
            if (null != mCallBack) {
                show.clear();
                mCallBack.itemOnClick(getAdapterPosition(), v);
            }
            if (null != mIOnClickListener) {
                mIOnClickListener.OnItemClickListener(v, getAdapterPosition());
            }
        }
    }

    public static class NullViewHolder extends RecyclerView.ViewHolder {
        public NullViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface ISelectItem {
        void setSelectItem(SparseArray<Integer> show, View view, int position);
    }

    public void setIOnClickListener(IOnClickListener IOnClickListener) {
        this.mIOnClickListener = IOnClickListener;
    }

    public void setISelectItem(ISelectItem iSelectItem) {
        this.mISelectItem = iSelectItem;
    }

    public void refreshData(ArrayList<FunctionBean> list) {
        this.mBeen = list;
        this.notifyDataSetChanged();
    }
}
