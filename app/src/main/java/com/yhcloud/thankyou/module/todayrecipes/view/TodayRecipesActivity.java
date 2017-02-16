package com.yhcloud.thankyou.module.todayrecipes.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.TermBean;
import com.yhcloud.thankyou.mAbstract.ABaseActivity;
import com.yhcloud.thankyou.module.todayrecipes.adapter.TodayRecipesPagerAdapter;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesPagerBean;
import com.yhcloud.thankyou.module.todayrecipes.bean.WeekBean;
import com.yhcloud.thankyou.module.todayrecipes.manage.TodayRecipesManage;
import com.yhcloud.thankyou.utils.myview.pickerview.OptionsPickerView;

import java.text.MessageFormat;
import java.util.ArrayList;

public class TodayRecipesActivity extends ABaseActivity implements ITodayRecipesView {

    private String TAG = getClass().getSimpleName();

    //视图控件
    private LinearLayout llBack, llTime;
    private TextView tvTitle, tvTime;
    private TabLayout tlDayTitle;
    private ViewPager vpDay;
    private OptionsPickerView opvTime;
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
        llTime = (LinearLayout) findViewById(R.id.ll_todayrecipes_time);
        tvTime = (TextView) findViewById(R.id.tv_todayrecipes_time);
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
                    case R.id.ll_todayrecipes_time:
                        if (null != opvTime) {
                            mManage.showOptionsPickerView();
                        }
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
        llTime.setOnClickListener(myOnClickListener);
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

    @Override
    public void cleanPagerView() {
        vpDay.removeAllViews();
    }

    @Override
    public void setTime(String time) {
        tvTime.setText(time);
    }

    @Override
    public void initOptionsPickerView(final ArrayList<TermBean> termBeen, final ArrayList<ArrayList<WeekBean>> weekBeen) {
        if (null == opvTime) {
            opvTime = new OptionsPickerView(this);
            opvTime.setPicker(termBeen, weekBeen, true);
            opvTime.setCyclic(false);
            opvTime.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3) {
                    mManage.getRecipesData(termBeen.get(options1).getId(), weekBeen.get(options1).get(options2).getId());
                    mManage.setTermNum(options1);
                    mManage.setWeekNum(options2);
                    setTime(MessageFormat.format("{0} {1}",termBeen.get(options1).getTitle(), weekBeen.get(options1).get(options2).getTitle()));
                }
            });
        }
    }

    @Override
    public void showOptionsPickerView(int x, int y) {
        opvTime.setSelectOptions(x, y);
        opvTime.show();
    }
}
