package com.yhcloud.thankyou.module.account.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.account.adapter.AccountFunctionAdapter;
import com.yhcloud.thankyou.module.account.bean.AccountFunctionBean;
import com.yhcloud.thankyou.module.account.manage.AccountManage;
import com.yhcloud.thankyou.module.account.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity implements IAccountView {

    private String TAG = getClass().getSimpleName();

    private LinearLayout llBack;
    private TextView tvTitle;
    private Banner mBanner;
    private RecyclerView mRecyclerView;
    private AccountFunctionAdapter afa;
    private ProgressDialog mProgressDialog;

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
    public void showBanner(List<String> list) {
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
                    startActivityForResult(list.get(position).getIntent(), list.get(position).getId());
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
//                Log.e(TAG, "返回界面是:" + afb.getTitle());
//            }
//        }
    }
}
