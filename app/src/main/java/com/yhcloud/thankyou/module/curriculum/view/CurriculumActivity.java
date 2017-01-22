package com.yhcloud.thankyou.module.curriculum.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mAbstract.ABaseActivity;
import com.yhcloud.thankyou.module.curriculum.adapter.CurriculumAdapter;
import com.yhcloud.thankyou.module.curriculum.bean.CurriculumItemBean;
import com.yhcloud.thankyou.module.curriculum.manage.CurriculumManage;
import com.yhcloud.thankyou.utils.DividerGridItemDecoration;

import java.util.ArrayList;

public class CurriculumActivity extends ABaseActivity implements ICurriculumActivityView {

    //视图控件
    private LinearLayout llBack;
    private TextView tvTitle, tvDefault;
    private ViewStub vsDefault;
    private RecyclerView rvCurriculumList;
    private CurriculumAdapter ca;
    //管理器
    private CurriculumManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);
        mManage = new CurriculumManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        rvCurriculumList = (RecyclerView) findViewById(R.id.rv_curriculum_list);
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
    public void showList(final ArrayList<CurriculumItemBean> list) {
        if (null == ca) {
            ca = new CurriculumAdapter(this, list);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 6);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (2 == list.get(position).getType()) {
                        return 6;
                    }
                    return 1;
                }
            });
            rvCurriculumList.setLayoutManager(layoutManager);
            rvCurriculumList.addItemDecoration(new DividerGridItemDecoration(this));
            rvCurriculumList.setAdapter(ca);
        }
    }

    @Override
    public void initViewStub(String msg) {
        vsDefault = (ViewStub) findViewById(R.id.vs_default);
        vsDefault.inflate();
        if (!"".equals(msg)) {
            tvDefault = (TextView) findViewById(R.id.tv_viewstub_default);
            tvDefault.setText(msg);
        }
    }
}
