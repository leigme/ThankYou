package com.yhcloud.thankyou.module.classteachers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mAbstract.ABaseActivity;
import com.yhcloud.thankyou.module.classteachers.adapter.TeacherListAdapter;
import com.yhcloud.thankyou.bean.TeacherBean;
import com.yhcloud.thankyou.module.classteachers.manage.ClassTeacherListManage;
import com.yhcloud.thankyou.utils.DividerItemDecoration;

import java.util.ArrayList;

public class ClassTeacherListActivity extends ABaseActivity implements IClassTeacherListActivityView {

    //视图控件
    private LinearLayout llBack;
    private TextView tvTitle;
    private RecyclerView rvTeacherList;
    //适配器
    private TeacherListAdapter tla;
    //管理器
    private ClassTeacherListManage mManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teacher_list);
        mManage = new ClassTeacherListManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        rvTeacherList = (RecyclerView) findViewById(R.id.rv_class_teacher_list);
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
    public void showList(ArrayList<TeacherBean> list) {
        if (null == tla) {
            tla = new TeacherListAdapter(this, list);
            rvTeacherList.setLayoutManager(new LinearLayoutManager(this));
            rvTeacherList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            rvTeacherList.setAdapter(tla);
        } else {
            tla.refreshData(list);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mManage.getClassTeacherList();
    }
}
