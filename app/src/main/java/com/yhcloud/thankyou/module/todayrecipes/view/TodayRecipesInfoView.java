package com.yhcloud.thankyou.module.todayrecipes.view;

import com.yhcloud.thankyou.minterface.BaseActivityView;

/**
 * Created by leig on 2017/2/10.
 */

public interface TodayRecipesInfoView extends BaseActivityView {
    void setImage(String imageUrl);
    void setDishInfo(String dishInfo);
}
