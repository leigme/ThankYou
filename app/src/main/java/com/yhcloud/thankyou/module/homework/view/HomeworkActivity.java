package com.yhcloud.thankyou.module.homework.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mabstract.ABaseActivity;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.homework.adapter.StudentHomeworkListAdapter;
import com.yhcloud.thankyou.module.homework.adapter.TeacherHomeworkListAdapter;
import com.yhcloud.thankyou.module.homework.bean.StudentHomeworkBean;
import com.yhcloud.thankyou.module.homework.bean.TeacherHomeworkBean;
import com.yhcloud.thankyou.module.homework.manage.HomeworkManage;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

public class HomeworkActivity extends ABaseActivity implements IHomeworkActivityView {

    private String TAG = getClass().getSimpleName();

    //视图控件
    private LinearLayout llBack, llRight;
    private TextView tvTitle;
    private ImageView ivRight;
    private SwipeRefreshLayout srlHomeworkList;
    private RecyclerView rvHomeworkList;
    //适配器
    private TeacherHomeworkListAdapter thla;
    private StudentHomeworkListAdapter shla;
    //管理器
    private HomeworkManage mManage;
    private boolean canGetMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        mManage = new HomeworkManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        srlHomeworkList = (SwipeRefreshLayout) findViewById(R.id.srl_homework_list);
        rvHomeworkList = (RecyclerView) findViewById(R.id.rv_homework_list);
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
        srlHomeworkList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mManage.refreshData();
            }
        });
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
    public void showRight() {
        llRight = (LinearLayout) findViewById(R.id.ll_header_right);
        llRight.setVisibility(View.VISIBLE);
        llRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManage.goAddTeacherHomework();
            }
        });
    }

    @Override
    public void showTeacherHomeworkList(ArrayList<TeacherHomeworkBean> list) {
        if (null == thla) {
            thla = new TeacherHomeworkListAdapter(this, list);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvHomeworkList.setLayoutManager(layoutManager);
            rvHomeworkList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    super.onScrolled(recyclerView, dx, dy);
                    if (0 < dy) {
                        canGetMore = true;
                    } else {
                        canGetMore = false;
                    }
                }
            });
            rvHomeworkList.setAdapter(thla);
            thla.setIOnClickListener(new IOnClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    mManage.goHomeworkInfo(position);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
        } else {
            thla.refreshData(list);
        }
    }

    @Override
    public void showStudentHomeworkList(ArrayList<StudentHomeworkBean> list) {
        if (null == shla) {
            shla = new StudentHomeworkListAdapter(this, list);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvHomeworkList.setLayoutManager(layoutManager);
            rvHomeworkList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    super.onScrolled(recyclerView, dx, dy);
                    if (0 < dy) {
                        canGetMore = true;
                    } else {
                        canGetMore = false;
                    }
                }
            });
            rvHomeworkList.setAdapter(shla);
            shla.setIOnClickListener(new IOnClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    mManage.goHomeworkInfo(position);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
        } else {
            shla.refreshData(list);
        }
    }

    @Override
    public void completeRefresh() {
        srlHomeworkList.setRefreshing(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tools.print(TAG, "返回列表页面了");
        mManage.refreshData();
    }
}
