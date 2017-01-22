package com.yhcloud.thankyou.module.dutystudent.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.TeacherBean;
import com.yhcloud.thankyou.mAbstract.ABaseActivity;
import com.yhcloud.thankyou.module.dutystudent.adapter.DutyStudentListAdapter;
import com.yhcloud.thankyou.module.dutystudent.manage.DutyStudentManage;
import com.yhcloud.thankyou.utils.DividerItemDecoration;

import java.util.ArrayList;

public class DutyStudentActivity extends ABaseActivity implements IDutyStudentActivityView {

    //视图控件
    private LinearLayout llBack;
    private TextView tvTitle, tvDefault;
    private RecyclerView rvDutyStudentList;
    //适配器
    private DutyStudentListAdapter dsla;
    //管理器
    private DutyStudentManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_student);
        mManage = new DutyStudentManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        rvDutyStudentList = (RecyclerView) findViewById(R.id.rv_duty_student_list);
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
    public void showDefault(boolean showed) {
        if (showed) {
            rvDutyStudentList.setVisibility(View.INVISIBLE);
            tvDefault.setVisibility(View.VISIBLE);
        } else {
            rvDutyStudentList.setVisibility(View.VISIBLE);
            tvDefault.setVisibility(View.GONE);
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
    public void showList(ArrayList<TeacherBean> list) {
        if (null == dsla) {
            dsla = new DutyStudentListAdapter(this, list);
            rvDutyStudentList.setLayoutManager(new LinearLayoutManager(this));
            rvDutyStudentList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            rvDutyStudentList.setAdapter(dsla);
        } else {
            dsla.refreshData(list);
        }
    }
}
