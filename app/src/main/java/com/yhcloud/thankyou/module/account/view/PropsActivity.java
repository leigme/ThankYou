package com.yhcloud.thankyou.module.account.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.account.adapter.AccountPropAdapter;
import com.yhcloud.thankyou.module.account.bean.AccountPropBean;
import com.yhcloud.thankyou.module.account.manage.AccountPropsManage;
import com.yhcloud.thankyou.utils.myview.MyToast;


import java.util.ArrayList;

public class PropsActivity extends AppCompatActivity implements IPropsView {

    private LinearLayout llBack;
    private TextView tvTitle, coin;
    private RecyclerView mRecyclerView;
    private AccountPropAdapter apa;
    private ProgressDialog mProgressDialog;

    private AccountPropsManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_props);

        mManage = new AccountPropsManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        coin = (TextView) findViewById(R.id.tv_prop_coin);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_props_list);
    }

    @Override
    public void initData() {

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
    public void showLoading() {
        hiddenLoading();
        mProgressDialog = ProgressDialog.show(this, null, getString(R.string.loading_data));
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
    public void setCoin(String coin) {
        this.coin.setText(coin);
    }

    @Override
    public void showPropList(ArrayList<AccountPropBean> list) {
        if (null == apa) {
            apa = new AccountPropAdapter(this, list);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(apa);
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
            apa.setIOnClickListener(new IOnClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    mManage.goPropInfo(position);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
        }
    }

    @Override
    public void showMsg(int msgId) {
        MyToast.showToast(this, msgId);
    }

    @Override
    public void showMsg(String msg) {
        MyToast.showToast(this, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mManage.getUserCurrency();
    }
}
