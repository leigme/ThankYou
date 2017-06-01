package com.yhcloud.thankyou.module.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.BaseActivity;
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.module.account.adapter.AccountMyPropsAdapter;
import com.yhcloud.thankyou.module.account.bean.AccountPropBean;
import com.yhcloud.thankyou.module.account.manage.AccountMyPropsManage;

import java.util.ArrayList;

public class MyPropsActivity extends BaseActivity implements MyPropsView {

    //视图控件
    private LinearLayout llBack, llRight, llAddPropsNum, llSubPropsNum;
    private TextView tvTitle, tvRight, tvDefault, tvPropsBuyNum;
    private ViewStub viewStub;
    private RecyclerView rvMyPropsList;
    //适配器
    private AccountMyPropsAdapter ampa;
    //管理器
    private AccountMyPropsManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_props);
        mManage = new AccountMyPropsManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        tvRight = (TextView) findViewById(R.id.tv_header_right);
        llRight = (LinearLayout) findViewById(R.id.ll_header_right);
        rvMyPropsList = (RecyclerView) findViewById(R.id.rv_myprops_list);
    }

    @Override
    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    @Override
    public void setRightTitle(String title) {

    }

    @Override
    public void initEvent() {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_header_left:
                        finish();
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
    }

    @Override
    public void showList(final ArrayList<AccountPropBean> list, final boolean canOnClick) {
        if (null == ampa) {
            ampa = new AccountMyPropsAdapter(this, list, canOnClick);
            if (canOnClick) {
                ampa.setIOnClickListener(new ItemClinkListener() {
                    @Override
                    public void OnItemClickListener(View view, int position) {
                        for (AccountPropBean bean: list) {
                            bean.setSelected(false);
                        }
                        mManage.setGiveNum(position);
                        list.get(position).setSelected(true);
                        ampa.refreshData(list, canOnClick);
                    }

                    @Override
                    public void OnItemLongClickListener(View view, int position) {

                    }
                });
            }
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvMyPropsList.setLayoutManager(layoutManager);
            rvMyPropsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {

                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
            rvMyPropsList.setAdapter(ampa);
        } else {
            ampa.refreshData(list, canOnClick);
        }
    }

    @Override
    public void initViewStub() {
        viewStub = (ViewStub) findViewById(R.id.vs_default);
        viewStub.inflate();
        tvDefault = (TextView) findViewById(R.id.tv_default);
    }

    @Override
    public void showDefault(boolean showed) {
        if (null != tvDefault) {
            if (showed) {
                tvDefault.setVisibility(View.VISIBLE);
            } else {
                tvDefault.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void initViewStub(boolean canGive) {
        if (canGive) {
            ViewStub giveNum = (ViewStub) findViewById(R.id.vs_my_props_num);
            giveNum.inflate();
            tvPropsBuyNum = (TextView) findViewById(R.id.tv_props_info_buynum);
            llAddPropsNum = (LinearLayout) findViewById(R.id.ll_props_info_plus);
            llSubPropsNum = (LinearLayout) findViewById(R.id.ll_props_info_less);
            llAddPropsNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mManage.addNum();
                }
            });
            llSubPropsNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mManage.subNum();
                }
            });
        }
    }

    @Override
    public int getNum() {
        int i = 0;
        if (null != tvPropsBuyNum) {
            i = Integer.parseInt(tvPropsBuyNum.getText().toString().trim());
        }
        return i;
    }

    @Override
    public void showNum(int num) {
        if (null != tvPropsBuyNum) {
            tvPropsBuyNum.setText(String.valueOf(num));
        }
    }

    @Override
    public void setCoin(String coin) {

    }

    @Override
    public void initRight(boolean canOnClick) {
        Log.e("右上角", "初始化右上角按钮");
        if (canOnClick) {
            Log.e("右上角", "设置右上角按钮可以点击");
            llRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("右上角", "点击了右上角按钮");
                    mManage.givePropsToPeople();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void processClick(View view) {

    }
}
