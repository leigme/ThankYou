package com.yhcloud.thankyou.module.schoolannouncement.view;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mAbstract.ABaseActivity;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.schoolannouncement.adapter.SchoolAnnouncementListAdapter;
import com.yhcloud.thankyou.module.schoolannouncement.bean.SchoolAnnouncementBean;
import com.yhcloud.thankyou.module.schoolannouncement.manage.SchoolAnnouncementManage;
import com.yhcloud.thankyou.utils.DividerItemDecoration;
import com.yhcloud.thankyou.utils.myview.MyToast;

import java.util.ArrayList;

public class SchoolAnnouncementActivity extends ABaseActivity implements ISchoolAnnouncementActivityView {

    //视图控件
    private LinearLayout llBack;
    private TextView tvTitle;
    private SwipeRefreshLayout srlSchoolAnnouncementList;
    private RecyclerView rvSchoolAnnouncementList;
    private ProgressDialog mProgressDialog;
    //适配器
    private SchoolAnnouncementListAdapter sala;
    //管理器
    private SchoolAnnouncementManage mManage;
    private boolean canGetMore;

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
        srlSchoolAnnouncementList = (SwipeRefreshLayout) findViewById(R.id.srl_schoolannouncement_list);
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
        SwipeRefreshLayout.OnRefreshListener myOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mManage.refreshData();
            }
        };
        srlSchoolAnnouncementList.setOnRefreshListener(myOnRefreshListener);
    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void showLoading(int msgId) {
        hiddenLoading();
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
    public void completeRefreshList() {
        if (null != srlSchoolAnnouncementList) {
            srlSchoolAnnouncementList.setRefreshing(false);
        }
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
                            if (canGetMore) {
                                mManage.getMoreData();
                            }
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (0 < dy) {
                        canGetMore = true;
                    } else {
                        canGetMore = false;
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
