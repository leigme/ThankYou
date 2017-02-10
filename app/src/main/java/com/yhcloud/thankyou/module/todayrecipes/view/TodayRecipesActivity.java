package com.yhcloud.thankyou.module.todayrecipes.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mAbstract.ABaseActivity;
import com.yhcloud.thankyou.module.todayrecipes.adapter.TodayRecipesPagerAdapter;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesPagerBean;
import com.yhcloud.thankyou.module.todayrecipes.manage.TodayRecipesManage;

import java.util.ArrayList;

public class TodayRecipesActivity extends ABaseActivity implements ITodayRecipesView {

    //视图控件
    private LinearLayout llBack;
    private TextView tvTitle;
    private TabLayout tlDayTitle;
    private ViewPager vpDay;
    //适配器
    private TodayRecipesPagerAdapter mPagerAdapter;
    //管理器
    private TodayRecipesManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_recipes);
        mManage = new TodayRecipesManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        tlDayTitle = (TabLayout) findViewById(R.id.tl_todayrecipes_title);
        vpDay = (ViewPager) findViewById(R.id.vp_todayrecipes_page);
    }

    @Override
    public void initEvent() {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
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
    public void showPager(ArrayList<TodayRecipesPagerBean> list) {
        if (null == mPagerAdapter) {
            mPagerAdapter = new TodayRecipesPagerAdapter(list);
            vpDay.setAdapter(mPagerAdapter);
            tlDayTitle.setupWithViewPager(vpDay);
            tlDayTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    vpDay.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } else {
            mPagerAdapter.refreshData(list);
        }
    }
}
