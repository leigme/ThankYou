package com.yhcloud.thankyou.module.classnotification.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.BaseActivity;
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.module.classnotification.adapter.ClassNotificationListAdapter;
import com.yhcloud.thankyou.module.classnotification.bean.ClassNotificationBean;
import com.yhcloud.thankyou.module.classnotification.manage.ClassNotificationManage;
import com.yhcloud.thankyou.utils.DividerItemDecoration;

import java.util.ArrayList;

public class ClassNotificationActivity extends BaseActivity implements ClassNotificationActivityView {

    private String TAG = getClass().getSimpleName();

    //视图控件
    private LinearLayout llBack, llMenu;
    private TextView tvTitle, tvMenu;
    private RecyclerView rvList;
    private SwipeRefreshLayout srlList;
    //适配器
    private ClassNotificationListAdapter cnla;
    //管理器
    private ClassNotificationManage mManage;
    private boolean canGetMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_notification);
        mManage = new ClassNotificationManage(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        srlList = (SwipeRefreshLayout) findViewById(R.id.srl_classnotification_list);
        rvList = (RecyclerView) findViewById(R.id.rv_classnotification_list);
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
        SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mManage.refreshList();
            }
        };
        srlList.setOnRefreshListener(mOnRefreshListener);
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
        tvMenu.setText(title);
    }

    @Override
    public void showRightMenu() {
        llMenu = (LinearLayout) findViewById(R.id.ll_header_right);
        llMenu.setVisibility(View.VISIBLE);
        tvMenu = (TextView) findViewById(R.id.tv_header_right);
        llMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManage.goAddClassNotification();
            }
        });
    }

    @Override
    public void showList(final ArrayList<ClassNotificationBean> list) {
        if (null == cnla) {
            cnla = new ClassNotificationListAdapter(this, list);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvList.setLayoutManager(layoutManager);
            rvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
            cnla.setIOnClickListener(new ItemClinkListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    mManage.goClassNotificationDetail(position);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            rvList.setAdapter(cnla);
        } else {
            cnla.refreshData(list);
        }
    }

    @Override
    public void completeRefreshList() {
        if (null != srlList) {
            srlList.setRefreshing(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<ClassNotificationBean> list = (ArrayList<ClassNotificationBean>) data.getSerializableExtra("list");
        mManage.refreshData(list);
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
