package com.yhcloud.thankyou.module.todayrecipes.view;

import com.yhcloud.thankyou.bean.TermBean;
import com.yhcloud.thankyou.minterface.BaseActivityView;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesPagerBean;
import com.yhcloud.thankyou.module.todayrecipes.bean.WeekBean;

import java.util.ArrayList;

/**
 * Created by leig on 2017/2/9.
 */

public interface TodayRecipesView extends BaseActivityView {
    void showPager(ArrayList<TodayRecipesPagerBean> list);
    void cleanPagerView();
    void setTime(String time);
    void initOptionsPickerView(ArrayList<TermBean> termBeen, ArrayList<ArrayList<WeekBean>> weekBeen);
    void showOptionsPickerView(int x, int y);
}
