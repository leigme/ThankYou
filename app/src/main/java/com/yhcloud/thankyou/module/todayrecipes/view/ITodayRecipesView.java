package com.yhcloud.thankyou.module.todayrecipes.view;

import com.yhcloud.thankyou.mInterface.IBaseActivityView;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesPagerBean;

import java.util.ArrayList;

/**
 * Created by leig on 2017/2/9.
 */

public interface ITodayRecipesView extends IBaseActivityView {
    void showPager(ArrayList<TodayRecipesPagerBean> list);
}
