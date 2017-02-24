package com.yhcloud.thankyou.module.propslist.view;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mabstract.ABaseActivity;
import com.yhcloud.thankyou.module.propslist.adapter.PropsListViewAdapter;
import com.yhcloud.thankyou.module.propslist.bean.PropsListViewBean;
import com.yhcloud.thankyou.module.propslist.manage.PropsListManage;

import java.util.ArrayList;

public class PropsListActivity extends ABaseActivity implements IPropsListActivityView {

    //视图控件
    private LinearLayout llBack;
    private TextView tvTitle;
    private TabLayout tlTitle;
    private ViewPager vpListPage;
    private ProgressDialog mProgressDialog;
    //管理器
    private PropsListManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_props_list);
        mManage = new PropsListManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        tlTitle = (TabLayout) findViewById(R.id.tl_propslist_title);
        vpListPage = (ViewPager) findViewById(R.id.vp_propslist_page);
    }

    @Override
    public void initEvent() {
        View.OnClickListener myOnClickListerner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_header_left:
                        finish();
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListerner);
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
    public void initViewPage(ArrayList<PropsListViewBean> list) {
        PropsListViewAdapter adapter = new PropsListViewAdapter(list);
        vpListPage.setAdapter(adapter);
        tlTitle.setupWithViewPager(vpListPage);
        tlTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpListPage.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
