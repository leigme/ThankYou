package com.yhcloud.thankyou.module.classcadre.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.TeacherBean;
import com.yhcloud.thankyou.comm.BaseActivity;
import com.yhcloud.thankyou.module.classcadre.adapter.ClassCadreListAdpater;
import com.yhcloud.thankyou.module.classcadre.manage.ClassCadreManage;
import com.yhcloud.thankyou.utils.DividerItemDecoration;

import java.util.ArrayList;

public class ClassCadreActivity extends BaseActivity implements ClassCadreActivityView {

    //视图控件
    private LinearLayout llBack;
    private TextView tvTitle, tvDefault;
    private RecyclerView rvClassCadreList;
    //适配器
    private ClassCadreListAdpater ccla;
    //管理器
    private ClassCadreManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_cadre);
        mManage = new ClassCadreManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        rvClassCadreList = (RecyclerView) findViewById(R.id.rv_class_cadre_list);
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
            tvDefault.setVisibility(View.VISIBLE);
            rvClassCadreList.setVisibility(View.INVISIBLE);
        } else {
            tvDefault.setVisibility(View.GONE);
            rvClassCadreList.setVisibility(View.VISIBLE);
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
        if (null == ccla) {
            ccla = new ClassCadreListAdpater(this, list);
            rvClassCadreList.setLayoutManager(new LinearLayoutManager(this));
            rvClassCadreList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            rvClassCadreList.setAdapter(ccla);
        } else {
            ccla.refreshData(list);
        }
    }
}
