package com.yhcloud.thankyou.module.todayrecipes.view;

import com.yhcloud.thankyou.minterface.IBaseActivityView;

/**
 * Created by leig on 2017/2/10.
 */

public interface ITodayRecipesInfoView extends IBaseActivityView {
    void setImage(String imageUrl);
    void setDishInfo(String dishInfo);
}
