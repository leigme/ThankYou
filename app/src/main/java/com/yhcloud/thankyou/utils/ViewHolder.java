package com.yhcloud.thankyou.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Leig on 2016/7/4.
 */
public class ViewHolder {

    private String TAG = getClass().getName();

    private SparseArray<View> mViews;
    private Context mContext;
    private LayoutInflater mInflater;
    private View mConvertView;
    private int mPosition;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mContext = context;
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        this.mInflater = LayoutInflater.from(mContext);
        this.mConvertView = mInflater.inflate(layoutId, parent, false);
        this.mConvertView.setTag(this);
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder(context, parent, layoutId, position);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
        }
        return holder;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getPosition() {
        return mPosition;
    }

    //为TextView设置文本的值
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

//    //为ExpandableTextView长文本设置带展开收起的值
//    public ViewHolder setContent(int viewId, SparseBooleanArray collapsedStatus, String text) {
//        ExpandableTextView etv = getView(viewId);
//        etv.setText(text, collapsedStatus, mPosition);
//        return this;
//    }

    //通过本地资源设置ImageView
    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    //通过Bitmap设置ImageView
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    //通过第三方Glide加载本地图片
    public ViewHolder setImageFile(int viewId, String file) {
        ImageView iv = getView(viewId);
        Glide.with(mContext).load(file).into(iv);
        return this;
    }
    //通过第三方Glide加载网络图片
    public ViewHolder setImageURL(int viewId, String url) {
        ImageView iv = getView(viewId);
        Tools.GlideImageUrl(mContext, url, iv);
        return this;
    }
    //通过第三方Glide加载网络图片
    public ViewHolder setCircleImageURL(int viewId, String url) {
        ImageView iv = getView(viewId);
        Tools.GlideCircleImageUrl(mContext, url, iv);
        return this;
    }

//    //通过第三方Glide加载网络图片
//    public ViewHolder setSerivceAddressImageURL(int viewId, String url) {
//        ImageView iv = getView(viewId);
//        if ("http://192.168.0.139/edu".equals(ServiceAPI.SERVICEADDRESS)) {
//            url = "http://192.168.0.139" + url;
//        } else if ("http://www.k12chn.com".equals(ServiceAPI.SERVICEADDRESS)) {
//            url = "http://www.k12chn.com" + url;
//        }
//        Glide.with(mContext)
//                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(iv);
//        return this;
//    }
}
