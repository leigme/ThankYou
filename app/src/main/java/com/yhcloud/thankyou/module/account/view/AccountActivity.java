package com.yhcloud.thankyou.module.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mabstract.ABaseActivity;
import com.yhcloud.thankyou.mInterfacea.IOnClickListener;
import com.yhcloud.thankyou.module.account.adapter.AccountFunctionAdapter;
import com.yhcloud.thankyou.module.account.bean.AccountFunctionBean;
import com.yhcloud.thankyou.module.account.manage.AccountManage;
import com.yhcloud.thankyou.module.account.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;

public class AccountActivity extends ABaseActivity implements IAccountView {

    private String TAG = getClass().getSimpleName();

    private LinearLayout llBack;
    private TextView tvTitle;
    private Banner mBanner;
    private RecyclerView mRecyclerView;
    private AccountFunctionAdapter afa;

    private AccountManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mManage = new AccountManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        mBanner = (Banner) findViewById(R.id.banner);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_account_function);
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
    public void showDefault(boolean showed) {

    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setRightTitle(String title) {

    }

    @Override
    public void showBanner(ArrayList<String> list) {
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(list);
        mBanner.start();
    }

    @Override
    public void showFunction(final ArrayList<AccountFunctionBean> list) {
        if (null == afa) {
            afa = new AccountFunctionAdapter(this, list);
            afa.setIOnClickListener(new IOnClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    mManage.goFunction(position);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
        } else {
            afa.refreshData(list);
        }
        mRecyclerView.setAdapter(afa);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        for (AccountFunctionBean afb: mBeen) {
//            if (resultCode == afb.getId()) {
//                Tools.print(TAG, "返回界面是:" + afb.getTitle());
//            }
//        }
    }
}
