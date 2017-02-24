package com.yhcloud.thankyou.module.allfuncation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.minterface.IOnClickListener;
import com.yhcloud.thankyou.utils.myview.drag.DragHolderCallBack;
import com.yhcloud.thankyou.utils.myview.drag.RecycleCallBack;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/11.
 */

public class AllFuncationListAdapter extends RecyclerView.Adapter<AllFuncationListAdapter.FuncationViewHolder> {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<FunctionBean> mBeen;
    private RecycleCallBack mCallBack;
    private IOnClickListener mIOnClickListener;
    private ISelectItem mISelectItem;
    public SparseArray<Integer> show = new SparseArray<>();
    private boolean mEdited;
    private int mCode;

    public AllFuncationListAdapter(Context context, ArrayList<FunctionBean> list, RecycleCallBack callBack) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
        this.mCallBack = callBack;
    }

    @Override
    public FuncationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_home_function, parent, false);
        FuncationViewHolder funcationViewHolder = new FuncationViewHolder(view, mCallBack);
        return funcationViewHolder;
    }

    @Override
    public void onBindViewHolder(FuncationViewHolder holder, int position) {
        if (!"".equals(mBeen.get(position).getTitle())){
            if (mEdited) {
                if (0 == mCode) {
                    holder.ivIconOpe.setImageResource(R.mipmap.icon_function_add);
                } else {
                    holder.ivIconOpe.setImageResource(R.mipmap.icon_function_del);
                }
                holder.ivIconOpe.setVisibility(View.VISIBLE);
            } else {
                holder.ivIconOpe.setVisibility(View.INVISIBLE);
            }
            holder.title.setText(mBeen.get(position).getTitle());
            holder.image.setImageResource(mBeen.get(position).getImage());
        }
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    public void setData(ArrayList<FunctionBean> list) {
        this.mBeen = list;
    }

    public void setEditMode(boolean edited, int code) {
        this.mEdited = edited;
        this.mCode = code;
        this.notifyDataSetChanged();
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

    class FuncationViewHolder extends RecyclerView.ViewHolder implements DragHolderCallBack, View.OnClickListener {
        RelativeLayout rlHome;
        ImageView image, ivIconOpe;
        TextView title;
        RecycleCallBack mCallBack;
        public FuncationViewHolder(View itemView, RecycleCallBack callBack) {
            super(itemView);
            rlHome = (RelativeLayout) itemView.findViewById(R.id.rl_function_home);
            ivIconOpe = (ImageView) itemView.findViewById(R.id.iv_function_ope);
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

}
