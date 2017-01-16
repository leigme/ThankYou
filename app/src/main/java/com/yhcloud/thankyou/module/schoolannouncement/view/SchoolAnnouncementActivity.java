package com.yhcloud.thankyou.module.schoolannouncement.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.schoolannouncement.adapter.SchoolAnnouncementListAdapter;
import com.yhcloud.thankyou.module.schoolannouncement.bean.SchoolAnnouncementBean;
import com.yhcloud.thankyou.module.schoolannouncement.manage.SchoolAnnouncementManage;
import com.yhcloud.thankyou.utils.DividerItemDecoration;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.utils.myview.MyToast;

import java.util.ArrayList;

public class SchoolAnnouncementActivity extends AppCompatActivity implements ISchoolAnnouncementView {

    //视图控件
    private LinearLayout llBack;
    private TextView tvTitle;
    private RecyclerView rvSchoolAnnouncementList;
    private ProgressDialog mProgressDialog;
    //适配器
    private SchoolAnnouncementListAdapter sala;
    //管理器
    private SchoolAnnouncementManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_announcement);
        mManage = new SchoolAnnouncementManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        rvSchoolAnnouncementList = (RecyclerView) findViewById(R.id.rv_schoolannouncement_list);
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
        tvTitle.setText(title);
    }

    @Override
    public void setRightTitle(String title) {

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
    public void showList(final ArrayList<SchoolAnnouncementBean> list) {
        if (null == sala) {
            sala = new SchoolAnnouncementListAdapter(this, list);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvSchoolAnnouncementList.setLayoutManager(layoutManager);
            rvSchoolAnnouncementList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                            mManage.getMoreData();
                        }
                    }
                }
            });
            rvSchoolAnnouncementList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            sala.setIOnClickListener(new IOnClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    mManage.goSchoolAnnouncementDetail(position);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            rvSchoolAnnouncementList.setAdapter(sala);
        } else {
            sala.refreshData(list);
        }
    }
}
