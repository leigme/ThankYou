package com.yhcloud.thankyou.module.allfuncation.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.mabstract.ABaseActivity;
import com.yhcloud.thankyou.module.allfuncation.adapter.AllFuncationListAdapter;
import com.yhcloud.thankyou.module.allfuncation.manage.AllFuncationManage;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.utils.myview.drag.DragItemCallBack;
import com.yhcloud.thankyou.utils.myview.drag.RecycleCallBack;

import java.util.ArrayList;
import java.util.Collections;

public class AllFuncationActivity extends ABaseActivity implements IAllFuncationActivityView {

    private String TAG = getClass().getSimpleName();

    //视图控件
    private LinearLayout llBack, llRight;
    private TextView tvTitle, tvRight;
    private ImageView ivRight;
    private RecyclerView rvAddFuncationList, rvNoneFuncationList;
    //适配器
    private AllFuncationListAdapter afla, nfla;
    //管理器
    private AllFuncationManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_funcation);
        mManage = new AllFuncationManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        rvAddFuncationList = (RecyclerView) findViewById(R.id.rv_add_funcation_list);
        rvNoneFuncationList = (RecyclerView) findViewById(R.id.rv_none_funcation_list);
    }

    @Override
    public void initEvent() {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_header_left:
                        mManage.closePage();
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    @Override
    public void setRightTitle(String title) {
        if (null == llRight) {
            llRight = (LinearLayout) findViewById(R.id.ll_header_right);
            llRight.setVisibility(View.VISIBLE);
            llRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mManage.edit();
                }
            });
            ivRight = (ImageView) findViewById(R.id.iv_header_right);
            ivRight.setVisibility(View.INVISIBLE);
            tvRight = (TextView) findViewById(R.id.tv_header_right);
        }
        this.tvRight.setText(title);
    }

    @Override
    public void onBackPressed() {
        mManage.closePage();
    }

    @Override
    public void showAddList(final ArrayList<FunctionBean> list) {
        if (null == afla) {
            RecycleCallBack rcb = new RecycleCallBack() {
                @Override
                public void itemOnClick(int position, View view) {
                    Tools.print(TAG, "点击事件...");
                    if (mManage.isEdited()) {
                        mManage.editFunctionList(1, position);
                    } else {
                        mManage.goAddFunction(position);
                    }
                }

                @Override
                public void itemOnLongClick(int position, View view) {
                    Tools.print(TAG, "长按事件...");
                }

                @Override
                public void onMove(int from, int to) {
                    if (mManage.isEdited()) {
                        Tools.print(TAG, "移动监听事件:从" + from + "到" + to);
                        synchronized (this) {
                            if (from > to) {
                                int count = from - to;
                                for (int i = 0; i < count; i++) {
                                    Collections.swap(list, from - i, from - i - 1);
                                }
                            }
                            if (from < to) {
                                int count = to - from;
                                for (int i = 0; i < count; i++) {
                                    Collections.swap(list, from + i, from + i + 1);
                                }
                            }
                        }
                        afla.setData(list);
                        afla.notifyItemMoved(from, to);
                        afla.show.clear();
                        afla.show.put(to, to);
                    } else {
                        showToastMsg("请打开编辑模式...");
                    }
                }

                @Override
                public void saveFunctionList() {
                    mManage.saveFunctionList();
                }
            };

            afla = new AllFuncationListAdapter(this, list, rcb);
            afla.setISelectItem(new AllFuncationListAdapter.ISelectItem() {
                @Override
                public void setSelectItem(SparseArray<Integer> show, View view, int position) {
                    if (mManage.isEdited()) {
                        show.clear();
                        view.setBackgroundColor(Color.LTGRAY);
                        show.put(position, position);
                    } else {
                        showToastMsg("请打开编辑模式...");
                    }
                }
            });
            GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
            rvAddFuncationList.setLayoutManager(layoutManager);
            rvAddFuncationList.setAdapter(afla);
            ItemTouchHelper ith = new ItemTouchHelper(new DragItemCallBack(rcb));
            ith.attachToRecyclerView(rvAddFuncationList);
        } else {
            afla.setData(list);
            afla.refreshData(list);
        }
    }

    @Override
    public void showNoneList(final ArrayList<FunctionBean> list) {
        if (null == nfla) {
            RecycleCallBack rcb = new RecycleCallBack() {
                @Override
                public void itemOnClick(int position, View view) {
                    Tools.print(TAG, "点击事件...");
                    if (mManage.isEdited()) {
                        mManage.editFunctionList(0, position);
                    } else {
                        mManage.goNoneFunction(position);
                    }
                }

                @Override
                public void itemOnLongClick(int position, View view) {
                    Tools.print(TAG, "长按事件...");
                }

                @Override
                public void onMove(int from, int to) {
                    if (mManage.isEdited()) {
                        Tools.print(TAG, "移动监听事件:从" + from + "到" + to);
                        synchronized (this) {
                            if (from > to) {
                                int count = from - to;
                                for (int i = 0; i < count; i++) {
                                    Collections.swap(list, from - i, from - i - 1);
                                }
                            }
                            if (from < to) {
                                int count = to - from;
                                for (int i = 0; i < count; i++) {
                                    Collections.swap(list, from + i, from + i + 1);
                                }
                            }
                        }
                        nfla.setData(list);
                        nfla.notifyItemMoved(from, to);
                        nfla.show.clear();
                        nfla.show.put(to, to);
                    } else {
                        showToastMsg("请打开编辑模式...");
                    }
                }

                @Override
                public void saveFunctionList() {
                    mManage.saveFunctionList();
                }
            };

            nfla = new AllFuncationListAdapter(this, list, rcb);
            nfla.setISelectItem(new AllFuncationListAdapter.ISelectItem() {
                @Override
                public void setSelectItem(SparseArray<Integer> show, View view, int position) {
                    if (mManage.isEdited()) {
                        show.clear();
                        view.setBackgroundColor(Color.LTGRAY);
                        show.put(position, position);
                    } else {
                        showToastMsg("请打开编辑模式...");
                    }
                }
            });
            GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
            rvNoneFuncationList.setLayoutManager(layoutManager);
            rvNoneFuncationList.setAdapter(nfla);
            ItemTouchHelper ith = new ItemTouchHelper(new DragItemCallBack(rcb));
            ith.attachToRecyclerView(rvNoneFuncationList);
        } else {
            nfla.setData(list);
            nfla.refreshData(list);
        }
    }

    @Override
    public void setEditMode(boolean edited) {
        afla.setEditMode(edited, 1);
        nfla.setEditMode(edited, 0);
    }
}
