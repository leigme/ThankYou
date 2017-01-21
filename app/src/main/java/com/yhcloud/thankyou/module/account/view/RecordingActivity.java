package com.yhcloud.thankyou.module.account.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.module.account.adapter.AccountRecordingListAdapter;
import com.yhcloud.thankyou.module.account.bean.AccountRecordingBean;
import com.yhcloud.thankyou.module.account.manage.AccountRecordingManage;
import com.yhcloud.thankyou.utils.myview.MyToast;


import java.util.ArrayList;

public class RecordingActivity extends AppCompatActivity implements IRecordingView {

    private LinearLayout llBack;
    private TextView tvTitle, tvDefault;
    private ProgressDialog mProgressDialog;
    private RecyclerView rvList;
    private AccountRecordingListAdapter arla;

    private AccountRecordingManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        mManage = new AccountRecordingManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        rvList = (RecyclerView) findViewById(R.id.rv_recording_list);
        tvDefault = (TextView) findViewById(R.id.tv_default);
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
    public void defaultText(boolean showed) {
        if (showed) {
            tvDefault.setVisibility(View.VISIBLE);
        } else {
            tvDefault.setVisibility(View.GONE);
        }
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
    public void showList(ArrayList<AccountRecordingBean> list) {
        if (null == arla) {
            arla = new AccountRecordingListAdapter(this, list);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                            mManage.getRecordingData();
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
            rvList.setLayoutManager(layoutManager);
            rvList.setAdapter(arla);
        } else {
            arla.refreshData(list);
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
}
