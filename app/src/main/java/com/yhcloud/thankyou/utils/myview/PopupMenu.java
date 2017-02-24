package com.yhcloud.thankyou.utils.myview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.PopupWindow;
import android.view.View;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.adapter.PopupmenuListAdapter;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.mInterfacea.IOnClickListener;
import com.yhcloud.thankyou.utils.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/21.
 */

public class PopupMenu extends PopupWindow {

    private Activity mActivity;
    private View mView;
    private RecyclerView rvPopupmenuList;

    private PopupmenuListAdapter pla;

    public PopupMenu(Activity activity, final ArrayList<FunctionBean> list) {
        super(activity);
        this.mActivity = activity;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popup_menu, null);// 加载菜单布局文件
        this.setContentView(mView);// 把布局文件添加到popupwindow中
        this.setWidth(dip2px(mActivity, 120));// 设置菜单的宽度（需要和菜单于右边距的距离搭配，可以自己调到合适的位置）
        this.setHeight(dip2px(mActivity, 160));
        this.setFocusable(true);// 获取焦点
        this.setTouchable(true); // 设置PopupWindow可触摸
        this.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);

        rvPopupmenuList = (RecyclerView) mView.findViewById(R.id.rv_popup_menu_list);
        if (null == pla) {
            pla = new PopupmenuListAdapter(mActivity, list);
            rvPopupmenuList.setLayoutManager(new LinearLayoutManager(mActivity));
            rvPopupmenuList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
            pla.setIOnClickListener(new IOnClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    mActivity.startActivity(list.get(position).getIntent());
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            rvPopupmenuList.setAdapter(pla);
        }
    }


    /**
     * 设置显示的位置
     *
     * @param resourId
     *            这里的x,y值自己调整可以
     */
    public void showLocation(int resourId) {
        showAsDropDown(mActivity.findViewById(resourId), dip2px(mActivity, 0),
                dip2px(mActivity, -8));
    }

    // dip转换为px
    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
