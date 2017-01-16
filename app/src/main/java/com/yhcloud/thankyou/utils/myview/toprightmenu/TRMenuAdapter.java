package com.yhcloud.thankyou.utils.myview.toprightmenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.FunctionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Bro0cL on 2016/12/26.
 */
public class TRMenuAdapter extends RecyclerView.Adapter<TRMenuAdapter.TRMViewHolder> {
    private Context mContext;
    private ArrayList<FunctionBean> menuItemList;
    private boolean showIcon;
    private TopRightMenu mTopRightMenu;
    private TopRightMenu.OnMenuItemClickListener onMenuItemClickListener;

    public TRMenuAdapter(Context context, TopRightMenu topRightMenu, ArrayList<FunctionBean> menuItemList, boolean show) {
        this.mContext = context;
        this.mTopRightMenu = topRightMenu;
        this.menuItemList = menuItemList;
        this.showIcon = show;
    }

    public void setData(ArrayList<FunctionBean> data){
        menuItemList = data;
        notifyDataSetChanged();
    }

    public void setShowIcon(boolean showIcon) {
        this.showIcon = showIcon;
        notifyDataSetChanged();
    }

    @Override
    public TRMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.trm_item_popup_menu_list, parent, false);
        return new TRMViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TRMViewHolder holder, int position) {
        final FunctionBean menuItem = menuItemList.get(position);
        if (showIcon){
            holder.icon.setVisibility(View.VISIBLE);
            int resId = menuItem.getIcon();
            holder.icon.setImageResource(resId < 0 ? 0 : resId);
        }else{
            holder.icon.setVisibility(View.GONE);
        }
        holder.text.setText(menuItem.getTitle());

        if (position == 0){
            holder.container.setBackgroundResource(R.drawable.trm_popup_top_pressed);
        }else if (position == menuItemList.size() - 1){
            holder.container.setBackgroundResource(R.drawable.trm_popup_bottom_pressed);
        }else {
            holder.container.setBackgroundResource(R.drawable.trm_popup_middle_pressed);
        }
//        holder.container.setAlpha(0.75f);

        final int pos = holder.getAdapterPosition();
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMenuItemClickListener != null) {
                    mTopRightMenu.dismiss();
                    onMenuItemClickListener.onMenuItemClick(pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItemList == null ? 0 : menuItemList.size();
    }

    class TRMViewHolder extends RecyclerView.ViewHolder{
        ViewGroup container;
        ImageView icon;
        TextView text;

        TRMViewHolder(View itemView) {
            super(itemView);
            container = (ViewGroup) itemView;
            icon = (ImageView) itemView.findViewById(R.id.trm_menu_item_icon);
            text = (TextView) itemView.findViewById(R.id.trm_menu_item_text);
        }
    }

    public void setOnMenuItemClickListener(TopRightMenu.OnMenuItemClickListener listener){
        this.onMenuItemClickListener = listener;
    }
}
