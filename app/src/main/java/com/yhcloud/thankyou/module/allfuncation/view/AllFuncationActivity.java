package com.yhcloud.thankyou.module.allfuncation.view;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.yhcloud.thankyou.module.allfuncation.adapter.AllFuncationListAdapter;
import com.yhcloud.thankyou.module.allfuncation.manage.AllFuncationManage;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.utils.myview.MyToast;
import com.yhcloud.thankyou.utils.myview.drag.DragItemCallBack;
import com.yhcloud.thankyou.utils.myview.drag.RecycleCallBack;

import java.util.ArrayList;
import java.util.Collections;

public class AllFuncationActivity extends AppCompatActivity implements IAllFuncationView {

    private String TAG = getClass().getSimpleName();

    //视图控件
    private LinearLayout llBack, llRight;
    private TextView tvTitle, tvRight;
    private ImageView ivRight;
    private RecyclerView rvAllFuncationList;
    private ProgressDialog mProgressDialog;
    //适配器
    private AllFuncationListAdapter afla;
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
        rvAllFuncationList = (RecyclerView) findViewById(R.id.rv_allfuncation_list);
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
    public void showLoading(int msgId) {
        mProgressDialog = ProgressDialog.show(this, null, getString(msgId));
    }

    @Override
    public void hiddenLoading() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
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
    public void showToastMsg(int msgId) {
        MyToast.showToast(this, msgId);
    }

    @Override
    public void showToastMsg(String msg) {
        MyToast.showToast(this, msg);
    }

    @Override
    public void onBackPressed() {
        mManage.closePage();
    }

    @Override
    public void showList(final ArrayList<FunctionBean> list) {
        if (null == afla) {
            RecycleCallBack rcb = new RecycleCallBack() {
                @Override
                public void itemOnClick(int position, View view) {
                    Tools.print(TAG, "点击事件...");
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
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (list.get(position).getId()) {
                        case -1:
                            return 4;
                        default:
                            return 1;
                    }
                }
            });
            rvAllFuncationList.setLayoutManager(layoutManager);
            rvAllFuncationList.setAdapter(afla);
            ItemTouchHelper ith = new ItemTouchHelper(new DragItemCallBack(rcb));
            ith.attachToRecyclerView(rvAllFuncationList);
        } else {
            afla.setData(list);
        }
    }
}
